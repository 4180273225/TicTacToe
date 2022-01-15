import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TicTacToePane pane = new TicTacToePane();

        Scene scene = new Scene(pane ,500 , 500);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
