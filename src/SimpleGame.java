
public class SimpleGame extends Game{

    public SimpleGame(int tours_max){
        super(tours_max);
    }
    protected void initializegame() {
        System.out.println("Initialisation");
    }

    protected void takeTurn() {
        System.out.println("Tour "+compteur);

    }

    protected void gameOver() {
        System.out.println("Game Over");
    }

    public void pause() {
        System.out.println("Pause");
    }

}
