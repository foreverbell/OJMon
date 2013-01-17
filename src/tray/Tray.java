package tray;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import user.Account;
import user.AccountManager;
import util.Logger;

public class Tray {

	private static Tray _instance = new Tray();
	private TrayIcon _trayIcon;
	private Image _emptyIcon, _defaultIcon;
	private boolean _isFlickering;
	private Timer _timer;
	private int _currentIcon;
	
	public static Tray getInstance() {
		return _instance;
	}
	
	public void startFlickering() {
		if (!_isFlickering) {
			TimerTask timerTaskInstance = new TimerTask() {
				public void run() {
					if (_currentIcon == 0) {
						_trayIcon.setImage(_emptyIcon);
					} else {
						_trayIcon.setImage(_defaultIcon);
					}
					_currentIcon = 1 - _currentIcon;
				}
			};
			_currentIcon = 0;
			_timer = new Timer();
			_timer.schedule(timerTaskInstance, 500, 500);
		}
		_isFlickering = true;
	}
	
	public void stopFlickering() {
		if (_isFlickering) {
			_timer.cancel();
			_timer = null;
			_trayIcon.setImage(_defaultIcon);
		}
		_isFlickering = false;
	}
	
	public void DisplayMessage(String title, String tooltip) {
		_trayIcon.displayMessage(title, tooltip, TrayIcon.MessageType.INFO);
	}

	public void initializeTray() {
		_emptyIcon = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/resource/empty.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		_defaultIcon = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/resource/icon.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		if (SystemTray.isSupported() && Desktop.isDesktopSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
		
			PopupMenu popup = new PopupMenu();
			for (Account account : AccountManager.getInstance().getAllAccount()) {
				popup.add(new MenuAccountBrowse(account));
			}
			popup.addSeparator();
			popup.add(new MenuForceUpdate());
			popup.addSeparator();
			popup.add(new MenuExit());
	
			ActionListener actionListener = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Tray.getInstance().stopFlickering();
					AccountManager.getInstance().clearNewRecordFlag();	
				}
			};
			_trayIcon = new TrayIcon(_defaultIcon, "OJMon", popup);
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
