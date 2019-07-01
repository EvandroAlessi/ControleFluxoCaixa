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
    private int categoriaContaID;
    private String descricao;
    private boolean positiva;

    public CategoriaConta() {
    }
    
    public CategoriaConta(int categoriaContaID, String descricao, boolean positiva) {
        this.categoriaContaID = categoriaContaID;
        this.descricao = descricao;
        this.positiva = positiva;
    }

    public CategoriaConta(String descricao, boolean b) {
        this.descricao = descricao;
        this.positiva = b;
    }

    public int getCategoriaContaID() {
        return categoriaContaID;
    }

    public void setCategoriaContaID(int categoriaContaID) {
        this.categoriaContaID = categoriaContaID;
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

    @Override
    public String toString() {
        return "CategoriaConta{" + "categoriaContaID=" + categoriaContaID + ", descricao=" + descricao + ", positiva=" + positiva + '}';
    }
}
