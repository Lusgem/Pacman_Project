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

    private boolean gagnant = false;
    private int food=0;
    private int score=0;
    private int vies;




    public PacmanGame(int tours_max, Maze maze) {
        super(tours_max);
        this.maze=maze;
        start();
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
        start();
        notifierObservateur();
    }

    @Override
    protected void takeTurn() {
        if(finDuJeu()){
            if(!gagnant){
                deathOfPacman.play();
            }
            gameOver();
        }
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
        stopMusic();
        init();
        stop();
        over=true;
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
     * Si la position n'est pas atteignable mais que l'agent est controlable, ne fait rien
     * @param agent
     * @param action
     * @return
     */
    public boolean moveAgent(Agent agent, AgentAction action){
        //Si le mouvement n'est pas possible et l'agent n'est pas controlable, le mouvement est refusé
        if(!isLegalMove(agent,action) && !agent.isControlable()){
            return false;
        }
        else if(!isLegalMove(agent,action) && agent.isControlable()){
            //Permet à un agent controlable de rester immobile si la direction qu'il a choisi n'est pas possible
            return true;
        }
        if(isLegalMove(agent,action)){
            PositionAgent newPos = new PositionAgent(agent.getPositionCourante().getX()+action.getVx(),agent.getPositionCourante().getY()+action.getVy(),action.getDirection());
            if(agent.getTypeAgent() == TypeAgent.PACMAN) {
                if(agent.isInvulnerable()){
                    death(agent,newPos,agent.getPositionCourante());
                }
                agent.setPositionCourante(newPos);
                if(maze.isFood(newPos.getX(),newPos.getY())){
                    maze.setFood(newPos.getX(),newPos.getY(),false);
                    food++;
                    eatFood.play();
                    score+=10;
                }
                else if(maze.isCapsule(newPos.getX(),newPos.getY())){
                    maze.setCapsule(newPos.getX(),newPos.getY(),false);
                    compteurVunerable=0;
                    ghostVulnerable.loop();
                    score+=100;
                    for(Agent a : fantomesAgents){
                        a.setVulnerable();
                    }
                    for(Agent a : pacmanAgents){
                        a.setInvulnerable();
                    }
                }

            }
            else {
                if(agent.isInvulnerable())
                    death(agent,newPos,agent.getPositionCourante());
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

    public int getScore() {
        return score;
    }

    public int getVies() {
        return vies;
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

    /**
     * Conditions de fin de jeu
     * Le jeu s'arrete si :
     * Il n'y a plus de pacman
     * Si le pacman a mangé toute la nourriture
     * Si il n'y avait pas de nourriture, si tous les fantomes sont morts
     *
     * @return
     */
    public boolean finDuJeu(){

        if(pacmanAgents.isEmpty()){
            return true;
        }
        else if(maze.getInitialfood()!=0 && maze.getInitialfood()==food) {
            gagnant=true;
            return true;
        }else if(maze.getInitialfood()==0 && fantomesAgents.isEmpty()){
            gagnant=true;
            return true;
        }
        return false;
    }

    /**
     * Fonction permettant de vérifier si un agent de type pacman ou fantome doit mourir à ce tour
     * Si un fantome meurt, il est renvoyé à sa position de base
     * Si pacman meurt, la musique concernée se lance et il est retiré de la liste des pacmans, si cette dernière est vide game over
     * @param attaquant
     * @param newPos
     * @param oldPos
     */
    public void death(Agent attaquant,PositionAgent newPos, PositionAgent oldPos){
        if(attaquant.getTypeAgent() == TypeAgent.PACMAN) {
            for (Agent fantome : fantomesAgents) {
                if ((fantome.getPositionCourante().getY() == newPos.getY() && fantome.getPositionCourante().getX() == newPos.getX()) || (fantome.getPositionCourante().getX() == oldPos.getX() && fantome.getPositionCourante().getY() == oldPos.getY())) {
                    fantomesAgents.get(fantomesAgents.indexOf(fantome)).setPositionCourante(fantome.getPositionInitiale());
                    break;
                }
            }
        }
        else {
            for(Agent pacman : pacmanAgents){
                if((pacman.getPositionCourante().getY() == newPos.getY() && pacman.getPositionCourante().getX() == newPos.getX()) || (pacman.getPositionCourante().getX() == oldPos.getX() && pacman.getPositionCourante().getY() == oldPos.getY())){
                    if(vies<=0){
                        pacmanAgents.remove(pacman);
                    }
                    else{
                        pacman.setPositionCourante(pacman.getPositionInitiale());
                        vies--;
                    }
                    deathOfPacman.play();
                    break;
                }
            }
        }
    }

    /**
     * Cette fonction sert à initialiser les positions et startégies des différents agents (sert à la création de la classe et lors d'une nouvelle partie
     *
     */
    public void start(){
        score=0;
        vies=3;
        if(!maze.getPacman_start().isEmpty()){
            for(int i=0;i<maze.getPacman_start().size();i++){
                PositionAgent pos = maze.getPacman_start().get(0);
                if(i==0){
                    pacmanAgents.add(FabriqueAgents.fabriqueJoueur1(pos));
                    vies=3;
                }
                else {
                    pacmanAgents.add(FabriqueAgents.fabriquePacman(pos));
                }
            }
        }
        for (PositionAgent pos : maze.getGhosts_start()){
            fantomesAgents.add(FabriqueAgents.fabriqueFantome(pos));
        }
        //On rend le premier pacman controlable et on lui assigne une stratégiejoueur1
        if(!pacmanAgents.isEmpty()){
            pacmanAgents.get(0).setControlable(true);
            pacmanAgents.get(0).setStrategie(StrategieJoueur1.getInstance());
        }
    }


}
