/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

/**
 *
 * @author SSAM
 */
public class Item {
    private String tipo;
    private String texto;

    public Item(String tipo, String texto) {
        this.tipo = tipo;
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        return 7;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item))return false;
        Item nuevo = (Item)o;
        return nuevo.getTipo().equals(tipo) && nuevo.getTexto().equals(texto);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(tipo);
        sb.append(" ");
        sb.append(texto);
        return sb.toString();
    }
}