/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Models.SubCategoria;
import Models.CategoriaConta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class SubCategoriaDAO {
    private final Contexto contexto = new Contexto();
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException{
        String query = "select * from subCategoria;";
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
     * @param subCategoria
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(SubCategoria subCategoria) throws ClassNotFoundException, SQLException{
        String sql = "insert into subCategoria(descricao)values(?)";
        try(PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql)) {

                preparestatement.setString(1, subCategoria.getDescricao()); //substitui o ? pelo dado do usuario

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
    public SubCategoria get(int id) throws ClassNotFoundException, SQLException{
        System.out.println("1");
        String query = "select * from subCategoria where subCategoriaid = '"+ id +"';";
        SubCategoria subCategoria = new SubCategoria();

        ResultSet dadosSub = contexto.executeQuery(query);
        
        while(dadosSub.next()){
            subCategoria.setSubCategoriaID(dadosSub.getInt("subCategoriaID"));
            subCategoria.setCategoriaContaID(dadosSub.getInt("CategoriaContaID"));
            subCategoria.setDescricao(dadosSub.getString("Descricao"));
            
            String queryCat = "select * from categoriaConta where categoriaContaid = '"
                    + subCategoria.getCategoriaContaID() 
                    +"';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);
            
            while(dadosCat.next()){
                subCategoria.setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"), 
                                dadosCat.getBoolean("positiva"))
                );
            }
            System.out.println(subCategoria.toString());
        }

        return subCategoria;
    }
    
    /**
     *
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<SubCategoria> getAll() throws ClassNotFoundException, SQLException{
        String query = "select * from subCategoria;";
        ArrayList<SubCategoria> list = new ArrayList<>();

        ResultSet dadosSub = contexto.executeQuery(query);

        while(dadosSub.next()){
            SubCategoria subCategoria = new SubCategoria();
            subCategoria.setSubCategoriaID(dadosSub.getInt("subCategoriaID"));
            subCategoria.setCategoriaContaID(dadosSub.getInt("CategoriaContaID"));
            subCategoria.setDescricao(dadosSub.getString("Descricao"));
            
            String queryCat = "select * from categoriaConta where categoriaContaid = '"+ subCategoria.getCategoriaContaID() +"';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);
            
            while(dadosCat.next()){
                subCategoria.setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"), 
                                dadosCat.getBoolean("positiva"))
                );
            }
            
            System.out.println(subCategoria.toString());
            list.add(subCategoria);
        }

        return list;
    }
    
    /**
     *
     * @param subCategoria
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(SubCategoria subCategoria) throws ClassNotFoundException, SQLException{
        StringBuilder columnsAndValues = new StringBuilder(255);
        
        columnsAndValues.append("descricao= '")
                .append(subCategoria.getDescricao())
                .append("'");
        
        columnsAndValues.append("CategoriaContaID= '")
                .append(subCategoria.getCategoriaContaID())
                .append("'");
        
        String query = "update subCategoria SET " 
                + columnsAndValues.toString() 
                + " WHERE subCategoriaid = " 
                + subCategoria.getCategoriaContaID();
        
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
        String sql = "delete from subCategoria where subCategoria = ?";
        try(PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)){
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
        }catch(SQLException e){ return false; }
        
        return true;
    }
}
