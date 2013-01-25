
import config.Config;
import user.AccountManager;

public class Main {

	public static void main(String[] args) {
		Config.getInstance().initializeConfig();
		AccountManager.getInstance().startMonitoring();
	}
}
