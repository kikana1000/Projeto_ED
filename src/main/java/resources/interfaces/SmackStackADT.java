/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package resources.interfaces;

public interface SmackStackADT<T> extends StackADT<T>{
    
    /**
     * Removes the last stack element.
     * 
     * @return the element to be removed.
     */
    public T smack();
}
