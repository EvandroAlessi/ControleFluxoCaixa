/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author evand
 */
public class Contexto {
    private String url = "jdbc:mysql://localhost:3306/controlefluxocaixa?useTimezone=true&serverTimezone=UTC";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String usuario = "root";
    private String senha = "root";
    private Connection conexao;


    public void getConnection() throws ClassNotFoundException, SQLException{
        if(conexao == null){
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Banco conectado");
        }
    }
    
    
    public int executeUpdate(String sql) throws ClassNotFoundException, SQLException{
        getConnection();
        Statement sessao = getConexao().createStatement();
        
        return sessao.executeUpdate(sql);
    }
    
    public ResultSet executeQuery(String query) throws ClassNotFoundException, SQLException{
        getConnection();
        Statement sessao = getConexao().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                ResultSet.CONCUR_READ_ONLY);
        
        return sessao.executeQuery(query);
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the conexao
     */
    public Connection getConexao() {
        return conexao;
    }

    /**
     * @param conexao the conexao to set
     */
    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }
}
