package darth.jminas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveFile {

    private Connection connection;
    private Statement statement;
    private static SaveFile uniqueInstance;

    private SaveFile() {
    }

    public static synchronized SaveFile getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SaveFile();
            uniqueInstance.connect();
        }

        return uniqueInstance;
    }

    public void connect() {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:statistics.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS finished_game (name STRING, time STRING)");
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
                    + "('played', 0),"
                    + "('lost', 0),"
                    + "('won', 0)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void incrementLine(String name) {
        try {
            String sql = "UPDATE overall_statistics SET value = value + 1 WHERE name = ?";
            PreparedStatement s = connection.prepareStatement(sql);

            s.setString(1, name);
            int rowsUpdated = s.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Value " + name + " incremented successfully!");
            } else {
                System.out.println("No matching rows found.");
            }

            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGameScore(String name, int points) {
        try {
            String sql = "INSERT INTO finished_game VALUES (?, ?)";
            PreparedStatement s = connection.prepareStatement(sql);

            s.setString(1, name);
            s.setInt(2, points);

            int rowsUpdated = s.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("GameScore added successfully!");
            } else {
                System.out.println("No matching rows found.");
            }

            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void incrementGamesWon() {
        this.incrementLine("won");
    }

    public void incrementGamesLost() {
        this.incrementLine("lost");
    }

    public void incrementGamesPlayed() {
        this.incrementLine("played");
    }

    private int getValueFromStatistics(String statistics) {
        try {
            String sql = "SELECT value FROM overall_statistics WHERE name = ?";
            PreparedStatement s = connection.prepareStatement(sql);
            s.setString(1, statistics);

            ResultSet resultSet = s.executeQuery();
            int value;
            if (resultSet.next()) {
                value = resultSet.getInt("value");
            } else {
                value = 0;
            }

            resultSet.close();
            s.close();

            return value;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getGamesWon() {
        return this.getValueFromStatistics("won");
    }

    public int getGamesLost() {
        return this.getValueFromStatistics("lost");
    }

    public int getGamesPlayed() {
        return this.getValueFromStatistics("played");
    }
}
