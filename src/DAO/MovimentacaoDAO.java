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
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class MovimentacaoDAO {

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
     * @param movimentacao
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(Movimentacao movimentacao) throws ClassNotFoundException, SQLException {
        String sql = "insert into movimentacao(Descricao, dataOcorrencia, valor, formaPagamento, suvCategoriaID)values(?, ?, ?, ?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setString(1, movimentacao.getDescricao()); //substitui o ? pelo dado do usuario
            preparestatement.setDate(2, Date.valueOf(movimentacao.getDataOcorrencia()));
            preparestatement.setDouble(3, movimentacao.getValor()); //substitui o ? pelo dado do usuario
            preparestatement.setInt(4, movimentacao.getFormaPagamento());
            preparestatement.setInt(5, movimentacao.getSubCategoria().getSubCategoriaID());
            //executando comando sql

            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    movimentacao.setMovimentacaoID(id.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }

    }

    public double getSaldo() throws ClassNotFoundException, SQLException {
        String query = "SELECT (SELECT SUM(valor) FROM movimentacao WHERE subcategoriaid in (\n"
                + "select subcategoriaid from subcategoria where subcategoriaid in (\n"
                + "select categoriacontaid from categoriaconta where positiva = 1))) "
                + "- (SELECT SUM(valor) FROM movimentacao WHERE subcategoriaid in (\n"
                + "select subcategoriaid from subcategoria where subcategoriaid in (\n"
                + "select categoriacontaid from categoriaconta where positiva = 0))) as saldo;";

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            return dados.getInt("saldo");
        }

        return 0;
    }

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Movimentacao get(int id) throws ClassNotFoundException, SQLException {
        String query = "select * from movimentacao where movimentacaoid = '" + id + "';";
        Movimentacao movimentacao = new Movimentacao();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            movimentacao.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            movimentacao.setDescricao(dados.getString("Descricao"));
            movimentacao.setDataOcorrencia(dados.getDate("dataOcorrencia").toLocalDate());
            movimentacao.setValor(dados.getDouble("valor"));
            movimentacao.setFormaPagamento(dados.getInt("formaPagamento"));

            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + dados.getInt("SubCategoriaID")
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);

            while (dadosSub.next()) {
                movimentacao.setSubCategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getString("Descricao"))
                );
                String queryCat = "select * from categoriaConta where categoriaContaid = '"
                        + dadosSub.getInt("CategoriaContaID")
                        + "';";
                ResultSet dadosCat = contexto.executeQuery(queryCat);

                while (dadosCat.next()) {
                    movimentacao.getSubCategoria().setCategoriaConta(
                            new CategoriaConta(
                                    dadosCat.getInt("CategoriaContaID"),
                                    dadosCat.getString("Descricao"),
                                    dadosCat.getBoolean("positiva"))
                    );
                }
            }
        }

        return movimentacao;
    }

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Movimentacao> getAll(boolean untilNow) throws ClassNotFoundException, SQLException {
        String query = "select * from movimentacao where dataocorrencia"
                + (untilNow ? " <= NOW() " : " > NOW() ")
                + "order by dataocorrencia desc;";
        ArrayList<Movimentacao> list = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            movimentacao.setDescricao(dados.getString("Descricao"));
            movimentacao.setDataOcorrencia(dados.getDate("dataOcorrencia").toLocalDate());
            movimentacao.setValor(dados.getDouble("valor"));
            movimentacao.setFormaPagamento(dados.getInt("formaPagamento"));

            String querySub = "select * from subcategoria where SubCategoriaid = '"
                    + dados.getInt("SubCategoriaID")
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);

            while (dadosSub.next()) {
                movimentacao.setSubCategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getString("Descricao"))
                );

                String queryCat = "select * from categoriaConta where categoriaContaid = '"
                        + dadosSub.getInt("CategoriaContaID")
                        + "';";
                ResultSet dadosCat = contexto.executeQuery(queryCat);

                while (dadosCat.next()) {
                    movimentacao.getSubCategoria().setCategoriaConta(
                            new CategoriaConta(
                                    dadosCat.getInt("CategoriaContaID"),
                                    dadosCat.getString("Descricao"),
                                    dadosCat.getBoolean("positiva"))
                    );
                }
            }

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
    public boolean update(Movimentacao movimentacao) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" descricao= '")
                .append(movimentacao.getDescricao())
                .append("',");
        columnsAndValues.append(" dataOcorrencia= '")
                .append(movimentacao.getDataOcorrencia())
                .append("',");
        columnsAndValues.append(" valor= '")
                .append(movimentacao.getValor())
                .append("',");
        columnsAndValues.append(" formaPagamento= '")
                .append(movimentacao.getFormaPagamento())
                .append("',");
        columnsAndValues.append(" SubCategoriaID= '")
                .append(movimentacao.getSubCategoria().getSubCategoriaID())
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
