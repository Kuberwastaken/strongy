package strongy.gui.mainwindow.triangulation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;

import strongy.event.IDisposable;
import strongy.gui.components.panels.ThemedPanel;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.calculator.ICalculatorResult;
import strongy.model.datastate.stronghold.ChunkPrediction;

public class DetailedTriangulationPanel extends ThemedPanel implements IDisposable {

	private static final int NUM_DETAILED_PANELS = 5;

	private final StrongyPreferences preferences;

	private final ChunkPanelHeader header;
	private final List<ChunkPanel> panels;

	public DetailedTriangulationPanel(StyleManager styleManager, StrongyPreferences preferences) {
		super(styleManager);
		this.preferences = preferences;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(0);
		header = new ChunkPanelHeader(styleManager, preferences);
		add(header);
		panels = new ArrayList<ChunkPanel>();
		for (int i = 0; i < NUM_DETAILED_PANELS; i++) {
			ChunkPanel panel = new ChunkPanel(styleManager, preferences);
			panels.add(panel);
			add(panel);
		}

		setBackgroundColor(styleManager.currentTheme.COLOR_NEUTRAL);
	}

	public void setResult(ICalculatorResult result) {
		header.updateHeaderText(preferences.strongholdDisplayType.get());
		if (result == null) {
			for (ChunkPanel p : panels) {
				p.setPrediction(null);
			}
			return;
		}
		List<ChunkPrediction> predictions = result.getTopPredictions();
		for (int i = 0; i < NUM_DETAILED_PANELS; i++) {
			ChunkPanel p = panels.get(i);
			if (i < predictions.size())
				p.setPrediction(predictions.get(i));
			else
				p.setPrediction(null);
		}
	}

	public void setAngleUpdatesEnabled(boolean b) {
		header.setAngleUpdatesEnabled(b);
		panels.forEach(p -> p.setAngleUpdatesEnabled(b));
	}

	public Iterable<ChunkPanel> getChunkPanels() {
		return panels;
	}

	@Override
	public void updateColors() {
		super.updateColors();
		for (int i = 0; i < NUM_DETAILED_PANELS; i++) {
			ChunkPanel p = panels.get(i);
			p.updateColors();
		}
	}

	@Override
	public void updateSize(StyleManager styleManager) {
		setPreferredSize(new Dimension(0, (1 + NUM_DETAILED_PANELS) * (styleManager.size.PADDING + styleManager.size.TEXT_SIZE_MEDIUM)));
		super.updateSize(styleManager);
	}

	@Override
	public void dispose() {
		header.dispose();
	}

}