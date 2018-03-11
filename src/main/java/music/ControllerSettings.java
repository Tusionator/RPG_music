package music;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.prefs.Preferences;

public class ControllerSettings {
    @FXML
    private TextField txtfDirectory;

    @FXML
    public void initialize() {
        try {
            String directory = ManagerFile.getMainDirectory().getAbsolutePath();
            txtfDirectory.setText(directory);
        } catch (Exception e) {
            txtfDirectory.setText("");
        }
    }

    @FXML
    public void handleButtonBrowse(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(((Node) event.getTarget()).getScene().getWindow());
        if (file != null && file.isDirectory()) {
            txtfDirectory.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void handleButtonSave() {
        String directoryStr = txtfDirectory.getText();
        ManagerFile.setMainDirectory(directoryStr);

        Stage stage = (Stage) txtfDirectory.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleButtonCancel() {
        Stage stage = (Stage) txtfDirectory.getScene().getWindow();
        stage.close();
    }

}
