package util.database;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;

import util.exceptions.ApplicationException;

public class Database extends DbUtil {

	private static final String APP_PROPERTIES = "src/main/resources/application.properties";
	private static final String SQL_SCHEMA = "src/main/resources/schema.sql";
	private static final String SQL_LOAD = "src/main/resources/data.sql";
	
	private static Database db;
	
	private String driver;
	private String url;

	private Database() {
		Properties prop=new Properties();
		
		try {
			prop.load(new FileInputStream(APP_PROPERTIES));
		} catch (IOException e) {
			throw new ApplicationException(e);
		}
		
		driver = prop.getProperty("datasource.driver");
		url = prop.getProperty("datasource.url");
		
		if (driver==null || url==null)
			throw new ApplicationException("Configuracion de driver y/o url no encontrada en application.properties");
		
		DbUtils.loadDriver(driver);
	}
	
	public static Database getInstance() {
		if (db == null) {
			db = new Database();
			db.executeScript(SQL_SCHEMA);
			db.executeScript(SQL_LOAD);
		}
		
		return db;
	}
	
	public String getUrl() {
		return url;
	}
	
}
