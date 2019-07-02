/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

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
     public CategoriaConta create(CategoriaConta categoriaConta) {
         try {
            CategoriaContaDAO dao = new CategoriaContaDAO();
             if (dao.create(categoriaConta)) {
                 return categoriaConta;
             }
         } catch (ClassNotFoundException | SQLException e) {
             
         }
        
        return null;
    }
    
    public CategoriaConta get(int id) {
        try {
            if (id != 0) {
                CategoriaConta categoriaConta = new CategoriaContaDAO().get(id);
                
                return categoriaConta;
            }
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public ArrayList<CategoriaConta> getAll() {
         try {
            ArrayList<CategoriaConta> categoriaContas = new CategoriaContaDAO().getAll();
            
            return categoriaContas;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public CategoriaConta update(CategoriaConta categoriaConta) {
        try{
            CategoriaContaDAO dao = new CategoriaContaDAO();
            if (categoriaConta.getCategoriaContaID() != 0) {
                if (dao.update(categoriaConta)) {
                    return categoriaConta;
                }
            }
         } catch (ClassNotFoundException | SQLException e) {
             
         }
        
        return null;
    }
    
    public boolean delete(int id) {
        try {
            if (id != 0) {
                return new CategoriaContaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            
        }
        
        return false;
    }
}
