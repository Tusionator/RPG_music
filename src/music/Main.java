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


        ManagerFile managerFile = new ManagerFile();
        txtaMusicSongsList = (TextArea) root.lookup("#txtaMusicList");
        addButtonGrid((GridPane) root.lookup("#gridForButtons"),
                managerFile.getSortedMusicFoldersList(), primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }


    //adds button(music folders) to gridpane, resizes minimum size of stage
    private void addButtonGrid(GridPane gridPane, ArrayList<File> foldersNamesList, Stage stage) {
        //TODO they cant be 0 or -1 or less than 1
        int colNo = Constants.BUTTONS_COLUMNS_COUNT;
        int rowsNo = ((foldersNamesList.size() - 1) / colNo) + 1;
        //TODO button size hardcoded - change when button image loaded
        double buttonMaxH = 100.0;
        double buttonMaxW = 150.0;

        for (int i = 0; i < colNo; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100 / colNo);
            column.setFillWidth(true);
            column.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(column);
        }
        for (int i = 0; i < rowsNo; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100 / rowsNo);
            row.setFillHeight(true);
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
        }
        System.out.println("colnr: " + colNo + " rows: " + rowsNo);

        for (int i = 0; i < foldersNamesList.size(); i++) {
            Button button = new Button();
            File folder = foldersNamesList.get(i);
            button.setText(ManagerFile.getMusicFolderName(folder));
            button.setUserData(folder);
            button.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    //TODO separate
                    if (mouseEvent.getClickCount() == 2) {
                        System.out.println("Double clicked");
                    } else if (mouseEvent.getClickCount() == 1) {
                        System.out.println("one clicked");
                        Button sourceButton = (Button) mouseEvent.getSource();
                        ArrayList<String> musicList = ManagerMusic.setMusicList((File) sourceButton.getUserData());
                        setMusicSongsList(musicList);
                    }
                }
            });
            button.setMaxSize(buttonMaxW, buttonMaxH);
            gridPane.add(button, i % Constants.BUTTONS_COLUMNS_COUNT,
                    i / Constants.BUTTONS_COLUMNS_COUNT);
            GridPane.setHalignment(button, HPos.CENTER);
        }
        //gridPane.setMaxHeight(rowsNo * (buttonMaxH + 10.0));
        stage.setMinHeight(stage.getHeight() + gridPane.getHeight());
        stage.setMinWidth(stage.getWidth() + 10.0);
    }

    private void setMusicSongsList(ArrayList<String> musicList) {
        if (musicList == null || musicList.size() < 1) {
            txtaMusicSongsList.setText("\n" + MyStrings.NO_MUSIC_MESSAGE);
            return;
        }
        String strMusicList = "\n";
        for (String hit : musicList) {
            strMusicList = strMusicList.concat(hit + "\n");
        }
        System.out.println("list: " + strMusicList);
        txtaMusicSongsList.setText(strMusicList);
    }
}

