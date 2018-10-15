
package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Vastaus;

public class VastausDao implements Dao<Vastaus, Integer> {

    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer kysymysid = rs.getInt("kysymys_id");
        String vastausteksti = rs.getString("vastausteksti");
        Boolean oikein = rs.getBoolean("oikein");

        Vastaus v = new Vastaus(id, kysymysid, vastausteksti, oikein);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus");

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Integer kysymysid = rs.getInt("kysymys_id");
            String vastausteksti = rs.getString("vastausteksti");
            Boolean oikein = rs.getBoolean("oikein");

            vastaukset.add(new Vastaus(id, kysymysid, vastausteksti, oikein));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    public List<Vastaus> findAll(Integer kysymysid) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
        
        stmt.setInt(1, kysymysid);

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String vastausteksti = rs.getString("vastausteksti");
            Boolean oikein = rs.getBoolean("oikein");

            vastaukset.add(new Vastaus(id, kysymysid, vastausteksti, oikein));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
     @Override
    public void saveOrUpdate(Vastaus vastaus) throws SQLException {
        save(vastaus);
    }
    
    private void save(Vastaus vastaus) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO Vastaus"
                + "(kysymys_id, vastausteksti, oikein)"
                + "VALUES (?, ?, ?)");
        
        stmt.setInt(1, vastaus.getKysymysId());
        stmt.setString(2, vastaus.getVastausteksti());
        stmt.setBoolean(3, vastaus.getOikein());
        
        stmt.executeUpdate();
        stmt.close();
        
        if (vastaus.getOikein()) {
            try (PreparedStatement stmt2 = connection.prepareStatement(
                    "UPDATE Kysymys SET piilotettu = ? WHERE id = ?")) {
                stmt2.setBoolean(1, false);
                stmt2.setInt(2, vastaus.getKysymysId());
                
                stmt2.executeUpdate();
            }
        }
        connection.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        
        Integer kysymysid = null;
        
        while (rs.next()) {
            kysymysid = rs.getInt("kysymys_id");
        }
        rs.close();
        stmt.close();
        
        PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM Vastaus WHERE id = ?");
        stmt2.setInt(1, key);
        stmt2.executeUpdate();
        stmt2.close();
        
        PreparedStatement stmt3 = connection.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = "+kysymysid);
        ResultSet rs2 = stmt3.executeQuery();
        Boolean piilotettu = true;
        while (rs2.next()) {
            if (rs2.getBoolean("oikein")) piilotettu = false;
        }
        rs2.close();
        stmt3.close();
        
        try (PreparedStatement stmt4 = connection.prepareStatement(
                "UPDATE Kysymys SET piilotettu = ? WHERE id = ?")) {
            stmt4.setBoolean(1, piilotettu);
            stmt4.setInt(2, kysymysid);

            stmt4.executeUpdate();
        }
        
        connection.close();
    }
}
