package pckg0;

import javafx.application.Application;
import javafx.stage.Stage;



public class JavaFXApplication extends Application {
    public static void main(String[] args){
        launch();
    }

    public void start(Stage theStage){
        theStage.setTitle("Hello World!");
        theStage.show();
    }

}
