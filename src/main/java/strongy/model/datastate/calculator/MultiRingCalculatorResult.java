package strongy.model.datastate.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import strongy.event.IObservable;
import strongy.event.IReadOnlyList;
import strongy.io.preferences.enums.McVersion;
import strongy.model.datastate.common.IPlayerPosition;
import strongy.model.datastate.endereye.IEnderEyeThrow;
import strongy.model.datastate.statistics.Posterior;
import strongy.model.datastate.stronghold.Chunk;
import strongy.model.datastate.stronghold.ChunkPrediction;
import strongy.model.datastate.stronghold.Ring;
import strongy.model.datastate.stronghold.StrongholdConstants;

/**
 * Calculator result that tracks stronghold predictions across multiple rings.
 * Each ring's best prediction is exposed via getRingPredictions().
 */
public class MultiRingCalculatorResult implements ICalculatorResult {

    private final ChunkPrediction bestPrediction;
    private final List<Chunk> topChunks;
    private final List<ChunkPrediction> topPredictions;
    private final List<RingPrediction> ringPredictions;

    public MultiRingCalculatorResult() {
        bestPrediction = new ChunkPrediction();
        topPredictions = new ArrayList<>();
        topChunks = new ArrayList<>();
        ringPredictions = new ArrayList<>();
    }

    public MultiRingCalculatorResult(Posterior posterior, IReadOnlyList<IEnderEyeThrow> eyeThrows,
            IObservable<IPlayerPosition> playerPosition, int numPredictions, McVersion version) {
        // Overall best prediction (same as standard CalculatorResult)
        Chunk predictedChunk = posterior.getMostProbableChunk();
        bestPrediction = new ChunkPrediction(predictedChunk, playerPosition, version);

        // All chunks sorted by weight
        topChunks = posterior.getChunks();
        topChunks.sort((a, b) -> -Double.compare(a.weight, b.weight));

        // Standard top predictions
        topPredictions = new ArrayList<>();
        for (Chunk c : topChunks) {
            topPredictions.add(new ChunkPrediction(c, playerPosition, version));
            if (topPredictions.size() >= numPredictions)
                break;
        }

        // Per-ring best predictions
        ringPredictions = new ArrayList<>();
        for (int ringIdx = 0; ringIdx < StrongholdConstants.numRings; ringIdx++) {
            Ring ring = Ring.get(ringIdx);
            Chunk ringBest = null;
            double ringTotalWeight = 0;

            for (Chunk c : topChunks) {
                double dist = Math.sqrt(c.x * c.x + c.z * c.z);
                if (dist >= ring.innerRadius && dist <= ring.outerRadius) {
                    ringTotalWeight += c.weight;
                    if (ringBest == null || c.weight > ringBest.weight) {
                        ringBest = c;
                    }
                }
            }

            if (ringBest != null && ringTotalWeight > 0.001) {
                ChunkPrediction ringPrediction = new ChunkPrediction(ringBest, playerPosition, version);
                ringPredictions.add(new RingPrediction(ringIdx, ringPrediction, ringTotalWeight));
            }
        }
    }

    @Override
    public ChunkPrediction getBestPrediction() {
        return bestPrediction;
    }

    @Override
    public List<ChunkPrediction> getTopPredictions() {
        return topPredictions;
    }

    @Override
    public List<Chunk> getTopChunks() {
        return topChunks;
    }

    @Override
    public List<RingPrediction> getRingPredictions() {
        return Collections.unmodifiableList(ringPredictions);
    }

    @Override
    public boolean success() {
        return bestPrediction.success;
    }

    @Override
    public void dispose() {
        bestPrediction.dispose();
        if (topPredictions != null)
            for (ChunkPrediction p : topPredictions)
                p.dispose();
        for (RingPrediction rp : ringPredictions)
            rp.best().dispose();
    }

}
