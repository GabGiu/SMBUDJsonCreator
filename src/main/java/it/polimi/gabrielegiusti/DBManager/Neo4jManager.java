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


    public void createFriendship(final String person1Name, final String person2Name) {
        // To learn more about the Cypher syntax, see https://neo4j.com/docs/cypher-manual/current/
        // The Reference Card is also a good resource for keywords https://neo4j.com/docs/cypher-refcard/current/
        var query = new Query(
                """
                   CREATE (p1:Person { name: $person1_name })
                   CREATE (p2:Person { name: $person2_name, surname: "rossi"})
                   CREATE (p1)-[:KNOWS]->(p2)
                   RETURN p1, p2
                   """,
                Map.of("person1_name", person1Name, "person2_name", person2Name));

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            // Write transactions allow the driver to handle retries and transient errors
            var record = session.executeWrite(tx -> tx.run(query).single());
            System.out.printf(
                    "Created friendship between: %s, %s%n",
                    record.get("p1").get("name").asString(),
                    record.get("p2").get("name").asString());
            // You should capture any errors along with the query and data for traceability
        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public void findPerson(final String personName) {
        var query = new Query(
                """
                   MATCH (p:Person)
                   WHERE p.name = $person_name
                   RETURN p.name AS name
                   """,
                Map.of("person_name", personName));

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))) {
            var records = session.executeRead(tx -> tx.run(query).list());
            for (Record record : records){
                System.out.printf("Found person: %s%n", record.get("name").asString());
            }
            // You should capture any errors along with the query and data for traceability
        } catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> findAllAuthors(){
        Query query = new Query(
                """
                        MATCH (a:Author)
                        RETURN a AS Author
                        """
        );

        try (var session = driver.session(SessionConfig.forDatabase("neo4j"))){
            var records = session.executeRead(tx -> tx.run(query).list());
            for (Record record : records){
                System.out.printf("Found author: %s%n", record.get("Author").get("name").asString());
            }
            return records;
        }catch (Neo4jException ex) {
            LOGGER.log(Level.SEVERE, query + " raised an exception", ex);
            throw ex;
        }
    }

    public List<Record> findAuthorByArticle(String articleID){
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
                        WHERE a.authorID = $authorID
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

    public  Record findLocationByAffiliationID(String affiliationID){
        Query query = new Query(
                """
                        MATCH (a:Affiliation)-[:PLACE]->(l:Location)
                        WHERE sa.articleID = $affiliationID
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



}

