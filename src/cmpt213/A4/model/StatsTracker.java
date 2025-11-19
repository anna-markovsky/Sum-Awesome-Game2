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
        registerAsMatchObserver();
    }
    public void addFillsCompleted() {

        this.fillsCompleted = game.getPlayer().getNumFills();
    }
    public void addMatch(boolean matchWon) {
        if(matchWon) {
            //matchesWon += 1;
            matches.merge("Won", 1, Integer::sum);

            //matches.put("Won", matchesWon);
        }
        else {
            //matchesLost += 1;
            //matches.put("Lost", matchesLost);
            matches.merge("Lost", 1, Integer::sum);

        }


    }

    public void updateDamageDealt() {
        this.damageDone = game.getPlayer().getDamageDealt();

        totalDamage.put("Done", damageDone);
        System.out.println("fill damage stats: " + damageDone);
    }
    public void updateDamageReceived() {
        this.damageReceived = game.getPlayer().getDamageReceived();
        System.out.println("damage recieved stats: " + game.getPlayer().getDamageReceived());
        totalDamage.put("Received", damageReceived);

    }

    public void updateEquipment() {
        String weaponName = game.getPlayer().getWeapon().getWeaponName();
        if(!weaponName.equals("")) {
            equipmentActivations.merge(weaponName, 1, Integer::sum);
        }
    }

    private void registerAsObserver() {
        game.addObserver(new PlayerAttackObserver() {
            @Override
            public void attackStateChanged() {
                //updateAttacks();
                updateEquipment();
                printStats();
            }
        });
    }
    private void registerAsMoveObserver() {
        game.addMoveObserver(new PlayerMoveObserver() {
            @Override
            public void moveStateChanged() {
                addFillsCompleted();
                //updateAttacks();
                updateDamageDealt();
                updateDamageReceived();
                //printStats();
            }
        });
    }
    private void registerAsMatchObserver() {
        game.addMatchObserver(new MatchCompleteObserver() {
            @Override
            public void stateChanged(boolean matchWon) {

                addMatch(matchWon);
                printStats();
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
        for (String i : matches.keySet()) {
            System.out.println(i + " " + matches.get(i));
        }
        System.out.println(events.get(index) + " " + damageDone);
        index++;
        for (String i : totalDamage.keySet()) {
            System.out.println(i + " " +totalDamage.get(i));
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