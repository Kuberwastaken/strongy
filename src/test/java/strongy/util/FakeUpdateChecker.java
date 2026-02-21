package strongy.util;

import java.util.function.Consumer;

import strongy.io.updatechecker.IUpdateChecker;
import strongy.io.updatechecker.VersionURL;

public class FakeUpdateChecker implements IUpdateChecker {

	@Override
	public void check(Consumer<VersionURL> downloadUrlCallback) {

	}

}
