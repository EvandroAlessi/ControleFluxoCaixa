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
public class Movimentacao implements IPagamento {
    protected int movimentacaoID;
    protected LocalDate dataOcorrencia;
    protected String descricao;
    protected double valor;
    protected int formaPagamento;
    protected SubCategoria subCategoria;
    
    public Movimentacao() {
        
    }

    public Movimentacao(LocalDate dataOcorrencia, String descricao, double valor, int formaPagamento) {
        this.dataOcorrencia = dataOcorrencia;
        this.descricao = descricao;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
    }
    
    public Movimentacao(LocalDate dataOcorrencia, String descricao, double valor, int movimentacaoID, int formaPagamento) {
        this.dataOcorrencia = dataOcorrencia;
        this.descricao = descricao;
        this.valor = valor;
        this.movimentacaoID = movimentacaoID;
        
        selectPayment(formaPagamento);
    }
    
    @Override
    public void selectPayment(int paymentType) {
       switch(paymentType){
            case CARTAO_CREDITO:
                formaPagamento = CARTAO_CREDITO;
                break;
            case DINHEIRO:
                 formaPagamento = DINHEIRO;
                break;
            case BOLETO:
                 formaPagamento = BOLETO;
                break;
            case DEPOSITO:
                 formaPagamento = DEPOSITO;
                break;
            case CONVENIO:
                 formaPagamento = CONVENIO;
                break;
            default:
                break;    
       }
    }
    
    public int getMovimentacaoID() {
        return movimentacaoID;
    }
    
    public void setMovimentacaoID(int fluxoCaixaID) {
        this.movimentacaoID = fluxoCaixaID;
    }
    
    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDate dataOcorrencia) {
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

    public int getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public SubCategoria getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    @Override
    public String toString() {
        return "Movimentacao{" + "movimentacaoID=" + movimentacaoID + ", dataOcorrencia=" + dataOcorrencia + ", descricao=" + descricao + ", valor=" + valor + ", formaPagamento=" + formaPagamento + ", subcategoria=" + subCategoria + '}';
    }
    
}
