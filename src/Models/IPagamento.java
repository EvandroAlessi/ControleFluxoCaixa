/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author evand
 */
public interface IPagamento {
    final int CARTAO_CREDITO = 1;
    final int DINHEIRO = 2;
    final int BOLETO = 3;
    final int DEPOSITO = 4;
    final int CONVENIO = 5;
    
    public abstract void selectPayment();
}
