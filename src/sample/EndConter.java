package sample;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import bigOperation.Operation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EndConter {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<bigOperation.Operation> tableView;

    @FXML
    private TableColumn<bigOperation.Operation, String> rahunok;


    @FXML
    private TableColumn<bigOperation.Operation, String> table_cod;

    @FXML
    private TableColumn<bigOperation.Operation, String> table_name;

    @FXML
    private TableColumn<bigOperation.Operation, Double> start_deb;

    @FXML
    private TableColumn<bigOperation.Operation, Double> start_cred;

    @FXML
    private TableColumn<bigOperation.Operation, Double> deb;

    @FXML
    private TableColumn<bigOperation.Operation, Double> cred;

    @FXML
    private TableColumn<bigOperation.Operation, Double> end_deb;

    @FXML
    private TableColumn<bigOperation.Operation, Double> end_cred;


    @FXML
    private DatePicker date_from;

    @FXML
    private DatePicker date_to;

    Usedb usedb = new Usedb();

    @FXML
    void initialize() {
        table_cod.setCellValueFactory(new PropertyValueFactory<>("Code"));
        table_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        start_deb.setCellValueFactory(new PropertyValueFactory<>("StrDeb1"));
        start_cred.setCellValueFactory(new PropertyValueFactory<>("StrCred1"));
        deb.setCellValueFactory(new PropertyValueFactory<>("Deb1"));
        cred.setCellValueFactory(new PropertyValueFactory<>("Cred1"));
        end_deb.setCellValueFactory(new PropertyValueFactory<>("EndDeb1"));
        end_cred.setCellValueFactory(new PropertyValueFactory<>("EndCred1"));

        tableView.setItems(observableList);

    }

    public void showEndTable(ActionEvent actionEvent) {

        tableView.getItems().clear();

        HashMap <String, ArrayList<Double>> map = new HashMap<>();

        // шукаю дані з даний період звичайних рахунків
        try {
            ResultSet resultSet = usedb.findByDate(date_from.getValue().toString(), date_to.getValue().toString());
            while (resultSet.next()){
                if(map.containsKey(resultSet.getString("debet_oper"))){
                    String s = resultSet.getString("debet_oper");
                    Double d = resultSet.getDouble("suma_oper");
                    Double d1 = map.get(s).get(0);
                    map.get(s).set(0, d1+d );
                }else if(!map.containsKey(resultSet.getString("debet_oper"))){
                    String s = resultSet.getString("debet_oper");
                    Double d = resultSet.getDouble("suma_oper");
                    map.put(s, new ArrayList<Double>(Arrays.asList(d, 0.0, 0.0, 0.0)));
                }
                if(map.containsKey(resultSet.getString("credet_oper"))){
                    String s = resultSet.getString("credet_oper");
                    Double c = resultSet.getDouble("suma_oper");
                    Double c1 = map.get(s).get(1);
                    map.get(s).set(1,c1+c);
                }else if(!map.containsKey(resultSet.getString("credet_oper"))) {
                    String s = resultSet.getString("credet_oper");
                    Double c = resultSet.getDouble("suma_oper");
                    map.put(s, new ArrayList<Double>(Arrays.asList(0.0, c, 0.0, 0.0)));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // пошук за минулий період
        try {
            ResultSet resultSet1 = usedb.findByDate("2019-12-23", date_from.getValue().toString());
            while (resultSet1.next()) {
                if(map.containsKey(resultSet1.getString("debet_oper"))){
                    String s = resultSet1.getString("debet_oper");
                    Double d = resultSet1.getDouble("suma_oper");
                    Double d1 = map.get(s).get(2);
                    map.get(s).set(2, d1+d );
                }else {
                    String s = resultSet1.getString("debet_oper");
                    Double d = resultSet1.getDouble("suma_oper");
                    map.put(s, new ArrayList<Double>(Arrays.asList(0.0, 0.0, d, 0.0)));
                }
                if(map.containsKey(resultSet1.getString("credet_oper"))){
                    String s = resultSet1.getString("credet_oper");
                    Double c = resultSet1.getDouble("suma_oper");
                    Double c1 = map.get(s).get(3);
                    map.get(s).set(3,c1+c);
                }else {
                    String s = resultSet1.getString("credet_oper");
                    Double c = resultSet1.getDouble("suma_oper");
                    map.put(s, new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0, c)));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //видалити ПОРОЖНІ ЗНАЧЕННЯ
        if(map.containsKey("")){
            map.remove("");
        }

        HashMap <String, ArrayList<Double>> map2 = new HashMap<>();
        ArrayList<String> list = new ArrayList();
        ArrayList<Double> list1 = new ArrayList<>(Arrays.asList(0.0,0.0,0.0,0.0,0.0,0.0));

        // пошук 361 рахунків І додавання їх в list
        for(String s: map.keySet()){
            if(s.equals("361") || s.equals("372") || s.equals("3721") || s.equals("631") || s.equals("685") || s.equals("6851")){
                if(!list.contains(s)){
                    list.add(s);
                }
            }
        }

        // видалення 361 з map
        for (int i = 0; i < list.size(); i++) {
            map.remove(list.get(i));
        }

        // почерзі з list брати рахунок знаходити значення і виводити
        for (int i = 0; i < list.size(); i++) {

            // пощук нових за 361
            try {
                ResultSet resultSet2 = usedb.findByDate(date_from.getValue().toString(), date_to.getValue().toString());
                while (resultSet2.next()) {
                    if(resultSet2.getString("debet_oper").equals(list.get(i)) || resultSet2.getString("credet_oper").equals(list.get(i))){
                    String sr = resultSet2.getString("debet_oper");
                    String sc = resultSet2.getString("credet_oper");
                    System.out.println(" нові" + resultSet2.getInt("rowid") + " " + resultSet2.getString("debet_oper") + " " + resultSet2.getString("credet_oper") + " " + resultSet2.getDouble("suma_oper") + " " + resultSet2.getString("note_oper") + " " + resultSet2.getString("date_oper"));
                    Double d = resultSet2.getDouble("suma_oper");
                    String note1 = resultSet2.getString("note_oper");
                    if (sr.equals("361") || sr.equals("372") || sr.equals("3721") || sr.equals("631") || sr.equals("685") || sr.equals("6851")) {
                        if (map2.containsKey(note1)) {
                            Double d12 = map2.get(note1).get(0);
                            map2.get(note1).set(0, d + d12);
                        } else if (!map2.containsKey(note1)) {
                            map2.put(note1, new ArrayList<Double>(Arrays.asList(d, 0.0, 0.0, 0.0, 0.0, 0.0)));
                        }
                    }
                    if (sc.equals("361") || sc.equals("372") || sc.equals("3721") || sc.equals("631") || sc.equals("685") || sc.equals("6851")) {
                        if (map2.containsKey(note1)) {
                            Double d12 = map2.get(note1).get(1);
                            map2.get(note1).set(1, d + d12);
                        } else if (!map2.containsKey(note1)) {
                            map2.put(note1, new ArrayList<Double>(Arrays.asList(0.0, d, 0.0, 0.0, 0.0, 0.0)));
                        }
                    }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            // пошук старих за 361
            try {
                ResultSet resultSet3 = usedb.findByDate("2019-12-23", date_from.getValue().toString());

                while (resultSet3.next()) {
                    if(resultSet3.getString("debet_oper").equals(list.get(i)) || resultSet3.getString("credet_oper").equals(list.get(i))) {
                        String sr = resultSet3.getString("debet_oper");
                        String sc = resultSet3.getString("credet_oper");
                        if (sr.equals("361") || sr.equals("372") || sr.equals("3721") || sr.equals("631") || sr.equals("685") || sr.equals("6851") || sc.equals("361") || sc.equals("372") || sc.equals("3721") || sc.equals("631") || sc.equals("685") || sc.equals("6851")) {
                            System.out.println(" Cтарі " + resultSet3.getInt("rowid") + " " + resultSet3.getString("debet_oper") + " " + resultSet3.getString("credet_oper") + " " + resultSet3.getDouble("suma_oper") + " " + resultSet3.getString("note_oper") + " " + resultSet3.getString("date_oper"));
                            Double d = resultSet3.getDouble("suma_oper");
                            String note = resultSet3.getString("note_oper");
                            if (sr.equals("361") || sr.equals("372") || sr.equals("3721") || sr.equals("631") || sr.equals("685") || sr.equals("6851")) {
                                if (map2.containsKey(note)) {
                                    Double d12 = map2.get(note).get(2);
                                    map2.get(note).set(2, d + d12);
                                } else if (!map2.containsKey(note)) {
                                    map2.put(note, new ArrayList<Double>(Arrays.asList(0.0, 0.0, d, 0.0, 0.0, 0.0)));
                                }
                            }
                            if (sc.equals("361") || sc.equals("372") || sc.equals("3721") || sc.equals("631") || sc.equals("685") || sc.equals("6851")) {
                                if (map2.containsKey(note)) {
                                    Double d12 = map2.get(note).get(3);
                                    map2.get(note).set(3, d + d12);
                                } else if (!map2.containsKey(note)) {
                                    map2.put(note, new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0, d, 0.0, 0.0)));
                                }
                            }

                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            for (String dr : map2.keySet()) {
                Double d4 = map2.get(dr).get(0) + map2.get(dr).get(2) - map2.get(dr).get(1) - map2.get(dr).get(3);
                if (d4 == 0) {

                } else if (d4 > 0) {
                    map2.get(dr).set(4, d4);
                }else if (d4 < 0){
                    map2.get(dr).set(5, d4 * (-1));
                }
            }

            for(String dr: map2.keySet()){
                list1.set(0, list1.get(0) + map2.get(dr).get(0));
                list1.set(1, list1.get(1) + map2.get(dr).get(1));
                list1.set(2, list1.get(2) + map2.get(dr).get(2));
                list1.set(3, list1.get(3) + map2.get(dr).get(3));
                list1.set(4, list1.get(4) + map2.get(dr).get(4));
                list1.set(5, list1.get(5) + map2.get(dr).get(5));
            }

            tableView.getItems().add(new Operation(list.get(i), list1.get(2), list1.get(3), list1.get(0), list1.get(1), list1.get(4), list1.get(5)));
            list1 = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
            map2 = new HashMap<String, ArrayList<Double>>();

        }

        for (String s : map.keySet()) {
            tableView.getItems().add(new Operation(s, map.get(s).get(2), map.get(s).get(3),map.get(s).get(0),map.get(s).get(1)));
        }
    }

    ObservableList<bigOperation.Operation> observableList = FXCollections.observableArrayList();

}
