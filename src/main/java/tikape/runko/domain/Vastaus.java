package tikape.runko.domain;

public class Vastaus {
    
    private Integer id;
    private Integer kysymysid;
    private String vastausteksti;
    boolean oikein;
    
    public Vastaus(Integer id, Integer kysymysid, String vastausteksti, Boolean oikein) {
        this.id = id;
        this.kysymysid = kysymysid;
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
    }
    
    public Vastaus(Integer kysymysid, String vastausteksti, Boolean oikein) {
        this.kysymysid = kysymysid;
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getKysymysId() {
        return kysymysid;
    }
    
    public String getVastausteksti() {
        return vastausteksti;
    }
    
    public void SetVastausteksti(String vastausteksti) {
        this.vastausteksti = vastausteksti;
    }
    
    public boolean getOikein() {
        return oikein;
    }
}
