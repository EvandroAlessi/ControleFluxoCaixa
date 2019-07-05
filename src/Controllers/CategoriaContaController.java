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
import com.sun.media.jfxmedia.logging.Logger;
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
             if (dao.create(categoriaConta)) {
                 return categoriaConta;
             }
         } catch (ClassNotFoundException | SQLException e) {
             Log.saveLog(e);
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
        }
        
        return null;
    }
    
    /**
     *
     * @param categoriaConta
     * @return
     */
    public CategoriaConta update(CategoriaConta categoriaConta) {
        try{
            CategoriaContaDAO dao = new CategoriaContaDAO();
            if (categoriaConta.getCategoriaContaID() != 0) {
                if (dao.update(categoriaConta)) {
                    return categoriaConta;
                }
            }
         } catch (ClassNotFoundException | SQLException e) {
             Log.saveLog(e);
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
        }
        
        return false;
    }
}
