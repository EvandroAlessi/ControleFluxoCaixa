/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 * Modelo estrutural de SubCategoria.
 * Gera objetos modelos para comunicação com BD ou utilização na GUI.
 * Uma SubCategoria tem um Categoria.
 * @author Evandro Alessi
 * @author Eric Ueta
 * @see CategoriaConta
 */
public class SubCategoria {
    private int subCategoriaID;
    private String descricao;
    private CategoriaConta categoriaConta;

    public SubCategoria(int subCategoriaID, String descricao) {
        this.subCategoriaID = subCategoriaID;
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
//        return "SubCategoria{" + "subCategoriaID=" + subCategoriaID + ", descricao=" + descricao + ", categoriaConta=" + categoriaConta + '}';
        return descricao;
    }
}
