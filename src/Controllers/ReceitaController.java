/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.ReceitaDAO;
import Models.Receita;
import com.sun.media.jfxmedia.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class ReceitaController {
     public Receita create(Receita receita) {
        
        return null;
    }
    
    public Receita get(int id) {
        try {
            Receita receita = new ReceitaDAO().get(id);
            
            return receita;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public ArrayList<Receita> getAll() {
         try {
            ArrayList<Receita> receitas = new ReceitaDAO().getAll();
            
            return receitas;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public boolean update(Receita receita) {
        

        return false;
    }
    
    public boolean delete(int id) {
        
        
        return true;
    }
}
