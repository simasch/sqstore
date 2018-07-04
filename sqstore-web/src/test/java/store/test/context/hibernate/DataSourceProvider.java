package store.test.context.hibernate;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Vlad Mihalcea
 */
public interface DataSourceProvider {

	enum IdentifierStrategy {
		IDENTITY,
		SEQUENCE
	}

	String hibernateDialect();

	DataSource dataSource();

	Class<? extends DataSource> dataSourceClassName();

	Properties dataSourceProperties();

	String url();

	String username();

	String password();

	Database database();
}
