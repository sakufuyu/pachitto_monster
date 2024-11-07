package main.java.com.sakufuyu.repository;

import main.java.com.sakufuyu.model.MonsterData;
import main.java.com.sakufuyu.model.Type;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MonsterRepository {
    private Map<Integer, MonsterData> monstersById = new HashMap<>();
    private Random random = new Random();

    public MonsterRepository() {
        loadMonsters();
    }

    private void loadMonsters() {
        String filePath = "src/main/resources/data/monsters.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip header
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                MonsterData monster = new MonsterData(
                    Integer.parseInt(data[0]),  // id
                    data[1],                    // name
                    Type.valueOf(data[2]),      // type
                    Integer.parseInt(data[3]),  // baseHp
                    Integer.parseInt(data[4]),  // baseAttack
                    Integer.parseInt(data[5]),  // baseDefense
                    data[6]                     // description
                );
                monstersById.put(monster.getId(), monster);
            }
        } catch (IOException e) {
            System.err.println("Error reading monsters.csv:" + e.getMessage());
            // read default monster data
            initializeDefaultMonsters();
        }

        // Read CSV file successfly but no data in the file
        // Load default monster
        if (monstersById.isEmpty()) {
            initializeDefaultMonsters();
        }
    }

    private void initializeDefaultMonsters() {
        addMonster(new MonsterData(0,"Pachichu",Type.FIRE,30,30,30,"Simple rat pachimon."));
    }

    private void addMonster(MonsterData monster) {
        monstersById.put(monster.getId(), monster);
    }

    public MonsterData getMonsterById(int id) {
        return monstersById.get(id);
    }

    public MonsterData getRandomMonster() {
        if (monstersById.isEmpty()) {
            return getMonsterById(0);
        }
        return monstersById.get(random.nextInt(monstersById.size()));
    }

    public List<MonsterData> getAllMonsters() {
        return new ArrayList<>(monstersById.values());
    }
}
