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
public class Movimentacao implements IPagamento {
    private int movimentacaoID;
    private int subCategoriaID;
    private Date dataOcorrencia;
    private String descricao;
    private double valor;
    private int formaPagamento;
    private SubCategoria subcategoria;
    
    public Movimentacao(Date dataOcorrencia, String descricao, double valor, int movimentacaoID, int formaPagamento, int subCategoriaID) {
        this.dataOcorrencia = dataOcorrencia;
        this.descricao = descricao;
        this.valor = valor;
        this.movimentacaoID = movimentacaoID;
        this.formaPagamento = formaPagamento;
        this.subCategoriaID = subCategoriaID;
    }

    public double getSaldo(){
        return 0;
    }
    
    public int getMovimentacaoID() {
        return movimentacaoID;
    }
    
    public void setMovimentacaoID(int fluxoCaixaID) {
        this.movimentacaoID = fluxoCaixaID;
    }

    public int getSubCategoriaID() {
        return subCategoriaID;
    }

    public void setSubCategoriaID(int subCategoriaID) {
        this.subCategoriaID = subCategoriaID;
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

    public int getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(int formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public SubCategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(SubCategoria subcategoria) {
        this.subcategoria = subcategoria;
    }
    
    @Override
    public void selectPayment() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
