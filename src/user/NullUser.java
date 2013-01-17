package user;

import oj.OJ;

public class NullUser extends User {
	
	public static final String NULLUSER_NAME = "";
	
	public boolean isNull() {
		return true;
	}
	
	public NullUser(OJ bindOJ) { 
		super(bindOJ, NULLUSER_NAME);
	}
}
