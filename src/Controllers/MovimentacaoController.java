/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.MovimentacaoDAO;
import Models.Movimentacao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Controlador de Movimentacoes
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Movimentacao
 * @see MovimentacaoDAO
 */
public class MovimentacaoController {

    /**
     *
     * @param movimentacao
     * @return
     */
    public Movimentacao create(Movimentacao movimentacao) {
        try {
            MovimentacaoDAO dao = new MovimentacaoDAO();
            if (movimentacao.getSubCategoria() != null) {
                if (movimentacao.getSubCategoria().getSubCategoriaID() != 0 && movimentacao.getDescricao().trim().length() > 0 && movimentacao.getDescricao() != null) {
                    if (movimentacao.getValor() != 0) {
                        if (movimentacao.getFormaPagamento() != 0) {
                            if (movimentacao.getDataOcorrencia() == null) {
                                movimentacao.setDataOcorrencia(LocalDate.now());
                            }
                            if (dao.create(movimentacao)) {
                                return movimentacao;
                            } else {
                                Mensagem.aviso("Não foi possivel cadastrar a receita.");
                            }
                        } else {
                            Mensagem.aviso("Deve ser selecionada uma forma de pagamento.");
                        }
                    } else {
                        Mensagem.aviso("A movimentacao deve ter um valor para a receita.");
                    }
                } else {
                    Mensagem.aviso("A movimentacao deve ter uma Descricao.");
                }
            } else {
                Mensagem.aviso("A movimentacao deve ter uma Categoria.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public Movimentacao get(int id) {
        try {
            Movimentacao movimentacao = new MovimentacaoDAO().get(id);

            return movimentacao;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    public double getSaldo() {
        try {
            return new MovimentacaoDAO().getSaldo();
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return 0;
    }

    /**
     *
     * @param untilNow
     * @return
     */
    public ArrayList<Movimentacao> getAll(boolean untilNow) {
        try {
            ArrayList<Movimentacao> movimentacoes = new MovimentacaoDAO().getAll(untilNow, null, null);

            return movimentacoes;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
    /**
     *
     * @param untilNow
     * @param beginDate
     * @param endDate
     * @return
     */
    public ArrayList<Movimentacao> getAll(boolean untilNow, LocalDate beginDate, LocalDate endDate) {
        try {
            ArrayList<Movimentacao> movimentacoes = new MovimentacaoDAO().getAll(untilNow, beginDate, endDate);

            return movimentacoes;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }
    
    /**
     *
     * @param movimentacao
     * @return
     */
    public Movimentacao update(Movimentacao movimentacao) {
        try {
            MovimentacaoDAO dao = new MovimentacaoDAO();
            if (movimentacao.getSubCategoria() != null) {
                if (movimentacao.getSubCategoria().getSubCategoriaID() != 0 && movimentacao.getDescricao().trim().length() > 0 && movimentacao.getDescricao() != null) {
                    if (movimentacao.getValor() != 0) {
                        if (movimentacao.getFormaPagamento() != 0) {
                            if (movimentacao.getDataOcorrencia() == null) {
                                Date date = new Date();
                                date.getTime();
                                movimentacao.setDataOcorrencia(LocalDate.now());
                            }
                            if (dao.update(movimentacao)) {
                                return movimentacao;
                            } else {
                                Mensagem.aviso("Não foi possivel cadastrar a receita.");
                            }
                        } else {
                            Mensagem.aviso("Deve ser selecionada uma forma de pagamento.");
                        }
                    } else {
                        Mensagem.aviso("A movimentacao deve ter um valor para a receita.");
                    }
                } else {
                    Mensagem.aviso("A movimentacao deve ter uma Descricao.");
                }
            } else {
                Mensagem.aviso("A despesa deve ter uma Categoria.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean delete(int id) {
        try {
            if (id != 0) {
                return new MovimentacaoDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }
}
