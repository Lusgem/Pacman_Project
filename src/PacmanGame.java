import java.util.ArrayList;

public class PacmanGame extends Game {

    private Maze maze;
    private ArrayList<Agent> pacmanAgents = new ArrayList<Agent>();
    private ArrayList<Agent> fantomesAgents = new ArrayList<Agent>();
    private ArrayList<PositionAgent> positionPacman = new ArrayList<>();
    private ArrayList<PositionAgent> positionFantomes = new ArrayList<>();

    private Strategie strategie = new StrategieRandom();




    public PacmanGame(int tours_max, Maze maze) {
        super(tours_max);
        this.maze=maze;
        positionPacman= maze.getPacman_start();
        positionFantomes = maze.getGhosts_start();
        for(PositionAgent pos : positionPacman){
            pacmanAgents.add(FabriqueAgents.fabriquePacman(pos));
        }
        for (PositionAgent pos : positionFantomes){
            fantomesAgents.add(FabriqueAgents.fabriqueFantome(pos));
        }
    }

    @Override
    protected void initializegame() {
        pacmanAgents.clear();
        fantomesAgents.clear();
        positionFantomes = maze.getGhosts_start();
        positionPacman = maze.getPacman_start();
        notifierObservateur();
        for(PositionAgent pos : maze.getPacman_start()){
            pacmanAgents.add(FabriqueAgents.fabriquePacman(pos));
        }
        for (PositionAgent pos : maze.getGhosts_start()){
            fantomesAgents.add(FabriqueAgents.fabriqueFantome(pos));
        }
    }

    @Override
    protected void takeTurn() {


        for (Agent a : pacmanAgents){
            while(true) {
                if (moveAgent(a, strategie.jouer(a, positionPacman, positionFantomes))) {
                break;
                }
                notifierObservateur();
            }

        }
        for (Agent a : fantomesAgents){

            while(true) {
                if (moveAgent(a, strategie.jouer(a, positionPacman, positionFantomes))) {
                break;
                }
                notifierObservateur();
            }
        }


    }

    @Override
    protected void gameOver() {

    }

    public Maze getMaze() {
        return maze;
    }

    public boolean isLegalMove(Agent agent, AgentAction action){
        return (!maze.isWall(agent.getPositionCourante().getX()+action.getVx(),agent.getPositionCourante().getY()+action.getVy()));
    }

    public boolean moveAgent(Agent agent, AgentAction action){
        if(isLegalMove(agent,action)){
            PositionAgent newPos = new PositionAgent(agent.getPositionCourante().getX()+action.getVx(),agent.getPositionCourante().getY()+action.getVy(),action.getDirection());

            if(agent.getType() == Type.PACMAN) {
                positionPacman.remove(agent.getPositionCourante());
                agent.setPositionCourante(newPos);
                positionPacman.add(newPos);
            }
            else {
                positionFantomes.remove(agent.getPositionCourante());
                agent.setPositionCourante(newPos);
                positionFantomes.add(newPos);
            }
            notifierObservateur();
            return true;
        }
        else{
            return false;
        }
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public ArrayList<Agent> getPacmanAgents() {
        return pacmanAgents;
    }

    public void setPacmanAgents(ArrayList<Agent> pacmanAgents) {
        this.pacmanAgents = pacmanAgents;
    }

    public ArrayList<Agent> getFantomesAgents() {
        return fantomesAgents;
    }

    public void setFantomesAgents(ArrayList<Agent> fantomesAgents) {
        this.fantomesAgents = fantomesAgents;
    }

    public ArrayList<PositionAgent> getPositionPacman() {
        return positionPacman;
    }

    public void setPositionPacman(ArrayList<PositionAgent> positionPacman) {
        this.positionPacman = positionPacman;
    }

    public ArrayList<PositionAgent> getPositionFantomes() {
        return positionFantomes;
    }

    public void setPositionFantomes(ArrayList<PositionAgent> positionFantomes) {
        this.positionFantomes = positionFantomes;
    }

    public AgentAction moveNorth(){
        return new AgentAction(Maze.NORTH);
    }
    public AgentAction moveSouth(){
        return new AgentAction(Maze.SOUTH);
    }
    public AgentAction moveEast(){
        return new AgentAction(Maze.EAST);
    }
    public AgentAction moveWest(){
        return new AgentAction(Maze.WEST);
    }

}
