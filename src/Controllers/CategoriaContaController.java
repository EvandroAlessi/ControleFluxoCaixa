/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.CategoriaContaDAO;
import Models.CategoriaConta;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class CategoriaContaController {

    /**
     *
     * @param categoriaConta
     * @return
     */
    public CategoriaConta create(CategoriaConta categoriaConta) {
        try {
            CategoriaContaDAO dao = new CategoriaContaDAO();
            if (categoriaConta.getDescricao() != null) {
                if (!dao.exists(categoriaConta.getDescricao(), categoriaConta.isPositiva())) {
                    if (dao.create(categoriaConta)) {
                        return categoriaConta;
                    } else {
                        Mensagem.aviso("Não foi possivel criar a categoria.");
                    }
                } else {
                    if (categoriaConta.isPositiva()) {
                        Mensagem.aviso("Já existe uma categoria de receita com essa Descrição.");
                    } else {
                        Mensagem.aviso("Já existe uma categoria de despesa com essa Descrição.");
                    }
                }
            } else {
                Mensagem.aviso("A categoria deve ter uma Descrição.");
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
    public CategoriaConta get(int id) {
        try {
            if (id != 0) {
                CategoriaConta categoriaConta = new CategoriaContaDAO().get(id);
                return categoriaConta;
            }
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
    public ArrayList<CategoriaConta> getAll() {
        try {
            ArrayList<CategoriaConta> categoriaContas = new CategoriaContaDAO().getAll();

            return categoriaContas;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param categoriaConta
     * @return
     */
    public CategoriaConta update(CategoriaConta categoriaConta) {
        try {
            CategoriaContaDAO dao = new CategoriaContaDAO();
            if (categoriaConta.getCategoriaContaID() != 0 && categoriaConta.getDescricao() != null) {
                if (!dao.exists(categoriaConta.getDescricao(), categoriaConta.isPositiva())) {
                    if (dao.update(categoriaConta)) {
                        return categoriaConta;
                    } else {
                        Mensagem.aviso("Não foi possivel realizar a Atualização.");
                    }
                } else {
                    if (categoriaConta.isPositiva()) {
                        Mensagem.aviso("Já existe uma categoria de receita com essa Descrição.");
                    } else {
                        Mensagem.aviso("Já existe uma categoria de despesa com essa Descrição.");
                    }
                }
            } else {
                Mensagem.aviso("Deve ser selecionada uma categoria e a categoria deve ter uma Descrição.");
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
                return new CategoriaContaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }
}
