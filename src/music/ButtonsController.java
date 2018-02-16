package music;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class ButtonsController {

    @FXML
    public void handleButtonPlay(ActionEvent event) {
        ManagerMusic.playOrPauseMusic();

    }

    @FXML
    public void handleButtonStop(ActionEvent event) {
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

}
