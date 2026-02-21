package strongy.gui.mainwindow.boateye;

import java.util.Objects;

import javax.swing.ImageIcon;

import strongy.Main;
import strongy.event.DisposeHandler;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.highprecision.IBoatDataState;

public class Mod360Icon extends ThemedLabel {

	private static final ImageIcon mod360Icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/nether_portal_with_pearl.png")));

	public Mod360Icon(StyleManager styleManager, IBoatDataState boatDataState, StrongyPreferences preferences, DisposeHandler sh) {
		super(styleManager);
		setIcon(mod360Icon);
		setVisible(preferences.usePreciseAngle.get() && boatDataState.reducingModulo360().get());

		sh.add(boatDataState.reducingModulo360().subscribeEDT(b -> this.setVisible(b && preferences.usePreciseAngle.get())));
		sh.add(preferences.usePreciseAngle.whenModified().subscribeEDT(b -> this.setVisible(b && boatDataState.reducingModulo360().get())));
	}

}
