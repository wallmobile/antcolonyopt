package de.jungblut.antcolony;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/*
 * TODO make this more robust: 
 * - safe up to 10 best results for each testing
 * - make 3 tests with a parameter, avg them and safe best of it
 * - step every 0.5 is enough
 */
public class ParameterTesting {

	static PriorityQueue<TestRecord> alphaQueue;

	public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException {

		alphatesting();
		// betatesting();
		// initPheroTest();
	}

	public static void alphatesting() throws IOException, InterruptedException,
			ExecutionException {

		PriorityQueue<TestRecord> queue = new PriorityQueue<ParameterTesting.TestRecord>(
				3, TestRecord.class);

		for (double i = -10.0d; i <= 10.0d; i += 0.5d) {
			double[] avg = new double[5];
			for (int times = 0; times < 5; times++) {
				System.out.println("Testing: " + i);
				AntColonyOptimization opt = new AntColonyOptimization();
				AntColonyOptimization.ALPHA = i;
				final double result = opt.start();
				avg[times] = result;
			}

			double best = Double.MAX_VALUE;
			double sum = 0.0;
			for (double d : avg) {
				sum += d;
				if (best > d)
					best = d;
			}

			double average = sum / avg.length;
			queue.insertWithOverflow(new TestRecord(i, average, best));

			System.out.println("Best alpha found until now was: "
					+ queue.top().toString());
		}

		System.out.println("Best alpha found was: " + queue.top().toString());
		alphaQueue = queue;
	}

	public static void betatesting() throws IOException, InterruptedException,
			ExecutionException {

		double bestBeta = 0d;
		double bestResult = Double.MAX_VALUE;
		for (double i = 0d; i <= 10.0d; i += 0.5d) {
			System.out.println("Testing: " + i);
			AntColonyOptimization opt = new AntColonyOptimization();
			AntColonyOptimization.BETA = i;
			double result = opt.start();
			if (result < bestResult) {
				bestBeta = i;
				bestResult = result;
			}
			System.out.println("Best beta found was: " + bestBeta);
		}

		System.out.println("Best beta found was: " + bestBeta);
	}

	private static void initPheroTest() throws IOException,
			InterruptedException, ExecutionException {
		double bestAlpha = -10.0d;
		double bestResult = Double.MAX_VALUE;
		for (double i = 0d; i <= 10.0d; i += 0.1d) {
			System.out.println("Testing: " + i);
			AntColonyOptimization opt = new AntColonyOptimization();
			AntColonyOptimization.PHEROMONE_PERSISTENCE = i;
			double result = opt.start();
			if (result < bestResult) {
				bestAlpha = i;
				bestResult = result;
			}
			System.out.println("Best phero found was: " + bestAlpha);
		}

		System.out.println("Best phero found was: " + bestAlpha);
	}

	static class TestRecord implements Comparable<TestRecord> {
		double parameter;
		double avg;
		double best;

		public TestRecord(double parameter, double avg, double best) {
			super();
			this.parameter = parameter;
			this.avg = avg;
			this.best = best;
		}

		@Override
		public int compareTo(TestRecord o) {
			return Double.compare(best, best);
		}

		@Override
		public String toString() {
			return "[parameter=" + parameter + ", avg=" + avg + ", best="
					+ best + "]";
		}

	}

}
