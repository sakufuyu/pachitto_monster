package main.java.com.sakufuyu.model;

public class Battle {
    private Monster playerMonster;
    private Monster wildMonster;

    public Battle(Monster playerMonster, Monster wildMonster) {
        this.playerMonster = playerMonster;
        this.wildMonster = wildMonster;
    }

    public int calculateDamage(Monster attacker, Monster defender) {
        // Calcurate damage with type compativility
        double typeMultiplier = getTypeMultiplier(attacker.type, defender.type);
        int baseDamage = attacker.getAttack() - defender.getDefense();

        return Math.max(1, (int)(baseDamage * typeMultiplier));
    }

    private double getTypeMultiplier(Type attackerType, Type defenderType) {
        if (attackerType == Type.FIRE && defenderType == Type.GRASS) return 2.0;
        if (attackerType == Type.GRASS && defenderType == Type.WATER) return 2.0;
        if (attackerType == Type.WATER && defenderType == Type.FIRE) return 2.0;
        if (attackerType == defenderType) return 1.0;
        return 0.5;
    }

    public boolean isOver() {
        return playerMonster.getHp() <= 0 || wildMonster.getHp() <= 0;
    }

    public Monster getWinner() {
        if (!isOver()) return null;
        return playerMonster.getHp() > 0 ? playerMonster : wildMonster;
    }

    // Getter
    public Monster getPlayerMonster() {return playerMonster;}
    public Monster getWildMonster() {return wildMonster;}
}
