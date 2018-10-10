package tikape.runko.domain;

public class Kysymys {
    
    private Integer id;
    private String kysymysteksti;
    
    public Kysymys(Integer id, String kysymysteksti) {
        this.id = id;
        this.kysymysteksti = kysymysteksti;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getKysymysteksti() {
        return kysymysteksti;
    }
    
    public void SetKysymysteksti(String kysymysteksti) {
        this.kysymysteksti = kysymysteksti;
    }
}
