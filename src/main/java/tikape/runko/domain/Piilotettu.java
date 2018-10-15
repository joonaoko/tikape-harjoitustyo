package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Piilotettu {
    String nimi = "";
    List<Kurssi> kurssit;
    List<Kysymys> kysymykset;
    
    public Piilotettu(Boolean piilotettu) {
        this.kysymykset = new ArrayList<>();
        this.kurssit = new ArrayList<>();
        if (piilotettu) this.nimi = "Piilotetut kysymykset";
    }
    
    public void jaaKursseihin() {
        String edellinen = null;
        Kurssi viimeisin = new Kurssi("");
        
        for (Kysymys k : kysymykset) {
            
            if (edellinen == null || !k.getKurssi().equals(edellinen)) {
                viimeisin = new Kurssi(k.getKurssi());
                viimeisin.lisaaKysymys(k);
                kurssit.add(viimeisin);
                edellinen = k.getKurssi();
            } else {
                viimeisin.lisaaKysymys(k);
            }
        }
        
        for (Kurssi kurssi : kurssit) {
            kurssi.jaaAiheisiin();
        }
    }
    
    public void lisaaKurssi(Kurssi k) {
        kurssit.add(k);
    }
    
    public void lisaaKysymys(Kysymys kysymys) {
        kysymykset.add(kysymys);
    }
    
    public int size() {
        return kysymykset.size();
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public List<Kurssi> getKurssit() {
        return kurssit;
    }
}
