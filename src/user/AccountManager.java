package user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import tray.Tray;

public class AccountManager {

	private static final int UPDATE_INTERVAL = 120000;
	private static final AccountManager _instance = new AccountManager();
	private boolean _isLocked = false;
	
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
	
	public Account [] getAllAccount() {
		return _accountList.toArray(new Account[0]);
	}

	public Account createAccount(String accountName) {
		// Creating an account after startMonitoring has been called is forbidden.
		if (_isLocked) return null;
		Account account = new Account(accountName);
		if (!_accountList.add(account)) return null;
		return account;
	}
	
	public void clearNewRecordFlag() {
		Iterator<Account> iter = _accountList.iterator();
		while (iter.hasNext()) {
			Account account = iter.next();
			if (account.hasNewRecord()) account.clearNewRecordFlag();
		}
	}

	private String generateMessage() {
		String message = new String();
		Iterator<Account> iter = _accountList.iterator();
		while (iter.hasNext()) {
			Account account = iter.next();
			if (account.hasNewRecord()) {
				message += account.getAccountName() + " fetched " + Integer.valueOf(account.getNewRecordCnt()).toString() + " submission(s).\n";
			}		
		}
		return message;
	}

	public void startMonitoring() {
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
					Tray.getInstance().iconDisplayMessage("New submissions fetched", generateMessage());
					Tray.getInstance().startFlickering();
				}
			}
		};
		Tray.getInstance().initializeTray();
		_isLocked = true;
		Timer tmr = new Timer();
		tmr.schedule(timerTaskInstance, 0, UPDATE_INTERVAL);		
	}
	
	private AccountManager() { }
}
