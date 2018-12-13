package Sources;

import Sources.View.Observateur;

public interface Sujet {
    public void enregistrerObservateur(Observateur observateur);
    public void supprimerObservateur(Observateur observateur);
    public void notifierObservateur();
}
