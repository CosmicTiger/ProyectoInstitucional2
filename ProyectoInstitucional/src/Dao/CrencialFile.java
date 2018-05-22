/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

/**
 *
 * @author kevin
 */
public class CrencialFile {

    private int IdCredencial;
    private String User;
    private String Pass;

    public CrencialFile() {
    }

    public CrencialFile(int IdCredencial, String User, String Pass) {
        this.IdCredencial = IdCredencial;
        this.User = User;
        this.Pass = Pass;
    }

    public int getIdCredencial() {
        return IdCredencial;
    }

    public void setIdCredencial(int IdCredencial) {
        this.IdCredencial = IdCredencial;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String Pass) {
        this.Pass = Pass;
    }

    
}
