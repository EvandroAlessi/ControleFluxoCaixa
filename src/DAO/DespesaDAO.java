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
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class DespesaDAO {

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
        }

        return columns;
    }

    /**
     *
     * @param despesa
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(Despesa despesa) throws ClassNotFoundException, SQLException {
        String sql = "insert into movimentacao(Descricao, dataOcorrencia, valor, formaPagamento, subCategoriaID)values(?, ?, ?, ?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setString(1, despesa.getDescricao()); //substitui o ? pelo dado do usuario
            preparestatement.setDate(2, Date.valueOf(despesa.getDataOcorrencia()));
            preparestatement.setDouble(3, despesa.getValor()); //substitui o ? pelo dado do usuario
            preparestatement.setInt(4, despesa.getFormaPagamento());
            preparestatement.setInt(5, despesa.getSubCategoria().getSubCategoriaID());
            
            //executando comando sql
            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    despesa.setMovimentacaoID(id.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }
    }

//    /**
//     *
//     * @param description
//     * @return
//     * @throws ClassNotFoundException
//     * @throws SQLException
//     */
//    public boolean exists(String description) throws ClassNotFoundException, SQLException{
//        String query = "select * from movimentacao where subcategoriaid in "
//                + "(select subcategoriaid from subcategoria where subcategoriaid in "
//                    + "(select categoriacontaid from categoriaconta where positiva = 0)) "
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
    public Despesa get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from movimentacao where subcategoriaid in "
                + "(select subcategoriaid from subcategoria where subcategoriaid in "
                + "(select categoriacontaid from categoriaconta where positiva = 0)) "
                + "AND movimentacaoid = '" + id + "';";
        Despesa despesa = new Despesa();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            despesa.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            despesa.setDescricao(dados.getString("Descricao"));
            despesa.setDataOcorrencia(dados.getDate("dataOcorrencia").toLocalDate());
            despesa.setValor(dados.getDouble("valor"));
            despesa.setFormaPagamento(dados.getInt("formaPagamento"));

            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + dados.getInt("SubCategoriaID")
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);

            while (dadosSub.next()) {
                despesa.setSubCategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getString("Descricao"))
                );

                String queryCat = "select * from categoriaConta where categoriaContaid = '"
                        + +dadosSub.getInt("CategoriaContaID")
                        + "';";
                ResultSet dadosCat = contexto.executeQuery(queryCat);

                while (dadosCat.next()) {
                    despesa.getSubCategoria().setCategoriaConta(
                            new CategoriaConta(
                                    dadosCat.getInt("CategoriaContaID"),
                                    dadosCat.getString("Descricao"),
                                    dadosCat.getBoolean("positiva"))
                    );
                }
            }
        }

        return despesa;
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Despesa> getAll() throws ClassNotFoundException, SQLException {
        String query = "select * from movimentacao where dataocorrencia <= NOW() AND subcategoriaid in "
                + "(select subcategoriaid from subcategoria where subcategoriaid in "
                + "(select categoriacontaid from categoriaconta where positiva = 0)) order by dataocorrencia desc;";
        ArrayList<Despesa> list = new ArrayList<>();
        
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Despesa despesa = new Despesa();
            despesa.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            despesa.setDescricao(dados.getString("Descricao"));
            despesa.setDataOcorrencia(dados.getDate("dataOcorrencia").toLocalDate());
            despesa.setValor(dados.getDouble("valor"));
            despesa.setFormaPagamento(dados.getInt("formaPagamento"));

            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + dados.getInt("SubCategoriaID")
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);

            while (dadosSub.next()) {
                despesa.setSubCategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getString("Descricao"))
                );

                String queryCat = "select * from categoriaConta where categoriaContaid = '"
                        + dadosSub.getInt("CategoriaContaID")
                        + "';";
                ResultSet dadosCat = contexto.executeQuery(queryCat);
                while (dadosCat.next()) {
                    despesa.getSubCategoria().setCategoriaConta(
                            new CategoriaConta(
                                    dadosCat.getInt("CategoriaContaID"),
                                    dadosCat.getString("Descricao"),
                                    dadosCat.getBoolean("positiva"))
                    );
                }
            }

            list.add(despesa);
        }

        return list;
    }

    /**
     *
     * @param despesa
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Despesa despesa) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" descricao= '")
                .append(despesa.getDescricao())
                .append("',");
        columnsAndValues.append(" dataOcorrencia= '")
                .append(despesa.getDataOcorrencia())
                .append("',");
        columnsAndValues.append(" valor= '")
                .append(despesa.getValor())
                .append("',");
        columnsAndValues.append(" formaPagamento= '")
                .append(despesa.getFormaPagamento())
                .append("',");
        columnsAndValues.append(" SubCategoriaID= '")
                .append(despesa.getSubCategoria().getSubCategoriaID())
                .append("'");

        String query = "update movimentacao SET "
                + columnsAndValues.toString()
                + " WHERE movimentacaoID = " + despesa.getMovimentacaoID();

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
            throw e;
        }

        return true;
    }
}
