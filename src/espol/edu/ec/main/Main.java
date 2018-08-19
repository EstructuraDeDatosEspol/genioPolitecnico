/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espol.edu.ec.main;

import espol.edu.ec.tda.Abb;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author SSAM
 */
public class Main extends Application{
    Pane root;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Abb ab = new Abb();
        
        stage.setScene(new Scene(ab.getPane(), 600, 400));
        stage.setResizable(false);
        stage.setTitle("Bomb it!");
        stage.show();
    }
    
    
}
