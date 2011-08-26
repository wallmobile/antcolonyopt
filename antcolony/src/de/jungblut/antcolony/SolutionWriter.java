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

		int[] arr = new int[] { 1, 49, 32, 45, 19, 41, 8, 9, 10, 43, 33, 51,
				11, 52, 14, 13, 47, 26, 27, 28, 12, 25, 4, 6, 15, 5, 24, 48,
				38, 37, 40, 39, 36, 35, 34, 44, 46, 16, 29, 50, 20, 23, 30, 2,
				7, 42, 21, 17, 3, 18, 31, 22 };

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
				"files/heidelberg.tsp")));

		for (int j = 0; j < arr.length; j++) {
			int i = arr[j] - 1;
			writer.write(records.get(i).x + " " + records.get(i).y + "\n");
		}
		writer.flush();
		writer.close();
	}
}
