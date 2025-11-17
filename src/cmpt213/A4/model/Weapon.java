package cmpt213.A4.model;


import java.util.ArrayList;
import java.util.List;

interface weaponAbilities {
    void assignWeapon(String weaponName, double[] damagePercentages);
    double[] assignDamagePercentages(int colIndex, double damageMain, double damageSide);
    }

class Weapon implements weaponAbilities{
    private String weaponName;
    //private double percentDamageLeftOpponent;
    //rivate double percentDamageMiddleOpponent;
   // private double percentDamageRightOpponent;
    private List<Double> percentDamageOpponents = new ArrayList<>();
    ;
    public Weapon() {

    }

    public String getWeaponName() {
        return weaponName;
    }
    public List<Double> getDamageOpponents() {
        return percentDamageOpponents;
    }

    @Override
    public void assignWeapon(String weaponName, double[] damagePercentages) {
        //System.out.println("MyClass is doing something!");
        this.weaponName = weaponName;
        for (int i = 0 ; i < damagePercentages.length ; i++) {
            this.percentDamageOpponents.add(damagePercentages[i]);
        }
    }
    @Override
    public double[] assignDamagePercentages(int colIndex, double damageMain, double damageSide) {
        double[] damages = new double[3];
        for (int i = 0 ; i < 3; i++) {
            if (i == colIndex) {
                damages[i] = damageMain;

            }
            else {
                damages[i] = damageSide;
            }
        }
        return damages;
    }

}

class NullWeapon extends Weapon{
    private String weaponName = "";
    private List<Double> percentDamageOpponents = new ArrayList<>();

    public String getWeaponName() {
        return weaponName;
    }
    public List<Double> getDamageOpponents() {
        return percentDamageOpponents;
    }

    @Override
    public void assignWeapon(String weaponName, double[] damagePercentages) {
        System.out.println("No weapon selected");
        //this.weaponName = weaponName;
    }
    @Override
    public double[] assignDamagePercentages(int colIndex, double damageMain, double damageSide) {
        double[] damages = {0.0, 0.0, 0.0};
        return damages;
    }

}
