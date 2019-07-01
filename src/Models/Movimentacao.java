/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import DAO.MovimentacaoDAO;
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
        this.subCategoriaID = subCategoriaID;
        
        selectPayment(formaPagamento);
    }

    public Movimentacao() {
        
    }
    
    public double getSaldo(){
        MovimentacaoDAO service = new MovimentacaoDAO();
        
//        return service.getSaldo();
        return 0;
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
    public String toString() {
        return "Movimentacao{" + "movimentacaoID=" + movimentacaoID + ", subCategoriaID=" + subCategoriaID + ", dataOcorrencia=" + dataOcorrencia + ", descricao=" + descricao + ", valor=" + valor + ", formaPagamento=" + formaPagamento + ", subcategoria=" + subcategoria + '}';
    }
    
}
