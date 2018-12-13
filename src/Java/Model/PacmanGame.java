package Java.Model;

import Java.*;
import Java.Etat.EtatInvulnerable;
import Java.Etat.EtatVulnerable;
import Java.Strategie.StrategieJoueur1;
import Java.View.ViewGame;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Classe principal du jeu pacman
 */
public class PacmanGame extends Game {

    private Maze maze;
    private ArrayList<Agent> pacmanAgents = new ArrayList<Agent>();
    private ArrayList<Agent> fantomesAgents = new ArrayList<Agent>();


    // Fichiers musicaux pour les bruitages et la musique du jeu
    // Trouvés sur http://www.classicgaming.cc/classics/pac-man/sounds
    private AudioClip mainTheme;
    private AudioClip eatFood;
    private AudioClip ghostVulnerable;
    private AudioClip deathOfPacman;
    private ArrayList<AudioClip> listMusic = new ArrayList<>();



    //Nombre de périodes de vulnérabilité
    private int compteurVunerable;
    private final int vunerableTime=20;




    public PacmanGame(int tours_max, Maze maze) {
        super(tours_max);
        this.maze=maze;

        for(PositionAgent pos : maze.getPacman_start()){
            pacmanAgents.add(FabriqueAgents.fabriquePacman(pos));
        }
        for (PositionAgent pos : maze.getGhosts_start()){
            fantomesAgents.add(FabriqueAgents.fabriqueFantome(pos));
        }
        pacmanAgents.get(0).setControlable(true);
        pacmanAgents.get(0).setStrategie(ViewGame.strategieJoueur1);

        mainTheme = initMusic("src/Music/pacman_beginning.wav");
        eatFood = initMusic("src/Music/pacman_chomp.wav");
        ghostVulnerable = initMusic("src/Music/pacman_intermission.wav");
        deathOfPacman = initMusic("src/Music/pacman_death.wav");
        listMusic.add(mainTheme);
        listMusic.add(ghostVulnerable);
        listMusic.add(eatFood);
        listMusic.add(deathOfPacman);

        mainTheme.play();
    }

    @Override
    protected void initializegame() {
        pacmanAgents.clear();
        fantomesAgents.clear();

        for(PositionAgent pos : maze.getPacman_start()){
            pacmanAgents.add(FabriqueAgents.fabriquePacman(pos));
        }

        for (PositionAgent pos : maze.getGhosts_start()){
            fantomesAgents.add(FabriqueAgents.fabriqueFantome(pos));
        }
        notifierObservateur();
    }

    @Override
    protected void takeTurn() {
        if(!fantomesAgents.isEmpty() && fantomesAgents.get(0).isVulnerable()){
            compteurVunerable++;
            if(compteurVunerable>=vunerableTime){
                //Arrete la musique correspondant au moment de vulnérabilité et change l'état des fantomes
                ghostVulnerable.stop();
                for(Agent a : fantomesAgents){
                    a.setInvulnerable();
                }
                for(Agent a : pacmanAgents){
                    a.setVulnerable();
                }
            }
        }


        for (Agent a : fantomesAgents){

            while(true) {
                if (moveAgent(a, a.getStrategie().jouer(a, maze))) {
                break;
                }
            }
        }
        for (Agent a : pacmanAgents){
            while(true) {
                if (moveAgent(a, a.getStrategie().jouer(a,maze))) {
                    break;
                }
            }

        }
        notifierObservateur();


    }

    @Override
    protected void gameOver() {
        System.out.println("Java.Model.Game Over");
        stopMusic();
        init();
    }

    public Maze getMaze() {
        return maze;
    }

    /**
     * Permet de vérifier si la position désignée ne contient pas un mur
     * @param agent
     * @param action
     * @return
     */
    public boolean isLegalMove(Agent agent, AgentAction action){
        return (!maze.isWall(agent.getPositionCourante().getX()+action.getVx(),agent.getPositionCourante().getY()+action.getVy()));
    }

    /**
     * Met à jour la position d'un agent si elle est atteignable
     * @param agent
     * @param action
     * @return
     */
    public boolean moveAgent(Agent agent, AgentAction action){
        if(!isLegalMove(agent,action) && !agent.isControlable()){
            return false;
        }
        else if(!isLegalMove(agent,action) && agent.isControlable()){
            return true;
        }

        if(isLegalMove(agent,action)){
            PositionAgent newPos = new PositionAgent(agent.getPositionCourante().getX()+action.getVx(),agent.getPositionCourante().getY()+action.getVy(),action.getDirection());
            if(agent.getTypeAgent() == TypeAgent.PACMAN) {
                agent.setPositionCourante(newPos);
                if(maze.isFood(newPos.getX(),newPos.getY())){
                    maze.setFood(newPos.getX(),newPos.getY(),false);
                    eatFood.play();
                }
                else if(maze.isCapsule(newPos.getX(),newPos.getY())){
                    maze.setCapsule(newPos.getX(),newPos.getY(),false);
                    compteurVunerable=0;
                    ghostVulnerable.loop();
                    for(Agent a : fantomesAgents){
                        a.setVulnerable();
                    }
                    for(Agent a : pacmanAgents){
                        a.setInvulnerable();
                    }
                }

            }
            else {
                for(Agent pacman : pacmanAgents){
                    if(pacman.getPositionCourante().equals(agent.getPositionCourante())){
                        pacmanAgents.remove(pacman);
                    }
                }
                agent.setPositionCourante(newPos);
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

    /**
     * Fonction permettant d'initialiser une musique à partir d'un fichier
     * @param path
     * @return
     */
    public AudioClip initMusic(String path){
        File file = new File(path);
        URL url = null;
        if (file.canRead()) {
            try {
                url = file.toURI().toURL();

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            return Applet.newAudioClip(url);
        }
        return  null;

    }

    /**
     * Fonction servant à stopper toutes les musiques lors de l'arret du jeu (Java.Model.Game over ou victoire)
     */
    public void stopMusic(){
        for(AudioClip audioClip : listMusic){
            audioClip.stop();
        }
    }


}
