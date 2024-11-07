package main.java.com.sakufuyu.model;

public class MonsterData {
    private int id;
    private String name;
    private Type type;
    private int baseHp;
    private int baseAttack;
    private int baseDefense;
    private String description;

    public MonsterData(int id, String name, Type type, int baseHp, int baseAttack, int baseDefense, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.baseHp = baseHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.description = description;
    }

    // Getters and Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public Type getType() {return type;};

    public int getBaseHp() {return baseHp;}
    public void setBaseHp(int baseHp) {this.baseHp = baseHp;}

    public int getBaseAttack() {return baseAttack;}
    public void setBaseAttack(int baseAttack) {this.baseAttack = baseAttack;}

    public int getBaseDefense() {return baseDefense;}
    public void setBaseDefense(int baseDefense) {this.baseDefense = baseDefense;}

    public String getDescription() {return description;}
}
