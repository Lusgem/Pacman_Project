import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class TestPacmanComplet {

    public static void main(String[] args) {

        FileSystemView vueSysteme = FileSystemView.getFileSystemView();
        File defaut = vueSysteme.getDefaultDirectory();
        File home = vueSysteme.getHomeDirectory();
        File layouts = new File("src/layouts");
        JFileChooser layoutChooser;
        String filename="";
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

        PacmanGame game = new PacmanGame(20,maze);


        ViewCommande view = new ViewCommande(game);
        ViewGame viewGame = new ViewGame(game);

    }
}
