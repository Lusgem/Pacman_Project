package Java.Model;

import Java.Sujet;
import Java.View.Observateur;

import java.util.ArrayList;


public abstract class Game implements Runnable, Sujet {

    int compteur=0;
    int tours_max;
    boolean running;
    boolean over;
    long time=1000;

    ArrayList<Observateur> observateurs = new ArrayList<Observateur>();
    Thread thread;

    public Game(int tours_max){
        this.tours_max=tours_max;
        running=true;
        over=false;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isOver(){
        return over;
    }

    public int getCompteur() {
        return compteur;
    }

    @Override
    public void enregistrerObservateur(Observateur observateur){
        observateurs.add(observateur);
    }

    @Override
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
        notifierObservateur();
        initializegame();
    }

    public void step(){
        if(compteur<tours_max) {
            compteur++;
            notifierObservateur();
            takeTurn();
        }
        else
            over = true;
    }


    public void launch(){
        running=true;
        over=false;
        notifierObservateur();
        thread = new Thread(this);
        thread.start();
    }

    public void run(){
        while(isRunning() && !isOver()){
            step();
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (isOver()){
            gameOver();
            notifierObservateur();
        }
    }
    public void stop(){
        running=false;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTours_max() {
        return tours_max;
    }
}
