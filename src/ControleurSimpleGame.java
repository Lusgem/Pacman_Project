public class ControleurSimpleGame implements ControleurGame{
    private Game game;


    public ControleurSimpleGame(Game game) {
        this.game = game;
    }

    @Override
    public void restart() {
        game.init();
    }

    @Override
    public void pause() {
        game.stop();
    }

    @Override
    public void run() {
        game.launch();
    }

    @Override
    public void step() {
        game.step();
    }

    @Override
    public void setTime(long time) {
        game.setTime(time);
    }
}
