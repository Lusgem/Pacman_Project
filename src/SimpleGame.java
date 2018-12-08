
public class SimpleGame extends Game{

    public SimpleGame(int tours_max,long time){
        super(tours_max,time);
    }
    protected void initializegame() {
        // TODO Auto-generated method stub
        isRunning=true;
        notifierObservateur();
        System.out.println("Lancement");
    }

    protected void takeTurn() {
        // TODO Auto-generated method stub
        compteur++;
        notifierObservateur();
        System.out.println("Tour "+compteur);

    }

    protected void gameOver() {
        stop();
        notifierObservateur();
        System.out.println("Game Over");
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        System.out.println("Pause");
        notifierObservateur();
        isRunning=false;
    }

}
