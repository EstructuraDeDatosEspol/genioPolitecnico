/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.tda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author SSAM
 */
public class Abb{
    private String siguiente = "Siguiente";
    private Node<Item> root;
    private ArrayList<Item> lista;
    private Deque<Node<Item>> pila;
    VBox pane;
    private Label pregunta;
    private Label primero;
    private TextField cuadro;
    private Button boton2;
    private HBox respuestas;
    private Button si;
    private Button no;
    Scanner s = new Scanner(System.in);
    
    
    public Abb(){
        pane = new VBox(30);
        pane.setId("principal");
        pane.getStylesheets().add(Abb.class.getResource("/espol/edu/ec/tda/Estilos.css").toExternalForm());
        disenio();
        
        lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"));){
            String linea;
            while((linea = br.readLine()) != null){
                lista.add(new Item(linea.substring(0,2), linea.substring(3,linea.length())));
            }
        }catch(Exception e){
            e.getStackTrace();
        }  
        
        add(lista);
        accioneS();
    }
    
    private void disenio(){
        Label titulo = new Label("Genio Politecnico");
        titulo.setId("welcome-text");
                
        primero = new Label("Primero piense en un animal que yo trataré de adivinarlo...");
        pregunta = new Label("Click en siguiente cuando este listo!");
        cuadro = new TextField();
        cuadro.setVisible(false);
        
        VBox cuerpo = new VBox(10);        
        cuerpo.getChildren().addAll(primero, pregunta, cuadro);
        cuerpo.setMinSize(400, 200);
        cuerpo.setAlignment(Pos.CENTER);
        
        boton2 = new Button(siguiente);
        si = new Button("Si");
        no = new Button("No");
        respuestas = new HBox(30);
        respuestas.setId("respuestas");
        respuestas.getChildren().addAll(si,no);
        pane.getChildren().addAll(titulo, cuerpo, boton2);
    }
    
    private void accioneS(){
        boton2.setOnAction(e-> {
            pregunta.setText("si o no");
            //cuadro.setVisible(true);
            boton2.setText("Responder");
            accioneP(root);
            pane.getChildren().remove(boton2);
            pane.getChildren().add(respuestas);
        });
    }
    
    private void accioneP(Node<Item> r){
        primero.setText(r.getData().getTexto());
        
        si.setOnAction(e -> {
            cuadro.setText("");
            if(r.getLeft().getLeft() == null){
                accionRespueta(r,"i");
            }else
                accioneP(r.getLeft());
        });
        no.setOnAction(e -> {
            cuadro.setText("");
            if(r.getRight().getRight()== null){
                accionRespueta(r,"d");
            }else
                accioneP(r.getRight());
        });
    }
    
    private void accionRespueta(Node<Item> r, String d){
        Node<Item> it = null;
        if(d.equals("i")) it = r.getLeft();
        else it = r.getRight();
        primero.setText("Es "+ it.getData().getTexto()+"? ");
        
        si.setOnAction(e -> {
            cuadro.setText("");
            primero.setText("Eh adivinado!");
            cuadro.setText("");
            //cuadro.setVisible(false);
            pregunta.setVisible(false);
            reseteo();
        });
        no.setOnAction(e -> {
            cuadro.setText("");
            primero.setText("Boo!");
            pregunta.setText("Ayudame a mejorar mi prediccion!");
            //cuadro.setVisible(false);
            nuevaPregunta(r,d);
        });
    }
    
    private void reseteo(){
        boton2.setText("Repetir");
        pane.getChildren().remove(respuestas);
        pane.getChildren().add(boton2);
        
        boton2.setOnAction(e -> {
            primero.setText("Primero piense en un animal que yo trataré de adivinarlo...");
            pregunta.setVisible(true);
            pregunta.setText("Click en siguiente cuando este listo!");
            boton2.setText(siguiente);
            accioneS();
        });        
    }
    
    private void nuevaPregunta(Node<Item> r,String d){
        boton2.setText("Si, Encantado!");
        pane.getChildren().remove(respuestas);
        pane.getChildren().add(boton2);
        
        boton2.setOnAction(e -> {            
            pregunta.setVisible(false);
            primero.setText("Que animal estabas pensando?");
            ingresoAnimal(r,d);
            cuadro.setVisible(true);
            boton2.setText(siguiente);
        });
    }
    
    private void ingresoAnimal(Node<Item> r, String d){
        boton2.setOnAction(e -> {
            String animal = cuadro.getText();
            if(!animal.equals("")){
                Node<Item> it = null;
                if(d.equals("i")) it = r.getLeft();
                else it = r.getRight();
                primero.setText("Escribe una pregunta que permita diferenciar entre "+animal+" y "+it.getData().getTexto());
                laRespuestaEs(r,d,animal);
                cuadro.setText("");
            }            
        });
    }
    
    private void laRespuestaEs(Node<Item> r, String d, String animal){
        boton2.setOnAction(e -> {
            String preg = cuadro.getText();
            if(!preg.equals("")){
                primero.setText("\nPara un "+animal+", la respuesta a la pregunta: \""+preg+"\" es:");

                pane.getChildren().remove(boton2);
                pane.getChildren().add(respuestas);

                pregunta.setText("si o no");
                pregunta.setVisible(true);

                cuadro.setVisible(false);
                si.setOnAction(f->{
                    agregarAnimal(r,d,animal,preg, true);
                });
                no.setOnAction(f->{
                    agregarAnimal(r,d,animal,preg, false);
                });
            }
        });
    }
    
    private void agregarAnimal(Node<Item> r, String d, String animal, String preguntaN, boolean respuesta){
        Node<Item> it = null;
        if(d.equals("i")) it = r.getLeft();
        else it = r.getRight();

        Node<Item> nuevo2 = new Node<>(new Item("#R",animal));
        Node<Item> nuevoP = new Node<>(new Item("#P",preguntaN));
        if(d.equals("i")) r.setLeft(nuevoP);
        else r.setRight(nuevoP);

        if(respuesta){
            nuevoP.setLeft(nuevo2);
            nuevoP.setRight(it);
        }
        else{
            nuevoP.setRight(nuevo2);
            nuevoP.setLeft(it);
        }

        cuadro.setText("");
        pregunta.setVisible(false);
        
        primero.setText("Gracias, he aprendido algo nuevo!");
        guardar();
        reseteo();
    }
    
    public Pane getPane(){
        return pane;
    }
    
    public void add(List<Item> lista){
        pila = new LinkedList<>();
        for(Item i: lista){
            add(new Node<Item>(i));
        }
    }
    
    private void add(Node<Item> n){
        if(root == null){
            root = n;
            pila.push(n);
        }else{
            if(pila.peek().getLeft() == null && !pila.peek().getData().getTipo().equals("#R")){
                pila.peek().setLeft(n);
            }else{
                Node<Item> nodo = buscarDerecha();
                nodo.setRight(n);
            }
        }
        pila.push(n);
    }
    
    private Node<Item> buscarDerecha(){
        Deque<Node<Item>> copia = new LinkedList<>();
        Deque<Node<Item>> copia2 = new LinkedList<>();
        
        while(!pila.isEmpty()){
            Node<Item> nodo = pila.pop();
            copia2.push(nodo);
        }
        while(!copia2.isEmpty()){
            Node<Item> nodo = copia2.pop();
            copia.push(nodo);
            pila.push(nodo);
        }
        
        Node<Item> prueba = copia.pop();
        while(prueba.getRight() != null || prueba.getData().getTipo().equals("#R")){
            prueba = copia.pop();
        }
        return prueba;
    }
    
    public void guardar(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("datos.txt"));){
            guardar(root, bw);
        }catch(Exception e){
            e.getStackTrace();
        }
    }
    
    private void guardar(Node<Item> r, BufferedWriter bw) throws IOException{
        if(r == null)return ;
        bw.write(r.getData().getTipo()+" "+r.getData().getTexto());
        bw.newLine();
        guardar(r.getLeft(), bw);
        guardar(r.getRight(), bw);
    }
    
    @Override
    public String toString(){
        if(root!=null){
            return preOrden(root);
        }
        return "";
    }
    
    private String preOrden(Node<Item> r){
        if(r == null)return "";
        StringBuilder sb = new StringBuilder();
        sb.append(r.getData());
        sb.append("\n");
        sb.append(preOrden(r.getLeft()));
        sb.append(preOrden(r.getRight()));
        return sb.toString();
    }
}
