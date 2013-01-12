package tray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import user.AccountManager;
import util.Logger;

public class Tray {

	public static final String DEFAULT_TOOLTIP = "OJMon";
	
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
	
	public void setTooltipText(String tooltip) {
		_trayIcon.setToolTip(tooltip);
	}
	
	public void initTray() {
		
		_emptyIcon = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/resource/empty.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		_defaultIcon = Toolkit.getDefaultToolkit().getImage(Tray.class.getResource("/resource/icon.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			MenuItem defaultItem = new MenuItem("Exit");
			PopupMenu popup = new PopupMenu();
			popup.add(defaultItem);
			ActionListener menuListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	                 System.exit(0);
				}
			};
			defaultItem.addActionListener(menuListener);
			
			MouseListener iconListener = new MouseListener() {
				public void mouseClicked(MouseEvent arg0) {
					AccountManager.getInstance().onTrayMouseClicked();
				}
				public void mouseEntered(MouseEvent arg0) { }
				public void mouseExited(MouseEvent arg0) { }
				public void mousePressed(MouseEvent arg0) { }
				public void mouseReleased(MouseEvent arg0) { }
			};
			_trayIcon = new TrayIcon(_defaultIcon, DEFAULT_TOOLTIP, popup);
			_trayIcon.addMouseListener(iconListener);
	        try {
	        	tray.add(_trayIcon);
	        	return;
	        } catch (AWTException e) {
	        	Logger.printlnError(e);
	        }
		} else {
			Logger.printlnError(new Exception("System tray is not supported."));
		}
		System.exit(0);
	}
	
	private Tray() { }
}
