package main.java.com.sakufuyu.controller;

import java.util.Map;
import java.util.Random;

import main.java.com.sakufuyu.model.Battle;
import main.java.com.sakufuyu.view.BattleView;
import main.java.com.sakufuyu.view.GameView;
import main.java.com.sakufuyu.model.Monster;
import main.java.com.sakufuyu.model.Player;

public class BattleController {
    private BattleView battleView;
    private Battle battle;
    private Random random;
    private GameController gameController;
    private static final String[] CPU_CHOICES = {"Rock", "Scissors", "Paper"};
    private static final String MONSTER_BALL = "Monster Ball";
    private static final double BASE_CAPTURE_RATE = 0.4;

    public BattleController(GameController gameController) {
        this.battleView = new BattleView();
        this.random = new Random();
        this.gameController = gameController;

        setupBattleControls();
    }

    public void startBattle(Player player, Monster wildMonster) {
        if (!player.hasActivemonster()) {
            battleView = new BattleView();
            battleView.showMessage("No monsters available to battle...", wildMonster);

            // Close the battle window
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    javafx.application.Platform.runLater(() -> battleView.close());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            return;
        }

        battle = new Battle(player.getActiveMonster(), wildMonster);
        battleView.showBattle(player.getActiveMonster(), wildMonster);
        setupBattleControls();
    }

    private void setupBattleControls() {
        battleView.getRockButton().setOnAction(e -> performTurn("Rock"));
        battleView.getPaperButton().setOnAction(e -> performTurn("Paper"));
        battleView.getScissorsButton().setOnAction(e -> performTurn("Scissors"));
        battleView.getThrowButton().setOnAction(e -> attemptCapture());
        updateBallCount();
    }

    private void performTurn(String playerChoice) {
        String cpuChoice = CPU_CHOICES[random.nextInt(3)];

        // 0 = win, 1 = draw, 2 = lose
        int result = isPlayerWinJanken(playerChoice, cpuChoice);

        // Calcurate damage with the result
        if (result == 0) {
            int damage = battle.calculateDamage(
                battle.getPlayerMonster(),
                battle.getWildMonster()
            );
            battle.getWildMonster().setHp(
                battle.getWildMonster().getHp() - damage
            );

            battleView.updateBattleLog(
                String.format("Your %s's attack! %d damage to wild %s!!",
                    battle.getPlayerMonster().getName(),
                    damage,
                    battle.getWildMonster().getName())
            );
        } else if (result == 1) {
            int damage = 1;
            battle.getWildMonster().setHp(
                battle.getWildMonster().getHp() - damage
            );
            battle.getPlayerMonster().setHp(
                battle.getPlayerMonster().getHp() - damage
            );

            battleView.updateBattleLog("Draw!!");
        } else {
            int damage = battle.calculateDamage(
                battle.getWildMonster(),
                battle.getPlayerMonster()
            );
            battle.getPlayerMonster().setHp(
                battle.getPlayerMonster().getHp() - damage
            );

            battleView.updateBattleLog(
                String.format("Wild %s's attack! %d damage to your %s!!",
                    battle.getWildMonster().getName(),
                    damage,
                    battle.getPlayerMonster().getName())
            );
        }

        // Update monsters info
        battleView.updateMonsterInfo(
            battle.getPlayerMonster(),
            battle.getWildMonster()
        );

        // Check if battle is end
        if (battle.isOver()) {
            endBattle(battle.getWinner() == battle.getPlayerMonster());
        }
    }

    private int isPlayerWinJanken(String playerChoice, String cpuChoice) {
        if (playerChoice.equals(cpuChoice)) return 1;
        if ((playerChoice.equals("Rock") && cpuChoice.equals("Scissors")) ||
            (playerChoice.equals("Scissors") && cpuChoice.equals("Paper")) ||
            (playerChoice.equals("Paper") && cpuChoice.equals("Rock"))) {
                return 0;
            }
        return 2;
    }

    private void endBattle(boolean isWin) {
        battleView.updateBattleLog(isWin ? "You Win!!":"You lose...");

        Monster wildMonster = battle.getWildMonster();

        // Wait few seconds and close the window
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                javafx.application.Platform.runLater(() -> {
                    battleView.close();
                    gameController.onBattleEnd(isWin, wildMonster);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateBallCount() {
        int ballCount = 0;
        Map<String, Integer> items = gameController.getPlayer().getItems();
        if (items.containsKey(MONSTER_BALL)) {
            ballCount = items.get(MONSTER_BALL);
        }
        battleView.updateBallCount(ballCount);
    }

    private void attemptCapture() {
        Player player = gameController.getPlayer();
        if (!player.hasItem(MONSTER_BALL)) {
            battleView.updateBattleLog("No Monster Balls left!");
            return;
        }
        player.useItem(MONSTER_BALL);
        updateBallCount();

        Monster wildMonster = battle.getWildMonster();
        boolean captureSuccess = calculateCaptureSuccess(wildMonster);

        if (captureSuccess) {
            player.captureMonster(wildMonster);
            battleView.showCaptureResult(true);
            endBattle(true);
        } else {
            battleView.showCaptureResult(false);
        }
    }

    private boolean calculateCaptureSuccess(Monster wildMonster) {
        // Wild monster HP is low, possibility to succeede capturing increase
        double hpRatio = (double)wildMonster.getHp()/wildMonster.getMaxHp();
        double captureRate = BASE_CAPTURE_RATE * (2.0 * hpRatio);
        return Math.random() < captureRate;
    }
}
