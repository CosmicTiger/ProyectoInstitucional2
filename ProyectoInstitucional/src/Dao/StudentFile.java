/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Pojo.Student;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author kevin
 */
public class StudentFile {
    
    public static final int SIZE = 331;
    private RandomAccessFile raf;
    private File file;

    public StudentFile() {
    }


    private void open() throws IOException {
        file = new File("Student.dat");
        if (!file.exists()) {
            file.createNewFile();
            raf = new RandomAccessFile(file, "rw");
            raf.seek(0);
            raf.writeInt(0);
        } else {
            raf = new RandomAccessFile(file, "rw");
        }
    }

    private void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }

    private String setStringLength(String text, int size) {
        StringBuilder buffer;
        if (text == null) {
            buffer = new StringBuilder(size);
        } else {
            buffer = new StringBuilder(text);
            buffer.setLength(size);
        }
        return buffer.toString();
    }

    public void create(Student t) throws IOException {
        open();
        raf.seek(0);
        int n = raf.readInt();
        long pos = 4 + n * SIZE;
        raf.seek(pos);
        raf.writeInt(t.getIdStudent());
        raf.writeUTF(setStringLength(t.getName(), 15));
        raf.writeUTF(setStringLength(t.getLastName(), 15));
        raf.writeUTF(setStringLength(t.getDocId(), 16));
        raf.writeUTF(setStringLength(t.getPhone(), 10));
        raf.writeUTF(setStringLength(t.getAddress(), 100));
        raf.seek(0);
        raf.writeInt(++n);
        close();
    }

    public void update(Student t) throws IOException {
        open();
        raf.seek(0);
        int n = raf.readInt();
        if (n <= 0) {
            return;
        }
        for (int i = 0; i < n; i++) {
            long pos = 4 + i * SIZE;
            raf.seek(pos);
            int id_temp = raf.readInt();
            if (t.getIdStudent()== id_temp) {
                raf.writeUTF(setStringLength(t.getName(), 15));
                raf.writeUTF(setStringLength(t.getLastName(), 15));
                raf.writeUTF(setStringLength(t.getDocId(), 16));
                raf.writeUTF(setStringLength(t.getPhone(), 10));
                raf.writeUTF(setStringLength(t.getAddress(), 100));
                close();
                break;
            }
        }
    }

    public void delete(Student t) throws IOException {
        open();
        raf.seek(0);
        int n = raf.readInt();
        if (n <= 0) {
            return;
        }
        File temp_file = new File("tempStudent.dat");
        temp_file.createNewFile();
        RandomAccessFile temp_raf = new RandomAccessFile(temp_file, "rw");
        temp_raf.seek(0);
        temp_raf.writeInt(0);
        int j = 0;
        for (int i = 0; i < n; i++) {
            long pos = 4 + i * SIZE;
            raf.seek(pos);
            Student p = read();
            if (p.getIdStudent()!= t.getIdStudent()) {
                long index = 4 + j * SIZE;
                temp_raf.seek(index);
                temp_raf.writeInt(p.getIdStudent());
                temp_raf.writeUTF(setStringLength(p.getName(), 15));
                temp_raf.writeUTF(setStringLength(p.getLastName(), 15));
                temp_raf.writeUTF(setStringLength(p.getDocId(), 16));
                temp_raf.writeUTF(setStringLength(p.getPhone(), 10));
                temp_raf.writeUTF(setStringLength(p.getAddress(), 100));
                j++;
            }
        }
        temp_raf.seek(0);
        temp_raf.writeInt(--n);
        close();
        temp_raf.close();
        try {
            if (file.delete()) {
                System.out.println("se elimino el archivo");
            }
        } catch (SecurityException ex) {
            System.err.println("error al eliminar" + ex.getMessage());
        } finally {
            if (temp_file.renameTo(new File("Client.dat"))) {
                System.out.println("se renombro");
            }
        }
    }

    public Student[] findAll() throws IOException {
        DefaultArrayModel model = new DefaultArrayModel();
        open();
        raf.seek(0);
        int n = raf.readInt();
        if (n <= 0) {
            return null;
        }
        for (int i = 0; i < n; i++) {
            long pos = 4 + i * SIZE;
            raf.seek(pos);
            Student e = read();
            model.addElement(e);
        }
        close();
        return Arrays.copyOf(model.toArray(), model.size(), Student[].class);
    }

    public int finalAccount() throws IOException {
        int c = 0;
        open();
        raf.seek(0);
        int n = raf.readInt();
        if (n <= 0) {
            return 0;
        }
        for (int i = 0; i < n; i++) {
            long pos = 4 + i * SIZE;
            raf.seek(pos);
            if (i == (n - 1)) {
                c = raf.readInt();
            }
        }
        close();
        return c;
    }

    public Student findById(int id) throws IOException {
        open();
        raf.seek(0);
        int n = raf.readInt();
        if (n <= 0) {
            return null;
        }
        Student e = null;
        for (int i = 0; i < n; i++) {
            long pos = 4 + i * SIZE;
            raf.seek(pos);
            int last_id = raf.readInt();
            if (last_id == id) {
                raf.seek(pos);
                e = read();
                close();
                return e;
            }
        }
        close();
        return e;
    }

    private Student read() throws IOException {
        Student e = new Student();
        e.setIdStudent(raf.readInt());
        e.setName(raf.readUTF().replace('\0', ' ').trim());
        e.setLastName(raf.readUTF().replace('\0', ' ').trim());
        e.setDocId(raf.readUTF().replace('\0', ' ').trim());
        e.setPhone(raf.readUTF().replace('\0', ' ').trim());
        e.setAddress(raf.readUTF().replace('\0', ' ').trim());
        return e;
    }

    public List<Student> findByName(String name) throws IOException {
        List<Student> elist = new ArrayList<>();
        open();
        raf.seek(0);
        int n = raf.readInt();
        if (n <= 0) {
            return null;
        }
        for (int i = 0; i < n; i++) {
            long pos = 4 + i * SIZE;
            raf.seek(pos);
            Student e = read();
            if (e.getName().equalsIgnoreCase(name)) {
                elist.add(e);
            }
        }
        close();

        return elist;
    }
}
