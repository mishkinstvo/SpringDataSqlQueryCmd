package sqlquery;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class QueryBean {
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void execute(String query) {
		jdbcTemplate.execute(query);
	}

	public SqlRowSet select(String query) {
		return jdbcTemplate.queryForRowSet(query);
	}
}
