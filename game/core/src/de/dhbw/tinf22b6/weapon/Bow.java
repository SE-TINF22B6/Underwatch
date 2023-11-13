package de.dhbw.tinf22b6.weapon;

public class Bow implements Weapon{

    int power;
    @Override
    public void shoot() {

    }

    @Override
    public double getFireRate() {
        return 1;
    }

    @Override
    public double getDamage() {
        return 0;
    }

    @Override
    public double getCritProp() {
        return 0;
    }

    @Override
    public double getRange() {
        return 0;
    }

    public int getPower(){
        return power;
    }
}
