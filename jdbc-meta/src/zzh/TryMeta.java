package zzh;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;
import org.nutz.lang.Strings;

import static java.lang.System.*;

public class TryMeta {

	static String bool(ResultSetMetaData md, String func, int i) {
		if ((Boolean) Mirror.me(md.getClass()).invoke(md, func, i))
			return "Y";
		return "n";
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		BasicDataSource ds = new BasicDataSource();
		oracle(ds);

		Connection conn = ds.getConnection();
		/* ========================================================= */
		// rsDetail(conn);
		DatabaseMetaData dmd = conn.getMetaData();
		out.printf("%s\n%s\n\n", dmd.getDatabaseProductName(), dmd.getDatabaseProductVersion());
		ResultSet rs = dmd.getPrimaryKeys(null, null, "t_pet");
		printRS(rs);
		out.println(Strings.dup('~', 110));
		rs = dmd.getIndexInfo(null, null, "t_pet", true, true);
		printRS(rs);
		/* ========================================================= */
		conn.close();
		ds.close();
	}

	private static void printRS(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int size = md.getColumnCount();
		for (int i = 1; i <= size; i++)
			out.printf(" %16s |", md.getColumnName(i));
		out.println();
		while (rs.next()) {
			for (int i = 1; i <= size; i++)
				out.printf(" %16s |", rs.getObject(i));
			out.println();
		}
		rs.close();
	}

	private static Map<Integer, String> types = new HashMap<Integer, String>();
	static {
		try {
			for (Field f : Types.class.getFields()) {
				Integer v = (Integer) f.get(null);
				types.put(v, f.getName());
			}
		} catch (Exception e) {
			throw Lang.wrapThrow(e);
		}
	}

	static void rsDetail(Connection conn) throws SQLException {
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM dao_supported_type");
		ResultSetMetaData md = rs.getMetaData();
		/*-----------------------------------------------------------*/
		String PTN = "%14s|%10s|%16s|%2s|%3s|%4s|%24s" + Strings.dup("|%2s", 6) + "\n";
		out.printf(PTN, "COL", "TYPE", "TYPENAME", "SZ", "PE", "SC", "CLASSNAME", "AU", "CS", "NN",
				"DW", "CR", "SG", "SB");
		out.println(Strings.dup('-', 100));

		for (int i = 1; i <= md.getColumnCount(); i++) {
			out.printf(PTN, md.getColumnName(i), types.get(md.getColumnType(i)), md
					.getColumnTypeName(i), md.getColumnDisplaySize(i), md.getPrecision(i), md
					.getScale(i), md.getColumnClassName(i), bool(md, "isAutoIncrement", i), bool(
					md, "isCaseSensitive", i), md.isNullable(i), bool(md, "isReadOnly", i), bool(
					md, "isDefinitelyWritable", i), bool(md, "isCurrency", i), bool(md, "isSigned",
					i), bool(md, "isSearchable", i));
		}

		/*-----------------------------------------------------------*/
		rs.close();
		stat.close();
	}

	static void h2(BasicDataSource ds) {
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:tcp://localhost/~/zzhtest");
		ds.setUsername("sa");
		ds.setPassword("");
	}

	static void oracle(BasicDataSource ds) {
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@192.168.1.104:1521:zzhtest");
		ds.setUsername("demo");
		ds.setPassword("123456");
	}

	static void sql2000(BasicDataSource ds) {
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUrl("jdbc:sqlserver://192.168.1.105:1433;databaseName=zzhtest;");
		ds.setUsername("sa");
		ds.setPassword("123456");
	}

	static void sql2005(BasicDataSource ds) {
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUrl("jdbc:sqlserver://192.168.1.104:1433;databaseName=zzhtest;");
		ds.setUsername("sa");
		ds.setPassword("12345678");
	}

	static void mysql(BasicDataSource ds) {
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://192.168.1.104:3306/zzhtest");
		ds.setUsername("root");
		ds.setPassword("123456");
	}

	static void psql(BasicDataSource ds) {
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/zzhtest");
		ds.setUsername("demo");
		ds.setPassword("123456");
	}

}
