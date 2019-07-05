/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author evand
 */
public class Despesa extends Movimentacao {

    public Despesa() {
    }

    public Despesa(Date dataOcorrencia, String descricao, double valor, int formaPagamento) {
        super(dataOcorrencia, descricao, valor, formaPagamento);
    }
    
    public Despesa(Date dataOcorrencia, String descricao, double valor, int movimentacaoID, int formaPagamento) {
        super(dataOcorrencia, descricao, valor, movimentacaoID, formaPagamento);
    }
}
