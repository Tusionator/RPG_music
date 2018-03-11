package music;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;

public class ControllerMain {
    @FXML
    private GridPane gridForButtons;
    @FXML
    private TextArea txtaMusicList;
    @FXML
    private VBox parentBox;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnStop;

    private Background backgroundPlay;
    private Background backgroundPause;
    private Background backgroundStop;
    private Background backgroundDisablePlay;
    private Background backgroundDisableStop;


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


        BackgroundImage backgroundImagePlay = new BackgroundImage(new Image(
                getClass().getClassLoader().getResource("images/play.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        backgroundPlay = new Background(backgroundImagePlay);
        BackgroundImage backgroundImagePause = new BackgroundImage(new Image(
                getClass().getClassLoader().getResource("images/pause.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        backgroundPause = new Background(backgroundImagePause);
        BackgroundImage backgroundImageStop = new BackgroundImage(new Image(
                getClass().getClassLoader().getResource("images/stop.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        backgroundStop = new Background(backgroundImageStop);
        BackgroundImage backgroundImageDisablePlay = new BackgroundImage(new Image(
                getClass().getClassLoader().getResource("images/play_dis.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        backgroundDisablePlay = new Background(backgroundImageDisablePlay);
        BackgroundImage backgroundImageDisableStop = new BackgroundImage(new Image(
                getClass().getClassLoader().getResource("images/stop_dis.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        backgroundDisableStop = new Background(backgroundImageDisableStop);
        btnPlay.setBackground(backgroundDisablePlay);
        btnStop.setBackground(backgroundDisableStop);

        setGridForButtonsView();

        ManagerMusic.sMusicStatus.addObserver(new Observer() {
            @Override
            public void update(java.util.Observable o, Object arg) {
                Integer i = (Integer) arg;

                if (i == MusicStatusObservable.PLAYING) {
                    btnPlay.setBackground(backgroundPause);
                    btnStop.setBackground(backgroundStop);

                    System.out.println("PLAYING");
                } else if (i == MusicStatusObservable.PAUSED) {
                    btnPlay.setBackground(backgroundPlay);
                    btnStop.setBackground(backgroundStop);
                    System.out.println("PAUSED");
                } else if (i == MusicStatusObservable.STOPPED) {
                    btnPlay.setBackground(backgroundPlay);
                    btnStop.setBackground(backgroundDisableStop);
                    System.out.println("STOPPED");
                } else if (i == MusicStatusObservable.NOTHING_TO_PLAY) {
                    btnPlay.setBackground(backgroundDisablePlay);
                    btnStop.setBackground(backgroundDisableStop);
                    System.out.println("NOTHING_TO_PLAY");
                }
            }
        });

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
            VBox vBox = FXMLLoader.load(getClass().getResource("../../resources/fxmls/view_settings.fxml"));
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
        double buttonMaxH = 60.0;
        double buttonMaxW = 170.0;

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
            gridForButtons.setMinHeight(rowsNo * buttonMaxH);
            gridForButtons.setMaxHeight(rowsNo * buttonMaxH);
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
