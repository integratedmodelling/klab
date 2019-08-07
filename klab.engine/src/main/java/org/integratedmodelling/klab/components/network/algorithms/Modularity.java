package org.integratedmodelling.klab.components.network.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * Find a partitioning of a graph trying to maximize the modularity. The
 * algorithm is a simple greedy algorithm.
 * 
 * @author Gilles Vuidel
 */
public class Modularity<N, E> {

	private final Graph<N, E> graph;
	private final EdgeWeighter<E> weighter;

	private TreeMap<Integer, Double> modularities;
	private TreeMapList<Double, Set<Cluster>> partitions;

	private HashMap<N, Double> nodeSumEdges;
	private double m;

	private Set<Integer> keepList;

	/**
	 * Creates a new Modularity class for the graph
	 * 
	 * @param graph    the graph
	 * @param weighter the edge weighter
	 */
	public Modularity(Graph<N, E> graph, EdgeWeighter<E> weighter) {
		this.graph = graph;
		this.weighter = weighter;

		m = 0;
		for (E e : (Collection<E>) graph.getEdges()) {
			m += weighter.getWeight(e);
		}

		nodeSumEdges = new HashMap<>();
		for (N n : graph.getVertices()) {
			double sum = 0;
			for (E e : graph.getIncidentEdges(n)) {
				sum += weighter.getWeight(e);
			}
			nodeSumEdges.put(n, sum);
		}
	}

	private boolean isClusteringKept(int size) {
		if (keepList == null) {
			return true;
		}
		return keepList.contains(size);
	}

	public void setKeepList(Set<Integer> list) {
		this.keepList = list;
	}

	/**
	 * Calculates the modularity for this partitionning
	 * 
	 * @param partition the partition of the graph
	 * @return the modularity index between -1/2 : 1
	 */
	public double getModularity(Set<Cluster> partition) {
		double mod = 0;
		for (Cluster c : partition) {
			mod += c.getPartModularity();
		}
		return mod;
	}

	/**
	 * Return all the modularity values calculated for each partitionning.
	 * {@link #partitions() } must be called before
	 * 
	 * @return the number of cluster (partition size) vs modularity
	 */
	public TreeMap<Integer, Double> getModularities() {
		return modularities;
	}

	/**
	 * 
	 * {@link #partitions() } must be called before
	 * 
	 * @return the size of the partitions (number of cluster) saved
	 */
	public TreeSet<Integer> getPartitionSize() {
		TreeSet<Integer> sizes = new TreeSet<>();
		for (double v : partitions.keySet()) {
			for (Set<Cluster> part : partitions.get(v)) {
				sizes.add(part.size());
			}
		}
		return sizes;
	}

	/**
	 * Returns the partition which has the max modularity. {@link #partitions() }
	 * must be called before
	 * 
	 * @return the best partition
	 */
	public Set<Cluster> getBestPartition() {
		Set<Cluster> bestPart = null;
		int minSize = Integer.MAX_VALUE;
		for (Set<Cluster> part : partitions.lastEntry().getValue()) {
			if (part.size() < minSize) {
				minSize = part.size();
				bestPart = part;
			}
		}
		return bestPart;
	}

	/**
	 * {@link #partitions() } must be called before
	 * 
	 * @param nbCluster the size of the partition
	 * @return the partition
	 * @throws IllegalArgumentException if no partition with nbCluster exists
	 */
	public Set<Cluster> getPartition(int nbCluster) {
		for (List<Set<Cluster>> parts : partitions.values()) {
			for (Set<Cluster> part : parts) {
				if (part.size() == nbCluster) {
					return part;
				}
			}
		}
		throw new IllegalArgumentException("No partition with " + nbCluster + " clusters.");
	}

	/**
	 * Retrieve the partition with nbCluster and try to optimize the modularity by
	 * exchanging nodes between clusters. {@link #partitions() } must be called
	 * before The returned partition may have a lesser size.
	 * 
	 * @param nbCluster the size of the partition
	 * @return the optimized partition
	 */
	public Set<Cluster> getOptimPartition(int nbCluster) {
		Set<Cluster> partition = getPartition(nbCluster);
		Set<Cluster> part = new HashSet<>();
		for (Cluster c : partition) {
			part.add(new Cluster(c.getId(), c.getNodes()));
		}
		optimPartition(part);
		return part;
	}

