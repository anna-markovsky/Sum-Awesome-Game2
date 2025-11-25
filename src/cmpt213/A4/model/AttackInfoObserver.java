package cmpt213.A4.model;
/**
 * Interface for observers to implement to be able to observe
 * and retrieve information about the attack.
 */
public interface AttackInfoObserver {
    void getAttackInformation(String weaponName, double[] damageRecieved);
    void getFillAttackInformation(int damageRecieved,Opponent selectedOpponent, int opponentIndex);
}
