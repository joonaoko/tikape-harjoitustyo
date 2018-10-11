
package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Kysymys;

public class KysymysDao implements Dao<Kysymys, Integer> {

    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }
    
    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        return DriverManager.getConnection("jdbc:sqlite:db/database.db");
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection connection = getConnection(); // database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String kurssi = rs.getString("kurssi");
        String aihe = rs.getString("aihe");
        String kysymysteksti = rs.getString("kysymysteksti");

        Kysymys k = new Kysymys(id, kurssi, aihe, kysymysteksti);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {

        Connection connection = getConnection(); // database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys");

        ResultSet rs = stmt.executeQuery();
        List<Kysymys> kysymykset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String kurssi = rs.getString("kurssi");
            String aihe = rs.getString("aihe");
            String kysymysteksti = rs.getString("kysymysteksti");

            kysymykset.add(new Kysymys(id, kurssi, aihe, kysymysteksti));
        }

        rs.close();
        stmt.close();
        connection.close();

        return kysymykset;
    }
    
    @Override
    public void saveOrUpdate(Kysymys kysymys) throws SQLException {
        save(kysymys);
    }
    
    private void save(Kysymys kysymys) throws SQLException {
        Connection connection = getConnection(); // database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Kysymys"
                + "(kurssi, aihe, kysymysteksti)"
                + "VALUES (?, ?, ?)");
        
        stmt.setString(1, kysymys.getKurssi());
        stmt.setString(2, kysymys.getAihe());
        stmt.setString(3, kysymys.getKysymysteksti());
        
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = getConnection(); // database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM Kysymys WHERE id = ?");
        
        stmt.setInt(1, key);
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
}
