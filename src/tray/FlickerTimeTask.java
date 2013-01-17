package tray;

import java.awt.Image;
import java.awt.TrayIcon;
import java.util.TimerTask;

public class FlickerTimeTask extends TimerTask {
	
	private TrayIcon _trayIcon;
	private Image _emptyIconImage, _defaultIconImage;
	private boolean _isEmptyIcon;
	
	FlickerTimeTask(TrayIcon trayIcon, Image emptyIconImage, Image defaultIconImage) {
		_trayIcon = trayIcon;
		_emptyIconImage = emptyIconImage;
		_defaultIconImage = defaultIconImage;
		_isEmptyIcon = false;
	}
	
	public void run() {
		if (!_isEmptyIcon) {
			_trayIcon.setImage(_emptyIconImage);
			_isEmptyIcon = true;
		} else {
			_trayIcon.setImage(_defaultIconImage);
			_isEmptyIcon = false;
		}
	}
}
