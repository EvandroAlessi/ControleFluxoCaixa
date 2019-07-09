/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDate;

/**
 * Modelo estrutural de Despesa.
 * Gera objetos modelos para comunicação com BD ou utilização na GUI.
 * Uma despesa é uma movimentação.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see Movimentacao
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
    
}
