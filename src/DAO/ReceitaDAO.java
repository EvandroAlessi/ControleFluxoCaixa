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
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class ReceitaDAO {

    private final Contexto contexto = new Contexto();

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException {
        String query = "select * from movimentacao;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];

        for (int i = 1; i <= fields.getColumnCount(); i++) {
            columns[i - 1] = fields.getColumnName(i);
            System.out.println(columns[i - 1]);
        }

        return columns;
    }

    /**
     *
     * @param receita
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(Receita receita) throws ClassNotFoundException, SQLException {
        String sql = "insert into movimentacao(Descricao, dataOcorrencia, valor, formaPagamento, subCategoriaID)values(?, ?, ?, ?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setString(1, receita.getDescricao()); //substitui o ? pelo dado do usuario
            preparestatement.setDate(2, Date.valueOf(receita.getDataOcorrencia()));
            preparestatement.setDouble(3, receita.getValor()); //substitui o ? pelo dado do usuario
            preparestatement.setInt(4, receita.getFormaPagamento());
            preparestatement.setInt(5, receita.getSubCategoria().getSubCategoriaID());
            //executando comando sql

            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    receita.setMovimentacaoID(id.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }

//    public boolean exists(String description) throws ClassNotFoundException, SQLException{
//        String query = "select * from movimentacao where subcategoriaid in "
//                + "(select subcategoriaid from subcategoria where subcategoriaid in "
//                    + "(select categoriacontaid from categoriaconta where positiva = 1)) "
//                + "AND descricao != '"+ description +"';";
//
//        ResultSet dados = contexto.executeQuery(query);
//
//        return dados != null;
//    }
    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Receita get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from movimentacao where subcategoriaid in "
                + "(select subcategoriaid from subcategoria where subcategoriaid in "
                + "(select categoriacontaid from categoriaconta where positiva = 1)) "
                + "AND movimentacaoid = '" + id + "';";
        Receita receita = new Receita();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            receita.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            receita.setDescricao(dados.getString("Descricao"));
            receita.setDataOcorrencia(dados.getDate("dataOcorrencia").toLocalDate());
            receita.setValor(dados.getDouble("valor"));
            receita.setFormaPagamento(dados.getInt("formaPagamento"));

            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + dados.getInt("SubCategoriaID")
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);

            while (dadosSub.next()) {
                receita.setSubCategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getString("Descricao"))
                );
            }

            String queryCat = "select * from categoriaConta where categoriaContaid = '"
                    + dadosSub.getInt("CategoriaContaID")
                    + "';";
            ResultSet dadosCat = contexto.executeQuery(queryCat);

            while (dadosCat.next()) {
                receita.getSubCategoria().setCategoriaConta(
                        new CategoriaConta(
                                dadosCat.getInt("CategoriaContaID"),
                                dadosCat.getString("Descricao"),
                                dadosCat.getBoolean("positiva"))
                );
            }
        }

        return receita;
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Receita> getAll() throws ClassNotFoundException, SQLException {
        String query = "select * from movimentacao where dataocorrencia <= NOW() AND subcategoriaid in "
                + "(select subcategoriaid from subcategoria where subcategoriaid in "
                + "(select categoriacontaid from categoriaconta where positiva = 1)) order by dataocorrencia desc;";
        ArrayList<Receita> list = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Receita receita = new Receita();
            receita.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            receita.setDescricao(dados.getString("Descricao"));
            receita.setDataOcorrencia(dados.getDate("dataOcorrencia").toLocalDate());
            receita.setValor(dados.getDouble("valor"));
            receita.setFormaPagamento(dados.getInt("formaPagamento"));
            
            System.out.println(receita.getDescricao() + receita.getDataOcorrencia());
            
            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + dados.getInt("SubCategoriaID")
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);

            while (dadosSub.next()) {
                receita.setSubCategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getString("Descricao"))
                );
                
                String queryCat = "select * from categoriaConta where categoriaContaid = '"
                        + dadosSub.getInt("CategoriaContaID")
                        + "';";
                ResultSet dadosCat = contexto.executeQuery(queryCat);

                while (dadosCat.next()) {
                    receita.getSubCategoria().setCategoriaConta(
                            new CategoriaConta(
                                    dadosCat.getInt("CategoriaContaID"),
                                    dadosCat.getString("Descricao"),
                                    dadosCat.getBoolean("positiva"))
                    );
                }
            }

            list.add(receita);
        }

        return list;
    }

    /**
     *
     * @param receita
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Receita receita) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" descricao= '")
                .append(receita.getDescricao())
                .append("',");
        columnsAndValues.append(" dataOcorrencia= '")
                .append(receita.getDataOcorrencia())
                .append("',");
        columnsAndValues.append(" valor= '")
                .append(receita.getValor())
                .append("',");
        columnsAndValues.append(" SubCategoriaID= '")
                .append(receita.getSubCategoria().getSubCategoriaID())
                .append("',");
        columnsAndValues.append(" formaPagamento= '")
                .append(receita.getFormaPagamento())
                .append("'");

        String query = " update movimentacao SET "
                + columnsAndValues.toString()
                + " WHERE movimentacaoID = " + receita.getMovimentacaoID();

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
        String sql = "delete from movimentacao where movimentacaoID = ?";
        try (PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }
}
