package tray;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import user.Account;
import util.Logger;

@SuppressWarnings("serial")
public class MenuAccountBrowse extends MenuItem {
	
	MenuAccountBrowse(final Account account) {
		super(account.getAccountName());
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(account.getAccountRecordFilePath()));
				} catch (Exception ee) { 
					Logger.printlnError(ee);
				}
			}
		};
		addActionListener(menuListener);
	}
}
