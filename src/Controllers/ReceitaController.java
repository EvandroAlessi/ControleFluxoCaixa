/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.ReceitaDAO;
import Models.Receita;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controlador de Receitas
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Receita
 * @see ReceitaDAO
 */
public class ReceitaController {

    /**
     *
     * @param receita
     * @return
     */
    public Receita create(Receita receita) {
        try {
            ReceitaDAO dao = new ReceitaDAO();
            if (receita.getSubCategoria() != null) {
                if (receita.getSubCategoria().getSubCategoriaID() != 0 && receita.getDescricao().trim().length() > 0 && receita.getDescricao() != null) {
                    if (receita.getValor() > 0) {
                        if (receita.getFormaPagamento() != 0) {
                            if (receita.getDataOcorrencia() == null) {
                                receita.setDataOcorrencia(LocalDate.now());
                            }
                            if (dao.create(receita)) {
                                return receita;
                            } else {
                                Mensagem.aviso("Não foi possivel cadastrar a receita.");
                            }
                        } else {
                            Mensagem.aviso("Deve ser selecionada uma forma de pagamento.");
                        }
                    } else {
                        Mensagem.aviso("A receita deve ter um valor  e ele não pode ser negativo.");
                    }
                } else {
                    Mensagem.aviso("A receita deve ter uma Descricao.");
                }
            } else {
                Mensagem.aviso("A Receita deve ter um Categoria.");
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
    public Receita get(int id) {
        try {
            Receita receita = new ReceitaDAO().get(id);

            return receita;
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
    public ArrayList<Receita> getAll() {
        try {
            ArrayList<Receita> receitas = new ReceitaDAO().getAll();

            return receitas;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param receita
     * @return
     */
    public Receita update(Receita receita) {
        try {
            ReceitaDAO dao = new ReceitaDAO();
            if (receita.getSubCategoria() != null) {
                if (receita.getSubCategoria().getSubCategoriaID() != 0 && receita.getDescricao().trim().length() > 0 && receita.getDescricao() != null) {
                    if (receita.getValor() != 0) {
                        if (receita.getFormaPagamento() != 0) {
                            if (receita.getDataOcorrencia() == null) {
                                receita.setDataOcorrencia(LocalDate.now());
                            }
                            if (dao.update(receita)) {
                                return receita;
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
                    Mensagem.aviso("A receita deve ter uma Descricao.");
                }
            } else {
                Mensagem.aviso("A Receita deve ter uma Categoria.");
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
                return new ReceitaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }
}
