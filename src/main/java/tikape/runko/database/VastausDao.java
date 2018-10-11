
package tikape.runko.database;

import java.sql.Connection;
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
        String vastausteksti = rs.getString("vastausteksti");
        Boolean oikein = rs.getBoolean("oikein");

        Vastaus v = new Vastaus(id, vastausteksti, oikein);

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
            String vastausteksti = rs.getString("vastausteksti");
            Boolean oikein = rs.getBoolean("oikein");

            vastaukset.add(new Vastaus(id, vastausteksti, oikein));
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

            vastaukset.add(new Vastaus(id, vastausteksti, oikein));
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
    
    private void save(Vastaus vastaus) {
        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
