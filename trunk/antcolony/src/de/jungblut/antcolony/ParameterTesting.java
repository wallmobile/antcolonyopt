package de.jungblut.antcolony;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ParameterTesting {

	public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException {

//		boolean finished = false;
//		while (!finished) {
			alphatesting(); // = -8.6
//		}

	}

	public static void alphatesting() throws IOException, InterruptedException,
			ExecutionException {
		
		double bestAlpha = -10.0d;
		double bestResult = Double.MAX_VALUE;
		for (double i = -10.0d; i <= 10.0d; i += 0.1d) {
			AntColonyOptimization opt = new AntColonyOptimization();
			AntColonyOptimization.ALPHA = i;
			double result = opt.start();
			if (result < bestResult) {
				bestAlpha = i;
				bestResult = result;
			}
		}

			
		System.out.println("Best alpha found was: " + bestAlpha);
	}
}
