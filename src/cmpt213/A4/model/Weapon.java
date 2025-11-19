package cmpt213.A4.model;


import java.util.ArrayList;
import java.util.List;

interface weaponAbilities {
    void assignWeapon(String weaponName, double[] damagePercentages);

    }

class Weapon implements weaponAbilities{
    private String weaponName;
    //private double percentDamageLeftOpponent;
    //private double percentDamageMiddleOpponent;
   // private double percentDamageRightOpponent;
    private List<Double> percentDamageOpponents;
    public String getWeaponName() {
        return weaponName;
    }


    @Override
    public void assignWeapon(String weaponName, double[] damagePercentages) {
        System.out.println("MyClass is doing something!");
        this.weaponName = weaponName;
        for (int i = 0 ; i < damagePercentages.length ; i++) {
            this.percentDamageOpponents.add(damagePercentages[i]);
        }
    }

}

class NullWeapon implements weaponAbilities{
    private String weaponName = "";
    private List<Double> percentDamageOpponents = new ArrayList<>();

    public String getWeaponName() {
        return weaponName;
    }


    @Override
    public void assignWeapon(String weaponName, double[] damagePercentages) {
        System.out.println("No weapon selected");
        //this.weaponName = weaponName;
    }

}
