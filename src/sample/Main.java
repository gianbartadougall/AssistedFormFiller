package sample;
import Controller.Controller;
import DataTransfer.Data;
import DataTransfer.DataSaver;
import Display.Display;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    String path = "res/Data/data.txt";
    Data data;

    @Override
    public void start(Stage primaryStage) throws Exception{
        data = new Data(path);
        Display display = new Display(1120, 800, "Forms.Form", data);
        Controller controller = new Controller(display, data);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        DataSaver saver = new DataSaver(data);
        saver.saveData(path);
    }
}
