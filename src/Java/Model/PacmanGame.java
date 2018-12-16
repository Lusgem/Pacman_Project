package Java.Model;

import Java.*;
import Java.Etat.EtatInactif;
import Java.Strategie.StrategieBruteEloignement;
import Java.Strategie.StrategieBruteRapprochement;

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

    private static Maze maze;
    private ArrayList<Agent> pacmanAgents = new ArrayList<Agent>();
    private ArrayList<Agent> fantomesAgents = new ArrayList<Agent>();
    private int nbJoueurs=1;

    private ArrayList<PositionItem> positionFood = new ArrayList<>();
    private ArrayList<PositionItem> positionCapsule = new ArrayList<>();


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




    public PacmanGame(int tours_max, Maze maze,int nbJoueurs) {
        super(tours_max);
        this.maze=maze;
        this.nbJoueurs=nbJoueurs;
        mainTheme = initMusic("src/Music/pacman_beginning.wav");
        eatFood = initMusic("src/Music/pacman_chomp.wav");
        ghostVulnerable = initMusic("src/Music/pacman_intermission.wav");
        deathOfPacman = initMusic("src/Music/pacman_death.wav");
        listMusic.add(mainTheme);
        listMusic.add(ghostVulnerable);
        listMusic.add(eatFood);
        listMusic.add(deathOfPacman);
        mainTheme.play();
        start();


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
        if (finDuJeu()) {
            if (!gagnant) {
                deathOfPacman.play();
            }
            gameOver();
        }
        else {
        if (!fantomesAgents.isEmpty() && resteFantomesVulnerables()) {
            compteurVunerable++;
            if (compteurVunerable >= vunerableTime) {
                //Arrete la musique correspondant au moment de vulnérabilité et change l'état des fantomes
                //Change la stratégie des fantomes pour qu'ils se rapprocahnet à nouveau du pacman
                ghostVulnerable.stop();
                for (Agent a : fantomesAgents) {
                    a.setInvulnerable();
                    if (a.getStrategie() instanceof StrategieBruteEloignement) {
                        a.setStrategie(new StrategieBruteRapprochement());
                    }
                }
                for (Agent a : pacmanAgents) {
                    a.setVulnerable();
                }
            }
        }
        for (Agent a : fantomesAgents) {
            moveAgent(a, a.getStrategie().jouer(a, maze, pacmanAgents));
            if(finDuJeu()){
                break;
            }
        }
        for (Agent a : pacmanAgents) {
            moveAgent(a, a.getStrategie().jouer(a, maze, fantomesAgents));
            if(finDuJeu()){
                break;
            }
        }
    }
        notifierObservateur();
    }

    /**
     * Arrete toutes les musiques, réinitialise les différents agents
     */
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
    public static boolean isLegalMove(Agent agent, AgentAction action){
        return (!maze.isWall(agent.getPositionCourante().getX()+action.getVx(),agent.getPositionCourante().getY()+action.getVy()));
    }

    /**
     * Met à jour la position d'un agent si elle est atteignable
     * Si la position n'est pas atteignable mais que l'agent est controlable, ne fait rien
     * @param agent
     * @param action
     * @return
     */
    public void moveAgent(Agent agent, AgentAction action){


        if(agent.isInactif()){
            ((EtatInactif)agent.getEtat()).setWaitingTime(((EtatInactif)agent.getEtat()).getWaitingTime()-1);
            if(((EtatInactif)agent.getEtat()).getWaitingTime()<=0) {
                agent.setInvulnerable();
            }
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
                    positionFood.add(new PositionItem(newPos.getX(),newPos.getY()));
                }
                else if(maze.isCapsule(newPos.getX(),newPos.getY())){
                    maze.setCapsule(newPos.getX(),newPos.getY(),false);
                    compteurVunerable=0;
                    ghostVulnerable.loop();
                    score+=100;
                    positionCapsule.add(new PositionItem(newPos.getX(),newPos.getY()));
                    for(Agent a : fantomesAgents){
                        a.setVulnerable();
                        if(a.getStrategie() instanceof StrategieBruteRapprochement){
                            a.setStrategie(new StrategieBruteEloignement());
                        }
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

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
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
     * Fonction servant à stopper toutes les musiques lors de l'arret du jeu (Game over ou victoire)
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
        else if(maze.getInitialfood()!=0 && maze.getInitialfood()==food ) {
            gagnant=true;
            return true;
        }
        else if(maze.getInitialfood()==0 && fantomesAgents.isEmpty()){
            gagnant=true;
            return true;
        }

        return false;
    }

    /**
     * Fonction permettant de vérifier si un agent de type pacman ou fantome doit mourir à ce tour
     * Si un fantome meurt, il est renvoyé à sa position de base et attends pendant 5 périodes
     * Si pacman meurt, la musique concernée se lance et il est retiré de la liste des pacmans, si cette dernière est vide game over
     * @param attaquant
     * @param newPos
     * @param oldPos
     */
    public void death(Agent attaquant,PositionAgent newPos, PositionAgent oldPos){
        if(attaquant.getTypeAgent() == TypeAgent.PACMAN && attaquant.isInvulnerable()) {
            for (Agent fantome : fantomesAgents) {
                if (fantome.isVulnerable() && (fantome.getPositionCourante().getY() == newPos.getY() && fantome.getPositionCourante().getX() == newPos.getX()) || (fantome.getPositionCourante().getX() == oldPos.getX() && fantome.getPositionCourante().getY() == oldPos.getY())) {
                    if(maze.getInitialfood()!=0) {
                        fantome.setInactif();
                        if(fantome.isInactif()){
                            ((EtatInactif)fantome.getEtat()).setWaitingTime(5);
                        }
                        fantomesAgents.get(fantomesAgents.indexOf(fantome)).setPositionCourante(fantome.getPositionInitiale());
                    }
                    else {
                        fantomesAgents.remove(fantome);
                            break;
                        }
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
        if(!positionFood.isEmpty()){
            for(PositionItem food : positionFood){
                maze.setFood(food.getX(),food.getY(),true);
            }
            positionFood.clear();
        }
        if(!positionCapsule.isEmpty()){
            for(PositionItem capsule : positionCapsule){
                maze.setCapsule(capsule.getX(),capsule.getY(),true);
            }
            positionCapsule.clear();
        }

        if(!maze.getPacman_start().isEmpty()){
            for(int i=0;i<maze.getPacman_start().size();i++){
                PositionAgent pos = maze.getPacman_start().get(i);
                if(i==0){
                    pacmanAgents.add(FabriqueAgents.fabriqueJoueur1(pos));
                    vies=3;
                }
                else {
                    pacmanAgents.add(FabriqueAgents.fabriquePacman(pos));
                }
            }
        }
        if(!maze.getGhosts_start().isEmpty()) {
            for (int i = 0; i < maze.getGhosts_start().size(); i++) {
                PositionAgent pos = maze.getGhosts_start().get(i);
                if (i == 0 && nbJoueurs==2) {
                    fantomesAgents.add(FabriqueAgents.fabriqueJoueur2(pos));
                }
                else {
                    fantomesAgents.add(FabriqueAgents.fabriqueFantomeBrute(pos));
                }
            }
        }
    }

    public boolean resteFantomesVulnerables(){
        for(Agent fantome : fantomesAgents){
            if(fantome.isVulnerable()){
                return true;
            }
        }
        return false;
    }


}
