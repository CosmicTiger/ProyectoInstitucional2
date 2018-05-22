/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

/**
 *
 * @author Kevin
 */


import java.util.Arrays;

public class DefaultArrayModel {
   private Object[] elements;
  

    public DefaultArrayModel() {
    }
    
    public void addElement(Object t) {
        if(elements == null){
            elements = new Object[1];
            elements[0]=t; 
            return;       
        }
        elements = Arrays.copyOf(elements, elements.length+1);
        elements[elements.length-1]=t;
    }

    
    public int size() {
        return (elements==null)? 0 :elements.length;
    }

    
    public Object[] toArray() {
        return this.elements;
    }
   
    public Object[] get() {
        return elements;
    }
    
   
   
}

