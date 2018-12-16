package Java.Strategie;

import Java.Agent;
import Java.AgentAction;
import Java.Maze;
import Java.Model.PacmanGame;
import Java.PositionAgent;

import java.util.ArrayList;


public class StrategieBruteEloignement implements Strategie {
    @Override
    public AgentAction jouer(Agent a, Maze maze, ArrayList<Agent> ennemies, ArrayList<Agent> allies) {
        PositionAgent pos = a.getPositionCourante();
        PositionAgent posEnnemie = ennemies.get(0).getPositionCourante();
        int direction = posEnnemie.getDir();
        int xEnnemie = posEnnemie.getX();
        int yEnnemie = posEnnemie.getY();

        int x = pos.getX();
        int y = pos.getY();

        // Si il peut, il va dans la même direction de son attaquant pour s'en éloigner
        if(pos.getDir()==direction && PacmanGame.isLegalMove(a,new AgentAction(direction))){
            return new AgentAction(direction);
        }
        else if(estOppose(direction,pos.getDir()) && PacmanGame.isLegalMove(a,new AgentAction(pos.getDir()))){
            return new AgentAction(pos.getDir());
        }
        else{
            if(direction==Maze.EAST){
                if(yEnnemie < y && PacmanGame.isLegalMove(a,new AgentAction(Maze.SOUTH))){
                    return new AgentAction(Maze.SOUTH);
                }
                else if(yEnnemie >= y && PacmanGame.isLegalMove(a,new AgentAction(Maze.NORTH))){
                    return new AgentAction(Maze.NORTH);
                }
                else if(xEnnemie >= x && PacmanGame.isLegalMove(a,new AgentAction(Maze.WEST))){
                    return new AgentAction(Maze.WEST);
                }
                else{
                    return CoupPasOptimal(a,direction);
                }
            }
            else if (direction==Maze.WEST){
                if(yEnnemie < y && PacmanGame.isLegalMove(a,new AgentAction(Maze.SOUTH))){
                    return new AgentAction(Maze.SOUTH);
                }
                else if(yEnnemie >= y && PacmanGame.isLegalMove(a,new AgentAction(Maze.NORTH))){
                    return new AgentAction(Maze.NORTH);
                }
                else if(xEnnemie >= x && PacmanGame.isLegalMove(a,new AgentAction(Maze.EAST))){
                    return new AgentAction(Maze.EAST);
                }
                else {
                    return CoupPasOptimal(a,direction);
                }
            }
            else if (direction==Maze.SOUTH){
                if(xEnnemie > x && PacmanGame.isLegalMove(a,new AgentAction(Maze.WEST))){
                    return new AgentAction(Maze.WEST);
                }
                else if(xEnnemie <= x && PacmanGame.isLegalMove(a,new AgentAction(Maze.EAST))){
                    return new AgentAction(Maze.EAST);
                }
                else if(yEnnemie >= y && PacmanGame.isLegalMove(a,new AgentAction(Maze.NORTH))){
                    return new AgentAction(Maze.NORTH);
                }
                else {
                    return CoupPasOptimal(a,direction);
                }
            }
            else if (direction==Maze.NORTH){
                if(xEnnemie < x && PacmanGame.isLegalMove(a,new AgentAction(Maze.EAST))){
                    return new AgentAction(Maze.EAST);
                }
                else if(xEnnemie >= x && PacmanGame.isLegalMove(a,new AgentAction(Maze.WEST))){
                    return new AgentAction(Maze.WEST);
                }
                else if(yEnnemie <= y && PacmanGame.isLegalMove(a,new AgentAction(Maze.SOUTH))){
                    return new AgentAction(Maze.SOUTH);
                }
                else {
                    return CoupPasOptimal(a,direction);
                }
            }

        }



        return new AgentAction(Maze.STOP);
    }

    /**
     * Fonction qui renvoi un coup dans le cas où l'agent est dans une posture ou sa position ne répond pas aux conditions de la fonction d'au dessus
     * @param a
     * @param dir
     * @return
     */
    public AgentAction CoupPasOptimal(Agent a, int dir){
        if(PacmanGame.isLegalMove(a,new AgentAction(Maze.EAST)) && dir != Maze.EAST){
            return new AgentAction(Maze.EAST);
        }
        else if(PacmanGame.isLegalMove(a,new AgentAction(Maze.NORTH)) && dir != Maze.EAST){
            return new AgentAction(Maze.NORTH);
        }
        else if(PacmanGame.isLegalMove(a,new AgentAction(Maze.SOUTH)) && dir != Maze.SOUTH){
            return new AgentAction(Maze.SOUTH);
        }
        else if(PacmanGame.isLegalMove(a,new AgentAction(Maze.WEST)) && dir != Maze.WEST){
            return new AgentAction(Maze.WEST);
        }
        return new AgentAction(dir);

    }

    public boolean estOppose(int dir1, int dir2){

        switch (dir1){
            case Maze.NORTH:
                return (dir2==Maze.SOUTH);
            case Maze.SOUTH:
                return (dir2==Maze.NORTH);
            case Maze.EAST:
                return (dir2==Maze.WEST);
            case Maze.WEST:
                return (dir2==Maze.EAST);

        }
        return false;
    }
}
