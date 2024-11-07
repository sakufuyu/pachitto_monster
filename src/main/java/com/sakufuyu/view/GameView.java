package main.java.com.sakufuyu.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import main.java.com.sakufuyu.model.Player;

public class GameView {
    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 15;
    public static final int TILE_SIZE = 20;
    public static final int SCENE_WIDTH = MAP_WIDTH * TILE_SIZE;
    public static final int SCENE_HEIGHT = MAP_HEIGHT * TILE_SIZE;
    private static final int HEADER_HEIGHT = 40;

    private Stage stage;
    private Scene scene;
    private StackPane gameArea;
    private BorderPane root;
    private MapView mapView;
    private PlayerView playerView;
    public PlayerStatusView playerStatusView;
    private Stage statusStage;
    private Button statusButton;

    public GameView(Stage stage) {
        this.stage = stage;

        // Create main layout
        root = new BorderPane();

        // Create header
        HBox header = createHeader();
        root.setTop(header);

        // Create game area
        gameArea = new StackPane();
        mapView = new MapView(MAP_WIDTH, MAP_HEIGHT);
        playerView = new PlayerView(0, 0);
        gameArea.getChildren().addAll(mapView, playerView);
        root.setCenter(gameArea);

        // Set status window
        statusStage = new Stage();
        statusStage.setTitle("Player Status");
        playerStatusView = new PlayerStatusView();
        Scene statusScene = new Scene(playerStatusView, 300, 400);
        statusStage.setScene(statusScene);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT + HEADER_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Pachitto Monster");
    }

    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setPrefHeight(HEADER_HEIGHT);
        header.setPadding(new Insets(5, 10, 5, 10));
        header.setStyle(
            "-fx-background-color: #333333;" +
            "-fx-border-color: #666666;" +
            "-fx-border-width: 0 0 1 0;"
        );

        // Create status button
        statusButton = new Button("Status");
        statusButton.setStyle(
            "-fx-background-color: #4CF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 5px 10px;" +
            "-fx-cursor: hand;"
        );

        // Hobar effect
        statusButton.setOnMouseEntered(e ->
            statusButton.setStyle(
                "-fx-background-color: #45a049;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 5px 15px;" +
                "-fx-cursor: hand;"
            )
        );

        statusButton.setOnMouseExited(e -> 
            statusButton.setStyle(
                "-fx-background-color: #4CAF50;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 5px 15px;" +
                "-fx-cursor: hand;"
            )
        );

        statusButton.setOnAction(e -> toggleStatusWindow());

        header.getChildren().add(statusButton);
        return header;
    }

    private void toggleStatusWindow() {
        if (statusStage.isShowing()) {
            statusStage.close();
        } else {
            // Add player status display
            // playerStatusView = new PlayerStatusView();
            // playerStatusView.setTranslateX(10);
            // playerStatusView.setTranslateY(10);
            // root.getChildren().add(playerStatusView);
            statusStage.setX(stage.getX() + stage.getWidth());
            statusStage.setY(stage.getY());
            statusStage.show();
        }
    }

    public void show() {
        stage.show();
    }

    public Scene getScene() {
        return scene;
    }

    public void movePlayer(int dx, int dy) {
        playerView.move(dx, dy);
        System.out.println("Move player called: dx=" + dx + ", dy=" + dy);  // For debug output
    }

    public MapView getMapView() {
        return mapView;
    }

    public void updatePlayerStatus(Player player) {
        playerStatusView.updateStatus(player);
    }

    public void showMessage(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
