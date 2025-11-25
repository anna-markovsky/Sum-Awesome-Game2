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
        //registerAsMatchObserver();
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

    /*private void registerAsMatchObserver() {
        game.addMatchObserver(new MatchCompleteObserver() {
            @Override
            public void stateChanged(boolean matchWon) {
                if (matchWon) {
                    Player player = game.getPlayer();
                    player.dropWeapon();
                    //equipRandomWeapon();
                }
                //selectRandomWeapon();
            }
        });
    }*/

    private int assignRandomOpponentDamage() {
        Random random = new Random();
        //List<Opponent> opponents = game.getOpponents();
        int index = random.nextInt(game.getOpponents().size());
        return index;
    }


    private void equipRandomWeapon() {
        FillConditions fillConditions = game.getFillConditions();
        Player player = game.getPlayer();
        //List<Weapon> weaponsAvailable = createAllWeapons(fillConditions);
        Random random = new Random();
        int index = random.nextInt(allWeapons.size());
        player.equipWeapon(allWeapons.get(index));
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
        if (allWeapons.isEmpty()) {
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
}