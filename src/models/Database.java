package models;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
    private Connection connection = null;
    private String databaseFile;
    private String databaseURL;
    private Model model;

    public Database(String databaseFile, Model model) {
        this.databaseFile = databaseFile;
        this.model = model;
        this.databaseURL = "jdbc:sqlite:" + this.databaseFile;
        this.checkDatabase();
        this.select();
    }

    private void checkDatabase() {
        File f = new File(databaseFile);
        if(!f.exists()) { // if databaseFile does not exist
            createTable(); // Create words table
            createTableScores(); // Create score table
        } else { // if databaseFile exists
            if (!tableExists("words")) {
                createTable();
            }
            if (!tableExists("scores")) {
                createTableScores();
            }
        }
    }

    private boolean tableExists(String databaseTable) {
        try {
            Connection conn = this.dbConnection();
            DatabaseMetaData dmd = conn.getMetaData();
            ResultSet rs = dmd.getTables(null, null, databaseTable, null);
            rs.next(); // Move reading order forward
            return rs.getRow() > 0;
        } catch (SQLException e) {
            // throw new RuntimeException(e);
            return false;
        }
    }

    private void createTable() {
        try {
            Connection conn = this.dbConnection();
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE \"words\" (\n" +
                    "\"id\" INTEGER NOT NULL UNIQUE,\n" +
                    " \"word\" TEXT NOT NULL,\n" +
                    " \"category\" TEXT NOT NULL,\n" +
                    " PRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void createTableScores() {
        try {
            Connection conn = this.dbConnection();
            Statement stmt = conn.createStatement();
            String sql = """
                    CREATE TABLE "scores" (
                        "id" INTEGER NOT NULL UNIQUE,
                        "playertime" TEXT NOT NULL,
                        "playername" TEXT NOT NULL,
                        "guessword" TEXT NOT NULL,
                        "wrongcharacters" TEXT,
                        "gametime" INTEGER,
                        PRIMARY KEY("id" AUTOINCREMENT)
                    );
                    """;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Connection dbConnection() throws SQLException {
        if(connection != null) {
            connection.close();
        }
        connection = DriverManager.getConnection(databaseURL);
        return connection;
    }
    public void select() {
        String sql = "SELECT * FROM words ORDER BY category, word";
        List<String> categories = new ArrayList<>();
        List<DatabaseData> databaseData = new ArrayList<>();
        try {
            Connection conn = this.dbConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String word = rs.getString("word");
                String category = rs.getString("category");
                databaseData.add(new DatabaseData(id, word, category));
                categories.add(category);
            }
            // https://howtodoinjava.com/java8/stream-find-remove-duplicates/
            List<String> unique = categories.stream().distinct().collect(Collectors.toList());
            model.setDatabaseData(databaseData);
            model.setCorrectComboNames(unique);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectByCategory(String cat) {
        String sql = "SELECT * FROM words WHERE category = ? ORDER BY category, word";
        List<String> categories = new ArrayList<>();
        List<DatabaseData> databaseData = new ArrayList<>();
        try {
            Connection conn = this.dbConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cat);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String word = rs.getString("word");
                String category = rs.getString("category");
                databaseData.add(new DatabaseData(id, word, category));
                categories.add(category);
            }
            // https://howtodoinjava.com/java8/stream-find-remove-duplicates/
            List<String> unique = categories.stream().distinct().collect(Collectors.toList());
            model.setDatabaseData(databaseData);
            model.setCorrectComboNames(unique);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(String newWord, String categoryName) {
        String sql = "INSERT INTO words (word, category) VALUES (?, ?)";
        try {
            Connection conn = this.dbConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newWord);
            ps.setString(2, categoryName);
            ps.executeUpdate();
            this.select(); // Update data in table
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(int id) {
        String sql = "DELETE FROM words WHERE id = ?";
        try {
            Connection conn = this.dbConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<DatabaseData> selectById(int id) {
        String sql = "SELECT * FROM words WHERE id = ?";
        List<DatabaseData> databaseData = new ArrayList<>();
        try {
            Connection conn = this.dbConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            databaseData.add(new DatabaseData(
                    rs.getInt("id"),
                    rs.getString("word"),
                    rs.getString("category")
            ));
            rs.next();
            return databaseData;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(int id, String newWord, String categoryName) {
        String sql = "UPDATE words SET word = ?, category = ? WHERE id = ?";
        try {
            Connection conn = this.dbConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newWord);
            ps.setString(2, categoryName);
            ps.setInt(3, id);
            ps.executeUpdate();
            select();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
