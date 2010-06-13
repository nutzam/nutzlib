package org.nutz.coder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.nutz.dao.DatabaseMeta;

public class Coder {
	
	private Connection connection;
	
	private List<SimpleEntity> entityList = new ArrayList<SimpleEntity>();
	
	private DatabaseMeta databaseMeta;
	
	public Coder(Connection connection){
		this.connection = connection;
	}
	
	public void loadDatabaseInfo() throws Throwable {
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		databaseMeta = new DatabaseMeta();
		databaseMeta.setProductName(databaseMetaData.getDatabaseProductName());
		databaseMeta.setVersion(databaseMetaData.getDatabaseProductVersion());
		ResultSet tableRet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", new String[]{"TABLE"});
		while(tableRet.next()) {
			SimpleEntity simpleEntity = new SimpleEntity();
			simpleEntity.setTableName(tableRet.getString("TABLE_NAME"));
			
			ResultSet pkSet = databaseMetaData.getPrimaryKeys(connection.getCatalog(), "%", simpleEntity.getTableName());
			Set<String> pks = new HashSet<String>(2);
			while (pkSet.next())
				pks.add(pkSet.getString("COLUMN_NAME"));
			ResultSet columnSet = databaseMetaData.getColumns(connection.getCatalog(), "%", tableRet.getString("TABLE_NAME"), "%");
			
			while (columnSet.next()) {
				SimpleEntityField entityField = new SimpleEntityField();
				entityField.setName(columnSet.getString("COLUMN_NAME"));
				entityField.setType(columnSet.getString("TYPE_NAME"));
				entityField.setPK(pks.contains(entityField.getName()));
				simpleEntity.getFields().add(entityField);
			}
			
			
			System.out.println("\n---------------------------------");
			System.out.printf("TABLE_NAME --> %s , Fields = %s",tableRet.getString("TABLE_NAME"),simpleEntity.getFields().size());
			
			entityList.add(simpleEntity);
		}
		System.out.println("\nTables = " + entityList.size());
	}

	public void makeFiles() {
		
	}
}

class SimpleEntity {
	private String tableName;
	private List<SimpleEntityField> fields = new ArrayList<SimpleEntityField>();
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<SimpleEntityField> getFields() {
		return fields;
	}
	public void setFields(List<SimpleEntityField> fields) {
		this.fields = fields;
	}
}

class SimpleEntityField {
	private String name;
	private String type;
	private boolean isPK;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isPK() {
		return isPK;
	}
	public void setPK(boolean isPK) {
		this.isPK = isPK;
	}
}

class SimplePk {
	private String tableName;
}