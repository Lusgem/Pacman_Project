
public class SimpleGame extends Game{

    public SimpleGame(int tours_max){
        super(tours_max);
    }
    protected void initializegame() {
        isRunning=true;
        notifierObservateur();
        System.out.println("Lancement");
    }

    protected void takeTurn() {
        compteur++;
        notifierObservateur();
        System.out.println("Tour "+compteur);

    }

    protected void gameOver() {
        stop();
        notifierObservateur();
        System.out.println("Game Over");
    }

    public void pause() {
        System.out.println("Pause");
        notifierObservateur();
        isRunning=false;
    }

}
