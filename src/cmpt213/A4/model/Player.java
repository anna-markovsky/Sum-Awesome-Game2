package cmpt213.A4.model;
import cmpt213.A4.model.*;
/**
 * A class for representing the player and handles operations such as equipping gear, updating health/damage
 * dealt and checking if win/lose condition is met.
 */
public class Player {
    private final int DEFAULT_HEALTH = 400;
    private final int MAX_NUM_RINGS = 3;
    private int health;
    private int damageReceived = 0;
    private Weapon weaponEquipped;
    private int damageDealt = 0;
    private int numFills;

    public Player() {
        NullWeapon nullWeapon = new NullWeapon();
        nullWeapon.assignWeapon(
                "",
                new double[]{0.0, 0.0, 0.0},
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return false;
                    }
                });
        this.health = DEFAULT_HEALTH;
        this.weaponEquipped = nullWeapon;
        this.damageDealt = 0;
        this.damageReceived = 0;
        this.numFills = 0;
    }
    public int getNumFills() {
        return numFills;
    }
    public void addNumFills() {
        this.numFills = numFills + 1;
    }
    public void equipWeapon(Weapon weapon) {
        this.weaponEquipped = weapon;
    }
    public void dropWeapon(){
        this.weaponEquipped = new NullWeapon();
    }
    public Weapon getWeapon() {
        return weaponEquipped;
    }
    public String outputWeaponInventory () {
        return weaponEquipped.getWeaponName();
    }
    public int getDamageReceived() {
        return damageReceived;
    }
    public int getDamageDealt() {
        return damageDealt;
    }
    public void addDamageDealt(int damageDealt) {
        this.damageDealt += damageDealt;
    }
    public void decreaseHealth(int damageReceived){
            this.health -= damageReceived;
            this.damageReceived += damageReceived;
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
