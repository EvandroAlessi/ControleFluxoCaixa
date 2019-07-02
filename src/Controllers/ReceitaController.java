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
        try {
            ReceitaDAO dao = new ReceitaDAO();
            if (receita.getMovimentacaoID() != 0 && receita.getSubCategoriaID() != 0) {
                if(!dao.exists(receita.getDescricao())){
                    if (dao.create(receita)) {
                        return receita;
                    }
                }
                else{
                    return null;//ajeitar para retornar despesa já cadastrada
                }
            }
         } catch (ClassNotFoundException | SQLException e) {
             
         }
        
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
    
    public Receita update(Receita receita) {
        try{
            ReceitaDAO dao = new ReceitaDAO();
            if (receita.getMovimentacaoID() != 0 && receita.getSubCategoriaID() != 0) {
                if(!dao.exists(receita.getDescricao())){
                    if (dao.update(receita)) {
                        return receita;
                    }
                }
                else{
                    return null;//ajeitar para retornar despesa já cadastrada
                }
                
            } 
         } catch (ClassNotFoundException | SQLException e) {
             
         }
        
        return null;
    }
    
    public boolean delete(int id) {
        try {
            if (id != 0) {
                return new ReceitaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            
        }
        
        return false;
    }
}
