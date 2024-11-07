package main.java.com.sakufuyu.controller;

import java.util.Random;

import main.java.com.sakufuyu.model.Monster;
import main.java.com.sakufuyu.model.MonsterData;
import main.java.com.sakufuyu.model.Player;
import main.java.com.sakufuyu.repository.MonsterRepository;
import main.java.com.sakufuyu.view.GameView;

public class GameController {
    private GameView view;
    private MonsterRepository monsterRepository;
    private Player player;
    private int playerX = 0;
    private int playerY = 0;
    private Random random = new Random();
    private BattleController battleController;
    private static final int BATTLE_WIN_REWARD = 100; // Get money when win the battle
    private static final int INITIAL_MONEY = 500; // Money at the begining

    public GameController(GameView view) {
        this.view = view;
        this.monsterRepository = new MonsterRepository(); 
        this.player = new Player("Satoshi");
        this.battleController = new BattleController(this);
        setupKeyHandlers();
        initializePlayer();
    }

    public void initializePlayer() {
        // Give a player an initial monster
        player.addMonster(new Monster(monsterRepository.getMonsterById(0), 5));
        player.setMoney(INITIAL_MONEY);
        view.updatePlayerStatus(player);
    }

    public void setupKeyHandlers() {
        view.getScene().setOnKeyPressed(event -> {
            System.out.println("Key pressed:" + event.getCode()); // For debug output
            switch (event.getCode()) {
                case UP:
                    tryMovePlayer(0, -1);
                    break;
                case DOWN:
                    tryMovePlayer(0, 1);
                    break;
                case LEFT:
                    tryMovePlayer(-1, 0);
                    break;
                case RIGHT:
                    tryMovePlayer(1, 0);
                    break;
                default:
                    break;
            }
        });
    }

    private void tryMovePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        if (newX >= 0 && newX < GameView.MAP_WIDTH && newY >= 0 && newY < GameView.MAP_HEIGHT) {
            System.out.println("newX="+newX+", MAP_WIDTH="+GameView.MAP_WIDTH+", newY="+newY+", MAP_HEIGHT="+GameView.MAP_HEIGHT);
            this.playerX = newX;
            this.playerY = newY;
            view.movePlayer(dx, dy);

            // Check if player can heal monsters
            tryHealAtPoint(newX, newY);

            if (view.getMapView().isGrass(newX, newY)) {
                checkEncounter();
            }
        } else {
            System.out.println("Invalid move attempted.");
            System.out.println("newX="+newX+", MAP_WIDTH="+GameView.MAP_WIDTH+", newY="+newY+", MAP_HEIGHT="+GameView.MAP_HEIGHT);
        }
    }

    public void startGame() {
        // Initialize game state
        view.movePlayer(playerX, playerY); // Set initial position
        view.show();
        System.out.println("Game started. Player at: (" + playerX + ", " + playerY + ")"); // For debug output
    }

    private void checkEncounter() {
        if (random.nextDouble() < 0.39) {
            System.out.println("Wild monster appeared!!");
            battleController.startBattle(player, generateWildMonster());
        }
    }

    private Monster generateWildMonster() {
        MonsterData data = monsterRepository.getRandomMonster();
        int level = 1 + random.nextInt(5);
        return new Monster(data, level);
    }

    public Player getPlayer() {
        return player;
    }

    public void onBattleEnd(boolean playerWon, Monster wildMonster) {
        if (playerWon) {
            // Get a reward
            player.addMoney(BATTLE_WIN_REWARD);
            view.showMessage("You won " + BATTLE_WIN_REWARD + " coins!");
        } else {
            // When lose...
            view.showMessage("You lost the battle...");
            teleportToLastHealPoint(); // back to last heal point
        }
        view.updatePlayerStatus(player);
    }

    // private boolean offerCaputure(Monster wildMonster) {
    //     if (player.getMonsters().size() >= 6) {
    //         view.showMessage("Cannot capture more monsters!");
    //         return false;
    //     }

    //     // Succeede capture or not
    //     double captureRate = 0.5 * (1.0 - (double)wildMonster.getHp()/wildMonster.getMaxHp());
    //     if (Math.random() < captureRate) {
    //         player.captureMonster(wildMonster);
    //         return true;
    //     }
    //     return false;
    // }

    // Healing monster
    private void tryHealAtPoint(int x, int y) {
        if (view.getMapView().isHealing(x, y)) {
            player.healAllMonsters();
            view.showMessage("All monsters have been healed!");

            view.updatePlayerStatus(player);
        }
    }
    // Teleport last healing spot when lose battle
    private void teleportToLastHealPoint() {
        playerX = 5;
        playerY = 5;
        view.movePlayer(5 - playerX, 5 - playerY);
    }
}
