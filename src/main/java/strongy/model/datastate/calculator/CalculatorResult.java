package strongy.model.datastate.calculator;

import java.util.ArrayList;
import java.util.List;

import strongy.event.IObservable;
import strongy.event.IReadOnlyList;
import strongy.io.preferences.enums.McVersion;
import strongy.model.datastate.common.IPlayerPosition;
import strongy.model.datastate.endereye.IEnderEyeThrow;
import strongy.model.datastate.statistics.Posterior;
import strongy.model.datastate.stronghold.Chunk;
import strongy.model.datastate.stronghold.ChunkPrediction;

public class CalculatorResult implements ICalculatorResult {

	private final ChunkPrediction bestPrediction;
	private final List<Chunk> topChunks;
	private final List<ChunkPrediction> topPredictions;

	public CalculatorResult() {
		bestPrediction = new ChunkPrediction();
		topPredictions = new ArrayList<>();
		topChunks = new ArrayList<>();
	}

	public CalculatorResult(Posterior posterior, IReadOnlyList<IEnderEyeThrow> eyeThrows, IObservable<IPlayerPosition> playerPosition, int numPredictions, McVersion version) {
		// Find chunk with the largest posterior probability
		Chunk predictedChunk = posterior.getMostProbableChunk();
		bestPrediction = new ChunkPrediction(predictedChunk, playerPosition, version);
		topChunks = createTopChunks(posterior);
		topPredictions = createTopPredictions(playerPosition, numPredictions, version);
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
	public boolean success() {
		return bestPrediction.success;
	}

	private List<Chunk> createTopChunks(Posterior posterior) {
		List<Chunk> topChunks = posterior.getChunks();
		topChunks.sort((a, b) -> -Double.compare(a.weight, b.weight));
		return topChunks;
	}

	private List<ChunkPrediction> createTopPredictions(IObservable<IPlayerPosition> playerPosition, int amount, McVersion version) {
		List<ChunkPrediction> topPredictions = new ArrayList<>();
		for (Chunk c : topChunks) {
			topPredictions.add(new ChunkPrediction(c, playerPosition, version));
			if (topPredictions.size() >= amount)
				break;
		}
		return topPredictions;
	}

	@Override
	public void dispose() {
		bestPrediction.dispose();
		if (topPredictions != null)
			for (ChunkPrediction p : topPredictions)
				p.dispose();
	}

}
