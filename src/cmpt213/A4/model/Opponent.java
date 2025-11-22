package cmpt213.A4.model;

import java.util.*;

public class Opponent {
    private int health;
    private int damageRecieved;
    private int damage;

    public Opponent(int health, int damage){
        this.health = health;
        this.damage = damage;
    }
    public int getHealth(){
        return health;
    }
    public void setHealth(int health){
        this.health = health;
    }
    public int getDamage(){
        return damage;
    }

    public void takeDamage(int damage){
        if (health - damage < 0){

            health = 0;
        }
        else{
            health -= damage;
        }
    }
    //TODO attack player

    public static void attack(){

        System.out.println("Attacking player");
    }
}
