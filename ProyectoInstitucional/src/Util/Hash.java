/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;
import Dao.CredencialFile;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Pojo.Credencial;
/**
 *
 * @author luisangelmarcia
 */
public class Hash {
    public Hash(){
    
    }
    
    
   public static String MD5hash(String text) throws NoSuchAlgorithmException { //md5.cz
        byte[] text2Byte = text.getBytes();
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text2Byte);
        byte[] bytesResult = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytesResult.length; i++) {
            sb.append(Integer.toString((bytesResult[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
    
    public static void main(String [] args) throws NoSuchAlgorithmException{
        CredencialFile cf = new CredencialFile();
        System.out.println("Contraseña "+ MD5hash("123"));
    }
    
}
