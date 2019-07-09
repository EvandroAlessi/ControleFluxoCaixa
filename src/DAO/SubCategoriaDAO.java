/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import Models.SubCategoria;
import Models.CategoriaConta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class SubCategoriaDAO {

    private final Contexto contexto = new Contexto();

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException {
        String query = "select * from SubCategoria;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];

        for (int i = 1; i <= fields.getColumnCount(); i++) {
            columns[i - 1] = fields.getColumnName(i);
        }

        return columns;
    }

    /**
     *
     * @param SubCategoria
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(SubCategoria SubCategoria) throws ClassNotFoundException, SQLException {
        String sql = "insert into SubCategoria(Descricao, CategoriaContaID)values(?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setString(1, SubCategoria.getDescricao()); //substitui o ? pelo dado do usuario
            preparestatement.setInt(2, SubCategoria.getCategoriaConta().getCategoriaContaID());

            //executando comando sql
            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    SubCategoria.setSubCategoriaID(id.getInt(1));

                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean exists(String desc) throws ClassNotFoundException, SQLException {
        String query = "select SubCategoriaID from SubCategoria where Descricao = '" + desc + "';";

        ResultSet dados = contexto.executeQuery(query);

        return dados == null;
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public SubCategoria get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from SubCategoria where SubCategoriaID = '" + id + "';";
        SubCategoria SubCategoria = new SubCategoria();

        ResultSet dadosSub = contexto.executeQuery(query);

        while (dadosSub.next()) {
            SubCategoria.setSubCategoriaID(dadosSub.getInt("SubCategoriaID"));
            SubCategoria.setDescricao(dadosSub.getString("Descricao"));

            String queryCat = "select * from CategoriaConta where CategoriaContaID = '"
                    + dadosSub.getInt("CategoriaContaID")
                    + "';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while (dadosCat.next()) {
                SubCategoria.setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"),
                                dadosCat.getBoolean("Positiva"))
                );
            }
        }

        return SubCategoria;
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<SubCategoria> getAll() throws ClassNotFoundException, SQLException {
        String query = "select * from SubCategoria;";
        ArrayList<SubCategoria> list = new ArrayList<>();

        ResultSet dadosSub = contexto.executeQuery(query);

        while (dadosSub.next()) {
            SubCategoria SubCategoria = new SubCategoria();
            SubCategoria.setSubCategoriaID(dadosSub.getInt("SubCategoriaID"));
            SubCategoria.setDescricao(dadosSub.getString("Descricao"));

            String queryCat = "select * from CategoriaConta where CategoriaContaID = '"
                    + dadosSub.getInt("CategoriaContaID")
                    + "';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while (dadosCat.next()) {
                SubCategoria.setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"),
                                dadosCat.getBoolean("Positiva"))
                );
            }

            list.add(SubCategoria);
        }

        return list;
    }

    /**
     *
     * @param SubCategoria
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(SubCategoria SubCategoria) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" Descricao= '")
                .append(SubCategoria.getDescricao())
                .append("',");

        columnsAndValues.append(" CategoriaContaID= '")
                .append(SubCategoria.getCategoriaConta().getCategoriaContaID())
                .append("'");

        String query = "update SubCategoria SET "
                + columnsAndValues.toString()
                + " WHERE SubCategoriaID = "
                + SubCategoria.getSubCategoriaID();

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
    public boolean delete(int id) throws ClassNotFoundException, SQLException {
        String sql = "delete from SubCategoria where SubCategoriaID = ?";
        try (PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            Log.saveLog(e);
            return false;
        }

        return true;
    }
}
