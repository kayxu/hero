package com.joymeng.game.db;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class BgDAO extends SimpleJdbcDaoSupport {
	public static final String SQL_DOWNLOAD_APK = "SELECT count(*) FROM download_user WHERE down_begin_time >= ? AND down_begin_time <= ? ";
	public static final String SQL_DOWNLOAD_SOURCE = "SELECT count(*) FROM download_user WHERE down_end_time >= ? AND down_end_time <= ? ";
	public static final String SQL_DOWNLOAD_UNZIP = "SELECT count(*) FROM download_user WHERE unzip_end_time >= ? AND unzip_end_time <= ? ";

	public int countAPK(String start, String end) {
		int num = getSimpleJdbcTemplate().queryForInt(SQL_DOWNLOAD_APK, start,
				end);
		return num;
	}

	public int countSource(String start, String end) {
		int num = getSimpleJdbcTemplate().queryForInt(SQL_DOWNLOAD_SOURCE,
				start, end);
		return num;
	}

	public int countUnzip(String start, String end) {
		int num = getSimpleJdbcTemplate().queryForInt(SQL_DOWNLOAD_UNZIP,
				start, end);
		return num;
	}

}
