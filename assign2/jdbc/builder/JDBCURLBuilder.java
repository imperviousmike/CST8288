package jdbc.builder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public abstract class JDBCURLBuilder {
	protected static final String JDBC = "jdbc";
	protected Map<String, String> properties;
	protected String dbType;
	protected int portNumber;
	protected String hostAddress;
	protected String catalogName;

	public JDBCURLBuilder() {
		properties = new LinkedHashMap<>();
	}

	public void setPort(String port) {
		Objects.requireNonNull(port);
		try {
			int portNum = Integer.parseInt(port);
			if (portNum < 0) {
				throw new IllegalArgumentException("Cannot enter negative port number");
			}
			portNumber = portNum;
		} catch (NumberFormatException e) {

		}

	}

	public void addURLProperty(String key, String value) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(value);
		properties.put(key, value);
	}

	protected void setDB(String db) {
		Objects.requireNonNull(db);
		dbType = db;
	}

	public abstract String getURL();
	
	public void setPort(int port) {
		if (port > 0) {
			this.portNumber = port;
		}
	}

	public void setAddress(String address) {
		Objects.requireNonNull(address);
		hostAddress = address;
	}

	public void setCatalog(String catalog) {
		Objects.requireNonNull(catalog);
		catalogName = catalog;
	}

}
