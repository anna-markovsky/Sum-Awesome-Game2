package cmpt213.A4.model;
import cmpt213.A4.model.*;
public class Player {
    //private List<>
    private final int DEFAULT_HEALTH = 200;
    private final int MAX_NUM_RINGS = 3;
    private int health;
    private int damageReceived = 0;
    private Weapon weaponEquipped;
    private int damageDealt = 0;
    private int numFills;
    //private Rings.Ring[] rings;

    public Player() {
        this.health = DEFAULT_HEALTH;
        this.weaponEquipped = new NullWeapon();
        this.damageDealt = 0;
        this.damageReceived = 0;
        this.numFills = 0;
        //rings = new Rings.Ring[3];
        //rings[0] = new Rings.NullRing();
    }
    public int getNumFills() {

        return numFills;
    }

    public void addNumFills() {
        this.numFills = numFills + 1;
    }
    public void equipWeapon(Weapon weapon) {
        System.out.println("equipping weapon here " + weapon.getWeaponName());
        this.weaponEquipped = weapon;
    }
    public void dropWeapon(){
        this.weaponEquipped = new NullWeapon();
    }

    public Weapon getWeapon() {
        return weaponEquipped;
    }

    public int getDamageReceived() {
        return damageReceived;
    }
    public int getDamageDealt() {
        return damageDealt;
    }
    public void addDamageDealt(int damageDealt) {
        this.damageDealt += damageDealt;
        System.out.println("damage dealt " + this.damageDealt);

    }
    public void decreaseHealth(int damageReceived){

        this.health -= damageReceived;
        this.damageReceived +=  damageReceived;
        System.out.println("damage recieved " + this.damageReceived);

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
