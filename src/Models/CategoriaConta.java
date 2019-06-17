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
public class CategoriaConta {
    //Acho que nem precisa do ID
    private int categoriaContaID;
    private int codigo;
    private String descricao;
    private boolean positiva;

    public CategoriaConta() {
    }

    public CategoriaConta(int categoriaContaID, int codigo, String descricao, boolean positiva) {
        this.categoriaContaID = categoriaContaID;
        this.codigo = codigo;
        this.descricao = descricao;
        this.positiva = positiva;
    }

    public int getCategoriaContaID() {
        return categoriaContaID;
    }

    public void setCategoriaContaID(int categoriaContaID) {
        this.categoriaContaID = categoriaContaID;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPositiva() {
        return positiva;
    }

    public void setPositiva(boolean positiva) {
        this.positiva = positiva;
    }
}
