public class ControleurSimpleGame implements ControleurGame{
    private SimpleGame game;


    public ControleurSimpleGame(SimpleGame game) {
        this.game = game;
    }

    @Override
    public void init() {
        game.initializegame();
    }

    @Override
    public void pause() {
        game.pause();
    }

    @Override
    public void launch() {
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
