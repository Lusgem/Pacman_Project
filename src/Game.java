import java.util.ArrayList;
import java.util.List;

public abstract class Game implements Runnable, Sujet {


    public int getCompteur() {
        return compteur;
    }

    int compteur=0;
    int tours_max;
    boolean isRunning;
    long time=1000;
    Thread thread;

    public Game(int tours_max){
        this.tours_max=tours_max;
        isRunning=true;
    }

    public boolean isRunning() {
        return isRunning;
    }



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
        while(isRunning && compteur<=tours_max){
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

    public void setTime(long time) {
        this.time = time;
    }
}
