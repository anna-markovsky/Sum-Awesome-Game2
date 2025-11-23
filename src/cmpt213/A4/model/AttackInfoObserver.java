package cmpt213.A4.model;

public interface AttackInfoObserver {
    void getAttackInformation(String weaponName, double[] damageRecieved);
    void getFillAttackInformation(int damageRecieved,Opponent selectedOpponent, int opponentIndex);
}
