package view.gui;

import controller.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.PrgState;
import model.value.IValue;
import model.statement.IStmt;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    private Controller controller;
    private Parent viewRoot; // To store the layout for scene switching
    private Parent selectRoot;
    private Stage primaryStage;

    @FXML private TextField nrPrgStatesField;
    @FXML private TableView<Map.Entry<Integer, IValue>> heapTableView;
    @FXML private TableColumn<Map.Entry<Integer, IValue>, Integer> heapAddressColumn;
    @FXML private TableColumn<Map.Entry<Integer, IValue>, IValue> heapValueColumn;
    @FXML private ListView<IValue> outListView;
    @FXML private ListView<String> fileTableListView;
    @FXML private ListView<Integer> prgStateIdListView;
    @FXML private TableView<Map.Entry<String, IValue>> symTableView;
    @FXML private TableColumn<Map.Entry<String, IValue>, String> symVarColumn;
    @FXML private TableColumn<Map.Entry<String, IValue>, IValue> symValueColumn;
    @FXML private ListView<String> exeStackListView;

    public void setViewRoot(Parent root) { this.viewRoot = root; }
    public Parent getViewRoot() { return viewRoot; }

    public void setSelectRoot(Parent selectRoot) {
        this.selectRoot = selectRoot;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleGoBack(ActionEvent event) {
        if (primaryStage != null && selectRoot != null) {
            // reuse the same Scene and swap root back to the select window
            primaryStage.getScene().setRoot(selectRoot);
        }
    }

    @FXML
    public void initialize() {
        heapAddressColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        heapValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        symVarColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey()));
        symValueColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        prgStateIdListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> updateSpecifics(newV));
    }

    public void setController(Controller ctrl) {
        this.controller = ctrl;
        updateAll();
    }

    private void updateAll() {
        List<PrgState> prgs = controller.getRepo().getPrgList();
        nrPrgStatesField.setText(String.valueOf(prgs.size()));

        if (!prgs.isEmpty()) {
            PrgState first = prgs.getFirst();
            heapTableView.setItems(FXCollections.observableArrayList(first.getHeap().getHeap().entrySet()));
            outListView.setItems(FXCollections.observableArrayList(first.getOutput().getList()));
            fileTableListView.setItems(FXCollections.observableArrayList(
                    first.getFileTable().getContent().keySet().stream().map(Object::toString).toList()
            ));
            prgStateIdListView.setItems(FXCollections.observableArrayList(
                    prgs.stream().map(PrgState::getId).toList()
            ));
            if (!prgStateIdListView.getItems().isEmpty()) {
                prgStateIdListView.getSelectionModel().selectFirst();
                updateSpecifics(prgStateIdListView.getSelectionModel().getSelectedItem());
            }
        }
    }

    private void updateSpecifics(Integer id) {
        if (id == null) return;
        controller.getRepo().getPrgList().stream()
                .filter(p -> p.getId() == id).findFirst().ifPresent(p -> {
                    symTableView.setItems(FXCollections.observableArrayList(p.getSymTable().getContent().entrySet()));
                    List<String> stack = p.getExeStack().getList().stream().map(Object::toString).toList();
                    exeStackListView.setItems(FXCollections.observableArrayList(stack).sorted(Collections.reverseOrder()));
                });
    }

    @FXML
    private void handleRunOneStep() {
        try {
            List<PrgState> prgList = controller.removeCompletedPrg(controller.getRepo().getPrgList());
            if (prgList.isEmpty()) throw new Exception("Execution completed!");

            nrPrgStatesField.setText(String.valueOf(prgList.size()));

            controller.oneStepForAllPrg(prgList);
            updateAll();
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
        }
    }
}
