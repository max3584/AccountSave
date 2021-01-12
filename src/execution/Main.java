package execution;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

import System.console.WindowsCC;
import data.Account;
import data.DatabaseData;
import data.MailAddress;
import data.Website;
import db.Exception.JDBCException;
import module.DatabaseConnection;
import module.Throw.AccountControlApplicationException;
import module.Throw.DatabaseException;
import module.Throw.argsException;

public class Main {

	private static DatabaseConnection dc;

	public static void main(String[] args) throws AccountControlApplicationException {
		try {
			checkArgs(args);

			String select = args[0];

			switch (select) {
			case "mail":
				mailRegistration();
				break;
			case "account":
				accountRegistration();
				break;
			case "show":
				accountShow();
				break;
			case "allshow":
				accountAllShow();
			default:
				throw new argsException("正常なものが入っていません");
			}
			System.out.println("complete");
		} catch (JDBCException e) {
			throw new DatabaseException("データベース接続時にエラーが発生しました");
		}
	}

	// Init Check Method
	private static void checkArgs(String[] args) throws argsException, JDBCException {
		if (args.length != 2) {
			throw new argsException("長さがおかしいですよ!!");
		}

		dc = new DatabaseConnection(args[1]);
	}

	// Functions
	private static String inputKey(String msg) {
		Console con = System.console();
		String text = con.readLine(msg);
		while (text.equals("")) {
			System.out.println("もう一度入力してください");
			text = con.readLine(msg);
		}
		return text;
	}

	private static String secretInputKey(String msg) {
		Console con = System.console();
		char[] securetText = con.readPassword(msg);
		while (securetText.length == 0) {
			System.out.println("もう一度入力してください");
			securetText = con.readPassword(msg);
		}
		return new String(securetText);
	}
	
	private static String accountNumberFormat(String accountNumber) {
		String[] accountNumbers = accountNumber.split("_");
		int number = Integer.parseInt(accountNumbers[1]);
		
		return String.format("%s_%d", accountNumbers[0], ++number);
	}
	
	private static HashMap<String, DatabaseData> DBDataCreate(String mail, String site, String id, String password, String domain, String detail, String accountNumber) {
		
		String accNumberFormat = accountNumberFormat(accountNumber);
		
		HashMap<String, DatabaseData> DBDatas = new HashMap<String, DatabaseData>(3);
		DBDatas.put("mail", new MailAddress(mail, domain, detail));
		DBDatas.put("website", new Website(site, id, mail, accNumberFormat, detail));
		DBDatas.put("account", new Account(accNumberFormat, password)); 
		
		return DBDatas;
	}
	
	private static Website createWebsite(String siteName, String userID, String mailAddress, String accountID, String detail) {
		return new Website(siteName, userID, mailAddress, accountID, detail);
	}
	
	private static Account createAccount(String accID, String password) {
		return new Account(accID, password);
	}
	
	private static ArrayList<String> showMailList() throws JDBCException {
		ArrayList<String> mails = dc.getMailList();
		System.out.println("メールアドレス一覧");
		for(int i = 0; i < mails.size(); i++) {
			System.out.println(String.format("%d.\t%s", i, mails.get(i)));
		}
		return mails;
	}
	
	private static String getAccountID(String site) throws JDBCException {
		String AccountID = dc.getWebsiteAccountNumber(site);
		String nowAccountID = isAccountNumber(AccountID) ? String.format("%s_0", site) : AccountID;
		String[] tmp = nowAccountID.split("_");
		String nextAccountID = String.format("%s_%d", tmp[0], Integer.parseInt(tmp[1]) + 1);
		return nextAccountID;
	}
	
	private static boolean isAccountNumber(String accountNumber) throws JDBCException {
		return accountNumber == null || accountNumber.equals("");
	}
	
	private static Website showUserID(String site) throws JDBCException {
		ArrayList<Website> accounts = dc.getWebsiteAccountList(site);
		boolean isSize = accounts.size() >= 2;
		for(int i = 0;  isSize && i < accounts.size(); i++) {
			System.out.println(String.format("%d.\t%s", i, accounts.get(i).getUserID()));
		}
		if (isSize) {
			int userID = Integer.parseInt(inputKey("数値を入力してください>"));
			return accounts.get(userID);
		} else {
			return accounts.get(0);
		}
	}

	// Main sub Programs
	private static void mailRegistration() throws JDBCException {
		String mail = inputKey("メールアドレス: ");
		String site = inputKey("サイトはどこで登録されましたか？\n");
		String password = secretInputKey("パスワード: ");
		String detail = inputKey("登録した理由や、活用目的を記入してください\n");
		String[] tmp = mail.split("@");
		String domain = tmp[1];
		String accountNumber = getAccountID(site);
		HashMap<String, DatabaseData> DBData;
		DBData = DBDataCreate(mail, site, mail, password, domain, detail, accountNumber);
		dc.mailInsert((MailAddress) DBData.get("mail"));
		dc.websiteInsert((Website) DBData.get("website"));
		dc.accountInsert((Account) DBData.get("account"));
	}

	private static void accountRegistration() throws JDBCException {
		String userID = inputKey("ＩＤ>");
		String pass = secretInputKey("パスワード >");
		String site = inputKey("サイト名>");
		try  {
			ArrayList<String> mails = showMailList();
			int mail = Integer.parseInt(inputKey("数字を入力してください >"));
			String AccID = getAccountID(site);
			if (dc.getSiteName(site)) {
				String detail = inputKey("使用目的や関連 >");
				dc.websiteInsert(createWebsite(site, userID, mails.get(mail), AccID, detail));
			}
			dc.accountInsert(createAccount(AccID, pass));
		} catch(NumberFormatException e) {
			throw new Error("正常な動作が行えない可能性があるのでプログラムを中断します");
		}
	}

	private static void accountShow() throws JDBCException {
		Website web = showUserID(inputKey("サイト名: "));
		
		String password = dc.getUserPassword(web.getAccountID());
		
		System.out.println(String.format("userID: %s\npassword: %s", web.getUserID(), "*****************"));
		
		String key = "clip";
		
		WindowsCC cmd = new WindowsCC();
		cmd.add(key, "cmd", "/c", "echo", password, "|", "clip");
		// Windows System Clip Password
		cmd.ConsoleCommand(key);
	}
	
	private static void accountAllShow() {
		System.out.println("まだ未実装です.");
	}

}
