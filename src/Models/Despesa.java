/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 *
 * @author evand
 */
public class Despesa extends Movimentacao {

    public Despesa() {
    }

    public Despesa(LocalDate dataOcorrencia, String descricao, double valor, int formaPagamento) {
        super(dataOcorrencia, descricao, valor, formaPagamento);
    }
    
    public Despesa(LocalDate dataOcorrencia, String descricao, double valor, int movimentacaoID, int formaPagamento) {
        super(dataOcorrencia, descricao, valor, movimentacaoID, formaPagamento);
    }
    
    public String getDateF(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = sdf.format(Date.valueOf(getDataOcorrencia()));
        
        return data;
    }
}
