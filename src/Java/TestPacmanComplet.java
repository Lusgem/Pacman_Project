package Java;

import Java.Controleur.ControleurPacmanGame;
import Java.Model.PacmanGame;
import Java.View.ViewCommande;
import Java.View.ViewGame;
import Java.View.ViewSelectionNbJoueurs;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TestPacmanComplet {

    /**
     * Classe de test du jeu pacman, permet de sélectionner le labyrinthe dans le dossier layout (restreint aux fichiers .lay)
     * Permet également de sélectionner le nombre de joueurs (1 ou 2)
     * @param args
     */
    public static void main(String[] args) {


        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File defaut = vueSysteme.getDefaultDirectory();
        File layouts = new File("src/layouts");
        JFileChooser layoutChooser;
        String filename;
        FileFilter filter = new FileNameExtensionFilter("Fichiers .lay uniquement", "lay");


        if (layouts.exists()){
            layoutChooser = new JFileChooser(layouts);

        }else {
            layoutChooser = new JFileChooser(defaut);
        }
        layoutChooser.setFileFilter(filter);
        layoutChooser.setAcceptAllFileFilterUsed(false);
        layoutChooser.showOpenDialog(null);
        filename = layoutChooser.getSelectedFile().getPath();


        Maze maze;
        try {
           maze = new Maze(filename);
        } catch (Exception e) {
            return;
        }


        // Possibilité de chosir un mode deux joueurs si il y a au moins un fantome dans le labyrinthe choisi
        // Sinon le mode 1 joueur est lancé par défaut
        if(!maze.getGhosts_start().isEmpty()){
            ViewSelectionNbJoueurs selectionNbJoueurs = new ViewSelectionNbJoueurs();
            selectionNbJoueurs.getValider().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    selectionNbJoueurs.setVisible(false);
                    PacmanGame game;

                    if (selectionNbJoueurs.getDeux_joueurs().isSelected()) {
                        game = new PacmanGame(500, maze,2);
                    }
                    else {
                        game = new PacmanGame(500, maze,1);
                    }
                    ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(game);
                    ViewCommande view = new ViewCommande(game, controleurPacmanGame);
                    ViewGame viewGame = new ViewGame(game);

                }

            });

        }
        else {
            PacmanGame game = new PacmanGame(500,maze,1);
            ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(game);
            ViewCommande view = new ViewCommande(game,controleurPacmanGame);
            ViewGame viewGame = new ViewGame(game);
        }



    }
}
