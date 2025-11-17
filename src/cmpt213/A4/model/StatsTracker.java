package cmpt213.A4.model;

import java.util.*;
public class StatsTracker {
    private List<String> events = Arrays.asList("Equipment Activations", "Matches:", "Total Damage", "Fills Completed");
    private HashMap<String, Integer> equipmentActivations = new HashMap<String, Integer>();
    private HashMap<String, Integer> matches = new HashMap<String, Integer>();
    private HashMap<String, Integer> totalDamage = new HashMap<String, Integer>();
    //private HashMap<String, Integer> fillsCompleted = new HashMap<String, Integer>();
    private int fillsCompleted;
    private int damageDone;
    private int damageReceived;
    private int matchesWon;
    private int matchesLost;
    private Game game;

    public StatsTracker(Game game){
        this.game = game;
        this.fillsCompleted = 0;
        registerAsObserver();
        registerAsMoveObserver();
    }
    public void addFillsCompleted() {
        this.fillsCompleted += 1;
    }
    public void updateAttacks() {
        this.damageDone += game.fillStrength;
        totalDamage.put("Done",damageDone);
        //Add opponent damage here
    }
    public void updateEquipment() {
        Weapon weapon = game.getPlayer().getWeapon();
        equipmentActivations.put(weapon.getWeaponName(), 1);
        //this.equipmentActivations
    }

    private void registerAsObserver() {
        game.addObserver(new PlayerAttackObserver() {
            @Override
            public void stateChanged() {
                updateAttacks();
                updateEquipment();
                printStats();
            }
        });
    }
    private void registerAsMoveObserver() {
        game.addMoveObserver(new PlayerMoveObserver() {
            @Override
            public void stateChanged() {
                addFillsCompleted();
                //printStats();
            }
        });
    }

    //public void updateEquipmentActivations(){

    //}

    public void printStats() {
        int index = 0;
        //while(index < events.size()) {
        System.out.println(events.get(index));
        index++;
        for (String i : equipmentActivations.keySet()) {
            System.out.println("equipment: " + i + " Activated: " + equipmentActivations.get(i));
        }
        System.out.println(events.get(index));
        index++;
        System.out.println(events.get(index) + " " + damageDone);
        index++;
        for (String i : totalDamage.keySet()) {
            System.out.println("equipment: " + i + " Activated: " + equipmentActivations.get(i));
        }
        System.out.println(events.get(index) + " " + fillsCompleted);
        /*for (String i : equipmentActivations.keySet()) {
            System.out.println("equipment: " + i + " Activated: " + equipmentActivations.get(i));
        }
        index++;

        for (String i : matches.keySet()) {
            System.out.println(i + matches.get(i));
        }
        for (String i : totalDamage.keySet()) {
            System.out.println(i + totalDamage.get(i));
        }
        System.out.println("Fills Completed " + totalDamage.get(index));*/
    }
}
