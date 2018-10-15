package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Aihe {
    String nimi;
    List <Kysymys> kysymykset;
    
    public Aihe(String nimi) {
        this.kysymykset = new ArrayList<>();
        this.nimi = nimi;
    }
    
    public void lisaaKysymys(Kysymys k) {
        kysymykset.add(k);
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public List<Kysymys> getKysymykset() {
        return kysymykset;
    }
}
