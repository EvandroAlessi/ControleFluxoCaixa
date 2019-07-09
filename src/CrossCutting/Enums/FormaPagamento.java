/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrossCutting.Enums;

/**
 * Enum das Formas de Pagamento
 * @author Evandro Alessi
 * @author Eric Ueta
 */
public enum FormaPagamento {
    CREDITO(1),
    DINHEIRO(2),
    BOLETO(3),
    DEPOSITO(4),
    CONVENIO(5);
    
    private final int value;

    FormaPagamento(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
