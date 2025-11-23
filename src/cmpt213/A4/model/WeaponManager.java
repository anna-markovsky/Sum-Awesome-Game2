package cmpt213.A4.model;

import java.util.*;

interface WeaponCondition {
    boolean isActive(FillConditions fillConditions);
}

public class WeaponManager {
    private Game game;
    public List<Weapon> allWeapons = new ArrayList<>();
    public WeaponManager() {
        createAllWeapons(game.getFillConditions());

    }
    public WeaponManager(Game game) {
        this.game = game;
        this.allWeapons.addAll(createAllWeapons(game.getFillConditions()));
        registerAsObserver();
        registerAsMatchObserver();
    }

    public List<Weapon> getAllWeapons() {
        return allWeapons;
    }
    private void registerAsObserver() {
        game.addObserver(new PlayerAttackObserver() {
            @Override
            public void attackStateChanged() {
                //selectRandomWeapon();
            }
        });
    }

    private void registerAsMatchObserver() {
        game.addMatchObserver(new MatchCompleteObserver() {
            @Override
            public void stateChanged(boolean matchWon) {
                if(matchWon){
                    selectRandomWeapon();
                }
                //selectRandomWeapon();
            }
        });
    }
    private int assignRandomOpponentDamage() {
        Random random = new Random();
        //List<Opponent> opponents = game.getOpponents();
        int index = random.nextInt(game.getOpponents().size());
        return index;
    }


    private void selectRandomWeapon() {
        FillConditions fillConditions = game.getFillConditions();
        Player player = game.getPlayer();
        //List<Weapon> weaponsAvailable = createAllWeapons(fillConditions);
        Random random = new Random();
        int index = random.nextInt(allWeapons.size());
        Weapon tempWeapon = allWeapons.get(index);
        player.equipWeapon(tempWeapon);
    }


