package strongy.model.datastate.statistics;

import strongy.model.datastate.stronghold.Chunk;

public interface IPrior {

	Iterable<Chunk> getChunks();

}
