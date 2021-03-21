package sample;

import java.sql.*;

public class Usedb {

    public static Connection connection;
    private static boolean hasData = false;

    private void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite::resource:" + getClass().getResource("/SQLiteOporation.db"));
        initialize();
    }

    private void initialize() throws SQLException {
        if (!hasData) {
            hasData = true;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type ='table' AND name = 'user'");
            if (!resultSet.next()) {
                Statement statement1 = connection.createStatement();
                statement1.execute("CREATE TABLE  user "
                        + "("
                        + "debet_oper TEXT NOT NULL,"
                        + "credet_oper TEXT NOT NULL,"
                        + "suma_oper REAL NOT NULL,"
                        + "note_oper TEXT NOT NULL,"
                        + "date_oper TEXT NOT NULL"
                        + ");");
            }
        }
    }

    //повертає всіх рядки таблиці
    public ResultSet displayUsers()throws ClassNotFoundException, SQLException {
        if (connection == null) {
            getConnection();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT rowid, debet_oper, credet_oper, suma_oper, note_oper, date_oper FROM user");
        return resultSet;
    }

    //додавання нового рядка в таблицю
    public void addUser(String debet, String credet, double sum, String note, String date) throws SQLException, ClassNotFoundException {

        if(connection == null){
            getConnection();
        }

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user values(?,?,?,?,?);");
        preparedStatement.setString(1,debet);
        preparedStatement.setString(2,credet);
        preparedStatement.setDouble(3,sum);
        preparedStatement.setString(4,note);
        preparedStatement.setString(5,date);
        preparedStatement.execute();
    }

    //повертає останій доданий рядок
    public Operation showLastAddOperation() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT rowid, debet_oper, credet_oper, suma_oper, note_oper, date_oper FROM user WHERE rowid = (SELECT max(rowid) FROM user);");
        return new Operation(resultSet.getInt("rowid"), resultSet.getString("debet_oper"), resultSet.getString("credet_oper"), resultSet.getDouble("suma_oper"), resultSet.getString("note_oper"), resultSet.getString("date_oper"));
    }

    //видалення рядка з таблиці
    public void removeByRowid(int remove_id) throws SQLException, ClassNotFoundException {

        if(connection == null){
            getConnection();
        }
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE rowid = " + remove_id + " ;");
        preparedStatement.executeUpdate();

    }

    //редагування запису
    public void editByRowid(Operation operation) throws SQLException, ClassNotFoundException {
        if(connection == null){
            getConnection();
        }
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET "
                        + "debet_oper = '" + operation.getDebet()
                        + "', credet_oper = '" + operation.getCredet()
                        + "', suma_oper = '" + operation.getSum1()
                        + "', note_oper = '" + operation.getNote()
                        + "', date_oper = '" + operation.getData()
                + "' WHERE rowid = " + operation.getId() + " ;" );
        preparedStatement.executeUpdate();
    }

    //пошук за датою
    public ResultSet findByDate(String from, String to) throws SQLException, ClassNotFoundException {
        if (connection == null) {
            getConnection();
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT rowid, debet_oper, credet_oper, suma_oper, note_oper, date_oper FROM user WHERE date_oper BETWEEN date('" + from + "') AND date('" + to +"');");
        return resultSet;
    }

    //це для тестування
    //видалити в кінці
    // повертає всі рядки в консоль
    public void showInConsol(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("rowid") + " " + resultSet.getString("debet_oper") + " " + resultSet.getString("credet_oper") + " " + resultSet.getDouble("suma_oper") + " "  + resultSet.getString("note_oper") + " " + resultSet.getString("date_oper"));
        }
    }

}

