/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.CategoriaConta;
import Models.Receita;
import Models.SubCategoria;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class ReceitaDAO {
    private final Contexto contexto = new Contexto();
    
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException{
        String query = "select * from movimentacao;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];
        
        for(int i = 1; i <= fields.getColumnCount(); i++){
            columns[i-1] = fields.getColumnName(i);
            System.out.println(columns[i-1]);
        }
        
        return columns;
    }
    
    public boolean create(Receita receita) throws ClassNotFoundException, SQLException{
        String sql = "insert into movimentacao(Descricao, dataOcorrencia, valor, formaPagamento)values(?, ?, ?, ?)";
        try(PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql)) {

                preparestatement.setString(1, receita.getDescricao()); //substitui o ? pelo dado do usuario
                preparestatement.setDate(2, (Date) receita.getDataOcorrencia());
                preparestatement.setDouble(1, receita.getValor()); //substitui o ? pelo dado do usuario
                preparestatement.setInt(2, receita.getFormaPagamento());   
                //executando comando sql

                return preparestatement.execute();
        } catch (SQLException e) { throw e; }
    }
    
    public Receita get(int id) throws ClassNotFoundException, SQLException{
        String query = "select * from movimentacao where subcategoriaid in "
                + "(select subcategoriaid from subcategoria where subcategoriaid in "
                    + "(select categoriacontaid from categoriaconta where positiva = 1)) "
                + "AND movimentacaoid = '"+ id +"';";
        Receita receita = new Receita();
        ResultSet dados = contexto.executeQuery(query);

        while(dados.next()){
            receita.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            receita.setSubCategoriaID(dados.getInt("SubCategoriaID"));
            receita.setDescricao(dados.getString("Descricao"));
            receita.setDataOcorrencia(dados.getDate("dataOcorrencia"));
            receita.setValor(dados.getDouble("valor"));
            receita.setFormaPagamento(dados.getInt("formaPagamento"));
            
            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + receita.getSubCategoriaID() 
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);
            
            while(dadosSub.next()){
                receita.setSubcategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getInt("CategoriaContaID"),
                                dadosSub.getString("Descricao"))
                );
            }
            
            String queryCat = "select * from categoriaConta where categoriaContaid = '"
                + receita.getSubcategoria().getCategoriaContaID()
                +"';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while(dadosCat.next()){
                receita.getSubcategoria().setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"), 
                                dadosCat.getBoolean("positiva"))
                );
            }
            
            System.out.println(receita.toString());
        }
        
        return receita;
    }
    
    public ArrayList<Receita> getAll() throws ClassNotFoundException, SQLException{
        String query = "select * from movimentacao where subcategoriaid in "
                + "(select subcategoriaid from subcategoria where subcategoriaid in "
                    + "(select categoriacontaid from categoriaconta where positiva = 1));";
        ArrayList<Receita> list = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while(dados.next()){
            Receita receita = new Receita();
            receita.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            receita.setSubCategoriaID(dados.getInt("SubCategoriaID"));
            receita.setDescricao(dados.getString("Descricao"));
            receita.setDataOcorrencia(dados.getDate("dataOcorrencia"));
            receita.setValor(dados.getDouble("valor"));
            receita.setFormaPagamento(dados.getInt("formaPagamento"));
            
            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + receita.getSubCategoriaID() 
                    +"';";
            ResultSet dadosSub = contexto.executeQuery(querySub);
            
            while(dadosSub.next()){
                receita.setSubcategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getInt("CategoriaContaID"),
                                dadosSub.getString("Descricao"))
                );
            }
            
            String queryCat = "select * from categoriaConta where categoriaContaid = '"
                + receita.getSubcategoria().getCategoriaContaID()
                +"';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while(dadosCat.next()){
                receita.getSubcategoria().setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"), 
                                dadosCat.getBoolean("positiva"))
                );
            }
            
            System.out.println(receita.toString());
            
            list.add(receita);
        }

        return list;
    }
    
    public boolean update(Receita receita) throws ClassNotFoundException, SQLException{
        StringBuilder columnsAndValues = new StringBuilder(255);
        
        columnsAndValues.append("descricao= '")
                .append(receita.getDescricao())
                .append("'");
        columnsAndValues.append("dataOcorrencia= '")
                .append(receita.getDataOcorrencia())
                .append("'");
        columnsAndValues.append("valor= '")
                .append(receita.getValor())
                .append("'");
        columnsAndValues.append("formaPagamento= '")
                .append(receita.getFormaPagamento())
                .append("'");
        
        String query = "update movimentacao SET " 
                + columnsAndValues.toString() 
                + " WHERE movimentacaoID = " + receita.getMovimentacaoID();
        
        int result = contexto.executeUpdate(query);

        return result > 0;
    }
    
    public boolean delete(int id) throws ClassNotFoundException, SQLException{
        String sql = "delete from movimentacao where movimentacaoID = ?";
        try(PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)){
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
        }catch(SQLException e){ return false; }
        
        return true;
    }
}
