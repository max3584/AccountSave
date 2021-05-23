package AccountControl;

import java.util.ArrayList;
import java.util.List;

import data.Account;
import javafx.scene.control.TableColumn;

import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewConfig {
	
	private final TableColumn<Account, String> siteCol = new TableColumn<Account, String>("サイト名");
	private final TableColumn<Account, String> idCol =  new TableColumn<Account, String>("ID");
	private final TableColumn<Account, String> mailCol= new TableColumn<Account, String>("メールアドレス");
	private final TableColumn<Account, String> passwordCol = new TableColumn<Account, String>("Password");

	public TableViewConfig() {
		this.siteCol.setCellValueFactory(new PropertyValueFactory<Account, String>("siteName"));
		this.idCol.setCellValueFactory(new PropertyValueFactory<Account, String>("id"));
		this.mailCol.setCellValueFactory(new PropertyValueFactory<Account, String>("mail"));
		this.passwordCol.setCellValueFactory(new PropertyValueFactory<Account, String>("password"));
	}
	
	public List<TableColumn<Account, String>> getColumnsParameter() {
		ArrayList<TableColumn<Account, String>> list = new ArrayList<TableColumn<Account, String>>();
		list.add(siteCol);
		list.add(idCol);
		list.add(mailCol);
		list.add(passwordCol);
		return list;
	}
}
