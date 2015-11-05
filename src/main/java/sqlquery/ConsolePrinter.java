package sqlquery;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public class ConsolePrinter {

	private int lineSize;
	private String cellFormat;

	public ConsolePrinter() {
		setLineSize(DEFAULT_LINE_SIZE);
	}

	public ConsolePrinter(int lineSize) {
		setLineSize(lineSize);
	}

	public void setLineSize (int lineSize) {
		this.lineSize = lineSize;
		this.cellFormat = "| %-" + lineSize + "s ";
	}

	public void printSelect(SqlRowSet rs) {
		SqlRowSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();

		printLine(columns);

		for (int i = 1; i <= columns; i++) {
			System.out.printf(cellFormat, rsmd.getColumnName(i));
		}

		System.out.println("|");
		printLine(columns);

		while (rs.next()) {
			for (int i = 1; i <= columns; i++) {
				System.out.printf(cellFormat, rs.getString(i));
			}
			System.out.println("|");
		}

		printLine(columns);
	}

	public void printLine(int columns) {
		int amount = ((lineSize + 3) * columns) + 1;
		StringBuilder sb = new StringBuilder(amount);

		for (int i = 0; i < amount; i++) {
			sb.append('-');
		}

		System.out.println(sb.toString());
	}

	public static final int DEFAULT_LINE_SIZE = 10;
}
