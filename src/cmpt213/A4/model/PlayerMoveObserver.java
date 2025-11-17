package cmpt213.A4.model;
/**
 * Interface for observers to implement to be able to observe
 * and react when the player makes a move
 */
public interface PlayerMoveObserver {
    void moveStateChanged();
}
