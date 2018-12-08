import java.util.ArrayList;
import java.util.List;

public abstract class Game implements Runnable, ControleurGame, Sujet {


    public int getCompteur() {
        return compteur;
    }

    int compteur=0;
    int tours_max;

    public boolean isRunning() {
        return isRunning;
    }

    boolean isRunning;
    long time;
    Thread thread;

    private List<Observateur> observateurs = new ArrayList<Observateur>();

    public void enregistrerObservateur(Observateur observateur){
        observateurs.add(observateur);
    }

    public void supprimerObservateur(Observateur observateur){
        observateurs.remove(observateur);
    }

    @Override
    public void notifierObservateur() {
        for (Observateur o: observateurs) {
            o.actualiser();
        }
    }

    public Game(int tours_max, long time){
        this.tours_max=tours_max;
        this.time = time;
        isRunning=true;
    }

    protected abstract void initializegame();
    protected abstract void takeTurn();
    protected abstract void gameOver();
    public void init(){
        compteur = 0;
        initializegame();
    }

    public void step(){
        if(compteur<tours_max)
            takeTurn();
        else
            gameOver();
    }


    public void launch(){
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    public void run(){
        init();
        while(isRunning==true && compteur<=tours_max){
            step();
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    protected void stop(){
        isRunning=false;
    }
}
