package strongy.model.datastate.statistics;

import java.util.Arrays;
import java.util.HashMap;

import strongy.model.datastate.divine.IDivineContext;
import strongy.model.datastate.stronghold.Chunk;
import strongy.model.datastate.stronghold.Ring;
import strongy.model.datastate.stronghold.RingIterator;
import strongy.model.datastate.stronghold.StrongholdConstants;
import strongy.util.Coords;
import strongy.util.Logger;

public class Prior implements IPrior {

	int size1d;
	int radius;
	int x0, z0, x1, z1;
	Chunk[] chunks;
	final IDivineContext divineContext;

	public Prior(int centerX, int centerZ, int radius, IDivineContext divineContext) {
		long t0 = System.currentTimeMillis();
		this.divineContext = divineContext;
		setInitialSize(centerX, centerZ, radius);
		chunks = new Chunk[size1d * size1d];
		for (int i = x0; i <= x1; i++) {
			for (int j = z0; j <= z1; j++) {
				int idx = idx(i, j);
				chunks[idx] = new Chunk(i, j);
			}
		}
		setInitialWeights();
		smoothWeights();
		Logger.log("Time to construct prior: " + (System.currentTimeMillis() - t0) / 1000f + " seconds.");
	}

	/**
	 * Calculates weights (prior probabilities) for all chunks in the domain.
	 */
	protected void setInitialWeights() {
		RingIterator ringIterator = new RingIterator();
		for (Ring ring : ringIterator) {
			int c0 = (int) ring.innerRadius - margin();
			int c1 = (int) ring.outerRadius + margin();
			int xStart = (-c1 > x0 ? -c1 : x0);
			int xEnd = (c1 < x1 ? c1 : x1);
			int zStart = (-c1 > z0 ? -c1 : z0);
			int zEnd = (c1 < z1 ? c1 : z1);
			int innerThreshold = (int) ((c0 - 1) / Math.sqrt(2)) - 10;
			for (int i = xStart; i <= xEnd; i++) {
				for (int j = zStart; j <= zEnd; j++) {
					// Skip ahead if inside inner part of ring
					if (j > -innerThreshold + 1 && j < innerThreshold - 1) {
						if (i < innerThreshold && i > -innerThreshold) {
							j = innerThreshold - 1;
							if (!inBounds(i, j)) {
								continue;
							}
						}
					}
					// Approximate integral by discretising the chunk into 4 points (centered at
					// (0,0), not (8,8))
					int n = discretisationPointsPerChunkSide();
					double weight = 0;
					if (n == 1) {
						weight = strongholdDensity(i, j, ring);
					} else {
						for (int k = 0; k < n; k++) {
							double x = i - 0.5 + k / (n - 1.0);
							for (int l = 0; l < n; l++) {
								double z = j - 0.5 + l / (n - 1.0);
								weight += strongholdDensity(x, z, ring);
							}
						}
					}
					weight /= (double) n * n; // Approximate percentage of chunk thats inside the ring
					chunks[idx(i, j)].weight += weight;
				}
			}
		}
	}

	/**
	 * Returns offset weights for biome snapping.
	 */
	protected static HashMap<Integer, Integer> getOffsetWeights() {
		HashMap<Integer, Integer> offsetWeights = new HashMap<Integer, Integer>();
		for (int i = -26; i <= 30; i++) {
			int chunkOffset = i >> 2;
			offsetWeights.put(-chunkOffset, offsetWeights.getOrDefault(-chunkOffset, 0) + 1);
		}
		return offsetWeights;
	}

