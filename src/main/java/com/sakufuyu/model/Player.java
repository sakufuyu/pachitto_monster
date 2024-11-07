package main.java.com.sakufuyu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String name;
    private List<Monster> monsters;
    private int money;
    private Map<String, Integer> items;
    private static final int MAX_MONSTERS = 6;

    public Player(String name) {
        this.name = name;
        this.monsters = new ArrayList<>();
        this.money = 0;
        this.items = new HashMap<>();

        this.items.put("Monster Ball", 10);
        this.items.put("Potion", 10);
    }

    // Getter methods
    public String getName() {
        return name;
    }
    public List<Monster> getMonsters() {
        return monsters;
    }
    public int getMoney() {
        return money;
    }
    public Map<String, Integer> getItems() {
        return items;
    }

    // Setter methods
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public void addItem(String itemName) {
        int count = items.get(itemName);
        items.put(itemName, count + 1);
    }
    public void removeItem(String item) {
        items.remove(item);
    }

    // Other methods
    public Monster getActiveMonster() {
        // Return the first monster in the list
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getHp() > 0) return monsters.get(i);
        }
        return null;
    }

    public void switchActiveMonster(int index) {
        if (index >= 0 && index < monsters.size()) {
            Monster temp = monsters.get(0);
            monsters.set(0, monsters.get(index));
            monsters.set(index, temp);
        }
    }

    // Catch wild monster
    public boolean captureMonster(Monster monster) {
        if (monsters.size() >= MAX_MONSTERS) {
            return false;
        }
        monsters.add(monster);
        return true;
    }

    // Release monster
    public boolean releaseMonster(int index) {
        int monster_size = monsters.size();
        if (index < 0 || index >= monster_size || monster_size <= 1) {
            return false;
        }
        monsters.remove(index);
        return true;
    }

    // Recovery all monsters
    public void healAllMonsters() {
        for (Monster monster: monsters) {
            monster.setHp(monster.getMaxHp());
        }
    }

    public boolean hasActivemonster() {
        for (Monster monster: monsters) {
            if (monster.getHp() > 0) return true;
        }
        return false;
    }

    // Increase money
    public void addMoney(int amount) {
        this.money += amount;
    }

    // Reduce money
    public boolean spendMoney(int amount) {
        if (this.money >= amount) {
            this.money -= amount;
            return true;
        }
        return false;
    }

    // Change monster order
    public boolean swapMonsters(int index1, int index2) {
        int monsters_size = monsters.size();
        if (index1 < 0 || index1 >= monsters_size || index2 < 0 || index2 >= monsters_size) {
            return false;
        }

        Monster temp = monsters.get(index1);
        monsters.set(index1, monsters.get(index2));
        monsters.set(index2, temp);

        return true;
    }

    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Player: ").append(name).append("\n");
        status.append("Money: ").append(money).append("\n");
        status.append("Monsters:\n");
        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            status.append(String.format("%d: %s (HP: %d/%d)\n", 
                i + 1, m.getName(), m.getHp(), m.getMaxHp()));
        }
        return status.toString();
    }


    // Items related methods

    public boolean hasItem(String itemName) {
        return items.containsKey(itemName) && items.get(itemName) > 0;
    }

    public boolean useItem(String itemName) {
        if (hasItem(itemName)) {
            int count = items.get(itemName);
            items.put(itemName, count - 1);

            if (items.get(itemName) <= 0) {
                items.remove(itemName);
            }
            return true;
        }
        return false;
    }
}
