/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.DespesaDAO;
import Models.Despesa;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controlador de Despesa
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Despesa
 * @see DespesaDAO
 */
public class DespesaController {

    /**
     *
     * @param despesa
     * @return
     */
    public Despesa create(Despesa despesa) {
        try {
            DespesaDAO dao = new DespesaDAO();
            if (despesa.getSubCategoria() != null) {
                if (despesa.getSubCategoria().getSubCategoriaID() != 0 && despesa.getDescricao().trim().length() > 0 && despesa.getDescricao() != null) {
                    if (despesa.getValor() > 0) {
                        if (despesa.getFormaPagamento() != 0) {
                            if (despesa.getDataOcorrencia() == null) {
                                despesa.setDataOcorrencia(LocalDate.now());
                            }
                            if (dao.create(despesa)) {
                                return despesa;
                            } else {
                                Mensagem.aviso("Não foi possivel cadastrar a Despesa.");
                            }
                        } else {
                            Mensagem.aviso("Deve ser selecionada uma forma de pagamento.");
                        }
                    } else {
                        Mensagem.aviso("A Despesa deve ter um valor e ele não pode ser negativo.");
                    }
                } else {
                    Mensagem.aviso("A Despesa deve ter uma Descricao.");
                }
            } else {
                Mensagem.aviso("A despesa deve ter um Categoria.");
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
    public Despesa get(int id) {
        try {
            Despesa despesa = new DespesaDAO().get(id);

            return despesa;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<Despesa> getAll() {
        try {
            ArrayList<Despesa> despesas = new DespesaDAO().getAll();

            return despesas;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param despesa
     * @return
     */
    public Despesa update(Despesa despesa) {
        try {
            DespesaDAO dao = new DespesaDAO();
            if (despesa.getSubCategoria() != null) {
                if (despesa.getMovimentacaoID() != 0 && despesa.getSubCategoria().getSubCategoriaID() != 0 && despesa.getDescricao().trim().length() > 0 && despesa.getDescricao() != null) {
                    if (despesa.getValor() != 0) {
                        if (despesa.getFormaPagamento() != 0) {
                            if (despesa.getDataOcorrencia() == null) {
                                despesa.setDataOcorrencia(LocalDate.now());
                            }
                            if (dao.update(despesa)) {
                                return despesa;
                            } else {
                                Mensagem.aviso("Não foi possivel cadastrar a receita.");
                            }
                        } else {
                            Mensagem.aviso("Deve ser selecionada uma forma de pagamento.");
                        }
                    } else {
                        Mensagem.aviso("A receita deve ter um valor para a receita.");
                    }
                } else {
                    Mensagem.aviso("A despesa deve ter uma Descricao.");
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
                return new DespesaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }

    /**
     *
     * @return
     */
    public String[] getAllMetaData() {
        try {
            return new DespesaDAO().getAllMetaData();
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }
        return null;
    }
}
