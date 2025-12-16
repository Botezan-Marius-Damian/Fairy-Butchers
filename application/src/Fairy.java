public class Fairy {
    private final int baseGroundDmg;
    private final int baseAirDmg;
    public int maxHp;
    public int hp;
    public double scaling = 1.0;

    public Fairy(int baseGroundDmg, int baseAirDmg, int maxHp) {
        this.baseGroundDmg = baseGroundDmg;
        this.baseAirDmg = baseAirDmg;
        this.maxHp = maxHp;
        this.hp = maxHp;
    }

    public int getGroundDmg() {
        return (int) (baseGroundDmg * scaling);
    }

    public int getAirDmg() {
        return (int) (baseAirDmg * scaling * 1.5);
    }

    public void increaseScaling() {

        scaling += 0.05;
        maxHp = (int) (maxHp * (1 + 0.05));
    }

    public boolean isAlive() {
        return hp > 0;
    }

}