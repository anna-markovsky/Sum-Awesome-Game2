package cmpt213.A4.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface weaponAbilities {
    void assignWeapon(String weaponName, double[] damagePercentages, WeaponCondition weaponCondition);
    double[] assignDamagePercentages(int colIndex, double damageMain, double damageSide);
    }

public class Weapon implements weaponAbilities{
    private String weaponName;
    private List<Double> percentDamageOpponents = new ArrayList<>();
    private WeaponCondition weaponCondition;
    public Weapon() {

    }

    public String getWeaponName() {
        return weaponName;
    }
    public List<Double> getPercentDamageOpponents() {

        return percentDamageOpponents;
    }

    public WeaponCondition getWeaponCondition() {
        return weaponCondition;
    }

    @Override
    public void assignWeapon(String weaponName, double[] damagePercentages, WeaponCondition weaponCondition) {
        this.weaponName = weaponName;
        this.weaponCondition = weaponCondition;
        //this.percentDamageOpponents.clear();

        for (double dmg : damagePercentages) {
            this.percentDamageOpponents.add(dmg);
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
    private WeaponCondition weaponCondition= new WeaponCondition() {
        @Override
        public boolean isActive(FillConditions fillConditions) {
            return false;
        }
    };

    private List<Double> percentDamageOpponents = new ArrayList<>();

    public String getWeaponName() {
        return weaponName;
    }
    public List<Double> getDamageOpponents() {
        return percentDamageOpponents;
    }
    public WeaponCondition getWeaponCondition() {
        return weaponCondition;
    }

    @Override
    public void assignWeapon(String weaponName, double[] damagePercentages, WeaponCondition weaponCondition) {
    }
    @Override
    public double[] assignDamagePercentages(int colIndex, double damageMain, double damageSide) {
        double[] damages = {0.0, 0.0, 0.0};
        return damages;
    }

}
