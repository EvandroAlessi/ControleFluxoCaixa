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
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * DAO Movimentacao
 * Responsável pela persistência das Movimentacoes
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Movimentacao
 * @see CategoriaConta
 * @see SubCategoria
 */
public class MovimentacaoDAO {

    private final Contexto contexto = new Contexto();

    /**
     *
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public String[] getAllMetaData() throws ClassNotFoundException, SQLException {
        String query = "select * from Movimentacao;";
        ResultSetMetaData fields = contexto.executeQuery(query).getMetaData();
        String[] columns = new String[fields.getColumnCount()];

        for (int i = 1; i <= fields.getColumnCount(); i++) {
            columns[i - 1] = fields.getColumnName(i);
        }

        return columns;
    }

    /**
     *
     * @param Movimentacao
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean create(Movimentacao Movimentacao) throws ClassNotFoundException, SQLException {
        String sql = "insert into Movimentacao(Descricao, DataOcorrencia, Valor, FormaPagamento, SubCategoriaID)values(?, ?, ?, ?, ?)";
        try (PreparedStatement preparestatement = contexto.getConexao().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparestatement.setString(1, Movimentacao.getDescricao()); //substitui o ? pelo dado do usuario
            preparestatement.setDate(2, Date.valueOf(Movimentacao.getDataOcorrencia()));
            preparestatement.setDouble(3, Movimentacao.getValor()); //substitui o ? pelo dado do usuario
            preparestatement.setInt(4, Movimentacao.getFormaPagamento());
            preparestatement.setInt(5, Movimentacao.getSubCategoria().getSubCategoriaID());
            //executando comando sql

            int result = preparestatement.executeUpdate();
            if (result > 0) {
                ResultSet id = preparestatement.getGeneratedKeys();
                if (id.next()) {
                    Movimentacao.setMovimentacaoID(id.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            throw e;
        }

    }

    public double getSaldo() throws ClassNotFoundException, SQLException {
        String query = "SELECT (IFNULL((SELECT SUM(valor) FROM Movimentacao "
                + "WHERE DataOcorrencia <= NOW() AND SubCategoriaID in ("
                + "select SubCategoriaID from SubCategoria where CategoriaContaID in ("
                + "select CategoriaContaID from CategoriaConta where Positiva = 1))), 0) "
                + "- (IFNULL((SELECT SUM(valor) FROM Movimentacao WHERE DataOcorrencia <= NOW() AND SubCategoriaID in ("
                + "select SubCategoriaID from SubCategoria where CategoriaContaID in ("
                + "select CategoriaContaID from CategoriaConta where Positiva = 0))), 0))) as saldo;";

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
        String query = "select * from Movimentacao where MovimentacaoID = '" + id + "';";
        Movimentacao Movimentacao = new Movimentacao();
        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Movimentacao.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            Movimentacao.setDescricao(dados.getString("Descricao"));
            Movimentacao.setDataOcorrencia(dados.getDate("DataOcorrencia").toLocalDate());
            Movimentacao.setValor(dados.getDouble("Valor"));
            Movimentacao.setFormaPagamento(dados.getInt("FormaPagamento"));

            String querySub = "select * from SubCategoria where SubCategoriaID = '"
                    + dados.getInt("SubCategoriaID")
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);

            while (dadosSub.next()) {
                Movimentacao.setSubCategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getString("Descricao"))
                );
                String queryCat = "select * from CategoriaConta where CategoriaContaID = '"
                        + dadosSub.getInt("CategoriaContaID")
                        + "';";
                ResultSet dadosCat = contexto.executeQuery(queryCat);

                while (dadosCat.next()) {
                    Movimentacao.getSubCategoria().setCategoriaConta(
                            new CategoriaConta(
                                    dadosCat.getInt("CategoriaContaID"),
                                    dadosCat.getString("Descricao"),
                                    dadosCat.getBoolean("Positiva"))
                    );
                }
            }
        }

        return Movimentacao;
    }

    /**
     *
     * @param untilNow
     * @param beginDate
     * @param endDate
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ArrayList<Movimentacao> getAll(boolean untilNow, LocalDate beginDate, LocalDate endDate) throws ClassNotFoundException, SQLException {
        String query = "select * from Movimentacao where DataOcorrencia"
                + (untilNow ? " <= NOW() " 
                    + (beginDate != null ? "AND DataOcorrencia >= '" + beginDate + "'" : "") 
                : " > NOW() ")
                + (endDate != null ? "AND DataOcorrencia <= '" + endDate + "'" : "")
                + " order by DataOcorrencia desc;";
        System.out.println(query);
        ArrayList<Movimentacao> list = new ArrayList<>();

        ResultSet dados = contexto.executeQuery(query);

        while (dados.next()) {
            Movimentacao Movimentacao = new Movimentacao();
            Movimentacao.setMovimentacaoID(dados.getInt("MovimentacaoID"));
            Movimentacao.setDescricao(dados.getString("Descricao"));
            Movimentacao.setDataOcorrencia(dados.getDate("dataOcorrencia").toLocalDate());
            Movimentacao.setValor(dados.getDouble("Valor"));
            Movimentacao.setFormaPagamento(dados.getInt("FormaPagamento"));

            String querySub = "select * from SubCategoria where SubCategoriaID = '"
                    + dados.getInt("SubCategoriaID")
                    + "';";
            ResultSet dadosSub = contexto.executeQuery(querySub);

            while (dadosSub.next()) {
                Movimentacao.setSubCategoria(
                        new SubCategoria(
                                dadosSub.getInt("SubCategoriaID"),
                                dadosSub.getString("Descricao"))
                );

                String queryCat = "select * from CategoriaConta where CategoriaContaID = '"
                        + dadosSub.getInt("CategoriaContaID")
                        + "';";
                ResultSet dadosCat = contexto.executeQuery(queryCat);

                while (dadosCat.next()) {
                    Movimentacao.getSubCategoria().setCategoriaConta(
                            new CategoriaConta(
                                    dadosCat.getInt("CategoriaContaID"),
                                    dadosCat.getString("Descricao"),
                                    dadosCat.getBoolean("Positiva"))
                    );
                }
            }

            list.add(Movimentacao);
        }

        return list;
    }
    
    /**
     *
     * @param Movimentacao
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public boolean update(Movimentacao Movimentacao) throws ClassNotFoundException, SQLException {
        StringBuilder columnsAndValues = new StringBuilder(255);

        columnsAndValues.append(" Descricao= '")
                .append(Movimentacao.getDescricao())
                .append("',");
        columnsAndValues.append(" DataOcorrencia= '")
                .append(Movimentacao.getDataOcorrencia())
                .append("',");
        columnsAndValues.append(" Valor= '")
                .append(Movimentacao.getValor())
                .append("',");
        columnsAndValues.append(" FormaPagamento= '")
                .append(Movimentacao.getFormaPagamento())
                .append("',");
        columnsAndValues.append(" SubCategoriaID= '")
                .append(Movimentacao.getSubCategoria().getSubCategoriaID())
                .append("'");

        String query = "update Movimentacao SET "
                + columnsAndValues.toString()
                + " WHERE MovimentacaoID = " + Movimentacao.getMovimentacaoID();

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
        String sql = "delete from Movimentacao where MovimentacaoID = ?";
        try (PreparedStatement preparedStatement = contexto.getConexao().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw e;
        }

        return true;
    }
}
