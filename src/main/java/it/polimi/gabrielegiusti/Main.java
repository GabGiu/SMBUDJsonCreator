package it.polimi.gabrielegiusti;

import it.polimi.gabrielegiusti.DBManager.Neo4jManager;
import it.polimi.gabrielegiusti.Models.Author;
import it.polimi.gabrielegiusti.Models.ScientificArticle;
import org.neo4j.driver.Config;
import org.neo4j.driver.Record;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String uri = "neo4j+s://b535a102.databases.neo4j.io";
        String username = "neo4j";
        String password = "ggeeRNSFsyayFSp86dQqNpZ4wUt7vroXY_lZyMiH-OU";

        List<ScientificArticle> scientificArticles = new ArrayList<>();

        try (var app = new Neo4jManager(uri, username, password, Config.defaultConfig())){

            List<Record> allArticlesRecord = app.findAllArticles();

            double i = 0;
            double j = allArticlesRecord.size();
            for (Record record : allArticlesRecord){
                double perc = ((i/j)*100);
                System.out.println(Math.floor(perc)+"%");
                ScientificArticle scientificArticle = new ScientificArticle();
                 scientificArticle.populateArticle(app, record);
                 scientificArticles.add(scientificArticle);
                 i++;
            }
            System.out.println(scientificArticles.get(0));


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
