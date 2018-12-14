package Java;

import Java.Controleur.ControleurPacmanGame;
import Java.Model.PacmanGame;
import Java.View.ViewCommande;
import Java.View.ViewGame;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class TestPacmanComplet {

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

        PacmanGame game = new PacmanGame(500,maze);
        ControleurPacmanGame controleurPacmanGame = new ControleurPacmanGame(game);


        ViewCommande view = new ViewCommande(game,controleurPacmanGame);
        ViewGame viewGame = new ViewGame(game);

    }
}
