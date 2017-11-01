package music;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view_main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        System.out.println("Start");
        primaryStage.show();

        Node gridForButtons = root.lookup("#gridForButtons");

        Button button = new Button("btn1");
        GridPane gridPane = (GridPane) gridForButtons;

        gridPane.add(button,1,1);

        //TODO show instruction - setting main directory,
        // how folders are named etc.
        // this should has checkbox with "don't show again"

//        //pick up main directory, where are folders with music
//        //create folders if there isn't any
//        ManagerFile managerFile = new ManagerFile();
//        managerFile.getMusicFoldersNames();



        //create 16? buttons in manager file










//        FileChooser fileChooser = new FileChooser();
//        File file = fileChooser.showOpenDialog(primaryStage);
//        System.out.println(file.getPath());



//        String bip = "/Users/april121/Work/MyMusic!/src/sample/Songs/01 Clarity.m4a";
//        Media hit = new Media(bip);
//        MediaPlayer mediaPlayer = new MediaPlayer(hit);
//        MediaPlayer.play();


//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setTitle("Choose directory");
//        File directoryMusic = directoryChooser.showDialog(primaryStage);
//        //null pointer
//
//
//        System.out.println(directoryMusic.getAbsolutePath());
//        for (String str : directoryMusic.list()) {
//            System.out.println(str);
//        }



    }


    public static void main(String[] args) {
        launch(args);
    }
}
