package music;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    private VBox root;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("view_main.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, Constants.STAGE_WIDTH, Constants.STAGE_HEIGHT));
//        primaryStage.setMinWidth(Constants.STAGE_WIDTH);
//        primaryStage.setMinHeight(Constants.STAGE_HEIGHT);
//        System.out.println("Start");
//        primaryStage.show();


        root = FXMLLoader.load(getClass().getResource("view_main.fxml"));
        primaryStage.setTitle(MyStrings.APP_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


        //TODO save resized size in preferences
        //TODO save main directory in preferences
        //TODO buttons on gridpane - should have max value, so when 

        //TODO show instruction - setting main directory,
        // how folders are named etc.
        // this should has checkbox with "don't show again"

        //pick up main directory, where are folders with music
        //create folders if there isn't any
        ManagerFile managerFile = new ManagerFile();


        addButtonGrid((GridPane) root.lookup("#gridForButtons"),
                managerFile.getSortedMusicFoldersList(), primaryStage);


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

    //adds button(music folders) to gridpane, resizes minimum size of stage
    private void addButtonGrid(GridPane gridPane, ArrayList<File> foldersNamesList, Stage stage) {
        int colNo = Constants.BUTTONS_COLUMNS_COUNT;
        int rowsNo = (foldersNamesList.size() / colNo) + 1;

        for (int i = 0; i < colNo; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setFillWidth(true);
            column.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(column);
        }
        for (int i = 0; i < rowsNo; i++) {
            RowConstraints row = new RowConstraints();
            row.setFillHeight(true);
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < foldersNamesList.size(); i++) {
            Button button = new Button();
            File folder = foldersNamesList.get(i);
            button.setText(ManagerFile.getMusicFolderName(folder));
            button.setUserData(folder);
            //TODO button size hardcoded - change when button image loaded
            button.setMaxSize(400.0, 400.0);
            gridPane.add(button, i % Constants.BUTTONS_COLUMNS_COUNT,
                    i / Constants.BUTTONS_COLUMNS_COUNT);
        }

        stage.setMinHeight(root.getHeight() + 100.0);
        stage.setMinWidth(root.getWidth() + 10.0);


        System.out.println("root w: " + root.getWidth() + " h: " + root.getHeight());
    }


    public static void main(String[] args) {
        launch(args);
    }

}

