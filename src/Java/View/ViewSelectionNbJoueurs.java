package Java.View;
import javax.swing.*;
import java.awt.*;

/**
 * Vue permettant de sélectionner le nombre de joueurs
 * Ce nombre de joueurs est récupéré par la classe de test au moment de l'appui sur le bouton valider
 *
 */
public class ViewSelectionNbJoueurs extends JFrame{

    Button valider = new Button("Valider");
    JRadioButton un_joueur = new JRadioButton("Un Joueur");
    JRadioButton deux_joueurs = new JRadioButton("Deux joueurs");

    public ViewSelectionNbJoueurs() throws HeadlessException {

        ButtonGroup bG = new ButtonGroup();
        bG.add(un_joueur);
        bG.add(deux_joueurs);
        setSize(200,100);
        setLayout( new FlowLayout());
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width;
        int dy = centerPoint.y - windowSize.height;
        setLocation(dx, dy);
        add(un_joueur);
        add(deux_joueurs);
        un_joueur.setSelected(true);
        add(valider);
        setVisible(true);



    }

    public Button getValider() {
        return valider;
    }

    public JRadioButton getUn_joueur() {
        return un_joueur;
    }

    public JRadioButton getDeux_joueurs() {
        return deux_joueurs;
    }
}
