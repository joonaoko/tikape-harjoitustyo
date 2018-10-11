package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }
        
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = postgresqlLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Kysymys (id integer PRIMARY KEY, kurssi varchar(255), aihe varchar(255), kysymysteksti varchar(1000));");
        lista.add("CREATE TABLE Vastaus (id integer PRIMARY KEY, kysymys_id integer, vastausteksti varchar(1000), oikein boolean, FOREIGN KEY (kysymys_id) REFERENCES Kysymys(id));");
        lista.add("INSERT INTO Kysymys (kurssi, aihe, kysymysteksti) VALUES ('Ekaluokan Matikka', 'Pluslaskut', '1+1?');");
        lista.add("INSERT INTO Kysymys (kurssi, aihe, kysymysteksti) VALUES ('Ekaluokan Matikka', 'Miinuslaskut', '1-1?');");
        lista.add("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (1, '1', 0);");
        lista.add("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (1, '2', 1);");
        lista.add("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (2, '1', 1);");
        lista.add("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (2, '2', 0);");

        return lista;
    }
    
    private List<String> postgresqlLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        lista.add("CREATE TABLE Kysymys (id SERIAL PRIMARY KEY, kurssi varchar(255), aihe varchar(255), kysymysteksti varchar(1000));");
        lista.add("CREATE TABLE Vastaus (id SERIAL PRIMARY KEY, kysymys_id integer, vastausteksti varchar(1000), oikein boolean, FOREIGN KEY (kysymys_id) REFERENCES Kysymys(id));");
        lista.add("INSERT INTO Kysymys (kurssi, aihe, kysymysteksti) VALUES ('Ekaluokan Matikka', 'Pluslaskut', '1+1?');");
        lista.add("INSERT INTO Kysymys (kurssi, aihe, kysymysteksti) VALUES ('Ekaluokan Matikka', 'Miinuslaskut', '1-1?');");
        lista.add("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (1, '1', 0);");
        lista.add("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (1, '2', 1);");
        lista.add("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (2, '1', 1);");
        lista.add("INSERT INTO Vastaus (kysymys_id, vastausteksti, oikein) VALUES (2, '2', 0);");

        return lista;
    }
}
