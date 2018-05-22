/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Pojo.Credencial;
import Dao.DefaultArrayModel;
/**
 *
 * @author kevin
 */
public class CredencialFile {

    public static final int SIZE = 132;
    private RandomAccessFile rCredentials;
    private File masterFile;
    
    private void open() throws IOException{
        masterFile = new File("Credencial.dat");
        if(!masterFile.exists())
        {
            masterFile.createNewFile();
            rCredentials = new RandomAccessFile(masterFile,"rw");
            rCredentials.seek(0);
            rCredentials.writeInt(0);
        }else
            rCredentials = new RandomAccessFile(masterFile, "rw");
    }
    
    private void close() throws IOException{
        if(rCredentials != null)
            rCredentials.close();
    }
    
    private String setStringLength(String text, int size){
        StringBuilder buffer;
        if(text == null){
            buffer = new StringBuilder(size);
        } else {
            buffer = new StringBuilder(text);
            buffer.setLength(size);
        }
        return buffer.toString();
    }
    
    public void create (Credencial c)throws IOException{
        open();
        rCredentials.seek(0);
        int n = rCredentials.readInt();
        long pos = 4 + n * SIZE;
        rCredentials.seek(pos);
        rCredentials.writeInt(c.getIdCredencial());
        rCredentials.writeUTF(setStringLength(c.getUser(), 10));
        rCredentials.writeUTF(setStringLength(c.getPass(), 15));
        rCredentials.seek(0);
        rCredentials.writeInt(++n);
        close();
    }
    
    public void update(Credencial c)throws IOException{
        open();
        rCredentials.seek(0);
        int n = rCredentials.readInt();
        if(n <= 0)
            return;
        
        for(int i = 0; i < n; i++)
        {
            long pos = 4 + i * SIZE;
            rCredentials.seek(pos);
            int id_temp = rCredentials.readInt();
            if(c.getIdCredencial() == id_temp){
                rCredentials.writeUTF(setStringLength(c.getUser(), 10));
                rCredentials.writeUTF(setStringLength(c.getPass(), 15));
                close();
                break;
            }
        }
    }
    
    public void delete(Credencial c) throws IOException{
        open();
        rCredentials.seek(0);
        int n = rCredentials.readInt();
        if(n<=0)
            return;
        File temp_file = new File("tempCredential.dat");
        temp_file.createNewFile();
        RandomAccessFile temp_rCredentials = new RandomAccessFile(temp_file, "rw");
        temp_rCredentials.seek(0);
        temp_rCredentials.writeInt(0);
        int j = 0;
        for (int i = 0; i < n; i++)
        {
            long pos = 4 + i * SIZE;
            rCredentials.seek(pos);
            Credencial p = read();
            if(p.getIdCredencial() != c.getIdCredencial()){
                long index = 4 + j * SIZE;
                temp_rCredentials.seek(index);
                temp_rCredentials.writeInt(p.getIdCredencial());
                temp_rCredentials.writeUTF(setStringLength(p.getUser(), 10));
                temp_rCredentials.writeUTF(setStringLength(p.getPass(), 15));
                j++;
            }
        }
        temp_rCredentials.seek(0);
        temp_rCredentials.writeInt(--n);
        close();
        temp_rCredentials.close();
        try{
            if(masterFile.delete())
                System.out.println("Se elimino el archivo");
        }catch(SecurityException ex){
            System.err.println("Error al eliminar"+ex.getMessage());
        }finally{
            if(temp_file.renameTo(new File("Credential.dat")))
                System.out.println("Se renombro");
        }
    }

    private Credencial read() throws IOException {
        Credencial e = new Credencial();
        e.setIdCredencial(rCredentials.readInt());
        e.setUser(rCredentials.readUTF().replace('\0', ' ').trim());
        e.setPass(rCredentials.readUTF().replace('\0', ' ').trim());
        return e;
    }
    
    public Credencial [] findAll() throws IOException{
        DefaultArrayModel model = new DefaultArrayModel();
        open();
        rCredentials.seek(0);
        int n = rCredentials.readInt();
        if(n <= 0)
            return null;
        
        for (int i = 0; i < n; i++)
        {
            long pos = 4 + i *SIZE;
            rCredentials.seek(pos);
            Credencial e = read();
            model.addElement(e);
        }
        close();
        return Arrays.copyOf(model.toArray(), model.size(), Credencial [].class);
    }
    
    public int lastAccount() throws IOException{
        int c = 0;
        open();
        rCredentials.seek(0);
        int n = rCredentials.readInt();
        if(n <= 0)
            return 0;
        
        for(int i = 0; i < n; i++)
        {
            long pos = 4 + i*SIZE;
            rCredentials.seek(pos);
            if(i == (n-1))
                c = rCredentials.readInt();
        }
        close();
        return c;
    }
    
    public Credencial findById(int id) throws IOException {
        open();
        rCredentials.seek(0);
        int n = rCredentials.readInt();
        if (n <= 0) {
            return null;
        }
        Credencial e = null;
        for (int i = 0; i < n; i++) {
            long pos = 4 + i * SIZE;
            rCredentials.seek(pos);
            int last_id = rCredentials.readInt();
            if (last_id == id) {
                rCredentials.seek(pos);
                e = read();
                close();
                return e;
            }
        }
        close();
        return e;
    }
    
    public Credencial findByUser(String user) throws IOException{
        open();
        rCredentials.seek(0);
        int n = rCredentials.readInt();
        if(n <= 0)
            return null;
        
        Credencial e = null;
        for(int i = 0; i < n; i++)
        {
            long pos = 4+i*SIZE;
            rCredentials.seek(pos);
            String last_User = rCredentials.readUTF();
            if(last_User == user){
                rCredentials.seek(pos);
                e = read();
                close();
                return e;
            }
        }
        close();
        return e;
    }
    
    public List<Credencial> findByName(String name) throws IOException{
        List<Credencial> cList = new ArrayList<>();
        open();
        rCredentials.seek(0);
        int n = rCredentials.readInt();
        if(n <= 0)
            return null;
        
        for(int i = 0; i < n; i++){
            long pos = 4 + i *SIZE;
            rCredentials.seek(pos);
            Credencial e = read();
            if(e.getUser().equalsIgnoreCase(name)){
                cList.add(e);
            }
        }
        close();
        return cList;
    }
    
}
