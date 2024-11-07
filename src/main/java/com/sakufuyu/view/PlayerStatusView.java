package main.java.com.sakufuyu.view;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import main.java.com.sakufuyu.model.Player;
import main.java.com.sakufuyu.model.Monster;

public class PlayerStatusView extends VBox {
    private Label nameLabel;
    private Label moneyLabel;
    private Label monstersLabel;
    private Button closeButton;

    public PlayerStatusView() {
        // Initialize UI component
        nameLabel = new Label();
        moneyLabel = new Label();
        monstersLabel = new Label();

        // Close button
        closeButton = new Button("X");
        closeButton.setStyle(
            "-fx-background-color: #f44336;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 12px;" +
            "-fx-padding: 5px 10px;"
        );
        closeButton.setOnAction(e -> getScene().getWindow().hide());

        // Set style
        this.setSpacing(10);
        this.setPadding(new javafx.geometry.Insets(15));
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");

        // Style labels
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        moneyLabel.setStyle("-fx-font-size: 14px;");
        monstersLabel.setStyle("-fx-font-size: 14px;");

        // Add component
        getChildren().addAll(nameLabel, moneyLabel, monstersLabel, closeButton);
    }

    public void updateStatus(Player player) {
        nameLabel.setText("Player: " + player.getName());
        moneyLabel.setText("Money: " + player.getMoney());

        // Monsters info
        StringBuilder monstersInfo = new StringBuilder("Monsters: \n");
        for (Monster monster: player.getMonsters()) {
            monstersInfo.append(String.format("- %s (HP: %d/%d)\n",
            monster.getName(),
            monster.getHp(),
            monster.getMaxHp()));
        }
        monstersLabel.setText(monstersInfo.toString());
    }
}
