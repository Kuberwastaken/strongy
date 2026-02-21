package strongy.model.datastate.calculator;

import strongy.model.datastate.stronghold.ChunkPrediction;

/**
 * Represents a single ring's best prediction with its certainty.
 */
public class RingPrediction {

    private final int ringIndex;
    private final ChunkPrediction best;
    private final double certainty;

    public RingPrediction(int ringIndex, ChunkPrediction best, double certainty) {
        this.ringIndex = ringIndex;
        this.best = best;
        this.certainty = certainty;
    }

    public int ringIndex() {
        return ringIndex;
    }

    public ChunkPrediction best() {
        return best;
    }

    public double certainty() {
        return certainty;
    }

}
