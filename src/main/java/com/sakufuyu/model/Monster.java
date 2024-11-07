package main.java.com.sakufuyu.model;

public class Monster extends Character {
    private int id;
    private int level;
    private int maxHp;
    private int attack;
    private int defense;
    private String description;

    public Monster(MonsterData data) {
        super(data.getName(), data.getType(), data.getBaseHp());
        this.id = data.getId();
        this.level = 1;
        this.maxHp = data.getBaseHp();
        this.attack = data.getBaseAttack();
        this.defense = data.getBaseDefense();
        this.description = data.getDescription();
    }

    public Monster(MonsterData data, int level) {
        this(data);
        this.level = level;
        calculateStats();
    }

    private void calculateStats() {
        maxHp = (hp * level) / 50 + 10;
        hp = maxHp;
        attack = (attack * level) / 50 + 5;
        defense = (defense * level) / 50 + 5;
    }

    // Getters
    public int getId() {return id;}
    public int getLevel() {return level;}
    public int getHp() {return hp;}
    public int getMaxHp() {return maxHp;}
    public int getAttack() {return attack;}
    public int getDefense() {return defense;}
    public String getDescription() {return description;}
    public String getName() {return name;}

    // Setters
    public void setHp(int hp) {this.hp = Math.max(0, hp);}

    public void levelUp() {
        level++;
        calculateStats();
    }

    @Override
    public String toString() {
        return String.format("Monster{name=%s, type=%s, level=%d, hp=%d/%d}",name, type, level, hp, maxHp);
    }
}
