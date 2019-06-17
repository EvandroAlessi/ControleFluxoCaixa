/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import CrossCutting.Enums.FormaPagamento;
import java.util.Date;

/**
 *
 * @author evand
 */
public class FluxoCaixa {
    private int fluxoCaixaID;
    private Date dataOcorrencia;
    private String descricao;
    private double valor;
    private FormaPagamento formaPagamento;

    public FluxoCaixa() {
    }

    public FluxoCaixa(Date dataOcorrencia, String descricao, double valor, int fluxoCaixaID, FormaPagamento formaPagamento) {
        this.dataOcorrencia = dataOcorrencia;
        this.descricao = descricao;
        this.valor = valor;
        this.fluxoCaixaID = fluxoCaixaID;
        this.formaPagamento = formaPagamento;
    }

    public int getFluxoCaixaID() {
        return fluxoCaixaID;
    }
    
    public void setFluxoCaixaID(int fluxoCaixaID) {
        this.fluxoCaixaID = fluxoCaixaID;
    }
    
    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setValorPgto(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}
