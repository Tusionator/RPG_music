package music;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {
    private TextArea txtaMusicSongsList;

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = FXMLLoader.load(getClass().getResource("view_main.fxml"));
        primaryStage.setTitle(MyStrings.APP_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


//        ManagerFile managerFile = new ManagerFile();
//        txtaMusicSongsList = (TextArea) root.lookup("#txtaMusicList");
//        addButtonGrid((GridPane) root.lookup("#gridForButtons"),
//                managerFile.getSortedMusicFoldersList(), primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

}

