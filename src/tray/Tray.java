package tray;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import user.Account;
import user.AccountManager;
import util.Logger;

public class Tray {

	private static Tray _instance = new Tray();
	private TrayIcon _trayIcon;
	private Image _emptyIconImage, _defaultIconImage;
	private boolean _isFlickering;
	private Timer _timerInstance;
	
	public static Tray getInstance() {
		return _instance;
	}
	
	public void startFlickering() {
		if (!_isFlickering) {
			_timerInstance = new Timer();
			_timerInstance.schedule(new FlickerTimeTask(_trayIcon, _emptyIconImage, _defaultIconImage), 500, 500);
		}
		_isFlickering = true;
	}
	
	public void stopFlickering() {
		if (_isFlickering) {
			_timerInstance.cancel();
			_trayIcon.setImage(_defaultIconImage);
		}
		_isFlickering = false;
	}
	
	public void DisplayMessage(String title, String tooltip) {
		_trayIcon.displayMessage(title, tooltip, TrayIcon.MessageType.INFO);
	}

	public void initializeTray() {
		_emptyIconImage = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/resource/empty.png"));
		_defaultIconImage = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/resource/icon.png"));
		if (SystemTray.isSupported() && Desktop.isDesktopSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
		
			/* Initialize popup menu. */
			PopupMenu popup = new PopupMenu();
			for (Account account : AccountManager.getInstance().getAllAccount()) {
				popup.add(new MenuAccountBrowse(account));
			}
			popup.addSeparator();
			popup.add(new MenuForceUpdate());
			popup.addSeparator();
			popup.add(new MenuExit());
			
			/* Initialize action listener. */
			ActionListener actionListener = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Tray.getInstance().stopFlickering();
					AccountManager.getInstance().clearNewRecordFlag();	
				}
			};
			
			_trayIcon = new TrayIcon(_defaultIconImage, "OJMon", popup);
			_trayIcon.addActionListener(actionListener);
	        try {
	        	tray.add(_trayIcon);
	        	return;
	        } catch (Exception e) {
	        	Logger.printlnError(e);
	        }
		} else {
			Logger.printlnError(new Exception("System tray or desktop is not supported."));
		}
		System.exit(0);
	}
	
	private Tray() { }
}
