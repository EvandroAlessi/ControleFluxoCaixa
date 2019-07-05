/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.CategoriaConta;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class CategoriaContaDAO {
    private final Contexto contexto = new Contexto();
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException{
        String query = "select * from categoriaconta;";
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
     * @param categoria
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean create(CategoriaConta categoria) throws SQLException, ClassNotFoundException{
        String sql = "insert into categoriaconta(Descricao, Positiva)values(?,?)";
        System.out.println(contexto.getConexao());
        try(PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql)) {
                preparestatement.setString(1, categoria.getDescricao()); //substitui o ? pelo dado do usuario
                preparestatement.setBoolean(2, categoria.isPositiva());

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
    public CategoriaConta get(int id) throws ClassNotFoundException, SQLException{
        String query = "select * from categoriaconta where categoriacontaid = '"+ id +"';";
        CategoriaConta categoria = new CategoriaConta();
        ResultSet dados = contexto.executeQuery(query);

        while(dados.next()){
            categoria.setDescricao(dados.getString("Descricao"));
            categoria.setCategoriaContaID(dados.getInt("CategoriaContaID"));
            categoria.setPositiva(dados.getBoolean("positiva"));
            
            System.out.println(categoria.toString());
        }

        return categoria;
    }
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<CategoriaConta> getAll() throws SQLException, ClassNotFoundException{
        String query = "select * from categoriaconta;";
        ArrayList<CategoriaConta> lista = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while(dados.next()){
            CategoriaConta categoria = new CategoriaConta();
            categoria.setDescricao(dados.getString("Descricao"));
            categoria.setCategoriaContaID(dados.getInt("CategoriaContaID"));
            categoria.setPositiva(dados.getBoolean("positiva"));
            
            System.out.println(categoria.toString());
            
            lista.add(categoria);
        }

        return lista;
    }
    
    /**
     *
     * @param categoria
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(CategoriaConta categoria) throws ClassNotFoundException, SQLException{
        StringBuilder columnsAndValues = new StringBuilder(255);
        
        columnsAndValues.append("descricao= '")
                .append(categoria.getDescricao())
                .append("'");
        columnsAndValues.append("positiva= '")
                .append(categoria.isPositiva())
                .append("'");
        
        String query = "update categoriaconta SET " 
                + columnsAndValues.toString() 
                + " WHERE categoriacontaid = " + categoria.getCategoriaContaID();
        
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
        String sql = "delete from categoriaconta where categoriacontaid = ?";
        try(PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)){
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
        }catch(SQLException e){ return false; }
        
        return true;
    }
}
