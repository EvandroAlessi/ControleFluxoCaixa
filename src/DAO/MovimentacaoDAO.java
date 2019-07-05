/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.CategoriaConta;
import Models.Movimentacao;
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
public class MovimentacaoDAO {
    private final Contexto contexto = new Contexto();
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    
    /**
     *
     * @param movimentacao
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(Movimentacao movimentacao) throws ClassNotFoundException, SQLException{
        String sql = "insert into movimentacao(Descricao, dataOcorrencia, valor, formaPagamento)values(?, ?, ?, ?)";
        try(PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql)) {

                preparestatement.setString(1, movimentacao.getDescricao()); //substitui o ? pelo dado do usuario
                preparestatement.setDate(2, (Date) movimentacao.getDataOcorrencia());
                preparestatement.setDouble(1, movimentacao.getValor()); //substitui o ? pelo dado do usuario
                preparestatement.setInt(2, movimentacao.getFormaPagamento());   
                //executando comando sql

                return preparestatement.execute();
        } catch (SQLException e) { throw e; }
        
        
    }
    
    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Movimentacao get(int id) throws ClassNotFoundException, SQLException{
        String query = "select * from movimentacao where movimentacaoid = '"+ id +"';";
        Movimentacao movimentacao = new Movimentacao();
        ResultSet dados = contexto.executeQuery(query);

        while(dados.next()){
            movimentacao.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            movimentacao.setSubCategoriaID(dados.getInt("SubCategoriaID"));
            movimentacao.setDescricao(dados.getString("Descricao"));
            movimentacao.setDataOcorrencia(dados.getDate("dataOcorrencia"));
            movimentacao.setValor(dados.getDouble("valor"));
            movimentacao.setFormaPagamento(dados.getInt("formaPagamento"));
            
            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + movimentacao.getSubCategoriaID() 
                    +"';";
            ResultSet dadosSub = contexto.executeQuery(querySub);
            
            while(dadosSub.next()){
                movimentacao.setSubcategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getInt("CategoriaContaID"),
                                dadosSub.getString("Descricao"))
                );
            }
            
            String queryCat = "select * from categoriaConta where categoriaContaid = '"
                + movimentacao.getSubcategoria().getCategoriaContaID()
                +"';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while(dadosCat.next()){
                movimentacao.getSubcategoria().setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"), 
                                dadosCat.getBoolean("positiva"))
                );
            }
            
            System.out.println(movimentacao.toString());
        }

        return movimentacao;
    }
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Movimentacao> getAll() throws ClassNotFoundException, SQLException{
        String query = "select * from movimentacao;";
        ArrayList<Movimentacao> list = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while(dados.next()){
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            movimentacao.setSubCategoriaID(dados.getInt("SubCategoriaID"));
            movimentacao.setDescricao(dados.getString("Descricao"));
            movimentacao.setDataOcorrencia(dados.getDate("dataOcorrencia"));
            movimentacao.setValor(dados.getDouble("valor"));
            movimentacao.setFormaPagamento(dados.getInt("formaPagamento"));
            
            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + movimentacao.getSubCategoriaID() 
                    +"';";
            ResultSet dadosSub = contexto.executeQuery(querySub);
            
            while(dadosSub.next()){
                movimentacao.setSubcategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getInt("CategoriaContaID"),
                                dadosSub.getString("Descricao"))
                );
            }
            
            String queryCat = "select * from categoriaConta where categoriaContaid = '"
                + movimentacao.getSubcategoria().getCategoriaContaID()
                +"';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while(dadosCat.next()){
                movimentacao.getSubcategoria().setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"), 
                                dadosCat.getBoolean("positiva"))
                );
            }
            
            System.out.println(movimentacao.toString());
            
            list.add(movimentacao);
        }

        return list;
    }
    
    /**
     *
     * @param movimentacao
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Movimentacao movimentacao) throws ClassNotFoundException, SQLException{
        StringBuilder columnsAndValues = new StringBuilder(255);
        
        columnsAndValues.append("descricao= '")
                .append(movimentacao.getDescricao())
                .append("'");
        columnsAndValues.append("dataOcorrencia= '")
                .append(movimentacao.getDataOcorrencia())
                .append("'");
        columnsAndValues.append("valor= '")
                .append(movimentacao.getValor())
                .append("'");
        columnsAndValues.append("formaPagamento= '")
                .append(movimentacao.getFormaPagamento())
                .append("'");
        
        String query = "update movimentacao SET " 
                + columnsAndValues.toString() 
                + " WHERE movimentacaoID = " + movimentacao.getMovimentacaoID();
        
        int result = contexto.executeUpdate(query);

        return result > 0;
    }
    
    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean delete(int id) throws ClassNotFoundException, SQLException{
        String sql = "delete from movimentacao where movimentacaoID = ?";
        try(PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)){
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
        }catch(SQLException e){ return false; }
        
        return true;
    }
}
