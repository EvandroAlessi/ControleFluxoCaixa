/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAL.Contexto;
import Models.CategoriaConta;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evand
 */
public class CategoriaContaService {
    
    
    public ArrayList<CategoriaConta> consultarCategorias(Contexto contexto){
        String sql = "select * from categoriaconta;";
        try {
            ArrayList<CategoriaConta> lista = new ArrayList<>();
            
            ResultSet dados = contexto.consultar(sql);
            ResultSetMetaData campos = dados.getMetaData();
            
            while(dados.next()){
                CategoriaConta categoria = new CategoriaConta();
                categoria.setDescricao(dados.getString("Descricao"));
                categoria.setCodigo(dados.getInt("CategoriaContaID"));
                categoria.setPositiva(dados.getBoolean("positiva"));
                System.out.println(categoria.getCodigo()+"\n"+categoria.getDescricao()+"\n"+categoria.isPositiva());
                lista.add(categoria);
            }
            return lista;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("deu ruim categorias banco");
            return null;
        }
    }
}
