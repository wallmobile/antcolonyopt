package de.jungblut.antcolony;

import java.util.concurrent.Callable;

import de.jungblut.antcolony.AntColonyOptimization.WalkedWay;

public final class Agent implements Callable<WalkedWay> {

	private final AntColonyOptimization instance;
	private double distanceWalked = 0.0d;
	private final int start;
	private final boolean[] visited;
	private final int[] way;
	private int toVisit;

	public Agent(AntColonyOptimization instance, int start) {
		super();
		this.instance = instance;
		this.visited = new boolean[instance.matrix.length];
		visited[start] = true;
		toVisit = visited.length - 1;
		this.start = start;
		this.way = new int[visited.length];
	}

	private final int getNextProbableNode(int y) {
		int node = -1;
		if (toVisit > 0) {
			double probability = -1.0d;
			for (int column = 0; column < visited.length; column++) {
				if (y != column) {
					final double p = calculateProbability(column, y);
					// System.out.println("Probability for " + y + " to "+
					// column + " is " + p);
					if (p > probability) {
						if (!visited[column]) {
							node = column;
							probability = p;
						}
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
	private final double calculateProbability(int row, int column) {
		final double p = Math.pow(instance.readPheromone(column, row),
				AntColonyOptimization.ALPHA)
				* Math.pow(instance.invertedMatrix[column][row],
						AntColonyOptimization.BETA);
		double sum = 0.0d;
		// TODO we don't have to do this all the time, this is a constant for
		// the column we are currently in
		for (int i = 0; i < visited.length; i++) {
			sum += Math.pow(instance.readPheromone(column, i),
					AntColonyOptimization.ALPHA)
					* Math.pow(instance.invertedMatrix[column][i],
							AntColonyOptimization.BETA);
		}

		return p / sum;
	}

	@Override
	public final WalkedWay call() throws Exception {

		int lastNode = start;
		int next = start;
		int i = 0;
		while ((next = getNextProbableNode(lastNode)) != -1) {
			way[i] = lastNode;
			i++;
			distanceWalked += instance.matrix[lastNode][next];
			instance.adjustPheromone(lastNode, next,
					(AntColonyOptimization.Q / (distanceWalked)));
			visited[next] = true;
			lastNode = next;
			toVisit--;
		}
		distanceWalked += instance.matrix[lastNode][start];
		way[i] = lastNode;

		return new WalkedWay(way, distanceWalked);
	}
}
