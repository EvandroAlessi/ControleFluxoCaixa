/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.CategoriaConta;
import Models.SubCategoria;
import Models.Despesa;
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
public class DespesaDAO {
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
    
    public boolean create(Despesa despesa) throws ClassNotFoundException, SQLException{
        String sql = "insert into movimentacao(Descricao, dataOcorrencia, valor, formaPagamento)values(?, ?, ?, ?)";
        try(PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql)) {

                preparestatement.setString(1, despesa.getDescricao()); //substitui o ? pelo dado do usuario
                preparestatement.setDate(2, (Date) despesa.getDataOcorrencia());
                preparestatement.setDouble(1, despesa.getValor()); //substitui o ? pelo dado do usuario
                preparestatement.setInt(2, despesa.getFormaPagamento());   
                //executando comando sql

                return preparestatement.execute();
        } catch (SQLException e) { throw e; }
    }
    
    public Despesa get(int id) throws ClassNotFoundException, SQLException{
        String query = "select * from movimentacao where subcategoriaid in "
                + "(select subcategoriaid from subcategoria where subcategoriaid in "
                    + "(select categoriacontaid from categoriaconta where positiva = 0)) "
                + "AND movimentacaoid = '"+ id +"';";
        Despesa despesa = new Despesa();
        ResultSet dados = contexto.executeQuery(query);

        while(dados.next()){
            despesa.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            despesa.setSubCategoriaID(dados.getInt("SubCategoriaID"));
            despesa.setDescricao(dados.getString("Descricao"));
            despesa.setDataOcorrencia(dados.getDate("dataOcorrencia"));
            despesa.setValor(dados.getDouble("valor"));
            despesa.setFormaPagamento(dados.getInt("formaPagamento"));
            
            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + despesa.getSubCategoriaID() 
                    +"';";
            ResultSet dadosSub = contexto.executeQuery(querySub);
            
            while(dadosSub.next()){
                despesa.setSubcategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getInt("CategoriaContaID"),
                                dadosSub.getString("Descricao"))
                );
            }
            
            String queryCat = "select * from categoriaConta where categoriaContaid = '"
                + despesa.getSubcategoria().getCategoriaContaID()
                +"';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while(dadosCat.next()){
                despesa.getSubcategoria().setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"), 
                                dadosCat.getBoolean("positiva"))
                );
            }
            
            System.out.println(despesa.toString());
        }
        
        return despesa;
    }
    
    public ArrayList<Despesa> getAll() throws ClassNotFoundException, SQLException{
        String query = "select * from movimentacao where subcategoriaid in "
                + "(select subcategoriaid from subcategoria where subcategoriaid in "
                    + "(select categoriacontaid from categoriaconta where positiva = 0));";
        ArrayList<Despesa> list = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while(dados.next()){
            Despesa despesa = new Despesa();
            despesa.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            despesa.setSubCategoriaID(dados.getInt("SubCategoriaID"));
            despesa.setDescricao(dados.getString("Descricao"));
            despesa.setDataOcorrencia(dados.getDate("dataOcorrencia"));
            despesa.setValor(dados.getDouble("valor"));
            despesa.setFormaPagamento(dados.getInt("formaPagamento"));
            
            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + despesa.getSubCategoriaID() 
                    +"';";
            ResultSet dadosSub = contexto.executeQuery(querySub);
            
            while(dadosSub.next()){
                despesa.setSubcategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getInt("CategoriaContaID"),
                                dadosSub.getString("Descricao"))
                );
            }
            
            String queryCat = "select * from categoriaConta where categoriaContaid = '"
                + despesa.getSubcategoria().getCategoriaContaID()
                +"';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while(dadosCat.next()){
                despesa.getSubcategoria().setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"), 
                                dadosCat.getBoolean("positiva"))
                );
            }
            
            System.out.println(despesa.toString());
            
            list.add(despesa);
        }

        return list;
    }
    
    public boolean update(Despesa despesa) throws ClassNotFoundException, SQLException{
        StringBuilder columnsAndValues = new StringBuilder(255);
        
        columnsAndValues.append("descricao= '")
                .append(despesa.getDescricao())
                .append("'");
        columnsAndValues.append("dataOcorrencia= '")
                .append(despesa.getDataOcorrencia())
                .append("'");
        columnsAndValues.append("valor= '")
                .append(despesa.getValor())
                .append("'");
        columnsAndValues.append("formaPagamento= '")
                .append(despesa.getFormaPagamento())
                .append("'");
        
        String query = "update movimentacao SET " 
                + columnsAndValues.toString() 
                + " WHERE movimentacaoID = " + despesa.getMovimentacaoID();
        
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
