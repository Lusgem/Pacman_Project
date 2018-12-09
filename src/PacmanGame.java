import java.util.ArrayList;

public class PacmanGame extends Game {

    private Maze maze;
    private ArrayList<Agent> pacmanAgents = new ArrayList<Agent>();
    private ArrayList<Agent> fantomesAgents = new ArrayList<Agent>();



    public PacmanGame(int tours_max, Maze maze) {
        super(tours_max);
        this.maze=maze;
    }

    @Override
    protected void initializegame() {
        for(PositionAgent pos : maze.getPacman_start()){
            pacmanAgents.add(FabriqueAgents.fabriquePacman(pos));
        }
        for (PositionAgent pos : maze.getGhosts_start()){
            pacmanAgents.add(FabriqueAgents.fabriqueFantome(pos));
        }
    }

    @Override
    protected void takeTurn() {

    }

    @Override
    protected void gameOver() {

    }

    public Maze getMaze() {
        return maze;
    }
}
