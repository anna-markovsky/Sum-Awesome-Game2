package cmpt213.A4.userinterface;

import cmpt213.A4.model.*;

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
    public String[] opponentsCorrespondingToIndex = {"Left", "Middle", "Right"};
    private Game game;

    public StatsTracker(Game game){
        this.game = game;
        this.fillsCompleted = 0;
        registerAsObserver();
        registerAsMoveObserver();
        registerAsMatchObserver();
        registerAsUserInterfaceObserver();
        registerAsAttackInfoObserver();
    }
    public void addFillsCompleted() {
        this.fillsCompleted++;
    }
    public void addMatch(boolean matchWon) {
        if(matchWon) {
            matches.merge("Won", 1, Integer::sum);
        }
        else {
            matches.merge("Lost", 1, Integer::sum);
        }
    }
    public void updateDamageDealt() {
        this.damageDone = game.getPlayer().getDamageDealt();
        totalDamage.put("Done", damageDone);
    }
    public void updateDamageReceived() {
        this.damageReceived = game.getPlayer().getDamageReceived();
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
                addFillsCompleted();
            }
        });
    }
    private void registerAsMoveObserver() {
        game.addMoveObserver(new PlayerMoveObserver() {
            @Override
            public void moveStateChanged() {
                updateDamageDealt();
                updateDamageReceived();
            }
        });
    }
    private void registerAsMatchObserver() {
        game.addMatchObserver(new MatchCompleteObserver() {
            @Override
            public void stateChanged(boolean matchWon) {
                addMatch(matchWon);
            }
        });
    }
    private void registerAsAttackInfoObserver() {
        game.addAttackInfoObserver(new AttackInfoObserver() {
            @Override
            public void getAttackInformation(String weaponName, double[] damageRecieved) {
                updateEquipment();
                printAttackInfo(weaponName, damageRecieved);
            }
            @Override
            public void getFillAttackInformation(int damageRecieved, Opponent selectedOpponent, int opponentIndex) {
                printFillAttackInfo(damageRecieved, selectedOpponent, opponentIndex);
            }
        });
    }
    private void registerAsUserInterfaceObserver() {
        TextUI.addUserInterfaceObserver(new UserInterfaceObserver() {
            @Override
            public void printRequestFromUI() {
                printStats();
            }
        });
    }
    public void printFillAttackInfo(int damageRecieved,Opponent selectedOpponent, int opponentIndex) {
        System.out.println("Fill strength " + damageRecieved + ".");
        if(selectedOpponent.getHealth() == 0) {
            System.out.println("Missed " +  opponentsCorrespondingToIndex[opponentIndex] + " character.");
        }
        else {
            if (selectedOpponent.getHealth() - damageRecieved < 0) {
                System.out.println("Hits " + opponentsCorrespondingToIndex[opponentIndex] + " character for "
                        + selectedOpponent.getHealth() + " damage.");
            } else {
                System.out.println("Hits " + opponentsCorrespondingToIndex[opponentIndex] + " character for "
                        + damageRecieved + " damage.");
            }
        }
    }
    public void printAttackInfo(String weaponName, double[] damageRecieved) {
        for (int i =0; i < damageRecieved.length; i++) {
            System.out.println(weaponName + " targets " + opponentsCorrespondingToIndex[i]);
            if((int) damageRecieved[i] == 0) {
                System.out.println("Misses " + opponentsCorrespondingToIndex[i] + " character.");
            }
            else{
                System.out.println("Hits " + opponentsCorrespondingToIndex[i]
                        + " character for " + (int) damageRecieved[i] + ".");
            }
        }
    }
    public void printStats() {
        int index = 0;
        System.out.println(events.get(index));
        index++;
        for (String i : equipmentActivations.keySet()) {
            System.out.println( i + " " + equipmentActivations.get(i));
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
    }
}