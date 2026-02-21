package strongy.model.datastate.calculator;

import java.util.Collections;
import java.util.List;

import strongy.event.IDisposable;
import strongy.model.datastate.stronghold.Chunk;
import strongy.model.datastate.stronghold.ChunkPrediction;

public interface ICalculatorResult extends IDisposable {

	ChunkPrediction getBestPrediction();

	List<ChunkPrediction> getTopPredictions();

	List<Chunk> getTopChunks();

	boolean success();

	/**
	 * Returns per-ring predictions when multi-stronghold tracking is enabled.
	 * Default returns empty list for backward compatibility with single-ring mode.
	 */
	default List<RingPrediction> getRingPredictions() {
		return Collections.emptyList();
	}

}
