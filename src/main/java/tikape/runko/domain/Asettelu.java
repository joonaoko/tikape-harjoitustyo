package tikape.runko.domain;
import java.util.List;
import java.util.ArrayList;

public class Asettelu {
    
    public List<Piilotettu> asettele(List<Kysymys> kysymykset) {
        List<Piilotettu> asettelu = new ArrayList<>();
        
        Piilotettu p0 = new Piilotettu(false);
        Piilotettu p1 = new Piilotettu(true);
        
        for (Kysymys k : kysymykset) {
            
            if (!k.getPiilotettu()) {
                p0.lisaaKysymys(k);
            }
            
            if (k.getPiilotettu()) {
                p1.lisaaKysymys(k);
            }
        }
        
        p0.jaaKursseihin();
        asettelu.add(p0);
        
        if (p1.size() > 0) {
            p1.jaaKursseihin();
            asettelu.add(p1);
        }
        
        return asettelu;
    }
    
    
    
    
}
