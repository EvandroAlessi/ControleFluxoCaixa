/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.DespesaDAO;
import Models.CategoriaConta;
import Models.Despesa;
import Models.SubCategoria;
import com.sun.media.jfxmedia.logging.Logger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author evand
 */
public class DespesaController {
    public Despesa create(Despesa despesa) {
        
        return null;
    }
    
    public Despesa get(int id) {
        try {
            Despesa despesa = new DespesaDAO().get(id);
            
            return despesa;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public ArrayList<Despesa> getAll() {
         try {
            ArrayList<Despesa> despesas = new DespesaDAO().getAll();
            
            return despesas;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public boolean update(Despesa despesa) {
        

        return false;
    }
    
    public boolean delete(int id) {
        
        
        return true;
    }
}
