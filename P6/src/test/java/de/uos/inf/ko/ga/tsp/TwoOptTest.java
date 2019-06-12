package de.uos.inf.ko.ga.tsp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.uos.inf.ko.ga.graph.Graph;
import de.uos.inf.ko.ga.graph.reader.GraphReader;

public class TwoOptTest {

	private static final List<String> GRAPHS = Arrays.asList(
			"tsp_01.gra",
			"tsp_02.gra",
			"tsp_03.gra"
	);

	@Test
	public void testRunTwoOptOnTestGraphs() {
		for (final String filename : GRAPHS) {
			final File fileGraph = new File("src/test/resources/" + filename);

			try {
				final Graph graph = GraphReader.readUndirectedGraph(fileGraph);
				assertNotNull(graph);
				
				// TODO: generate 100 random TSP tours and call TwoOpt.iterativeTwoOpt() on them

				//Ich nutze meine Funktion um automatisch 100 random touren zu bauen
				Tour[] randomTours = Tour.randomTour(graph);

				for(int i = 0; i < randomTours.length ; i++)
				{
					//für alle 100 berechne ich die kürzeste tour
					randomTours[i] = TwoOpt.iterativeTwoOpt(randomTours[i],false);
				}

				// Es werden die kürzeste, die längste und die durschnittliche Distanz gesucht und ausgegeben.

				int best = Integer.MAX_VALUE;
				int worst = 0;
				double average = 0;

				for(int i = 0; i < randomTours.length -1; i++)
				{
					if(randomTours[i].getCosts() > worst )
					{
						worst = i;
					}

					if(randomTours[i].getCosts() < best )
					{
						best = i;
					}

					average = average + randomTours[i].getCosts();
				}

				System.out.println("Die beste Tour ist: " + randomTours[best].toString() + "mit Kosten: " + randomTours[best].getCosts());
				System.out.println("Die schlechteste Tour ist: " + randomTours[worst].toString() + "mit Kosten: " + randomTours[worst].getCosts());
				System.out.println("Die durchschnittliche Tour hat eine Länge von: " + average/100);
				System.out.println();

				
				// TODO: output minimum, maximum, and average tour length

			} catch (Exception ex) {
				fail("caught an exception while computing TSP tours: " + ex);
			}
		}
	}
}
