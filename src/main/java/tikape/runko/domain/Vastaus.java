package tikape.runko.domain;

public class Vastaus {
    
    private Integer id;
    private String vastausteksti;
    
    public Vastaus(Integer id, String vastausteksti) {
        this.id = id;
        this.vastausteksti = vastausteksti;
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
}
