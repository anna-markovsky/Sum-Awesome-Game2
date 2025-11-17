package cmpt213.A4.model;

import java.util.*;

public class WeaponManager {
    private Game game;

    public WeaponManager(Game game){
        this.game = game;
        registerAsObserver();
    }

    private void registerAsObserver() {
        game.addObserver(new PlayerAttackObserver() {
            @Override
            public void stateChanged() {
                selectPlayerWeapon();
            }
        });
    }
//TODO handle avoiding selecting dead opponents
    private double[] assignRandomOpponentDamage(double damage) {
        Random random = new Random();
        int index = random.nextInt(3);
        double[] damagePercentages = {};
        for (int i = 0; i < 3; i++) {
            if (index == i) {
                damagePercentages[i] = damage;
            }
            else {
                damagePercentages[i] = 0.0;
            }
        }
        return damagePercentages;
    }


    private void selectPlayerWeapon() {
        FillConditions fillConditions = game.getFillConditions();
        //check if any of the conditions for a weapon were met
        if (fillConditions.isAscending()) {
            System.out.println("frost bow");
            Weapon weapon = new Weapon();
            double[] damagePercentages = {1.0,1.0,1.0};
            weapon.assignWeapon("frost bow", damagePercentages);

        }
        if (fillConditions.isDescending()) {
            System.out.println("diamond sword");
            Weapon weapon = new Weapon();
            double[] damagePercentages = {0.75,1.0,0.75};
            weapon.assignWeapon("diamond sword", damagePercentages);
        }
        if (fillConditions.getNumFills() >= 10) {
            if (fillConditions.getNumFills() >= 15) {
                System.out.println("Fire staff");
                Weapon weapon = new Weapon();
                //TODO make it so that it checks what character was selected
                double[] damagePercentages = {0.5,1.0,0.5};
                weapon.assignWeapon("fire staff", damagePercentages);

            }
            else {
                System.out.println("Stone hammer");
                Weapon weapon = new Weapon();
                double[] damagePercentages = {0.8,0.8,0.8};
                weapon.assignWeapon("fire staff", damagePercentages);

            }
        }
        if (fillConditions.getSecondsTaken() <= 20) {
            if (fillConditions.getSecondsTaken() <= 10) {
                Weapon weapon = new Weapon();
                double[] damagePercentages = assignRandomOpponentDamage(1.0);
                weapon.assignWeapon("lightning wand", damagePercentages);

                System.out.println("seconds taken "+ fillConditions.getSecondsTaken());
                System.out.println("lightning wand");
            }
            else {
                Weapon weapon = new Weapon();
                double[] damagePercentages = assignRandomOpponentDamage(0.5);
                weapon.assignWeapon("sparkle dagger", damagePercentages);

                System.out.println("Sparkle dagger");
            }

        }
        else {
            System.out.println("nothing");
            NullWeapon nullWeapon = new NullWeapon();
            double[] damagePercentages = {0.0,0.0,0.0};
            nullWeapon.assignWeapon("",damagePercentages);
        }

    }
}



