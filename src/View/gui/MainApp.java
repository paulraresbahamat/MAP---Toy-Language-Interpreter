package view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader selectLoader = new FXMLLoader(getClass().getResource("/resources/view/gui/SelectWindow.fxml"));
        Parent selectRoot = selectLoader.load();
        SelectController selectController = selectLoader.getController();

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/resources/view/gui/MainWindow.fxml"));
        Parent mainRoot = mainLoader.load();
        MainController mainController = mainLoader.getController();

        selectController.setMainController(mainController);
        mainController.setViewRoot(mainRoot);

        mainController.setSelectRoot(selectRoot);
        mainController.setPrimaryStage(primaryStage);
        selectController.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Toy Language - Select Program");
        primaryStage.setScene(new Scene(selectRoot));
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}