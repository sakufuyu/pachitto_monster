package main.java.com.sakufuyu.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.File;


public class MapView extends Canvas {
    private int[][] map;
    private static final int TILE_SIZE = GameView.TILE_SIZE;
    public static final int ROAD = 0;
    public static final int GRASS = 1;
    public static final int HEALING = 2;

    private Image roadImage;
    private Image grassImage;
    private Image healingImage;

    public MapView(int width, int height) {
        super(width * TILE_SIZE, height * TILE_SIZE);
        map = new int[height][width];

        // Load images
        try {
            String currentPath = new File(".").getCanonicalPath();
            roadImage = new Image(new File(currentPath + "/src/main/resources/images/tiles/road.jpeg").toURI().toString());
            grassImage = new Image(new File(currentPath + "/src/main/resources/images/tiles/grass.jpeg").toURI().toString());
            healingImage = new Image(new File(currentPath + "/src/main/resources/images/tiles/healing.jpeg").toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load images, create map with color");
        }

        // Set temp map data
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 5 && y == 5) {
                    map[y][x] = HEALING;
                } else {
                    map[y][x] = (Math.random() > 0.3) ? ROAD:GRASS; // 0: road, 1: grass
                }
            }
        }
        draw();
    }

    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == ROAD) {
                    if (roadImage != null) {
                        gc.drawImage(roadImage, x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    } else {
                        gc.setFill(javafx.scene.paint.Color.GRAY);
                        gc.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                } else if (map[y][x] == GRASS) {
                    if (grassImage != null) {
                        gc.drawImage(grassImage, x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    } else {
                        gc.setFill(javafx.scene.paint.Color.GREEN);
                        gc.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                } else {
                    if (healingImage != null) {
                        gc.drawImage(healingImage, x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    } else {
                        gc.setFill(javafx.scene.paint.Color.PINK);
                        gc.fillRect(x*TILE_SIZE, y*TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }
    }

    public boolean isGrass(int x, int y) {
        return map[y][x] == GRASS;
    }

    public boolean isHealing(int x, int y) {
        if (x < 0 || x >= map[0].length || y < 0 || y >= map.length) {
            return false;
        }
        return map[y][x] == HEALING;
    }
}
