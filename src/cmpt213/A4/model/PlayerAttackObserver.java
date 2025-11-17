package cmpt213.A4.model;

    /**
     * Interface for observers to implement to be able to observe
     * and react when the player makes an attack.
     */
    public interface PlayerAttackObserver {
        void attackStateChanged();
    }