	/**
	 * Try to optimize the modularity of the partition by exchanging nodes between
	 * clusters.
	 * 
	 * @param partition the partition to optimize
	 */
	public void optimPartition(Set<Cluster> partition) {
		boolean increase = true;
		while (increase) {
			increase = false;
			HashMapList<E, Cluster> interEdges = new HashMapList<>();
			for (Cluster c : partition) {
				for (E e : c.interEdges) {
					interEdges.putValue(e, c);
				}
			}

			double gain = 0;
			for (E e : new ArrayList<>(interEdges.keySet())) {
				Cluster c1 = interEdges.get(e).get(0);
				Cluster c2 = interEdges.get(e).get(1);
				double m1 = c1.getPartModularity();
				double m2 = c2.getPartModularity();

				double d1 = c1.getDiffMod(e, c2);
				double d2 = c2.getDiffMod(e, c1);

				double newM = Double.NaN;
				if (d1 >= d2 && d1 > 0) {
					c1.includeEdge(e, c2);
					if (c2.getNodes().isEmpty()) {
						partition.remove(c2);
					}
					newM = c1.getPartModularity() + c2.getPartModularity();
					if (newM < (m1 + m2) || Math.abs((newM - (m1 + m2 + d1)) / newM) > 1e-4) {
						Logger.getLogger(Modularity.class.getName())
								.warning("Modularity decrease : " + (newM - (m1 + m2)));
					}
				} else if (d2 >= d1 && d2 > 0) {
					c2.includeEdge(e, c1);
					if (c1.getNodes().isEmpty()) {
						partition.remove(c1);
					}
					newM = c1.getPartModularity() + c2.getPartModularity();
					if (newM < (m1 + m2) || Math.abs((newM - (m1 + m2 + d2)) / newM) > 1e-4) {
						Logger.getLogger(Modularity.class.getName())
								.warning("Modularity decrease : " + (newM - (m1 + m2)));
					}
				}
				if (!Double.isNaN(newM)) {
					gain += newM - (m1 + m2);
					increase = true;
				}
			}
			Logger.getLogger(Modularity.class.getName()).info("Modularity gain : " + gain);
		}

	}

	private final class Delta implements Comparable<Delta> {
		private Cluster c1, c2;
		private double delta;

		public Delta(Cluster c1, Cluster c2, double delta) {
			this.c1 = c1;
			this.c2 = c2;
			this.delta = delta;
		}

		@Override
		public int compareTo(Delta d) {
			if (delta == d.delta) {
				return Integer.compare(c1.getId(), d.c1.getId());
			} else {
				return delta > d.delta ? -1 : 1;
			}
		}

	}

