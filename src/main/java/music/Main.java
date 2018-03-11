package music;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResource("res//nightwarrior.ttf").toExternalForm(), 10);

        VBox root = FXMLLoader.load(getClass().getResource("view_main.fxml"));
        primaryStage.setTitle(MyStrings.APP_NAME);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(Constants.STAGE_HEIGHT);
        primaryStage.setMinWidth(Constants.STAGE_WIDTH);
        primaryStage.setMaxWidth(Constants.STAGE_WIDTH);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

