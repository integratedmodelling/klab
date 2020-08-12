package org.integratedmodelling.geoprocessing.algorithms.viewshed;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 2D array of doubles read/written from/to dtm files.
 * Has corresponding weights for display purposes.
 * 
 * @author Alejandro Frias
 * @version July 2014
 */
public class WeightsMap {
	final double DIAG = 1.414213562373095;
	double _maxValue;
	double[][] _source;
	double[][] _map;
	
	/**
	 * Creates random  weight map and rights it to the given file.
	 * 
	 * @param fn Filename of dtm file to store new random map in
	 * @param w Width of new height map
	 * @param h Height of new height map
	 */
	public WeightsMap(String fn, int w, int h) throws IOException {
		_map = new double[h][w];
		fillRandom(_map);
		writeMap(fn);
	}
	
	/**
	 * Creates a weight map from file
	 * @param fn Filename of .dtm height map file
	 */
	public WeightsMap(String fn) throws IOException {
		readMap(fn);
	}
	
	/**
	 * Creates a blank (all values zero) WeightsMap not associated with any .dtm file
	 * 
	 * @param w Width of new weight map
	 * @param h Height of new weight map
	 */
	public WeightsMap(int w, int h) {
		_map = new double[h][w];
		_source = new double[h][w];
		_maxValue = 0.0;
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				_map[j][i] = 0.0;
				_source[j][i] = 0.0;
			}
		}
	}
	
	public void raise(int x, int y) {
		_map[y][x] += 256;
		if (_map[y][x] > 1024) _map[y][x] = 100000;
	}
	
	public void lower(int x, int y) {
		_map[y][x] -= 256;
		if (_map[y][x] < 0) _map[y][x] = 0;
	}
	
	public void high(int x, int y) {
		_map[y][x] = 100000;
	}

	public void low(int x, int y) {
		_map[y][x] = 0;
	}
	
	/**
	 * @return Weighted value at (x, y)
	 */
	public double getWeight(int x, int y) {
		return _map[y][x];
	}
	
	/**
	 * @return Source value at (x, y)
	 */
	public double getSource(int x, int y) {
		return _source[y][x];
	}
	
	/**
	 * 
	 * Sets the source at (x, y) to value
	 */
	public void setSource(int x, int y, double value) {
		if (_maxValue < value) {
			_maxValue = value;
		}
		_source[y][x] = value;
		
	}
	
	public double getDistance(int nodeA, int nodeB) {
		int w = _map[0].length;
		int yA = nodeA / w;
		nodeA = nodeA % w;
		int yB = nodeB / w;
		nodeB = nodeB % w;
		if (Math.abs(nodeA-nodeB) == 1 && yA != yB) {
			return DIAG * (_map[yA][nodeA] + _map[yB][nodeB]); 
		}
		return _map[yA][nodeA] + _map[yB][nodeB];
	}
	
	public int h() {
		return _map == null?0:_map.length;
	}
	
	public int w() {
		return _map == null || _map.length < 1?0:_map[0].length;
	}
	
	public int length() {
		if (_map != null && _map.length > 0) return _map.length * _map[0].length;
		return 0;
	}
	
	public void readMap(String fn) throws IOException {
		FileInputStream fin = new FileInputStream(fn);
		BufferedInputStream bin = new BufferedInputStream(fin);
		DataInputStream in = new DataInputStream(bin);
		int w = in.readInt() + 1;
		int h = in.readInt() + 1;
		in.readFloat();
		in.readFloat();
		in.readFloat();
		in.readFloat();
		_maxValue = Double.MIN_VALUE;
		_source = new double[h][w];
		for (int i = 0; i < _source.length; i++) {
			double[] row = _source[i];
			for (int j = 0; j < row.length; j++) {
				_source[i][j] = (in.readFloat());
				if (_source[i][j] > _maxValue) _maxValue = _source[i][j]; 
			}
		}
		fin.close();
		reset();
	}
	
	public void reset() {
		double[][] map = new double[_source.length][_source[0].length];
		double scale = 1023.0 / (double) _maxValue;
		for (int i = 0; i < map.length; i++) {
			double[] row = map[i];
			for (int j = 0; j < row.length; j++) {
				map[i][j] = _source[i][j] * scale;
			}
		}
		_map = map;
	}
	
	public void writeMap(String fn) throws IOException {
		FileOutputStream fout = new FileOutputStream(fn);
		DataOutputStream out = new DataOutputStream(fout);
		out.writeInt(_map[0].length-1);
		out.writeInt(_map.length-1);
		out.writeFloat(_map.length-1);
		out.writeFloat(_map[0].length-1);
		out.writeFloat(_map.length-1);
		out.writeFloat(_map[0].length-1);
		for (double[] row: _map)
			for (double d: row) {
				out.writeFloat((float)d);
			}
		fout.close();
	}
	
	public void writeRandom(String fn, int w, int h) throws IOException {
		double[][] map = new double[h][w];
		fillRandom(map);
		_map = map;
		writeMap(fn);
	}
	
	void fillRandom(double[][] map) {
		for (int i = 0; i < map.length; i++) {
			double[] row = map[i];
			for (int j = 0; j < row.length; j++) {
				map[i][j] = 1024*Math.random();
			}
		}
	}
	
	/**
	 * Prints the weights to Sytem.out in a semi-readable fashion
	 */
	void printMap() {
		for (int i = 0; i < _map.length; i++) {
			double[] row = _map[i];
			for (int j = 0; j < row.length; j++) {
				System.out.print(_map[i][j] + "\t");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Prints the source values to Sytem.out in a semi-readable fashion
	 */
	void printSource() {
		for (int i = 0; i < _source.length; i++) {
			double[] row = _source[i];
			for (int j = 0; j < row.length; j++) {
				System.out.print(_source[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
