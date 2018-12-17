package Java;

import Java.Etat.Etat;
import Java.Etat.EtatInvulnerable;
import Java.Etat.EtatVulnerable;
import Java.Etat.EtatInactif;
import Java.Strategie.Strategie;
import Java.Strategie.StrategieRandom;

public class Agent {

    private TypeAgent typeAgent;
    private PositionAgent positionCourante;
    private PositionAgent positionInitiale;
    private Etat etat;
    private Strategie strategie;
    private boolean controlable = false;

    public Agent(TypeAgent typeAgent, PositionAgent positionCourante, Etat etat) {
        this.typeAgent = typeAgent;
        this.positionInitiale = positionCourante;
        this.positionCourante = positionCourante;
        this.etat = etat;
        this.strategie = new StrategieRandom();
    }


    public PositionAgent getPositionCourante() {
        return positionCourante;
    }

    public void setPositionCourante(PositionAgent positionCourante) {
        this.positionCourante = positionCourante;
    }

    public TypeAgent getTypeAgent() {
        return typeAgent;
    }

    public void setTypeAgent(TypeAgent typeAgent) {
        this.typeAgent = typeAgent;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public Strategie getStrategie() {
        return strategie;
    }

    public void setStrategie(Strategie strategie) {
        this.strategie = strategie;
    }

    public PositionAgent getPositionInitiale() {
        return positionInitiale;
    }

    public void setVulnerable(){
        setEtat(new EtatVulnerable());
    }

    public void setInvulnerable(){
        setEtat(new EtatInvulnerable());
    }

    public boolean isVulnerable(){
        return etat instanceof EtatVulnerable;
    }

    public boolean isInvulnerable(){
        return etat instanceof EtatInvulnerable;
    }

    public boolean isInactif(){
        return etat instanceof EtatInactif;
    }

    public void setInactif() throws Exception {
        if (typeAgent==TypeAgent.PACMAN){
            throw new Exception();
        }
        setEtat(new EtatInactif());
    }

    public boolean isControlable() {
        return controlable;
    }

    public void setControlable(boolean controlable) {
        this.controlable = controlable;
    }

    public void setPositionInitiale(PositionAgent positionInitiale) {
        this.positionInitiale = positionInitiale;
    }


}
