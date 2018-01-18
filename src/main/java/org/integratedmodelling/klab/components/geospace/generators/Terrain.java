package org.integratedmodelling.klab.components.geospace.generators;

import java.util.Random;

/**
 * Terrain generation using the diamond-square algorithm (Fournier et al. 1982)
 * From http://www.javaworld.com/article/2076745/learn-java/3d-graphic-java--render-fractal-landscapes.html
 * 
 * @author Merlin Hughes
 * @author Ferd
 *
 */
public class Terrain {

    private double[][] terrain;
    private double     roughness, min, max;
    private int        divisions;
    private Random     rng;

    /**
     * @param lod level of detail (number of iterations)
     * @param roughness (0 to 1)
     */
    public Terrain(int lod, double roughness) {

        this.roughness = roughness;
        this.divisions = 1 << lod;
        terrain = new double[divisions + 1][divisions + 1];
        rng = new Random();
        terrain[0][0] = rnd();
        terrain[0][divisions] = rnd();
        terrain[divisions][divisions] = rnd();
        terrain[divisions][0] = rnd();
        double rough = roughness;
        for (int i = 0; i < lod; ++i) {
            int q = 1 << i, r = 1 << (lod - i), s = r >> 1;
            for (int j = 0; j < divisions; j += r)
                for (int k = 0; k < divisions; k += r)
                    diamond(j, k, r, rough);
            if (s > 0)
                for (int j = 0; j <= divisions; j += s)
                    for (int k = (j + s) % r; k <= divisions; k += r)
                        square(j - s, k - s, r, rough);
            rough *= roughness;
        }
        min = max = terrain[0][0];
        for (int i = 0; i <= divisions; ++i)
            for (int j = 0; j <= divisions; ++j)
                if (terrain[i][j] < min)
                    min = terrain[i][j];
                else if (terrain[i][j] > max)
                    max = terrain[i][j];
    }

    private void diamond(int x, int y, int side, double scale) {
        if (side > 1) {
            int half = side / 2;
            double avg = (terrain[x][y] + terrain[x + side][y] +
                    terrain[x + side][y + side] + terrain[x][y + side]) * 0.25;
            terrain[x + half][y + half] = avg + rnd() * scale;
        }
    }

    private void square(int x, int y, int side, double scale) {
        int half = side / 2;
        double avg = 0.0, sum = 0.0;
        if (x >= 0) {
            avg += terrain[x][y + half];
            sum += 1.0;
        }
        if (y >= 0) {
            avg += terrain[x + half][y];
            sum += 1.0;
        }
        if (x + side <= divisions) {
            avg += terrain[x + side][y + half];
            sum += 1.0;
        }
        if (y + side <= divisions) {
            avg += terrain[x + half][y + side];
            sum += 1.0;
        }
        terrain[x + half][y + half] = avg / sum + rnd() * scale;
    }

    private double rnd() {
        return 2. * rng.nextDouble() - 1.0;
    }

    /**
     * Use to obtain values from normalized cell coordinates.
     * @param i 0-1
     * @param j 0-1
     * @return the altitude at normalized coordinates
     */
    public double getAltitude(double i, double j) {
        double alt = terrain[(int) (i * divisions)][(int) (j * divisions)];
        return (alt - min) / (max - min);
    }

}