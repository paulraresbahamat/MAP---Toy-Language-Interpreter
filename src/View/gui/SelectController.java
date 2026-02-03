package view.gui;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.PrgState;
import model.adt.CustomDict;
import model.adt.CustomHeap;
import model.adt.CustomList;
import model.adt.CustomStack;
import model.statement.IStmt;
import repository.Repository;

public class SelectController {
    @FXML private ListView<IStmt> programListView;
    private MainController mainController;
    private Stage primaryStage;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        programListView.setItems(FXCollections.observableArrayList(
                view.View.ex1,
                view.View.ex2,
                view.View.ex3,
                view.View.ex4,
                view.View.ex5,
                view.View.ex6,
                view.View.ex7,
                view.View.ex8,
                view.View.ex9,
                view.View.ex10
        ));
    }

    @FXML
    private void handleSelectProgram() {
        IStmt selectedStmt = programListView.getSelectionModel().getSelectedItem();
        if (selectedStmt == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a program!").showAndWait();
            return;
        }

        try {
            selectedStmt.typecheck(new CustomDict<>());

            PrgState prg = new PrgState(new CustomStack<>(), new CustomDict<>(), new CustomList<>(),
                    selectedStmt, new CustomDict<>(), new CustomHeap<>());
            Controller ctrl = new Controller(new Repository(prg, "log_gui.txt"));

            mainController.setController(ctrl);

            Stage stage = (Stage) programListView.getScene().getWindow();
            stage.getScene().setRoot(mainController.getViewRoot());
            stage.setTitle("Interpreter Dashboard");

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Selection Error: " + e.getMessage()).showAndWait();
        }
    }
}