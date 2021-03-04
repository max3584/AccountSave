package data.db;

public interface DatabaseColumnInterface {

	public String getColumn(String column);
	public int getColumnNum(String column);
	public String[] getColumns();
	public int getColumunLength();
	public boolean checkColumn(String column);
}
