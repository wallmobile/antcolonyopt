package de.jungblut.antcolony;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import de.jungblut.antcolony.AntColonyOptimization.Record;

public class SolutionWriter {

	/**
	 * @param args
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException,
			IOException {

		int[] arr = new int[] { 0, 21, 30, 17, 2, 16, 20, 41, 6, 1, 29, 22, 19,
				49, 15, 28, 46, 25, 26, 27, 11, 50, 10, 51, 13, 12, 32, 42, 9,
				8, 7, 40, 18, 44, 31, 48, 35, 34, 33, 38, 39, 37, 36, 47, 23,
				4, 14, 5, 3, 24, 45, 43 };

		final BufferedReader br = new BufferedReader(new FileReader(new File(
				"files/berlin52.tsp")));

		final ArrayList<Record> records = new ArrayList<Record>();

		boolean readAhead = false;
		String line;
		while ((line = br.readLine()) != null) {

			if (line.equals("EOF")) {
				break;
			}

			if (readAhead) {
				String[] split = line.split(" ");
				records.add(new AntColonyOptimization.Record(Double
						.parseDouble(split[1]), Double.parseDouble(split[2])));
			}

			if (line.equals("NODE_COORD_SECTION")) {
				readAhead = true;
			}
		}

		br.close();

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				"files/my.tsp")));

		for (int j = 0; j < arr.length; j++) {
			int i = arr[j];
			writer.write(records.get(i).x + " " + records.get(i).y + "\n");
		}
		writer.flush();
		writer.close();
	}

}
