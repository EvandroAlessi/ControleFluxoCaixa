/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import CrossCutting.Log;
import CrossCutting.Mensagem;
import DAO.ReceitaDAO;
import Models.Receita;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author evand
 */
public class ReceitaController {

    /**
     *
     * @param receita
     * @return
     */
    public Receita create(Receita receita) {
        try {
            ReceitaDAO dao = new ReceitaDAO();
            if (receita.getSubCategoriaID() != 0 && receita.getDescricao() != null) {
                if (receita.getValor() != 0) {
                    if (receita.getFormaPagamento() != 0) {
                        if (receita.getDataOcorrencia() == null) {
                            Date date = new Date();
                            date.getTime();
                            receita.setDataOcorrencia(date);
                        }
                        if (dao.create(receita)) {
                            return receita;
                        }
                        else{
                            Mensagem.aviso("Não foi possivel cadastrar a receita.");
                        }
                    }
                    else{
                        Mensagem.aviso("Deve ser selecionada uma forma de pagamento.");
                    }
                }
                else{
                    Mensagem.aviso("A receita deve ter um valor para a receita.");
                }
            }
            else{
                Mensagem.aviso("A receita deve ter uma Descricao.");
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
    public Receita get(int id) {
        try {
            Receita receita = new ReceitaDAO().get(id);
            
            return receita;
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
    public ArrayList<Receita> getAll() {
         try {
            ArrayList<Receita> receitas = new ReceitaDAO().getAll();
            
            return receitas;
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }
        
        return null;
    }
    
    /**
     *
     * @param receita
     * @return
     */
    public Receita update(Receita receita) {
        try{
            ReceitaDAO dao = new ReceitaDAO();
            if (receita.getMovimentacaoID() != 0 && receita.getSubCategoriaID() != 0 && receita.getDescricao() != null) {
                if (receita.getValor() != 0) {
                    if (receita.getFormaPagamento() != 0) {
                        if (receita.getDataOcorrencia() == null) {
                            Date date = new Date();
                            date.getTime();
                            receita.setDataOcorrencia(date);
                        }
                        
                        if (dao.update(receita)) {
                            return receita;
                        }
                        else{
                            Mensagem.aviso("Não foi possivel cadastrar a receita.");
                        }
                    }
                    else{
                        Mensagem.aviso("Deve ser selecionada uma forma de pagamento.");
                    }
                }
                else{
                    Mensagem.aviso("A receita deve ter um valor para a receita.");
                }
            }
            else{
                Mensagem.aviso("A receita deve ter uma Descricao.");
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
                return new ReceitaDAO().delete(id);
            }
        } catch (ClassNotFoundException | SQLException e) {
            Log.saveLog(e);
            Mensagem.excecao(e);
        }
        
        return false;
    }
}
