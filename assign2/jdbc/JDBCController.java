package jdbc;

import jdbc.builder.JDBCURLBuilder;

import java.sql.SQLException;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JDBCController {

	private JDBCURLBuilder builder;
	private JDBCModel model;
	private StringProperty tableInUse;
	private ObservableList<String> tableNamesList;

	public JDBCController() {
		tableNamesList = FXCollections.observableArrayList();
		model = new JDBCModel();
		tableInUse = new SimpleStringProperty();
		tableInUse.addListener((value, oldValue, newValue) -> {
			try {
				model.getAndInitializeColumnNames(newValue);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public StringProperty tableInUseProperty() {
		return tableInUse;
	}

	public JDBCController setURLBuilder(JDBCURLBuilder builder) {
		this.builder = builder;
		return this;
	}

	public JDBCController setDataBase(String address, String port, String catalog) {
		builder.setAddress(address);
		builder.setPort(port);
		builder.setCatalog(catalog);
		return this;
	}

	public JDBCController addConnectionURLProperty(String key, String value) {
		builder.addURLProperty(key, value);
		return this;
	}

	public JDBCController setCredentials(String user, String pass) {
		model.setCredentials(user, pass);
		return this;
	}

	public JDBCController connect() throws SQLException {
		model.connectTo(builder.getURL());
		return this;
	}

	public boolean isConnected() throws SQLException {
		return model.isConnected();
	}

	public List<String> getColumnNames() throws SQLException {
		return model.getAndInitializeColumnNames(tableInUse.get());
	}

	public List<List<Object>> getAll() throws SQLException {
		return model.getAll(tableInUse.get());
	}

	public List<List<Object>> search(String searchTerm) throws SQLException {
		return model.search(tableInUse.get(), searchTerm);
	}

	public void close() throws Exception {
		model.close();
	}

	public ObservableList<String> getTableNames() throws SQLException {
		if (model.isConnected()) {
			tableNamesList.clear();
			tableNamesList.addAll(model.getTableNames());
		}
		return tableNamesList;
	}

}
