package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Operation> table_view;

    @FXML
    private TableColumn<Operation, Integer> table_Colom_id;

    @FXML
    private TableColumn<Operation, String> table_Colom_debet;

    @FXML
    private TableColumn<Operation, String> table_colom_Credut;

    @FXML
    private TableColumn<Operation, String> table_colom_sum;

    @FXML
    private TableColumn<Operation, String> Table_colom_note;

    @FXML
    private TableColumn<Operation, String> table_colom_data;

    @FXML
    private TextField Debet_field;

    @FXML
    private TextField Credet_field;

    @FXML
    private TextField Sum_field;

    @FXML
    private TextField NoteField;

    @FXML
    private DatePicker datafild;

    @FXML
    private TextField edit_debet;

    @FXML
    private TextField edit_id;

    @FXML
    private TextField edit_suma;

    @FXML
    private TextField edit_credet;

    @FXML
    private TextField edit_data;

    @FXML
    private TextField edit_note;

    @FXML
    private DatePicker strart_period;

    @FXML
    private DatePicker end_period;

    Usedb usedb = new Usedb();

    @FXML   //натиснута кнопка додати користувача
    public void press(javafx.event.ActionEvent actionEvent) {


        if (Debet_field.getText() != "" && Credet_field.getText() != "" && Sum_field.getText() != "" && datafild.getValue() != null) {
            try {
                usedb.addUser(Debet_field.getText(),
                        Credet_field.getText(),
                        Double.parseDouble(Sum_field.getText()),
                        NoteField.getText(),
                        datafild.getValue().toString());

                table_view.getItems().add(usedb.showLastAddOperation());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //Встановлення порожніх значень в поля
            Debet_field.setText("");
            Credet_field.setText("");
            Sum_field.setText("");
            NoteField.setText("");
        }
    }

    @FXML   //натиснута кнопка видалити користувача
    private void delateOperatation() {
        int selectedid = table_view.getSelectionModel().getSelectedItem().getId();

        int selectedIndex = table_view.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                usedb.removeByRowid(selectedid);
                table_view.getItems().remove(selectedIndex);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML   //натиснута редагувати
    private void EditOperation() {
        Operation selectedOperation = table_view.getSelectionModel().getSelectedItem();

        if (selectedOperation != null) {
            selectedOperation.setDebet(edit_debet.getText());
            selectedOperation.setCredet(edit_credet.getText());
            selectedOperation.setSum(Double.parseDouble(edit_suma.getText()));
            selectedOperation.setNote(edit_note.getText());
            selectedOperation.setData(edit_data.getText());

            try {
                usedb.editByRowid(selectedOperation);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        table_view.refresh();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ініціалізація таблиці стовпцями
        table_Colom_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        table_Colom_debet.setCellValueFactory(new PropertyValueFactory<>("Debet"));
        table_colom_Credut.setCellValueFactory(new PropertyValueFactory<>("Credet"));
        table_colom_sum.setCellValueFactory(new PropertyValueFactory<>("Sum"));
        Table_colom_note.setCellValueFactory(new PropertyValueFactory<>("Note"));
        table_colom_data.setCellValueFactory(new PropertyValueFactory<>("Data"));
        table_view.setItems(observableList);
        fromDbtoTableview();
        showOperationDetal(null);
        table_view.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showOperationDetal(newValue));
    }

    ObservableList<Operation> observableList = FXCollections.observableArrayList();

    //для відображення додаткових даних про операцію
    private void showOperationDetal(Operation operation) {
        if (operation != null) {
            edit_id.setText(Integer.toString(operation.getId()));
            edit_debet.setText(operation.getDebet());
            edit_credet.setText(operation.getCredet());
            edit_suma.setText(operation.getSum0());
            edit_data.setText(operation.getData());
            edit_note.setText(operation.getNote());
        } else {
            edit_id.setText("");
            edit_debet.setText("");
            edit_credet.setText("");
            edit_suma.setText("");
            edit_data.setText("");
            edit_note.setText("");
        }
    }

    //для ПОШУКУ ЗА ДАТОБ
    public void findOperationByDate(ActionEvent actionEvent) {
        if (strart_period.getValue() != null && end_period.getValue() != null) {
            try {
                ResultSet resultSet = usedb.findByDate(strart_period.getValue().toString(), end_period.getValue().toString());
                table_view.getItems().clear();
                while (resultSet.next()) {
                    table_view.getItems().add(new Operation(resultSet.getInt("rowid"),
                            resultSet.getString("debet_oper"),
                            resultSet.getString("credet_oper"),
                            resultSet.getDouble("suma_oper"),
                            resultSet.getString("note_oper"),
                            resultSet.getString("date_oper")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //для відобрежнння даних із бд
    public void fromDbtoTableview(MouseEvent mouseEvent) {
        table_view.getItems().clear();
        try {
            ResultSet resultSet = usedb.displayUsers();
            while (resultSet.next()) {
                table_view.getItems().add(new Operation(resultSet.getInt("rowid"),
                        resultSet.getString("debet_oper"),
                        resultSet.getString("credet_oper"),
                        resultSet.getDouble("suma_oper"),
                        resultSet.getString("note_oper"),
                        resultSet.getString("date_oper")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //для відобрежнння даних із бд
    public void fromDbtoTableview() {
        try {
            table_view.getItems().clear();
            ResultSet resultSet = usedb.displayUsers();
            while (resultSet.next()) {
                table_view.getItems().add(new Operation(resultSet.getInt("rowid"),
                        resultSet.getString("debet_oper"),
                        resultSet.getString("credet_oper"),
                        resultSet.getDouble("suma_oper"),
                        resultSet.getString("note_oper"),
                        resultSet.getString("date_oper")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void startEnd(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("end.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Обороно-сальдова відомість");
        primaryStage.setScene(new Scene(root, 1347, 600));
        primaryStage.show();
    }
}
