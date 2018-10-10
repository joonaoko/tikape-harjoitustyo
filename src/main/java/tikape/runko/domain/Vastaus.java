package tikape.runko.domain;

public class Vastaus {
    
    private Integer id;
    private String vastausteksti;
    boolean oikein;
    
    public Vastaus(Integer id, String vastausteksti, Boolean oikein) {
        this.id = id;
        this.vastausteksti = vastausteksti;
        this.oikein = oikein;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
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
