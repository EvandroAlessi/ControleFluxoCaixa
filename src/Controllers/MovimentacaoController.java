/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.MovimentacaoDAO;
import Models.Movimentacao;
import com.sun.media.jfxmedia.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author evand
 */
public class MovimentacaoController {
     public Movimentacao create(Movimentacao movimentacao) {
        
        return null;
    }
    
    public Movimentacao get(int id) {
        try {
            Movimentacao movimentacao = new MovimentacaoDAO().get(id);
            
            return movimentacao;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public ArrayList<Movimentacao> getAll() {
         try {
            ArrayList<Movimentacao> movimentacoes = new MovimentacaoDAO().getAll();
            
            return movimentacoes;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.logMsg(Logger.ERROR, "Failed to load cinematic context");
        }
        
        return null;
    }
    
    public boolean update(Movimentacao movimentacao) {
        

        return false;
    }
    
    public boolean delete(int id) {
        
        
        return true;
    }
}