    private List<Weapon> createAllWeapons(FillConditions fillConditions) {
        List<Weapon> qualifingWeapons = new ArrayList<>();
        //allWeapons.clear();
        int randIndex = assignRandomOpponentDamage();

        int colIndex = game.getFillConditions().getLastSelectedColIndex();
        Weapon frostBow = new Weapon();
        frostBow.assignWeapon(
                "frost bow",
                new double[]{1.0, 1.0, 1.0},
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkAddedCellsAscending();
                    }
                }
        );
        /*if(frostBow.getWeaponCondition().isActive(fillConditions)) {
            qualifingWeapons.add(frostBow);
            //player.equipWeapon(frostBow);
        }*/
        qualifingWeapons.add(frostBow);

        //allWeapons.add(frostBow);
        Weapon diamondSword = new Weapon();
        //int colIndex = game.getFillConditions().getLastSelectedColIndex();
        //double[] damagePercentages = diamondSword.assignDamagePercentages(colIndex, 1.0, 0.75);

        diamondSword.assignWeapon(
                "diamond sword",
                diamondSword.assignDamagePercentages(colIndex, 1.0, 0.75),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkAddedCellsDescending();
                    }
                }
        );
        /*if(diamondSword.getWeaponCondition().isActive(fillConditions)) {
            qualifingWeapons.add(diamondSword);
            //player.equipWeapon(diamondSword);
        }*/
        qualifingWeapons.add(diamondSword);

        //allWeapons.add(diamondSword);
        Weapon fireStaff = new Weapon();
        fireStaff.assignWeapon(
                "fire staff",
                fireStaff.assignDamagePercentages(colIndex, 1.0, 0.75),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkNumFills(15);
                    }
                }
        );
        /*if(fireStaff.getWeaponCondition().isActive(fillConditions)) {
            //player.equipWeapon(fireStaff);
            qualifingWeapons.add(fireStaff);
        }*/
        qualifingWeapons.add(fireStaff);
        //allWeapons.add(fireStaff);
        Weapon stoneHammer = new Weapon();
        stoneHammer.assignWeapon(
                "stone hammer",
                stoneHammer.assignDamagePercentages(colIndex, 0.8, 0.8),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkNumFills(10);
                    }
                }
        );
        /*if(stoneHammer.getWeaponCondition().isActive(fillConditions)) {
            //player.equipWeapon(fireStaff);
            qualifingWeapons.add(stoneHammer);
        }*/
        qualifingWeapons.add(stoneHammer);
        //allWeapons.add(stoneHammer);
        Weapon lightningWand = new Weapon();
        lightningWand.assignWeapon(
                "lightning wand",
                lightningWand.assignDamagePercentages(randIndex, 1.0, 0.0),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        System.out.println("seconds taken" + fillConditions.getSecondsTaken());
                        return fillConditions.checkTime(20);
                    }
                }
        );
        //allWeapons.add(lightningWand);
        /*if(lightningWand.getWeaponCondition().isActive(fillConditions)) {
            //player.equipWeapon(fireStaff);
            qualifingWeapons.add(lightningWand);
        }*/
        qualifingWeapons.add(lightningWand);
        Weapon sparkleDagger = new Weapon();
        sparkleDagger.assignWeapon(
                "sparkle dagger",
                sparkleDagger.assignDamagePercentages(randIndex, 0.5, 0.0),
                new WeaponCondition() {
                    @Override
                    public boolean isActive(FillConditions fillConditions) {
                        return fillConditions.checkTime(30);
                    }
                }
        );
        /*if(sparkleDagger.getWeaponCondition().isActive(fillConditions)) {
            qualifingWeapons.add(sparkleDagger);
        }*/
        qualifingWeapons.add(sparkleDagger);
        //allWeapons.add(sparkleDagger);
        if(allWeapons.isEmpty()) {
            NullWeapon nullWeapon = new NullWeapon();
            nullWeapon.assignWeapon(
                    "",
                    nullWeapon.assignDamagePercentages(randIndex, 0.0, 0.0),
                    new WeaponCondition() {
                        @Override
                        public boolean isActive(FillConditions fillConditions) {
                            return false;
                        }
                    }
            );
            //qualifingWeapons.add(nullWeapon);
            //allWeapons.add(nullWeapon);
        }
        return qualifingWeapons;
    }


    private Weapon determineWeapon(FillConditions fillConditions) {
        //check if any of the conditions for a weapon were met
        if (fillConditions.checkAddedCellsAscending()) {
            System.out.println("frost bow");
            Weapon weapon = new Weapon();
            double[] damagePercentages = {1.0,1.0,1.0};
           // weapon.assignWeapon("frost bow", damagePercentages);
            return weapon;

        }
        if (fillConditions.checkAddedCellsDescending()) {
            System.out.println("diamond sword");
            Weapon weapon = new Weapon();
            int colIndex = game.getFillConditions().getLastSelectedColIndex();
            double[] damagePercentages = weapon.assignDamagePercentages(colIndex, 1.0, 0.75);
            //weapon.assignWeapon("diamond sword", damagePercentages);
            return weapon;
        }
        if (fillConditions.calculateNumFills() >= 10) {
            if (fillConditions.calculateNumFills() >= 15) {
                System.out.println("Fire staff");
                Weapon weapon = new Weapon();
                int colIndex = game.getFillConditions().getLastSelectedColIndex();
                double[] damagePercentages = weapon.assignDamagePercentages(colIndex, 1.0, 0.5);

                //weapon.assignWeapon("fire staff", damagePercentages);
                return weapon;

            } else {
                System.out.println("Stone hammer");
                Weapon weapon = new Weapon();
                int colIndex = game.getFillConditions().getLastSelectedColIndex();

                double[] damagePercentages = weapon.assignDamagePercentages(colIndex, 0.8, 0.8);
               // weapon.assignWeapon("stone hammer", damagePercentages);
                return weapon;

            }
        }
        System.out.println("Seconds taken "+ game.getFillConditions().getSecondsTaken() );
        if (game.getFillConditions().getSecondsTaken() <= 30 && game.getFillConditions().getSecondsTaken() > 0) {
            if (game.getFillConditions().getSecondsTaken() <= 20 ) {
                Weapon weapon = new Weapon();
                int mainIndex = assignRandomOpponentDamage();
                double[] damagePercentages = weapon.assignDamagePercentages(mainIndex, 1.0,0.0);
                //weapon.assignWeapon("lightning wand", damagePercentages);

                System.out.println("seconds taken "+ fillConditions.getSecondsTaken());
                System.out.println("lightning wand");
                return weapon;
            }
            else {
                Weapon weapon = new Weapon();
                int mainIndex = assignRandomOpponentDamage();
                double[] damagePercentages = weapon.assignDamagePercentages(mainIndex, 0.5,0.0);
               // weapon.assignWeapon("sparkle dagger", damagePercentages);

                System.out.println("Sparkle dagger");
                return weapon;
            }

        }
        else {
            System.out.println("nothing");
            NullWeapon nullWeapon = new NullWeapon();
            double[] damagePercentages = {0.0,0.0,0.0};
           // nullWeapon.assignWeapon("",damagePercentages);
            return nullWeapon;
        }

    }}




