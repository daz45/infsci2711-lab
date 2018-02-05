import java.io.File;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4jExample {

	public static void main(String[] args) {
		Neo4jExample abc = new Neo4jExample();
		abc.cleanDB();
		abc.addNodeRel();
		abc.update("alia", "reading");
	}

	public enum NodeLabelSet implements Label {
		person, student, player, officer, mother;
	}

	public enum RelationshipLabelSet implements RelationshipType {
		friends_of, like;
	}

	GraphDatabaseService db;

	public Neo4jExample() {
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		db = dbFactory.newEmbeddedDatabase(new File(
				"C:\\Users\\DAZ45\\AppData\\Roaming\\Neo4j Desktop\\Application\\neo4jDatabases\\database-1595c5e2-6b56-4d58-972e-72f3cfb09153\\installation-3.3.2\\data\\databases\\graph.db"));

	}

	public void addNodeRel() {
		try (Transaction tx = db.beginTx()) {
			Node peter = db.createNode(NodeLabelSet.person, NodeLabelSet.student);
			peter.setProperty("gender", "male");
			peter.setProperty("name", "Peter");
			peter.setProperty("age", 25);

			Node kelly = db.createNode(NodeLabelSet.person, NodeLabelSet.player);
			kelly.setProperty("gender", "female");
			kelly.setProperty("name", "Kelly");
			kelly.setProperty("age", 20);

			Node kary = db.createNode(NodeLabelSet.person, NodeLabelSet.student);
			kary.setProperty("gender", "female");
			kary.setProperty("name", "Kary");
			kary.setProperty("age", 25);

			Node alia = db.createNode(NodeLabelSet.person, NodeLabelSet.officer);
			alia.setProperty("gender", "female");
			alia.setProperty("name", "alia");
			alia.setProperty("age", 23);

			Node alia2 = db.createNode(NodeLabelSet.person, NodeLabelSet.officer);
			alia2.setProperty("gender", "female");
			alia2.setProperty("name", "alia2222");
			alia2.setProperty("age", 23);

			Relationship relationship = kary.createRelationshipTo(peter, RelationshipLabelSet.friends_of);
			relationship.setProperty("years", 2);

			tx.success();
		}

		showAll();
	}

	public void update(String people, String hobby) {
		showAll();
		db.execute("match(n) where n.name=\"" + people + "\" set n.hobby=\"" + hobby + "\"");
		showAll();
	}

	public void cleanDB() {
		Result execResult = db.execute("match (n) detach delete n");
		System.out.println(execResult.resultAsString());
		showAll();
	}

	public void showAll() {
		Result execResult = db.execute("MATCH (n) RETURN n");
		String results = execResult.resultAsString();
		System.out.println(results);
	}

}
