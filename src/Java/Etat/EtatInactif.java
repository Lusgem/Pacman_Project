package Java.Etat;

/**
 * Etat que prend un fantome qui vient d'être mangé
 * Possède un attribut qui représente le nombre de périodes restantes où il est inactif
 */
public class EtatInactif implements Etat {


    private int waitingTime;
    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
