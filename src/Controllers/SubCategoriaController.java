/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.SubCategoriaDAO;
import Models.SubCategoria;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de SubCategorias
 * Propicia a comunicação entre a GUI e DAO
 * Implementa a lógica de negócios
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see SubCategoria
 * @see SubCategoriaDAO
 */
public class SubCategoriaController {

    /**
     *
     * @param subCategoria
     * @return
     */
    public SubCategoria create(SubCategoria subCategoria) {
        try {
            SubCategoriaDAO dao = new SubCategoriaDAO();
            if (subCategoria.getCategoriaConta().getCategoriaContaID() != 0 && subCategoria.getDescricao() != null) {
                if (!dao.exists(subCategoria.getDescricao())) {
                    if (dao.create(subCategoria)) {
                        return subCategoria;
                    } else {
                        Mensagem.aviso("Não foi possivel criar a Subcategoria.");
                    }
                } else {
                    Mensagem.aviso("Já existe uma Subcategoria com essa descricao.");
                }
            } else {
                Mensagem.aviso("Você deve selecionar uma Categoria e a SubCategoria deve ter uma descrição.");
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
    public SubCategoria get(int id) {
        try {
            SubCategoria subCategoria = new SubCategoriaDAO().get(id);

            return subCategoria;
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
    public ArrayList<SubCategoria> getAll() {
        try {
            ArrayList<SubCategoria> subCategorias = new SubCategoriaDAO().getAll();

            return subCategorias;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return null;
    }

    /**
     *
     * @param subCategoria
     * @return
     */
    public SubCategoria update(SubCategoria subCategoria) {
        try {
            SubCategoriaDAO dao = new SubCategoriaDAO();
            if (subCategoria.getSubCategoriaID() != 0 && subCategoria.getCategoriaConta().getCategoriaContaID() != 0 && subCategoria.getDescricao() != null) {
                if (!dao.exists(subCategoria.getDescricao())) {
                    if (dao.update(subCategoria)) {
                        return subCategoria;
                    } else {
                        Mensagem.aviso("Não foi possivel realizar a atualização.");
                    }
                } else {
                    Mensagem.aviso("Já existe uma Subcategoria com essa descricao.");
                }
            } else {
                Mensagem.aviso("Você deve selecionar uma Categoria e a SubCategoria deve ter uma descrição.");
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
                return new SubCategoriaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }

        return false;
    }
}
