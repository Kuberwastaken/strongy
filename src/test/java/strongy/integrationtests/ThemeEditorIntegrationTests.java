package strongy.integrationtests;

import java.util.Locale;

import javafx.application.Platform;

import strongy.gui.frames.ThemeEditorDialog;
import strongy.gui.frames.ThemedDialog;
import strongy.gui.style.theme.CustomTheme;
import strongy.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class ThemeEditorIntegrationTests {

	@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
	@Test
	public void canOpenDialog() {
		// Arrange
		IntegrationTestBuilder integrationTestBuilder = new IntegrationTestBuilder();
		Locale.setDefault(Locale.US);

		// Act + Assert
		try {

			Platform.runLater(() -> {
				ThemeEditorDialog dialog = new ThemeEditorDialog(TestUtils.createStyleManager(),
						integrationTestBuilder.preferences, null, new CustomTheme());
			});
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

}
