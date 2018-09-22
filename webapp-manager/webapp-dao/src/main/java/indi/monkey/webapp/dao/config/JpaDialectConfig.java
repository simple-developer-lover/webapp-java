package indi.monkey.webapp.dao.config;

import org.hibernate.dialect.MySQL5Dialect;

public class JpaDialectConfig extends MySQL5Dialect {

	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf-8mb4";
	}

}
