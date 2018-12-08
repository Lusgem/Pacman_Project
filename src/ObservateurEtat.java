public class ObservateurEtat implements Observateur {

    private Game game;

    public ObservateurEtat(Game game) {
        this.game = game;
        game.enregistrerObservateur(this);
    }

    @Override
    public void actualiser() {

    }
}
