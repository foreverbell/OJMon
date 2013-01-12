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

	public User createUser(OJ attachedOJ, String userName) {
		User user = new User(attachedOJ, userName);
		if (!_userList.add(user)) return null;
		return user;
	}
	
	public User createNullUser(OJ attachedOJ) {
		User user = new NullUser(attachedOJ);
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
	
	public int getNewRecordCnt() {
		int newRecordCnt = 0;
		Iterator<User> iter = _userList.iterator();
		while (iter.hasNext()) {
			newRecordCnt += iter.next().getNewRecordCnt();
		}
		return newRecordCnt;
	}
	
	public void clearNewRecordFlag() {
		Iterator<User> iter = _userList.iterator();
		while (iter.hasNext()) {
			iter.next().clearNewRecordFlag();
		}
	}
	
	public void updateToHTML() {
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
