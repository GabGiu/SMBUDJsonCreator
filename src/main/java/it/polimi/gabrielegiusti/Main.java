package it.polimi.gabrielegiusti;

import it.polimi.gabrielegiusti.DBManager.Neo4jManager;
import it.polimi.gabrielegiusti.Models.Author;
import it.polimi.gabrielegiusti.Models.ScientificArticle;
import org.neo4j.driver.Config;

public class Main {

    public static void main(String[] args) {

        String uri = "neo4j+s://b535a102.databases.neo4j.io";
        String username = "neo4j";
        String password = "ggeeRNSFsyayFSp86dQqNpZ4wUt7vroXY_lZyMiH-OU";

        ScientificArticle scientificArticle;
        Author author;

        try (var app = new Neo4jManager(uri, username, password, Config.defaultConfig())){


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
