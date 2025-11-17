package cmpt213.A4.model;

/**
 * Interface for observers to implement to be able to observe
 * and react when a game ends
 */
public interface MatchCompleteObserver {
    void stateChanged();
}


