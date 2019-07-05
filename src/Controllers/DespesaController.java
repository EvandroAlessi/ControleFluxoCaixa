/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import DAO.DespesaDAO;
import Models.Despesa;
import com.sun.media.jfxmedia.logging.Logger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author evand
 */
public class DespesaController {

    /**
     *
     * @param despesa
     * @return
     */
    public Despesa create(Despesa despesa) {
        try {
            DespesaDAO dao = new DespesaDAO();
            if (despesa.getMovimentacaoID() != 0 && despesa.getSubCategoriaID() != 0) {
                if(!dao.exists(despesa.getDescricao())){
                    if (dao.create(despesa)) {
                        return despesa;
                    }
                }
                else{
                    return null;//ajeitar para retornar despesa já cadastrada
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
    public Despesa get(int id) {
        try {
            Despesa despesa = new DespesaDAO().get(id);
            
            return despesa;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
        }
        
        return null;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Despesa> getAll() {
         try {
            ArrayList<Despesa> despesas = new DespesaDAO().getAll();
            
            return despesas;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
        }
        
        return null;
    }
    
    /**
     *
     * @param despesa
     * @return
     */
    public Despesa update(Despesa despesa) {
        try{
            DespesaDAO dao = new DespesaDAO();
            if (despesa.getMovimentacaoID() != 0 && despesa.getSubCategoriaID() != 0) {
                if (dao.update(despesa)) {
                    return despesa;
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
                return new DespesaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
        }
        
        return false;
    }

    /**
     *
     * @return
     */
    public String[] getAllMetaData(){
        try {
            return new DespesaDAO().getAllMetaData();
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
        }
        return null;
    }
}
