package strongy.gui.mainwindow.boateye;

import java.util.HashMap;
import java.util.Objects;

import javax.swing.ImageIcon;

import strongy.Main;
import strongy.event.DisposeHandler;
import strongy.event.IObservable;
import strongy.gui.components.labels.ThemedLabel;
import strongy.gui.style.StyleManager;
import strongy.io.preferences.StrongyPreferences;
import strongy.model.datastate.highprecision.BoatState;

public class BoatIcon extends ThemedLabel {

	public BoatIcon(StyleManager styleManager, IObservable<BoatState> boatState, StrongyPreferences preferences, DisposeHandler sh) {
		super(styleManager);
		setIcon(getBoatIcon(boatState.get()));
		setVisible(preferences.usePreciseAngle.get());

		sh.add(boatState.subscribeEDT(b -> setIcon(getBoatIcon(b))));
		sh.add(preferences.usePreciseAngle.whenModified().subscribeEDT(this::setVisible));
	}

	private static final HashMap<String, ImageIcon> cachedIcons = new HashMap<>();

	public static ImageIcon getBoatIcon(BoatState boatState) {
		switch (boatState) {
			case ERROR:
				return getOrCreateCachedIcon("/boat_red.png");
			case MEASURING:
				return getOrCreateCachedIcon("/boat_blue.png");
			case VALID:
				return getOrCreateCachedIcon("/boat_green.png");
			default:
				return getOrCreateCachedIcon("/boat_gray.png");
		}
	}

	private static ImageIcon getOrCreateCachedIcon(String path) {
		if (!cachedIcons.containsKey(path))
			cachedIcons.put(path, new ImageIcon(Objects.requireNonNull(Main.class.getResource(path))));
		return cachedIcons.get(path);
	}

}
