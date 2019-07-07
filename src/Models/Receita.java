/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDate;

/**
 *
 * @author evand
 */
public class Receita extends Movimentacao {

    public Receita() {
    }

    public Receita(LocalDate dataOcorrencia, String descricao, double valor, int formaPagamento) {
        super(dataOcorrencia, descricao, valor, formaPagamento);
    }

    public Receita(LocalDate dataOcorrencia, String descricao, double valor, int movimentacaoID, int formaPagamento) {
        super(dataOcorrencia, descricao, valor, movimentacaoID, formaPagamento);
    }
}