	/**
	 * Calculates the partitionning with the greedy algorithm
	 */
	public void partitions() {
		// ProgressBar progressBar = Config.getProgressBar("Clustering",
		// graph.getNodes().size());
		// progressBar.setIndeterminate(true);
		partitions = new TreeMapList<>();
		modularities = new TreeMap<>();
		Set<Cluster> bestPart = null;
		double bestMod = Double.NEGATIVE_INFINITY;
		Set<Cluster> clusters = new HashSet<>();
		double mod = 0;
		int i = 1;
		HashMap<N, Cluster> initClusterMap = new HashMap<>();
		for (N n : graph.getVertices()) {
			Cluster c = new Cluster(i++, n);
			clusters.add(c);
			initClusterMap.put(n, c);
			mod += c.getPartModularity();
		}
		partitions.putValue(mod, clusters);
		modularities.put(clusters.size(), mod);
		PriorityQueue<Delta> queue = new PriorityQueue<>();
		for (Cluster c1 : clusters) {
			N n1 = c1.nodes.iterator().next();

			for (E e : graph.getIncidentEdges(n1)) {
				Cluster c2 = initClusterMap.get(getOtherNode(e, n1));
				if (c1.getId() > c2.getId()) {
					continue;
				}
				double d = c1.getDiffMod(c2, weighter.getWeight(e));
				queue.add(new Delta(c1, c2, d));
				c1.addNeighbour(c2, d);
				c2.addNeighbour(c1, d);
			}

		}
		initClusterMap = null;

		// progressBar.setIndeterminate(false);
		while (!queue.isEmpty()) {

			Delta delta = queue.poll();
			if (!clusters.contains(delta.c1) || !clusters.contains(delta.c2)) {
				continue;
			}

			Cluster merge = delta.c1.merge(i++, delta.c2, delta.delta);
			clusters.remove(delta.c1);
			clusters.remove(delta.c2);
			clusters.add(merge);
			mod += delta.delta;
			modularities.put(clusters.size(), mod);
			if (mod >= bestMod) {
				bestMod = mod;
				bestPart = new HashSet<>(clusters);
			}
			if (Double.isNaN(mod)) {
				throw new RuntimeException("NaN modularity");
			}
			if (i % 100 == 0) {
				int n = 0;
				for (Iterator<Delta> it = queue.iterator(); it.hasNext();) {
					Delta d = it.next();
					if (!clusters.contains(d.c1) || !clusters.contains(d.c2)) {
						it.remove();
						n++;
					}
				}
				Logger.getLogger(Modularity.class.getName())
						.info("Remove " + n + " elems from the queue. Rest : " + queue.size());
			}

			if (isClusteringKept(clusters.size())) {
				HashSet<Cluster> clusts = new HashSet<>();
				for (Cluster c : clusters) {
					clusts.add(new Cluster(c));
				}
				partitions.putValue(mod, clusts);
			}

			// update delta
			Set<Cluster> con = new HashSet<>(delta.c1.getNeighbours());
			con.addAll(delta.c2.getNeighbours());
			con.remove(delta.c1);
			con.remove(delta.c2);
			for (Cluster c : con) {
				Double d1 = c.neighbours.remove(delta.c1);
				Double d2 = c.neighbours.remove(delta.c2);
				double sumComEdges = 0;
				if (d1 != null) {
					sumComEdges += c.getSumCommonEdges(delta.c1, d1);
				}
				if (d2 != null) {
					sumComEdges += c.getSumCommonEdges(delta.c2, d2);
				}
				double d = merge.getDiffMod(c, sumComEdges);
				c.addNeighbour(merge, d);
				merge.addNeighbour(c, d);
				queue.add(new Delta(merge, c, d));
			}

			// progressBar.incProgress(1);
		}

		HashSet<Cluster> clusts = new HashSet<>();
		for (Cluster c : bestPart) {
			clusts.add(new Cluster(c));
		}
		partitions.putValue(bestMod, clusts);
		// progressBar.close();
	}

	private N getOtherNode(E e, N n1) {
		Pair<N> endpoints = graph.getEndpoints(e);
		return endpoints.getFirst().equals(n1) ? endpoints.getSecond() : endpoints.getFirst();
	}

	/**
	 * Represents a part of a graph, a cluster of nodes
	 */
	public final class Cluster {
		private final int id;
		private final Set<N> nodes;
		private Set<E> interEdges;
		private double sumIntraEdges = -1;
		private double sumEdges = -1;
		private Map<Cluster, Double> neighbours;

		public Cluster(int id, N n) {
			this.id = id;
			this.nodes = new HashSet<>(1);
			nodes.add(n);
			init();
		}

		public Cluster(int id, Set<N> nodes) {
			this.id = id;
			this.nodes = new HashSet<>(nodes);
			init();
		}

		public Cluster(Cluster c) {
			this.id = c.id;
			this.nodes = c.nodes;
			this.interEdges = null;
			this.sumEdges = c.sumEdges;
			this.sumIntraEdges = c.sumIntraEdges;
		}

		public Cluster(int id, Set<N> nodes, Set<E> interEdges, double sumIntraEdges, double sumEdges) {
			this.id = id;
			this.nodes = nodes;
			this.interEdges = interEdges;
			this.sumEdges = sumEdges;
			this.sumIntraEdges = sumIntraEdges;
			this.neighbours = new HashMap<>();
		}

		public double getPartModularity() {
			return getPartModularity(sumIntraEdges, sumEdges);
		}

		private double getPartModularity(double intra, double sum) {
			return intra / m - Math.pow(sum / (2 * m), 2);
		}

