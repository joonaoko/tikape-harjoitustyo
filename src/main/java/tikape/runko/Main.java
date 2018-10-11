package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausDao;
import tikape.runko.domain.Kysymys;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:db/database.db");
        database.init();

        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymysDao.findAll());

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
           String aihe = req.queryParams("aihe");
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
    }
}
