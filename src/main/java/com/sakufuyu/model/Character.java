package main.java.com.sakufuyu.model;

public abstract class Character {
    protected String name;
    protected Type type;
    protected int hp;

    public Character(String name, Type type, int hp) {
        this.name = name;
        this.type = type;
        this.hp = hp;
    }

    // Getters and Setters
}
