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
public class SubCategoria {
    private int subCategoriaID;
    private int CategoriaContaID;
    private String descricao;
    private CategoriaConta categoriaConta;

    public SubCategoria(int subCategoriaID, int CategoriaContaID, String descricao) {
        this.subCategoriaID = subCategoriaID;
        this.CategoriaContaID = CategoriaContaID;
        this.descricao = descricao;
    }

    public SubCategoria() {
        
    }

    public int getSubCategoriaID() {
        return subCategoriaID;
    }

    public void setSubCategoriaID(int subCategoriaID) {
        this.subCategoriaID = subCategoriaID;
    }

    public int getCategoriaContaID() {
        return CategoriaContaID;
    }

    public void setCategoriaContaID(int CategoriaContaID) {
        this.CategoriaContaID = CategoriaContaID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public CategoriaConta getCategoriaConta() {
        return categoriaConta;
    }

    public void setCategoriaConta(CategoriaConta categoriaConta) {
        this.categoriaConta = categoriaConta;
    }

    @Override
    public String toString() {
        return "SubCategoria{" + "subCategoriaID=" + subCategoriaID + ", CategoriaContaID=" + CategoriaContaID + ", descricao=" + descricao + ", categoriaConta=" + categoriaConta + '}';
    }
}
