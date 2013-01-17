package tray;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import user.AccountManager;

@SuppressWarnings("serial")
public class MenuForceUpdate extends MenuItem {

	MenuForceUpdate() {
		super("Force update");
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountManager.getInstance().updateAll(true);
			}
		};
		addActionListener(menuListener);
	}
}
