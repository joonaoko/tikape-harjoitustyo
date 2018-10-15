package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Kurssi {
    String nimi;
    List<Aihe> aiheet;
    List<Kysymys> kysymykset;
    
    public Kurssi(String nimi) {
        this.kysymykset = new ArrayList<>();
        this.aiheet = new ArrayList<>();
        this.nimi = nimi;
    }
    
    public void jaaAiheisiin() {
        String edellinen = null;
        Aihe viimeisin = new Aihe("");
        
        for (Kysymys k : kysymykset) {
            if (edellinen == null || !k.getAihe().equals(edellinen)) {
                viimeisin = new Aihe(k.getAihe());
                viimeisin.lisaaKysymys(k);
                aiheet.add(viimeisin);
                edellinen = k.getAihe();
            } else {
                viimeisin.lisaaKysymys(k);
            }
        }
    }
    
    public void lisaaAihe(Aihe a) {
        aiheet.add(a);
    }
    
    public void lisaaKysymys(Kysymys kysymys) {
        kysymykset.add(kysymys);
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public List<Aihe> getAiheet() {
        return aiheet;
    }
}
