package cmpt213.A4.model;

/**
 * Interface for observers to implement to be able to observe
 * and react when UI makes request to display stats
 */
public interface UserInterfaceObserver {
    void printRequestFromUI();
}
