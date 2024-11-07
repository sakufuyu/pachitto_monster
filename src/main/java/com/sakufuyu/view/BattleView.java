package main.java.com.sakufuyu.view;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import main.java.com.sakufuyu.model.Monster;

public class BattleView {
    private Stage battleStage;
    private Scene battleScene;
    private VBox layout;
    private Label playerMonsterInfo;
    private Label wildMonsterInfo;
    private Label battleLog;
    private Button rockButton;
    private Button paperButton;
    private Button scissorsButton;
    private Button throwBallButton;
    private Label ballCountLabel;

    private ImageView playerMonsterImage;
    private ImageView wildMonsterImage;
    private static final double MONSTER_IMAGE_SIZE = 100;

    public BattleView() {
        battleStage = new Stage();
        battleStage.setTitle("Battle!");

        // Main layout of battle screen
        layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        // Area for Info and Images of Monsters
        HBox monsterArea = new HBox(20);
        monsterArea.setAlignment(Pos.CENTER);

        // Player's Monster
        VBox playerSide = new VBox(10);
        playerSide.setAlignment(Pos.CENTER);
        playerMonsterImage = new ImageView();
        playerMonsterImage.setFitWidth(MONSTER_IMAGE_SIZE);
        playerMonsterImage.setFitHeight(MONSTER_IMAGE_SIZE);
        playerMonsterInfo = new Label();
        playerSide.getChildren().addAll(playerMonsterImage, playerMonsterInfo);

        // Wild Monster
        VBox wildSide = new VBox(10);
        wildSide.setAlignment(Pos.CENTER);
        wildMonsterImage = new ImageView();
        wildMonsterImage.setFitWidth(MONSTER_IMAGE_SIZE);
        wildMonsterImage.setFitHeight(MONSTER_IMAGE_SIZE);
        wildMonsterInfo = new Label();
        wildSide.getChildren().addAll(wildMonsterImage, wildMonsterInfo);

        monsterArea.getChildren().addAll(playerSide, wildSide);

        // Battle logs
        battleLog = new Label("Battle Start!!");
        battleLog.setStyle("-fx-font-size: 14px;");

        // Set Battle buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        rockButton = new Button("Rock");
        paperButton = new Button("Paper");
        scissorsButton = new Button("Scissors");
        buttonBox.getChildren().addAll(rockButton, paperButton, scissorsButton);

        // Set throw ball button
        throwBallButton = new Button("Throw Monster Ball");
        throwBallButton.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 5px 10px;"
        );
        ballCountLabel = new Label();
        HBox captureBox =  new HBox(10);
        captureBox.setAlignment(Pos.CENTER);
        captureBox.getChildren().addAll(throwBallButton, ballCountLabel);

        layout.getChildren().addAll(
            monsterArea,
            battleLog,
            buttonBox,
            captureBox
        );

        battleScene = new Scene(layout, 600, 400);
        battleStage.setScene(battleScene);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: #4CAF50;"+
            "-fx-text-fill: white;"+
            "-fx-font-size: 14px;"+
            "-fx-padding: 10px 20px;"
        );
        return button;
    }

    public void showBattle(Monster playerMonster, Monster wildMonster) {
        updateMonsterInfo(playerMonster, wildMonster);
        loadMonsterImages(playerMonster, wildMonster);
        battleLog.setText("A wild " + wildMonster.getName() + " appeared!!");
        battleStage.show();
    }

    private void loadMonsterImages(Monster playerMonster, Monster wildMonster) {
        try {
            // Load player monster image
            String playerImagePath = String.format("/src/main/resources/images/monsters/%s.jpeg", playerMonster.getName());
            File playerFile = new File(new File(".").getCanonicalPath() + playerImagePath);

            if (playerFile.exists()) {
                playerMonsterImage.setImage(new Image(playerFile.toURI().toString()));
            } else {
                // Set default image
                setDefaultMonsterImage(playerMonsterImage);
            }

            // Load wild monster image
            String wildImagePath = String.format("/src/main/resources/images/monsters/%s.jpeg", wildMonster.getName());
            File wildFile = new File(new File(".").getCanonicalPath() + wildImagePath);

            if (wildFile.exists()) {
                wildMonsterImage.setImage(new Image(wildFile.toURI().toString()));
            } else {
                setDefaultMonsterImage(wildMonsterImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Set default image if error occures
            setDefaultMonsterImage(playerMonsterImage);
            setDefaultMonsterImage(wildMonsterImage);
        }
    }

    private void setDefaultMonsterImage(ImageView imageView) {
        try {
            String defaultImagePath = "/src/main/resources/images/monsters/default.jpeg";
            File defaultFile = new File(new File(".").getCanonicalPath() + defaultImagePath);

            if (defaultFile.exists()) {
                imageView.setImage(new Image(defaultFile.toURI().toString()));
            } else {
                System.out.println(defaultFile.toURI().toString() + " file not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMonsterInfo(Monster playerMonster, Monster wildMonster) {
        playerMonsterInfo.setText(
            String.format("%s (HP: %d/%d)",
                playerMonster.getName(),
                playerMonster.getHp(),
                playerMonster.getMaxHp())
        );
        wildMonsterInfo.setText(
            String.format("%s (HP: %d/%d)",
                wildMonster.getName(),
                wildMonster.getHp(),
                wildMonster.getMaxHp())
        );
    }

    public void updateBattleLog(String message) {
        battleLog.setText(message);
    }

    public Button getRockButton() {return rockButton;}
    public Button getPaperButton() {return paperButton;}
    public Button getScissorsButton() {return scissorsButton;}

    public void showMessage(String message, Monster wildMonster) {
        layout.getChildren().clear();

        VBox messageLayout = new VBox(20);
        messageLayout.setAlignment(Pos.CENTER);

        ImageView wildMonsterImage = new ImageView();
        wildMonsterImage.setFitWidth(MONSTER_IMAGE_SIZE);
        wildMonsterImage.setFitHeight(MONSTER_IMAGE_SIZE);

        try {
            String wildImagePath = String.format("/src/main/resources/images/monsters/%d-front.jpeg", wildMonster.getId());
            File wildFile = new File(new File(".").getCanonicalPath() + wildImagePath);

            if (wildFile.exists()) {
                wildMonsterImage.setImage(new Image(wildFile.toURI().toString()));
            } else {
                setDefaultMonsterImage(wildMonsterImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setDefaultMonsterImage(wildMonsterImage);
        }

        // Display monster name
        Label nameLabel = new Label("A wild " + wildMonster.getName() + " appeared!");
        nameLabel.setStyle("-fx-font-size: 16px;");

        // Display message
        Label messageLable = new Label(message);
        messageLable.setStyle("-fx-font-size: 18px; -fx-text-fill:red;");

        // Add elements to layout
        messageLayout.getChildren().addAll(wildMonsterImage, nameLabel, messageLable);
        layout.getChildren().add(messageLayout);

        battleStage.show();
    }

    // Ball trow functions

    public void updateBallCount(int ballCount) {
        ballCountLabel.setText("Monster Balls: " + ballCount);
    }

    public Button getThrowButton() {
        return throwBallButton;
    }

    public void showCaptureResult(boolean success) {
        String message = success ? "Capture successful!" : "Capure failed..";
        battleLog.setText(message);
    }

    public void close() {
        battleStage.close();
    }
}