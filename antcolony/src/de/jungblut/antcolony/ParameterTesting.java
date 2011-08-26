package de.jungblut.antcolony;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/*
 * TODO make this more robust: 
 * - safe up to 10 best results for each testing
 * - make 3 tests with a parameter, avg them and safe best of it
 */
public class ParameterTesting {

	private static double alpha;
	
	public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException {

		alphatesting();
		betatesting();
	}

	public static void alphatesting() throws IOException, InterruptedException,
			ExecutionException {

		double bestAlpha = -10.0d;
		double bestResult = Double.MAX_VALUE;
		for (double i = -10.0d; i <= 10.0d; i += 0.1d) {
			System.out.println("Testing: " + i);
			AntColonyOptimization opt = new AntColonyOptimization();
			AntColonyOptimization.ALPHA = i;
			double result = opt.start();
			if (result < bestResult) {
				bestAlpha = i;
				bestResult = result;
			}
			System.out.println("Best alpha found was: " + bestAlpha);
		}

		System.out.println("Best alpha found was: " + bestAlpha);
		alpha = bestAlpha;
	}

	public static void betatesting() throws IOException, InterruptedException,
			ExecutionException {

		double bestBeta = 0d;
		double bestResult = Double.MAX_VALUE;
		for (double i = 0d; i <= 10.0d; i += 0.1d) {
			System.out.println("Testing: " + i);
			AntColonyOptimization opt = new AntColonyOptimization();
			AntColonyOptimization.ALPHA = alpha;
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

}
