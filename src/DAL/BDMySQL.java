/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.*;

/**
 *
 * @author SpaceBR
 */

public class BDMySQL {
    private String url = "jdbc:mysql://localhost:3306/alocacaosalas";
    private String driver = "com.mysql.jdbc.Driver";
    private String usuario = "root";
    private String senha = "root";
    private Connection conexao;

    public void conectar() throws ClassNotFoundException, SQLException{
        if(conexao == null){
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Banco conectado");
        }
    }
    
    public int atualizar(String sql) throws ClassNotFoundException, SQLException{
        conectar();
        Statement sessao = getConexao().createStatement();
        return sessao.executeUpdate(sql);
    }
    
    public ResultSet consultar(String sql) throws ClassNotFoundException, SQLException{
        conectar();
        Statement sessao = getConexao().createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                ResultSet.CONCUR_READ_ONLY);
        return sessao.executeQuery(sql);
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
