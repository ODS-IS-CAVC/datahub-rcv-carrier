package datahub.constants;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * 定数
 *
 */
public class Constants {

	/**著作権表示、秘密表示 */
	String copyright = "Copyright 2023 FUJITSU LIMITED";
	String confidential = "FUJITSU　CONFIDENTIAL";

	/** ロケール */
	public static final Locale LOCALE = Locale.getDefault();

	/** INIファイル定義のキー名 */
	public static final String INI_TO_ID = "to_id";
	public static final String INI_LOOP_TIME = "loop_time";
	public static final String INI_LOOP_COUNT = "loop_count";
	public static final String INI_API_URL_FILE_CREATE = "api_url_files_create";
	public static final String INI_API_URL_FILE_STATUS = "api_url_files_status";
	public static final String INI_API_URL_FILE_DOWNLOAD = "api_url_files_donwload";
	public static final String INI_AUTH_KEY = "auth_key";
	public static final String INI_DH_USER_ID = "dh_user_id";
	public static final String INI_DH_PASSWORD = "dh_password";
	public static final String INI_DB_USERNAME = "db_username";
	public static final String INI_DB_PASSWORD = "db_password";
	public static final String INI_DB_URL = "db_url";
	public static final String INI_JDBC_DRIVER = "jdbc_driver";
	public static final String INI_POSTFIX_EXTRACT_COND = "_extract_cond";
	public static final String INI_POSTFIX_WHERE_COLUMN = "_where_column";
	public static final String INI_POSTFIX_COLUMN_TYPE = "_column_type";
	public static final String INI_POSTFIX_DATE_TIME_FORMAT = "_date_time_format";
	public static final String INI_POSTFIX_OUTPUT_COND = "_output_cond";
	public static final String INI_POSTFIX_TABLE = "_table";
	public static final String INI_POSTFIX_COLUMNS = "_columns";

	/** INIファイル項目の論理名 */
	public static final String INI_LOOP_TIME_DEFAULT = "30";
	public static final String INI_LOOP_COUNT_DEFAULT = "20";
	public static final String COLUMN_TYPE_VARCHAR = "0";
	public static final String COLUMN_TYPE_DATE = "1";
	public static final String COLUMN_TYPE_TIMESTAMP = "2";
	public static final String COLUMN_TYPE_TIMESTAMP_LTZ = "3";
	public static final String EXTRACT_ALL = "0";
	public static final String EXTRACT_DATE_TIME = "2";
	public static final String OUTPUT_TO_DB_FILE = "0";
	public static final String OUTPUT_TO_DB = "1";
	public static final String OUTPUT_TO_FILE = "2";
	
	/** ファイルパス */
	/** アプリケーションパス*/
	public static final String APP_PATH = System.getProperty("user.dir");
	/** INIファイルのフォルダ名 */
	public static final String PROPERTY = "configuration" + File.separator + "properties";
	/** 前回抽出キーファイルのフォルダ名 */
	public static final String PREVKEY = "configuration" + File.separator + "prevKey";

	/** 出力ファイルの格納先*/
	public static final String RECV_PATH = "data" + File.separator + "recv";
	public static final String WORK_PATH = "data" + File.separator + "work";
	/** 日時用のフォーマット */
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	/** 文字コード */
	public static final String ENCODE = Charset.defaultCharset().displayName();
}