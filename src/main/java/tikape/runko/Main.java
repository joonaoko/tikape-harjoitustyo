package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausDao;
import tikape.runko.domain.Aihe;
import tikape.runko.domain.Asettelu;
import tikape.runko.domain.Kurssi;
import tikape.runko.domain.Kysymys;
import tikape.runko.domain.Piilotettu;
import tikape.runko.domain.Vastaus;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:db/database.db");
        database.init();

        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("", "");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            Asettelu a = new Asettelu();
            List<Piilotettu> lista = a.asettele(kysymysDao.findAll());
            map.put("asettelu", lista);

            return new ModelAndView(map, "kysymykset");
        }, new ThymeleafTemplateEngine());

        get("/kysymykset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymys", kysymysDao.findOne(Integer.parseInt(req.params("id"))));
            map.put("vastaukset", vastausDao.findAll(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());
        
        post("/kysymykset", (req, res) -> {
           String kurssi = req.queryParams("kurssi");
           if (kurssi.equals("")) kurssi = "Muut kurssit";
           String aihe = req.queryParams("aihe");
           if (aihe.equals("")) aihe = "Muut aiheet";
           String kysymysteksti = req.queryParams("kysymysteksti");
           Kysymys kysymys = new Kysymys(kurssi, aihe, kysymysteksti);
           
           kysymysDao.saveOrUpdate(kysymys);
           res.redirect("/kysymykset");
           return "";
        });
        
        post("/kysymykset/delete/:id", (req, res) -> {
            kysymysDao.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/kysymykset");
            return "";
        });
        
        post("/kysymykset/:id", (req, res) -> {
            String vastausteksti = req.queryParams("vastausteksti");
            Boolean oikein = false;
            if (req.queryParams("oikein") != null) oikein = true;
            Integer kysymysid = Integer.parseInt(req.params(":id"));
            Vastaus vastaus = new Vastaus(kysymysid, vastausteksti, oikein);
            
            vastausDao.saveOrUpdate(vastaus);
            res.redirect("/kysymykset/"+req.params(":id"));
            return "";
        });
        
        post("/kysymykset/:kysymysid/delete/:id", (req, res) -> {
           vastausDao.delete(Integer.parseInt(req.params(":id")));
           res.redirect("/kysymykset/"+req.params(":kysymysid"));
           return "";
        });
    }
}
