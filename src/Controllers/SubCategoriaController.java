/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.SubCategoriaDAO;
import Models.SubCategoria;
import com.sun.media.jfxmedia.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class SubCategoriaController {
     public SubCategoria create(SubCategoria subCategoria) {
        try {
            SubCategoriaDAO dao = new SubCategoriaDAO();
             if (dao.create(subCategoria)) {
                 return subCategoria;
             }
         } catch (ClassNotFoundException | SQLException e) {
             
         }
        
        return null;
    }
    
    public SubCategoria get(int id) {
        try {
            SubCategoria subCategoria = new SubCategoriaDAO().get(id);
            
            return subCategoria;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public ArrayList<SubCategoria> getAll() {
         try {
            ArrayList<SubCategoria> subCategorias = new SubCategoriaDAO().getAll();
            
            return subCategorias;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public SubCategoria update(SubCategoria subCategoria) {
        try{
            SubCategoriaDAO dao = new SubCategoriaDAO();
            if (subCategoria.getCategoriaContaID() != 0 && subCategoria.getSubCategoriaID() != 0) {
                if (dao.update(subCategoria)) {
                    return subCategoria;
                }
            } 
         } catch (ClassNotFoundException | SQLException e) {
             
         }
        
        return null;
    }
    
    public boolean delete(int id) {
        try {
            if (id != 0) {
                return new SubCategoriaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            
        }
        
        return false;
    }
}
