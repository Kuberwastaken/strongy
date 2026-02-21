package strongy.gui.components.panels;

import javax.swing.JPanel;

import strongy.event.DisposeHandler;
import strongy.event.IDisposable;
import strongy.event.IModifiable;
import strongy.event.ISubscribable;
import strongy.event.ObservableProperty;

public class ResizablePanel extends JPanel implements IModifiable<ResizablePanel>, IDisposable {

	protected final DisposeHandler disposeHandler = new DisposeHandler();
	protected final ObservableProperty<ResizablePanel> whenSizeModified = new ObservableProperty<ResizablePanel>();

	@Override
	public void dispose() {
		disposeHandler.dispose();
	}

	@Override
	public ISubscribable<ResizablePanel> whenModified() {
		return whenSizeModified;
	}

}
