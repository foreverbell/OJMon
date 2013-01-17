package tray;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class MenuExit extends MenuItem  {
	
	MenuExit() {
		super("Exit");
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		addActionListener(menuListener);
	}
}
