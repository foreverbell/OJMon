package user;

import html.HTMLLocalTextWriter;

import java.util.Iterator;
import java.util.HashSet;

import oj.OJ;

public class Account {

	private String _accountName;
	private HashSet<User> _userList;
	private HTMLLocalTextWriter _HTMLWriter;
	
	public String getAccountName() {
		return _accountName;
	}

	public String getAccountRecordFilePath() {
		return getAccountName() + ".html";
	}
	
	public User [] getAllUser() {
		return _userList.toArray(new User[0]);
	}

	public User createUser(OJ bindOJ, String userName) {
		User user = new User(bindOJ, userName);
		if (!_userList.add(user)) return null;
		return user;
	}
	
	public User createNullUser(OJ bindOJ) {
		User user = new NullUser(bindOJ);
		if (!_userList.add(user)) return null;
		return user;
	}

	public int updateAll() {
		int updatedCnt = 0;
		Iterator<User> iter = _userList.iterator();
		while (iter.hasNext()) {
			updatedCnt += iter.next().update();
		}
		return updatedCnt;
	}
	
	public boolean hasNewRecord() {
		Iterator<User> iter = _userList.iterator();
		while (iter.hasNext()) {
			if (iter.next().hasNewRecord()) return true;
		}
		return false;
	}
	
	public int getNewRecordCount() {
		int newRecordCount = 0;
		Iterator<User> iter = _userList.iterator();
		while (iter.hasNext()) {
			newRecordCount += iter.next().getNewRecordCount();
		}
		return newRecordCount;
	}
	
	public void clearNewRecordFlag() {
		Iterator<User> iter = _userList.iterator();
		while (iter.hasNext()) {
			iter.next().clearNewRecordFlag();
		}
	}
	
	public void writeToHTML() {
		_HTMLWriter.writeToHTML();
	}
	
	public Account(String accountName) {
		_accountName = accountName;
		_userList = new HashSet<User>();
		_HTMLWriter = new HTMLLocalTextWriter(this);
	}

	public boolean equals(Object obj) {
		Account account2 = (Account) obj;
		return this._accountName.equals(account2._accountName);
	}
}
