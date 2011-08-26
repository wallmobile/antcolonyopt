package de.jungblut.antcolony;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import de.jungblut.antcolony.AntColonyOptimization.Record;

public class SolutionReader {

	/**
	 * @param args
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		final BufferedReader br = new BufferedReader(new FileReader(new File("files/berlin52.tsp")));

		final ArrayList<Record> records = new ArrayList<Record>();

		boolean readAhead = false;
		String line;
		while ((line = br.readLine()) != null) {

			if (line.equals("EOF")) {
				break;
			}

			if (readAhead) {
				String[] split = line.split(" ");
				records.add(new AntColonyOptimization.Record(Double.parseDouble(split[1]), Double
						.parseDouble(split[2])));
			}

			if (line.equals("NODE_COORD_SECTION")) {
				readAhead = true;
			}
		}

		br.close();

		double distance = 0.0d;
		for (int i = 0; i < records.size() - 1; i++) {
			Record r = records.get(i);
			Record h = records.get(i + 1);
			distance += calculateEuclidianDistance(r.x, r.y, h.x, h.y);
		}
		
		Record r = records.get(records.size()-1);
		Record h = records.get(0);
		distance += calculateEuclidianDistance(r.x, r.y, h.x, h.y);
		
		System.out.println("Optimal distance is: " + distance);
	}

	private static final double calculateEuclidianDistance(double x1, double y1, double x2,
			double y2) {
		return (Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}

}
