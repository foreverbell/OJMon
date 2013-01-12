package user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import tray.Tray;

public class AccountManager {

	private static final int UPDATE_INTERVAL = 120000;
	private static final AccountManager _instance = new AccountManager();
	
	public static AccountManager getInstance() {
		return _instance;
	}
	
	private HashSet<Account> _accountList = new HashSet<Account>();
	
	public Account getAccount(String accountName) {
		Iterator<Account> iter = _accountList.iterator();
		while (iter.hasNext()) {
			Account account = iter.next();
			if (account.getAccountName() == accountName) {
				return account;
			}
		}
		return null;	
	}
	
	public boolean destroyAccount(Account account) {
		return _accountList.remove(account);
	}
	
	public boolean destroyAccount(String accountName) {
		Account account = getAccount(accountName);
		if (account == null) return false;
		return destroyAccount(account);
	}

	public Account createAccount(String accountName) {
		Account account = new Account(accountName);
		if (!_accountList.add(account)) return null;
		return account;
	}
	
	/** TODO: those codes need to be refactored. */
	private String generateTooltip() {
		String tooltip = new String();
		Iterator<Account> iter = _accountList.iterator();
		while (iter.hasNext()) {
			Account account = iter.next();
			if (account.hasNewRecord()) {
				tooltip += account.getAccountName() + " fetched " + Integer.valueOf(account.getNewRecordCnt()).toString() + " submission(s).\n";
			}		
		}	
		return Tray.DEFAULT_TOOLTIP + "\n" + tooltip;
	}
	
	public void onTrayMouseClicked() {
		Tray.getInstance().stopFlickering();
		Tray.getInstance().setTooltipText(Tray.DEFAULT_TOOLTIP);
		Iterator<Account> iter = _accountList.iterator();
		while (iter.hasNext()) {
			Account account = iter.next();
			if (account.hasNewRecord()) account.clearNewRecordFlag();
		}
	}
	
	private AccountManager() { 
		TimerTask timerTaskInstance = new TimerTask() {
			public void run() {
				Iterator<Account> iter = _accountList.iterator();
				boolean hasNewRecord = false;
				while (iter.hasNext()) {
					Account account = iter.next();
					account.updateAll();
					account.updateToHTML();
					hasNewRecord |= account.hasNewRecord();
				}
				if (hasNewRecord) {
					Tray.getInstance().setTooltipText(generateTooltip());
					Tray.getInstance().startFlickering();
				} else {
					Tray.getInstance().setTooltipText(Tray.DEFAULT_TOOLTIP);
				}
			}
		};
		Timer tmr = new Timer();
		tmr.schedule(timerTaskInstance, 1000, UPDATE_INTERVAL);
	}
}
