package main.java.com.sakufuyu;

import main.java.com.sakufuyu.controller.GameController;
import main.java.com.sakufuyu.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    private GameView view;
    private GameController controller;

    @Override
    public void start(Stage primaryStage)
    {
        try {
            view = new GameView(primaryStage);
            controller = new GameController(view);
            controller.startGame();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
