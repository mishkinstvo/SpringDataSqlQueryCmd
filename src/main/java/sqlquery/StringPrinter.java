package sqlquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public class StringPrinter {
	private int    lineSize;
	private String cellFormat;

	public StringPrinter() {
		setLineSize(DEFAULT_LINE_SIZE);
	}

	public StringPrinter(int lineSize) {
		setLineSize(lineSize);
	}

	public void setLineSize(int lineSize) {
		this.lineSize = lineSize;
		this.cellFormat = "| %-" + lineSize + "s ";
	}

	public String printSelect(SqlRowSet rs) {
		StringBuilder result = new StringBuilder();

		SqlRowSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();

		result.append(printLine(columns));
		result.append(String.format("%n"));

		for (int i = 1; i <= columns; i++) {
			result.append(
				String.format(
					cellFormat,
					StringUtils.abbreviate(
						rsmd.getColumnName(i),
						lineSize
					)
				)
			);
		}

		result.append(String.format("|%n"));
		result.append(printLine(columns));
		result.append(String.format("%n"));

		while (rs.next()) {
			for (int i = 1; i <= columns; i++) {
				result.append(
					String.format(
						cellFormat,
						StringUtils.abbreviate(
							rs.getString(i),
							lineSize
						)
					)
				);
			}

			result.append(String.format("|%n"));
		}

		result.append(printLine(columns));
		result.append(String.format("%n"));
		return result.toString();
	}

	protected StringBuilder printLine(int columns) {
		int amount = ((lineSize + 3) * columns) + 1;
		StringBuilder sb = new StringBuilder(amount);

		for (int i = 0; i < amount; i++) {
			sb.append('-');
		}

		return sb;
	}

	public static final int DEFAULT_LINE_SIZE = 20;
}