		public double getDiffMod(E edge, Cluster c) {
			if (!interEdges.contains(edge) || !c.interEdges.contains(edge)) {
				return Double.NaN;
			}

			N n = nodes.contains(graph.getSource(edge)) ? graph.getDest(edge) : graph.getSource(edge);
			double intra1 = sumIntraEdges, intra2 = c.sumIntraEdges;
			double sum1 = sumEdges, sum2 = c.sumEdges;

			// test un majorant
			double sum = nodeSumEdges.get(n);
			sum1 += sum;
			sum2 -= sum;
			intra1 += sum;

			double d = (getPartModularity(intra1, sum1) + c.getPartModularity(intra2, sum2))
					- (this.getPartModularity() + c.getPartModularity());
			// si le majorant <= 0 alors pas la peine de continuer
			if (d <= 0) {
				return 0;
			}

			intra1 = sumIntraEdges;

			for (E e : graph.getIncidentEdges(n)) {
				N otherNode = getOtherNode(e, n);
				if (nodes.contains(otherNode)) {
					intra1 += weighter.getWeight(e);
				} else if (c.nodes.contains(otherNode)) {
					intra2 -= weighter.getWeight(e);
				}
			}

			return (getPartModularity(intra1, sum1) + c.getPartModularity(intra2, sum2))
					- (this.getPartModularity() + c.getPartModularity());
		}

		public N includeEdge(E edge, Cluster c) {
			if (!interEdges.contains(edge) || !c.interEdges.contains(edge)) {
				throw new IllegalArgumentException();
			}
			N n = nodes.contains(graph.getSource(edge)) ? graph.getDest(edge) : graph.getSource(edge);
			c.nodes.remove(n);
			nodes.add(n);
			init();
			c.init();
			return n;
		}

		public Cluster merge(int newId, Cluster c, double delta) {
			Set<N> nodeSet = new HashSet<>(nodes);
			nodeSet.addAll(c.nodes);
			HashSet<E> edges = new HashSet<>(interEdges);
			for (E e : c.interEdges) {
				if (!edges.remove(e)) {
					edges.add(e);
				}
			}
			Cluster merge = new Cluster(newId, nodeSet, edges, getSumCommonEdges(c) + sumIntraEdges + c.sumIntraEdges,
					sumEdges + c.sumEdges);
			return merge;
		}

		public double getDiffMod(Cluster c) {
			return getDiffMod(c, getSumCommonEdges(c));
		}

		public double getDiffMod(Cluster c, double sumCom) {

			double intra = this.sumIntraEdges + c.sumIntraEdges + sumCom;

			double mod = intra / m - Math.pow((this.sumEdges + c.sumEdges) / (2 * m), 2);

			return mod - (this.getPartModularity() + c.getPartModularity());
		}

		public double getSumCommonEdges(Cluster c, double delta) {
			return (delta + (this.getPartModularity() + c.getPartModularity())
					+ Math.pow((this.sumEdges + c.sumEdges) / (2 * m), 2)) * m - (this.sumIntraEdges + c.sumIntraEdges);
		}

		private double getSumCommonEdges(Cluster c) {
			if (this.interEdges.size() > c.interEdges.size()) {
				return c.getSumCommonEdges(this);
			}
			double sum = 0;
			boolean common = false;
			for (E e : interEdges) {
				if (c.interEdges.contains(e)) {
					sum += weighter.getWeight(e);
					common = true;
				}
			}
			return common ? sum : Double.NaN;
		}

		public Set<N> getNodes() {
			return nodes;
		}

		public int getId() {
			return id;
		}

		void init() {
			sumIntraEdges = 0;
			sumEdges = 0;
			interEdges = new HashSet<>();
			for (N n : nodes) {
				Collection<E> edges = graph.getIncidentEdges(n);
				for (E e : edges) {
					final double imp = weighter.getWeight(e);
					if (graph.getSource(e).equals(n) && nodes.contains(graph.getDest(e))) {
						sumIntraEdges += imp;
					}
					if (!nodes.contains(graph.getSource(e)) || !nodes.contains(graph.getDest(e))) {
						interEdges.add(e);
					}
					sumEdges += imp;
				}
			}

			neighbours = new HashMap<>();
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 37 * hash + this.id;
			return hash;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Modularity.Cluster) {
				return this.id == ((Cluster) obj).id;
			}
			return false;
		}

		private void addNeighbour(Cluster c, double delta) {
			neighbours.put(c, delta);
		}

		public Set<Cluster> getNeighbours() {
			return neighbours.keySet();
		}

	}

	public static final class OneWeighter<E> implements EdgeWeighter<E> {
		@Override
		public double getWeight(E e) {
			return 1;
		}

		@Override
		public double getToGraphWeight(double dist) {
			return 0;
		}
	}
}
