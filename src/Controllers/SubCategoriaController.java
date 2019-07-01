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
    
    public boolean update(SubCategoria subCategoria) {
        

        return false;
    }
    
    public boolean delete(int id) {
        
        
        return true;
    }
}
