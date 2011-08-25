package de.jungblut.antcolony;

import java.util.concurrent.Callable;

public final class Agent implements Callable<Double> {

	private final AntColonyOptimization instance;
	private double distanceWalked = 0.0d;
	private final int start;
	private final boolean[] visited;
	private int toVisit;

	public Agent(AntColonyOptimization instance, int start) {
		super();
		this.instance = instance;
		this.visited = new boolean[instance.matrix.length];
		visited[start] = true;
		toVisit = visited.length - 1;
		this.start = start;
	}

	private final int getNextProbableNode(int x, int y) {
		int node = -1;
		if (toVisit > 0) {
			double probability = -1.0d;
			for (int column = 0; column < visited.length; column++) {
				final double p = calculateProbability(x, y);
				if (p > probability) {
					if (!visited[column]) {
						node = column;
						probability = p;
					}
				}
			}
		}

		return node;
	}

	/*
	 * (pheromones ^ ALPHA) * ((1/length) ^ BETA) divided by the sum of all
	 * rows.
	 */
	private final double calculateProbability(int column, int row) {
		final double p = Math.pow(instance.pheromones[column][row],
				AntColonyOptimization.ALPHA)
				* Math.pow(instance.invertedMatrix[column][row],
						AntColonyOptimization.BETA);
		double sum = 0.0d;

		for (int i = 0; i < visited.length; i++) {
			sum += Math.pow(instance.pheromones[column][i],
					AntColonyOptimization.ALPHA)
					* Math.pow(instance.invertedMatrix[column][i],
							AntColonyOptimization.BETA);
		}

		return p / sum;
	}

	@Override
	public final Double call() throws Exception {

		int lastNode = start;
		int next = start;
		while ((next = getNextProbableNode(lastNode, next)) != -1) {
			distanceWalked += instance.matrix[lastNode][next];
			visited[next] = true;
			lastNode = next;
			toVisit--;
			// TODO this is experimental.., Decay is missing!
			instance.adjustPheromone(
					lastNode,
					next,
					(instance.pheromones[lastNode][next] + (AntColonyOptimization.Q / distanceWalked)));
		}
		return distanceWalked;
	}
}
