package Java.Controleur;

import Java.Agent;
import Java.AgentAction;
import Java.Model.Game;
import Java.Model.PacmanGame;
import Java.PositionAgent;

public class ControleurPacmanGame implements ControleurGame {

    private Game game;

    public ControleurPacmanGame(Game game) {
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

    public void movePlayer(Agent a, AgentAction action){
        if(game instanceof PacmanGame){
            ((PacmanGame) game).moveAgent(a,action);
        }
    }
}
