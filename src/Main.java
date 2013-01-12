
import oj.*;
import tray.Tray;
import user.Account;
import user.AccountManager;

public class Main {

	public static void main(String[] args) {
		Tray.getInstance().initTray();
		
		Account account = AccountManager.getInstance().createAccount("Codeforces");
		account.createNullUser(new OJCodeforces());
		
		Account account2 = AccountManager.getInstance().createAccount("POJ");
		account2.createNullUser(new OJPOJ());
	}
}
