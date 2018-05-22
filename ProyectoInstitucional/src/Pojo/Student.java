/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojo;

/**
 *
 * @author kevin
 */
public class Student {

    private int IdStudent;
    private String Name;
    private String LastName;
    private String DocId;
    private String Phone;
    private String Address;

    public Student() {
    }

    public Student(int IdStudent, String Name, String LastName, String DocId, String Phone, String Address) {
        this.IdStudent = IdStudent;
        this.Name = Name;
        this.LastName = LastName;
        this.DocId = DocId;
        this.Phone = Phone;
        this.Address = Address;
    }

    public int getIdStudent() {
        return IdStudent;
    }

    public void setIdStudent(int IdStudent) {
        this.IdStudent = IdStudent;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getDocId() {
        return DocId;
    }

    public void setDocId(String DocId) {
        this.DocId = DocId;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    
}
