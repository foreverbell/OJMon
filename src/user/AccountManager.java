package user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import tray.Tray;

public class AccountManager {

	private static final AccountManager _instance = new AccountManager();
	
	public static AccountManager getInstance() {
		return _instance;
	}
	
	private HashSet<Account> _accountList = new HashSet<Account>();
	private int _updateInterval; // in MillionSeconds
	
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

	/**
	 * Creating an account after startMonitoring has been called is forbidden.
	 */
	public Account createAccount(String accountName) {
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

	public void updateAll(boolean isForceShowMessage) {
		Iterator<Account> iter = _accountList.iterator();
		boolean hasNewRecord = false;
		while (iter.hasNext()) {
			Account account = iter.next();
			account.updateAll();
			account.writeToHTML();
			hasNewRecord |= account.hasNewRecord();
		}
		if (hasNewRecord) {
			Tray.getInstance().DisplayMessage("New submissions fetched.", generateMessage());
			Tray.getInstance().startFlickering();
		} else if (isForceShowMessage) {
			Tray.getInstance().DisplayMessage("No new submissions.", null);
		}
	}

	public void setUpdateInterval(int updateInterval) {
		_updateInterval = updateInterval;
	}
	
	public void startMonitoring() {
		TimerTask timerTaskInstance = new TimerTask() {
			public void run() {
				updateAll(false);
			}
		};
		Tray.getInstance().initializeTray();
		Timer tmr = new Timer();
		tmr.schedule(timerTaskInstance, 0, _updateInterval);		
	}
	
	
	private String generateMessage() {
		String message = new String();
		Iterator<Account> iter = _accountList.iterator();
		while (iter.hasNext()) {
			Account account = iter.next();
			if (account.hasNewRecord()) {
				message += String.format("%s fetched %d submissions.\n", account.getAccountName(), account.getNewRecordCount());
			}		
		}
		return message;
	}
	
	private AccountManager() { }
}
