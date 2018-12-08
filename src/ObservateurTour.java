public class ObservateurTour implements Observateur {

    private Game game;

    public ObservateurTour(Game game) {
        this.game = game;
        game.enregistrerObservateur(this);
    }

    @Override
    public void actualiser() {

    }
}
