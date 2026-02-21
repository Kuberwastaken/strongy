package strongy.gui.themeeditor;

import java.util.function.Consumer;

import strongy.io.updatechecker.IUpdateChecker;
import strongy.io.updatechecker.VersionURL;

public class PreviewUpdateChecker implements IUpdateChecker {
	@Override
	public void check(Consumer<VersionURL> downloadUrlCallback) {
	}
}
