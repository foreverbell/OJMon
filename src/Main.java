
import oj.*;
import user.Account;
import user.AccountManager;

public class Main {

	public static void main(String[] args) {
		Account account = AccountManager.getInstance().createAccount("Codeforces");
		account.createNullUser(new OJCodeforces());
		
		Account account2 = AccountManager.getInstance().createAccount("POJ");
		account2.createNullUser(new OJPOJ());
		
		AccountManager.getInstance().startMonitoring();
	}
}
