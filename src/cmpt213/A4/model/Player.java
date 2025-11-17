package cmpt213.A4.model;
import cmpt213.A4.model.*;
public class Player {
    //private List<>
    private final int DEFAULT_HEALTH = 200;
    private final int MAX_NUM_RINGS = 3;
    private int health;
    private Weapon weaponEquipped;
    //private Rings.Ring[] rings;

    public Player() {
        this.health = DEFAULT_HEALTH;
        this.weaponEquipped = new NullWeapon();
        //rings = new Rings.Ring[3];
        //rings[0] = new Rings.NullRing();
    }

    public void equipWeapon(Weapon weapon) {

        this.weaponEquipped = weapon;
    }

    public Weapon getWeapon() {
        return weaponEquipped;
    }

    public void decreaseHealth(int damageReceived){
        this.health -= damageReceived;
    }

    public int getPlayerHealth(){
        return health;
    }
    public void resetPlayerHealth() {
        health = DEFAULT_HEALTH;
    }

    public boolean didPlayerLose(){

        return health <= 0;
    }


}