	/**
	 * Simulates biome snapping to smooth the weights.
	 */
	protected void smoothWeights() {
		HashMap<Integer, Integer> offsetWeights = getOffsetWeights();

		// Precompute 1D kernel and its sum
		int sr = StrongholdConstants.snappingRadius;
		int kernelSize = 2 * sr + 1;
		double[] kernel1D = new double[kernelSize];
		double kernelSum = 0;
		for (int k = -sr; k <= sr; k++) {
			kernel1D[k + sr] = offsetWeights.get(k);
			kernelSum += kernel1D[k + sr];
		}
		// Normalize the 1D kernel
		for (int k = 0; k < kernelSize; k++) {
			kernel1D[k] /= kernelSum;
		}

		Chunk[] oldChunks = chunks;
		int oldSize1d = size1d;
		int oldX0 = x0;
		int oldZ0 = z0;

		int oldZ1 = z1;
		setSize((x0 + x1) / 2, (z0 + z1) / 2, this.radius);

		// Pass 1: Smooth along X (columns), writing into intermediate buffer
		// Output domain: x in [x0..x1], z in [oldZ0..oldZ1]
		int pass1Width = x1 - x0 + 1;
		int pass1Height = oldZ1 - oldZ0 + 1;
		double[] intermediate = new double[pass1Width * pass1Height];
		for (int j = oldZ0; j <= oldZ1; j++) {
			for (int i = x0; i <= x1; i++) {
				double w = 0;
				for (int k = -sr; k <= sr; k++) {
					int srcIdx = oldSize1d * (j - oldZ0) + (i + k - oldX0);
					w += oldChunks[srcIdx].weight * kernel1D[k + sr];
				}
				intermediate[(j - oldZ0) * pass1Width + (i - x0)] = w;
			}
		}

		// Pass 2: Smooth along Z (rows), writing into final chunks
		chunks = new Chunk[size1d * size1d];
		for (int i = x0; i <= x1; i++) {
			for (int j = z0; j <= z1; j++) {
				double w = 0;
				for (int l = -sr; l <= sr; l++) {
					int srcIdx = (j + l - oldZ0) * pass1Width + (i - x0);
					w += intermediate[srcIdx] * kernel1D[l + sr];
				}
				int idx = (j - z0) + (i - x0) * size1d;
				chunks[idx] = new Chunk(i, j);
				chunks[idx].weight = w;
			}
		}
	}

	protected int discretisationPointsPerChunkSide() {
		return 5;
	}

	protected int margin() {
		return 0;
	}

	/**
	 * Density (pdf) of strongholds at chunk coords (cx, cy), in the given ring.
	 */
	protected double strongholdDensity(double cx, double cz, Ring ring) {
		double d2 = cx * cx + cz * cz;
		if (d2 > ring.innerRadius * ring.innerRadius && d2 < ring.outerRadius * ring.outerRadius) {
			double phi = Coords.getPhi(cx, cz);
			double pdfPhi = ring.ring == 0 ? divineContext.getDensityAtAngleBeforeSnapping(phi)
					: (1.0 / (2.0 * Math.PI));
			double pdfR = ring.numStrongholds / ((ring.outerRadius - ring.innerRadius) * Math.sqrt(d2));
			return pdfR * pdfPhi;
		}
		return 0;
	}

	protected void setInitialSize(int centerX, int centerZ, int radius) {
		this.radius = radius;
		x0 = centerX - radius - StrongholdConstants.snappingRadius;
		z0 = centerZ - radius - StrongholdConstants.snappingRadius;
		x1 = centerX + radius + StrongholdConstants.snappingRadius;
		z1 = centerZ + radius + StrongholdConstants.snappingRadius;
		size1d = 2 * (radius + StrongholdConstants.snappingRadius) + 1;
	}

	protected void setSize(int centerX, int centerZ, int radius) {
		this.radius = radius;
		x0 = centerX - radius;
		z0 = centerZ - radius;
		x1 = centerX + radius;
		z1 = centerZ + radius;
		size1d = 2 * radius + 1;
	}

	private boolean inBounds(int i, int j) {
		return i >= x0 && i <= x1 && j >= z0 && j <= z1;
	}

	private int idx(int i, int j) {
		return (j - z0) + (i - x0) * size1d;
	}

	@Override
	public Iterable<Chunk> getChunks() {
		return Arrays.asList(chunks);
	}

}
