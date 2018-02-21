package music;

import com.sun.javafx.css.Style;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerMain {
    @FXML
    private GridPane gridForButtons;
    @FXML
    private TextArea txtaMusicList;
    @FXML
    private VBox parentBox;

    @FXML
    public void initialize() {
        File mainDirectory = ManagerFile.getMainDirectory();
        if (mainDirectory == null) {
            openSettings();
            mainDirectory = ManagerFile.getMainDirectory();
            if (mainDirectory == null || !mainDirectory.isDirectory()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Define main directory");
                alert.setHeaderText("Main directory not defined or isn't a directory!");
                alert.setContentText("Please, define main directory, where are folders with RPG music in SETTINGS");
                alert.showAndWait();
            }
        }
        setGridForButtonsView();
    }

    @FXML
    public void handleButtonPlay() {
        ManagerMusic.playOrPauseMusic();
    }

    @FXML
    public void handleButtonStop() {
        ManagerMusic.stopMusic();
    }

    @FXML
    public void handleVolumeScroll(Event event) {
        System.out.println("slider");
        Slider slider = (Slider) event.getSource();
        slider.adjustValue(slider.getValue());

        double vol = slider.getValue();
        System.out.println(vol);
        ManagerMusic.changeVolume(vol);
    }

    @FXML
    public void handleSettings() {
        System.out.println("handleSettings");
        openSettings();
        setGridForButtonsView();
    }


    //NON FXML-----------------------------------------------------------------------------------------
    private void openSettings() {
        System.out.println("openSettings");
        try {
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Settings");
            VBox vBox = FXMLLoader.load(getClass().getResource("view_settings.fxml"));
            Scene scene = new Scene(vBox);
            window.setScene(scene);
            window.showAndWait();
        } catch (IOException e) {
            System.out.println("failed to load settings");
            e.printStackTrace();
        }
    }


    //adds button(music folders) to gridpane, resizes minimum size of stage
    private void setGridForButtonsView() {
        gridForButtons.getChildren().clear();
        gridForButtons.getColumnConstraints().clear();
        gridForButtons.getRowConstraints().clear();
        ArrayList<File> foldersNamesList = ManagerFile.getSortedMusicFoldersList();
        if (foldersNamesList == null || foldersNamesList.isEmpty()) {
            return;
        }
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
            gridForButtons.getColumnConstraints().add(column);
        }
        for (int i = 0; i < rowsNo; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100 / rowsNo);
            row.setFillHeight(true);
            row.setVgrow(Priority.ALWAYS);
            gridForButtons.getRowConstraints().add(row);
        }
        System.out.println("colnr: " + colNo + " rows: " + rowsNo);

        for (int i = 0; i < foldersNamesList.size(); i++) {
            Button button = new Button();
            File folder = foldersNamesList.get(i);
            button.setText(ManagerFile.getMusicFolderName(folder));
            button.setUserData(folder);
            button.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    System.out.println("one clicked");
                    Button sourceButton = (Button) mouseEvent.getSource();
                    ArrayList<String> musicList = ManagerMusic.setMusicList((File) sourceButton.getUserData());
                    setMusicSongsList(musicList);
                }
            });
            button.setMaxSize(buttonMaxW, buttonMaxH);
            gridForButtons.add(button, i % Constants.BUTTONS_COLUMNS_COUNT,
                    i / Constants.BUTTONS_COLUMNS_COUNT);
            GridPane.setHalignment(button, HPos.CENTER);
        }
        parentBox.setMinHeight(parentBox.getHeight() + gridForButtons.getHeight());
        parentBox.setMinWidth(parentBox.getWidth());
    }

    private void setMusicSongsList(ArrayList<String> musicList) {
        if (musicList == null || musicList.size() < 1) {
            txtaMusicList.setText(MyStrings.NO_MUSIC_MESSAGE);
            return;
        }
        String strMusicList = "";
        for (String hit : musicList) {
            strMusicList = strMusicList.concat(hit + "\n");
        }
        System.out.println("list: " + strMusicList);
        txtaMusicList.setText(strMusicList);
    }
}
