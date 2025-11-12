package cmpt213.A4.model;
import cmpt213.A4.model.*;
public class Player {
    //private List<>
    private final int DEFAULT_HEALTH = 600;
    private final int MAX_NUM_RINGS = 3;
    private int health;
    //private Rings.Ring[] rings;

    public Player() {
        this.health = DEFAULT_HEALTH;
        //rings = new Rings.Ring[3];
        //rings[0] = new Rings.NullRing();
    }

    public void decreaseHealth(int damageReceived){
        this.health -= damageReceived;
    }

    public int getPlayerHealth(){
        return health;
    }
    public boolean didPlayerLose(){
        return health <= 0;
    }

}
