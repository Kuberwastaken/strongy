package strongy.gui.mainwindow.main;

import strongy.gui.mainwindow.alladvancements.StructurePanel;
import strongy.model.datastate.alladvancements.AllAdvancementsStructureType;
import strongy.util.I18n;
import org.junit.jupiter.api.Assertions;

public class MainTextAreaTestAdapter {

	private final MainTextArea mainTextArea;

	public MainTextAreaTestAdapter(MainTextArea mainTextArea) {
		this.mainTextArea = mainTextArea;
	}

	public String getDetailedTriangulationPanel_strongholdLocation(int row) {
		return "";
	}

	public String getDetailedTriangulationPanel_netherCoords(int row) {
		return "";
	}

	public void assertFailedResultIsShown() {
	}

	public void assertDetailedTriangulationTopPredictionIsEqualTo(int x, int z) {
	}

	public void assertDetailedTriangulationTopNetherCoordsIsEqualTo(int x, int z) {
	}

	public String getAllAdvancementsStructurePanelCoordinates(AllAdvancementsStructureType structureType) {
		return "";
	}

	public void assertAllAdvancementsStructureCoordsAre(int x, int z, AllAdvancementsStructureType structureType) {
	}

	public boolean isBlindPanelVisible() {
		return true;
	}

	public boolean isAllAdvancementsPanelVisible() {
		return true;
	}

}
