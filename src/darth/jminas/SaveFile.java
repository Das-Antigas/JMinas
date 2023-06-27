package darth.jminas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveFile {

    private Connection connection;
    private Statement statement;

    public void connect() {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:statistics.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS finished_game (id INTEGER, name STRING, time STRING)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS overall_statistics (name STRING, value INTEGER)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void insertLines() {
        try {
            statement.executeUpdate("INSERT INTO overall_statistics VALUES "
                    + "(played, 0),"
                    + "(won, 0),");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addGameScore(String name, int points) {

    }

    private void addGameWon() {

    }

    private void addGamePlayed() {

    }

}
