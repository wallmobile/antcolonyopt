package de.jungblut.antcolony;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.jungblut.antcolony.AntColonyOptimization.Record;

public class SolutionWriter {

	/**
	 * @param args
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {

		int[] arr = new int[] { 27, 26, 25, 46, 13, 12, 51, 10, 50, 11, 24, 3, 5, 4, 14, 23, 47,
				37, 39, 36, 38, 35, 34, 33, 43, 15, 49, 19, 22, 29, 41, 6, 1, 16, 2, 17, 30, 20,
				21, 0, 48, 31, 44, 18, 40, 7, 8, 9, 32, 42, 45};

		final BufferedReader br = new BufferedReader(new FileReader(new File("files/berlin52.tsp")));

		final HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
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
				map.put(Integer.parseInt(split[0])-1, records.size() - 1);
			}

			if (line.equals("NODE_COORD_SECTION")) {
				readAhead = true;
			}
		}

		br.close();

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("files/my.tsp")));

		for (int j = 0; j < arr.length; j++) {
			int i = arr[j];
			writer.write(records.get(i).x + " " + records.get(i).y + "\n");
		}
		writer.flush();
		writer.close();
	}

}
