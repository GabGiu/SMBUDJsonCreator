package it.polimi.gabrielegiusti.DBManager;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.Neo4jException;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Neo4jManager implements AutoCloseable{

    private static final Logger LOGGER = Logger.getLogger(Neo4jManager.class.getName());
    private final Driver driver;

    public Neo4jManager(String uri, String user, String password, Config config){
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password), config);
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public List<Record> findAllAuthors(){
        Query query = new Query(
                """
                        MATCH (a:Author)-[:WRITING]->(sa:ScientificArticle)
                        RETURN a AS Author, collect(sa.articleID) AS articleID
                        """
        );

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        }catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> findAllAffiliations(){
        Query query = new Query(
                """
                        MATCH (a1:Affiliation)<-[:RESEARCH]-(a:Author)
                        RETURN a1 AS Affiliation, a.authorOrcid AS authorID
                        """
        );

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        }catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> findAllLocations(){
        Query query = new Query(
                """
                        MATCH (l:Location)<-[:PLACE]-(a:Affiliation)
                        RETURN l AS Location, a.affiliationID AS affiliationID
                        """
        );

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        }catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> findAuthorByArticleID(int articleID){
        Query query = new Query(
                """
                        MATCH (a:Author)-[:WRITING]->(sa:ScientificArticle)
                        WHERE sa.articleID = $articleID
                        RETURN a AS Author
                        """,
                Map.of("articleID", articleID)
        );

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        }catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> findAllArticles(){
        Query query = new Query(
                """
                        MATCH (sa:ScientificArticle)
                        RETURN sa AS Article
                        LIMIT 5900
                        """
        );

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        }catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public Record findAffiliationByAuthorID(String authorID){
        Query query = new Query(
                """
                        MATCH (a:Author)-[:RESEARCH]->(a1:Affiliation)
                        WHERE a.authorOrcid = $authorID
                        RETURN a1 AS Affiliation
                        """,
                Map.of("authorID", authorID)
        );

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).single());
        }catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public  Record findLocationByAffiliationID(int affiliationID){
        Query query = new Query(
                """
                        MATCH (a:Affiliation)-[:PLACE]->(l:Location)
                        WHERE a.affiliationID = $affiliationID
                        RETURN l AS Location
                        """,
                Map.of("affiliationID", affiliationID)
        );

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).single());
        }catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> getDataForScientificArticleModel(){
        Query query = new Query(
                """
                        MATCH (sa:ScientificArticle)<-[:WRITING]-(a1:Author)-[:RESEARCH]->(a:Affiliation)-[:PLACE]->(l:Location)
                        RETURN sa AS Article, collect(a1) AS Authors, a AS Affiliation, l AS Location
                        """
        );

        try(var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> getChunksOfAuthors(int skip){
        Query query = new Query(
                """
                        MATCH (a:Author)-[:WRITING]->(sa:ScientificArticle)
                        RETURN a AS Author, collect(sa.articleID) AS articleID
                        SKIP $skip
                        LIMIT 1000
                        """,
                Map.of("skip", skip)
        );

        try(var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> getChunksOfAffiliations(int skip){
        Query query = new Query(
                """
                        MATCH (a1:Affiliation)<-[:RESEARCH]-(a:Author)
                        RETURN a1 AS Affiliation, a.authorOrcid AS authorID
                        SKIP $skip
                        LIMIT 1000
                        """,
                Map.of("skip", skip)
        );

        try(var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> getChunksOfLocations(int skip){
        Query query = new Query(
                """
                        MATCH (l:Location)<-[:PLACE]-(a:Affiliation)
                        RETURN l AS Location, a.affiliationID AS affiliationID
                        SKIP $skip
                        LIMIT 1000
                        """,
                Map.of("skip", skip)
        );

        try(var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).list());
        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public int getNumberOfNodes(String nodeType){
        Query query = new Query(
                "MATCH (s:" + nodeType + ")" +
                        "RETURN count(s) AS count "
        );

        try(var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            return session.executeRead(tx -> tx.run(query).single()).get("count").asInt();
        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

}

