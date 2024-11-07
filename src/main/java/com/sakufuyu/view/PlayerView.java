package main.java.com.sakufuyu.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.File;

public class PlayerView extends Canvas {
    private int x, y;
    private final int SIZE = GameView.TILE_SIZE;
    private Image playerImage;

    public PlayerView(int startX, int startY) {
        super(GameView.SCENE_WIDTH, GameView.SCENE_HEIGHT);
        this.x = startX;
        this.y = startY;

        try {
            String currentPath = new File(".").getCanonicalPath();
            playerImage = new Image(new File(currentPath + "/src/main/resources/images/characters/player.jpeg").toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load image, create player with circle");
        }
        draw();
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
        draw();
        System.out.println("Player move to: (" + x + ", " + y + ")"); // For debug outpu
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        if (playerImage != null) {
            gc.drawImage(playerImage, x*SIZE, y*SIZE, SIZE, SIZE);
        } else {
            gc.setFill(javafx.scene.paint.Color.RED);
            gc.fillOval(x * SIZE, y * SIZE, SIZE, SIZE);
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        draw();
    }
}
