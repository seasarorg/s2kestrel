package org.seasar.extension.unit;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.seasar.extension.dataset.ColumnType;
import org.seasar.extension.dataset.DataReader;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.DataWriter;
import org.seasar.extension.dataset.impl.SqlDeleteTableWriter;
import org.seasar.extension.dataset.impl.SqlReloadReader;
import org.seasar.extension.dataset.impl.SqlReloadTableReader;
import org.seasar.extension.dataset.impl.SqlTableReader;
import org.seasar.extension.dataset.impl.SqlWriter;
import org.seasar.extension.dataset.impl.XlsReader;
import org.seasar.extension.dataset.impl.XlsWriter;
import org.seasar.extension.dataset.types.ColumnTypes;
import org.seasar.extension.jdbc.UpdateHandler;
import org.seasar.extension.jdbc.impl.BasicUpdateHandler;
import org.seasar.extension.mock.servlet.MockHttpServletRequest;
import org.seasar.extension.mock.servlet.MockHttpServletResponse;
import org.seasar.extension.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.extension.mock.servlet.MockServlet;
import org.seasar.extension.mock.servlet.MockServletConfig;
import org.seasar.extension.mock.servlet.MockServletConfigImpl;
import org.seasar.extension.mock.servlet.MockServletContext;
import org.seasar.extension.mock.servlet.MockServletContextImpl;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ContainerConstants;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ConnectionUtil;
import org.seasar.framework.util.DataSourceUtil;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.FileOutputStreamUtil;
import org.seasar.framework.util.MethodUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.kestrel.regex.RegexUtil;

/**
 * <p>
 * JUnitのTestCaseを拡張した、S2を使ったテストを楽しくおこなうための クラスです。
 * </p>
 * 
 * <p>
 * 各テストメソッドを実行する際には、以下の順番で処理をおこないます。 テストメソッド名が関係する処理がありますので、ここではtestXxx()という
 * テストメソッドを実行すると仮定します。
 * </p>
 * 
 * <ol>
 * <li>S2Containerのインスタンスを作成します。</li>
 * <li>setUp()を実行します。</li>
 * <li>setUpXxxという名前のメソッドがあれば実行します。</li>
 * <li>S2Containerを初期化します。</li>
 * <li>"j2ee.dataSource"という名前のデータソースをコンテナから取得します。</li>
 * <li>setUpAfterContainerInit()を実行します。</li>
 * <li>フィールドにDIをおこないます。</li>
 * <li>testXxx()を実行します。</li>
 * <li>tearDownBeforeContainerDestroy()を実行します。</li>
 * <li>データソースを開放します。</li>
 * <li>S2Containerの終了処理をおこないます。</li>
 * <li>tearDownXxx()という名前のメソッドがあれば実行します。</li>
 * <li>tearDown()を実行します。</li>
 * </ol>
 * 
 * <p>
 * setUp()内、またはsetUpXxx()内でinclude("j2ee.dicon")を行い、テスト
 * メソッド名の最後に"Tx"と付けると、テストメソッドの実行前にトランザク ションを開始し、テストメソッドの終了後にトランザクションをロールバック
 * します。DBのレコードを変更するようなテストの場合にこの機能を利用すると、 クリーンアップ処理が不要になります。
 * </p>
 * 
 * @author higa
 * @see junit.framework.TestCase
 */
public class S2TestCase extends TestCase {

	private static final String DATASOURCE_NAME = "j2ee"
			+ ContainerConstants.NS_SEP + "dataSource";

	private S2Container container_;

	private Servlet servlet_;

	private MockServletConfig servletConfig_;

	private MockServletContext servletContext_;

	private MockHttpServletRequest request_;

	private MockHttpServletResponse response_;

	private DataSource dataSource_;

	private Connection connection_;

	private DatabaseMetaData dbMetaData_;

	private List bindedFields_;

	/**
	 * <p>
	 * シリアライゼーションを有効にするためのデフォルトコンストラクタです。
	 * </p>
	 * <p>
	 * <code>TestCase#setName()</code>
	 * メソッドを呼び出されることなくこのコンストラクタが使われることは意図していません。
	 * </p>
	 *  
	 */
	public S2TestCase() {
	}

	/**
	 * <p>
	 * 指定された名前でテストケースを作成します。
	 * </p>
	 * 
	 * @param name
	 *            テストケースの名前
	 */
	public S2TestCase(String name) {
		super(name);
	}

	/**
	 * <p>
	 * テストケースが保有するS2コンテナを取得します。
	 * </p>
	 * 
	 * @return S2コンテナ
	 */
	public S2Container getContainer() {
		return container_;
	}

	/**
	 * <p>
	 * コンポーネント名を指定してS2コンテナからコンポーネントを取得 します。
	 * </p>
	 * <p>
	 * 指定した名前を持つコンポーネントが登録されていない場合はnullを 返します。
	 * </p>
	 * 
	 * @param componentName
	 *            取得するコンポーネント名
	 * @return 指定した名前を持つコンポーネント
	 * @see org.seasar.framework.container.S2Container#getComponent(java.lang.Object)
	 */
	public Object getComponent(String componentName) {
		return container_.getComponent(componentName);
	}

	/**
	 * <p>
	 * クラスを指定してS2コンテナからコンポーネントを取得します。
	 * </p>
	 * <p>
	 * インターフェースを指定した場合はそのインターフェースを実装して いるコンポーネント、クラスを指定した場合はそのクラスかそのクラスを
	 * 親に持つ子クラスのコンポーネントを取得します。
	 * </p>
	 * <p>
	 * 指定したクラスのコンポーネントが登録されていない場合はnullを 返します。
	 * </p>
	 * 
	 * @param componentClass
	 *            取得するクラス
	 * @return 指定したクラスのコンポーネント
	 * @see org.seasar.framework.container.S2Container#getComponent(java.lang.Object)
	 */
	public Object getComponent(Class componentClass) {
		return container_.getComponent(componentClass);
	}

	/**
	 * <p>
	 * コンポーネント名を指定してS2コンテナからコンポーネント定義を 取得します。
	 * </p>
	 * 
	 * @param componentName
	 *            取得するコンポーネント名
	 * @return 指定した名前を持つコンポーネント定義
	 * @see org.seasar.framework.container.S2Container#getComponentDef(java.lang.Object)
	 */
	public ComponentDef getComponentDef(String componentName) {
		return container_.getComponentDef(componentName);
	}

	/**
	 * <p>
	 * クラスを指定してS2コンテナからコンポーネント定義を取得します。
	 * </p>
	 * 
	 * @param componentClass
	 *            取得するクラス
	 * @return 指定したクラスのコンポーネント定義
	 * @see org.seasar.framework.container.S2Container#getComponentDef(java.lang.Object)
	 */
	public ComponentDef getComponentDef(Class componentClass) {
		return container_.getComponentDef(componentClass);
	}

	/**
	 * <p>
	 * クラスをS2コンテナにコンポーネント定義として登録します。
	 * </p>
	 * 
	 * @param componentClass
	 *            コンポーネントのクラス
	 * @see org.seasar.framework.container.S2Container#register(java.lang.Class)
	 */
	public void register(Class componentClass) {
		container_.register(componentClass);
	}

	/**
	 * <p>
	 * クラスをS2コンテナに名前付きコンポーネント定義として登録 します。
	 * </p>
	 * 
	 * @param componentClass
	 *            コンポーネントのクラス
	 * @param componentName
	 *            コンポーネントの名前
	 * @see org.seasar.framework.container.S2Container#register(java.lang.Class,
	 *      java.lang.String)
	 */
	public void register(Class componentClass, String componentName) {
		container_.register(componentClass, componentName);
	}

	/**
	 * <p>
	 * オブジェクトをS2コンテナにコンポーネントとして登録します。
	 * </p>
	 * <p>
	 * キーはオブジェクトのクラスになります。
	 * </p>
	 * 
	 * @param component
	 *            コンポーネントとして登録するオブジェクト
	 * @see org.seasar.framework.container.S2Container#register(java.lang.Object)
	 */
	public void register(Object component) {
		container_.register(component);
	}

	/**
	 * <p>
	 * オブジェクトをS2コンテナに名前付きコンポーネントとして登録 します。
	 * </p>
	 * 
	 * @param component
	 *            コンポーネントとして登録するオブジェクト
	 * @param componentName
	 *            コンポーネント名
	 * @see org.seasar.framework.container.S2Container#register(java.lang.Object,
	 *      java.lang.String)
	 */
	public void register(Object component, String componentName) {
		container_.register(component, componentName);
	}

	/**
	 * <p>
	 * S2コンテナにコンポーネント定義を登録します。
	 * </p>
	 * 
	 * @param componentDef
	 *            登録するコンポーネント定義
	 * @see org.seasar.framework.container.S2Container#register(org.seasar.framework.container.ComponentDef)
	 */
	public void register(ComponentDef componentDef) {
		container_.register(componentDef);
	}

	/**
	 * <p>
	 * 設定ファイルのパスを指定して子コンテナをincludeします。
	 * </p>
	 * <p>
	 * パスはCLASSPATHで指定されているディレクトリをルートとする 設定ファイルの絶対パスか、ファイル名のみを指定します。
	 * </p>
	 * <p>
	 * ファイル名のみの場合、テストケースと同じパッケージにあるもの とします。
	 * </p>
	 * 
	 * @param path
	 *            子コンテナの設定ファイルのパス
	 */
	public void include(String path) {
		S2ContainerFactory.include(container_, convertPath(path));
	}

	private String convertPath(String path) {
		if (ResourceUtil.getResourceNoException(path) != null) {
			return path;
		} else {
			String prefix = RegexUtil.replaceFirst(RegexUtil.replaceAll(getClass().getName(), '.', '/'), "/[^/]+$", "");
			return prefix + "/" + path;
		}
	}

	/**
	 * <p>
	 * データソースを取得します。
	 * </p>
	 * 
	 * @return データソース
	 */
	public DataSource getDataSource() {
		if (dataSource_ == null) {
			throw new EmptyRuntimeException("dataSource");
		}
		return dataSource_;
	}

	/**
	 * <p>
	 * データソースからコネクションを取得します。
	 * </p>
	 * 
	 * @return データソースから取得したコネクション
	 */
	public Connection getConnection() {
		if (connection_ != null) {
			return connection_;
		}
		connection_ = DataSourceUtil.getConnection(getDataSource());
		return connection_;
	}

	/**
	 * <p>
	 * コネクションのデータベースメタデータを取得します。
	 * </p>
	 * 
	 * @return コネクションのデータベースメタデータ
	 */
	public DatabaseMetaData getDatabaseMetaData() {
		if (dbMetaData_ != null) {
			return dbMetaData_;
		}
		dbMetaData_ = ConnectionUtil.getMetaData(getConnection());
		return dbMetaData_;
	}

	/**
	 * <p>
	 * Excelファイルを読み、DataSetを作成します。
	 * </p>
	 * <p>
	 * シート名をテーブル名、一行目をカラム名、二行目以降をデータ として読み込みます。
	 * </p>
	 * 
	 * <p>
	 * パスはCLASSPATHで指定されているディレクトリをルートとする 設定ファイルの絶対パスか、ファイル名のみを指定します。ファイル名
	 * のみの場合、テストケースと同じパッケージにあるものとします。
	 * </p>
	 * 
	 * @param path
	 *            Excelファイルのパス
	 * @return Excelファイルの内容から作成したDataSet
	 * @see org.seasar.extension.dataset.impl.XlsReader#read()
	 */
	public DataSet readXls(String path) {
		DataReader reader = new XlsReader(convertPath(path));
		return reader.read();
	}

	/**
	 * <p>
	 * DataSetの内容から、Excelファイルを作成します。
	 * </p>
	 * <p>
	 * シート名にテーブル名、一行目にカラム名、二行目以降にデータ を書き込みます。
	 * </p>
	 * 
	 * <p>
	 * パスはCLASSPATHで指定されているディレクトリをルートとする 設定ファイルの絶対パスか、ファイル名のみを指定します。ファイル
	 * 名のみの場合、テストケースと同じパッケージにあるものとします。
	 * </p>
	 * 
	 * @param path
	 *            Excelファイルのパス
	 * @param dataSet
	 *            Excelファイルに書き込む内容のDataSet
	 * @see org.seasar.extension.dataset.impl.XlsWriter#write(DataSet)
	 */
	public void writeXls(String path, DataSet dataSet) {
		File dir = ResourceUtil.getBuildDir(getClass());
		File file = new File(dir, convertPath(path));
		DataWriter writer = new XlsWriter(FileOutputStreamUtil.create(file));
		writer.write(dataSet);
	}

	/**
	 * <p>
	 * DataSetをDBに書き込みます。
	 * </p>
	 * 
	 * @param dataSet
	 *            データベースに書き込む内容のDataSet
	 * @see org.seasar.extension.dataset.impl.SqlWriter#write(DataSet)
	 */
	public void writeDb(DataSet dataSet) {
		DataWriter writer = new SqlWriter(getDataSource());
		writer.write(dataSet);
	}

	/**
	 * <p>
	 * DBからレコードを読み込み、DataTableを作成します。
	 * </p>
	 * 
	 * @param table
	 *            読み込むテーブル名
	 * @return 読み込んだ内容から作成したDataTable
	 * @see org.seasar.extension.dataset.impl.SqlTableReader#read()
	 */
	public DataTable readDbByTable(String table) {
		return readDbByTable(table, null);
	}

	/**
	 * <p>
	 * DBからレコードを読み込み、DataTableを作成します。
	 * </p>
	 * <p>
	 * 読み込むレコードはconditionの条件を満たすレコードです。 conditionには" WHERE "より後ろをセットしてください。
	 * </p>
	 * 
	 * @param table
	 *            読み込むテーブル名
	 * @param condition
	 *            条件句(WHEREの後ろ)
	 * @return 読み込んだ内容から作成したDataTable
	 * @see org.seasar.extension.dataset.impl.SqlTableReader#read()
	 */
	public DataTable readDbByTable(String table, String condition) {
		SqlTableReader reader = new SqlTableReader(getDataSource());
		reader.setTable(table, condition);
		return reader.read();
	}

	/**
	 * <p>
	 * DBからSQL文の実行結果を取得し、DataTableを作成します。
	 * </p>
	 * <p>
	 * 作成したDataTableのテーブル名はtableNameになります。
	 * </p>
	 * 
	 * @param sql
	 *            実行するSQL文
	 * @param tableName
	 *            作成するDataTableのテーブル名
	 * @return 読み出した内容のDataTable
	 * @see org.seasar.extension.dataset.impl.SqlTableReader#read()
	 */
	public DataTable readDbBySql(String sql, String tableName) {
		SqlTableReader reader = new SqlTableReader(getDataSource());
		reader.setSql(sql, tableName);
		return reader.read();
	}

	/**
	 * <p>
	 * Excelファイルを読み込み、DBに書き込みます。
	 * </p>
	 * <p>
	 * シート名をテーブル名、一行目をカラム名、二行目以降をデータ として読み込みます。
	 * </p>
	 * 
	 * <p>
	 * パスはCLASSPATHで指定されているディレクトリをルートとする 設定ファイルの絶対パスか、ファイル名のみを指定します。ファイル名
	 * のみの場合、テストケースと同じパッケージにあるものとします。
	 * </p>
	 * 
	 * @param path
	 *            Excelファイルのパス
	 * @see org.seasar.extension.dataset.impl.XlsReader#read()
	 * @see org.seasar.extension.dataset.impl.SqlWriter#write(DataSet)
	 */
	public void readXlsWriteDb(String path) {
		writeDb(readXls(path));
	}

	/**
	 * <p>
	 * Excelファイルを読み込み、DBに書き込みます。
	 * </p>
	 * <p>
	 * シート名をテーブル名、一行目をカラム名、二行目以降をデータ として読み込みます。
	 * </p>
	 * 
	 * <p>
	 * Excelの内容とDBのレコードとで主キーが一致するものがあれば、 そのレコードを削除した後に書き込みます。
	 * </p>
	 * 
	 * <p>
	 * パスはCLASSPATHで指定されているディレクトリをルートとする 設定ファイルの絶対パスか、ファイル名のみを指定します。ファイル名
	 * のみの場合、テストケースと同じパッケージにあるものとします。
	 * </p>
	 * 
	 * @param path
	 *            Excelファイルのパス
	 * @see org.seasar.extension.dataset.impl.XlsReader#read()
	 * @see org.seasar.extension.dataset.impl.SqlWriter#write(DataSet)
	 */
	public void readXlsReplaceDb(String path) {
		DataSet dataSet = readXls(path);
		deleteDb(dataSet);
		writeDb(dataSet);
	}

	/**
	 * <p>
	 * Excelファイルを読み込み、DBに書き込みます。
	 * </p>
	 * <p>
	 * シート名をテーブル名、一行目をカラム名、二行目以降をデータ として読み込みます。
	 * </p>
	 * 
	 * <p>
	 * 対象となるテーブルのレコードを全て削除した後に書き込みます。
	 * </p>
	 * 
	 * <p>
	 * パスはCLASSPATHで指定されているディレクトリをルートとする 設定ファイルの絶対パスか、ファイル名のみを指定します。ファイル名
	 * のみの場合、テストケースと同じパッケージにあるものとします。
	 * </p>
	 * 
	 * @see org.seasar.extension.dataset.impl.XlsReader#read()
	 * @see org.seasar.extension.dataset.impl.SqlWriter#write(DataSet)
	 */
	public void readXlsAllReplaceDb(String path) {
		DataSet dataSet = readXls(path);
		for (int i = dataSet.getTableSize() - 1; i >= 0; --i) {
			deleteTable(dataSet.getTable(i).getTableName());
		}
		writeDb(dataSet);
	}

	/**
	 * <p>
	 * DataSetに対応するDBのレコードを読み込み、DataSetを作成します 。
	 * </p>
	 * 
	 * @param dataSet
	 *            対象DBに対応するDataSet
	 * @return 最新状態のDataSet
	 * @see org.seasar.extension.dataset.impl.SqlReloadReader#read()
	 */
	public DataSet reload(DataSet dataSet) {
		return new SqlReloadReader(getDataSource(), dataSet).read();
	}

	/**
	 * <p>
	 * DataTableに対応するDBのレコードを読み込み、DataTableを作成 します。
	 * </p>
	 * 
	 * @param table
	 *            対象DBに対応するDataTable
	 * @return 最新状態のDataTable
	 * @see org.seasar.extension.dataset.impl.SqlReloadTableReader#read()
	 */
	public DataTable reload(DataTable table) {
		return new SqlReloadTableReader(getDataSource(), table).read();
	}

	/**
	 * <p>
	 * DataSetに対応するDBのレコードを削除します。
	 * </p>
	 * 
	 * @param dataSet
	 *            対象DBに対応するDataSet
	 * @see org.seasar.extension.dataset.impl.SqlDeleteTableWriter#write(DataTable)
	 */
	public void deleteDb(DataSet dataSet) {
		SqlDeleteTableWriter writer = new SqlDeleteTableWriter(getDataSource());
		for (int i = dataSet.getTableSize() - 1; i >= 0; --i) {
			writer.write(dataSet.getTable(i));
		}
	}

	/**
	 * <p>
	 * DBから指定するテーブルの全レコードを削除します。
	 * </p>
	 * 
	 * @param tableName
	 *            削除対象のテーブル名
	 */
	public void deleteTable(String tableName) {
		UpdateHandler handler = new BasicUpdateHandler(getDataSource(),
				"DELETE FROM " + tableName);
		handler.execute(null);
	}

	/**
	 * <p>
	 * DataSet同士を比較します。
	 * </p>
	 * <p>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param expected
	 *            予測値
	 * @param actual
	 *            実際値
	 */
	public void assertEquals(DataSet expected, DataSet actual) {
		assertEquals(null, expected, actual);
	}

	/**
	 * <p>
	 * DataSet同士を比較します。
	 * </p>
	 * <p>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param message
	 *            assert失敗時のメッセージ
	 * @param expected
	 *            予測値
	 * @param actual
	 *            実際値
	 */
	public void assertEquals(String message, DataSet expected, DataSet actual) {
		message = message == null ? "" : message;
		assertEquals(message + ":TableSize", expected.getTableSize(), actual
				.getTableSize());
		for (int i = 0; i < expected.getTableSize(); ++i) {
			assertEquals(message, expected.getTable(i), actual.getTable(i));
		}
	}

	/**
	 * <p>
	 * DataTable同士を比較します。
	 * </p>
	 * <p>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param expected
	 *            予測値
	 * @param actual
	 *            実際値
	 */
	public void assertEquals(DataTable expected, DataTable actual) {
		assertEquals(null, expected, actual);
	}

	/**
	 * </p>
	 * DataTable同士を比較します。
	 * </p>
	 * <p>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param message
	 *            assert失敗時のメッセージ
	 * @param expected
	 *            予測値
	 * @param actual
	 *            実際値
	 */
	public void assertEquals(String message, DataTable expected,
			DataTable actual) {

		message = message == null ? "" : message;
		message = message + ":TableName=" + expected.getTableName();
		assertEquals(message + ":RowSize", expected.getRowSize(), actual
				.getRowSize());
		for (int i = 0; i < expected.getRowSize(); ++i) {
			DataRow expectedRow = expected.getRow(i);
			DataRow actualRow = actual.getRow(i);
			List errorMessages = new ArrayList();
			for (int j = 0; j < expected.getColumnSize(); ++j) {
				try {
					String columnName = expected.getColumnName(j);
					Object expectedValue = expectedRow.getValue(columnName);
					ColumnType ct = ColumnTypes.getColumnType(expectedValue);
					Object actualValue = actualRow.getValue(columnName);
					if (!ct.equals(expectedValue, actualValue)) {
						assertEquals(message + ":Row=" + i + ":columnName="
								+ columnName, expectedValue, actualValue);
					}
				} catch (AssertionFailedError e) {
					errorMessages.add(e.getMessage());
				}
			}
			if (!errorMessages.isEmpty()) {
				fail(message + errorMessages);
			}
		}
	}

	/**
	 * <p>
	 * オブジェクトをDataSetと比較します。
	 * </p>
	 * <p>
	 * オブジェクトは、Bean、Map、BeanのList、MapのListのいずれか でなければなりません。
	 * </p>
	 * 
	 * </p>
	 * Beanの場合はプロパティ名を、Mapの場合はキーをカラム名として 比較します。 <br>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param expected
	 *            予測値
	 * @param actual
	 *            実際値
	 */
	public void assertEquals(DataSet expected, Object actual) {
		assertEquals(null, expected, actual);
	}

	/**
	 * <p>
	 * オブジェクトをDataSetと比較します。
	 * </p>
	 * <p>
	 * オブジェクトは、Bean、Map、BeanのList、MapのListのいずれか でなければなりません。
	 * </p>
	 * 
	 * <p>
	 * Beanの場合はプロパティ名を、Mapの場合はキーをカラム名として 比較します。 <br>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param message
	 *            assert失敗時のメッセージ
	 * @param expected
	 *            予測値
	 * @param actual
	 *            実際値
	 */
	public void assertEquals(String message, DataSet expected, Object actual) {
		if (expected == null || actual == null) {
			Assert.assertEquals(message, expected, actual);
			return;
		}
		if (actual instanceof List) {
			List actualList = (List) actual;
			Assert.assertFalse(actualList.isEmpty());
			Object actualItem = actualList.get(0);
			if (actualItem instanceof Map) {
				assertMapListEquals(message, expected, actualList);
			} else {
				assertBeanListEquals(message, expected, actualList);
			}
		} else if (actual instanceof Object[]) {
			assertEquals(message, expected, Arrays.asList((Object[]) actual));
		} else {
			if (actual instanceof Map) {
				assertMapEquals(message, expected, (Map) actual);
			} else {
				assertBeanEquals(message, expected, actual);
			}
		}
	}

	/**
	 * <p>
	 * MapをDataSetと比較します。
	 * </p>
	 * <p>
	 * Mapのキーをカラム名として比較します。 <br>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param message
	 *            assert失敗時のメッセージ
	 * @param expected
	 *            予測値
	 * @param map
	 *            実際値
	 */
	protected void assertMapEquals(String message, DataSet expected, Map map) {

		MapReader reader = new MapReader(map);
		assertEquals(message, expected, reader.read());
	}

	/**
	 * <p>
	 * MapのListをDataSetと比較します。
	 * </p>
	 * <p>
	 * Mapのキーをカラム名として比較します。 <br>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param message
	 *            assert失敗時のメッセージ
	 * @param expected
	 *            予測値
	 * @param list
	 *            実際値
	 */
	protected void assertMapListEquals(String message, DataSet expected,
			List list) {

		MapListReader reader = new MapListReader(list);
		assertEquals(message, expected, reader.read());
	}

	/**
	 * <p>
	 * BeanをDataSetと比較します。
	 * </p>
	 * <p>
	 * Beanのプロパティ名をカラム名として比較します。 <br>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param message
	 *            assert失敗時のメッセージ
	 * @param expected
	 *            予測値
	 * @param bean
	 *            実際値
	 */
	protected void assertBeanEquals(String message, DataSet expected,
			Object bean) {

		BeanReader reader = new BeanReader(bean);
		assertEquals(message, expected, reader.read());
	}

	/**
	 * <p>
	 * BeanのListをDataSetと比較します。
	 * </p>
	 * <p>
	 * Beanのプロパティ名をカラム名として比較します。 <br>
	 * カラムの並び順は比較に影響しません。 <br>
	 * 数値は全てBigDecimalとして比較します。
	 * </p>
	 * 
	 * @param message
	 *            assert失敗時のメッセージ
	 * @param expected
	 *            予測値
	 * @param list
	 *            実際値
	 */
	protected void assertBeanListEquals(String message, DataSet expected,
			List list) {

		BeanListReader reader = new BeanListReader(list);
		assertEquals(message, expected, reader.read());
	}

	/**
	 * @see junit.framework.TestCase#runBare()
	 */
	public void runBare() throws Throwable {
		setUpContainer();
		setUp();
		try {
			setUpForEachTestMethod();
			try {
				container_.init();
				try {
					setupDataSource();
					try {
						setUpAfterContainerInit();
						bindFields();
						setUpAfterBindFields();
						try {
							runTestTx();
						} finally {
							tearDownBeforeUnbindFields();
							unbindFields();
						}
						tearDownBeforeContainerDestroy();
					} finally {
						tearDownDataSource();
					}
				} finally {
					tearDownContainer();
				}
			} finally {
				tearDownForEachTestMethod();
			}

		} finally {
			for (int i = 0; i < 5; ++i) {
				System.runFinalization();
				System.gc();
			}
			tearDown();
		}
	}

	/**
	 * <p>
	 * コンテナをセットアップするメソッドです。
	 * </p>
	 * <p>
	 * 必要な場合にオーバーライドしてください。
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void setUpContainer() throws Throwable {
		container_ = new S2ContainerImpl();
		servletContext_ = new MockServletContextImpl("s2jsf-example");
		request_ = servletContext_.createRequest("/hello.html");
		response_ = new MockHttpServletResponseImpl(request_);
		servletConfig_ = new MockServletConfigImpl();
		servletConfig_.setServletContext(servletContext_);
		servlet_ = new MockServlet();
		servlet_.init(servletConfig_);
		container_.setServletContext(servletContext_);
		container_.setRequest(request_);
		container_.setResponse(response_);
		SingletonS2ContainerFactory.setContainer(container_);
	}

	protected void tearDownContainer() throws Throwable {
		container_.destroy();
		SingletonS2ContainerFactory.setContainer(null);
		container_ = null;
		servletContext_ = null;
		request_ = null;
		response_ = null;
		servletConfig_ = null;
		servlet_ = null;
	}

	/**
	 * <p>
	 * コンテナ初期化後に実行されるセットアップメソッドです。
	 * </p>
	 * <p>
	 * 必要な場合にオーバーライドしてください。
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void setUpAfterContainerInit() throws Throwable {
	}

	/**
	 * <p>
	 * フィールドが設定された後に実行されるセットアップメソッドです。
	 * </p>
	 * <p>
	 * 必要な場合にオーバーライドしてください。
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void setUpAfterBindFields() throws Throwable {
	}

	/**
	 * <p>
	 * フィールドの設定が解除される前に実行される処理終了メソッドです。
	 * </p>
	 * <p>
	 * 必要な場合にオーバーライドしてください。
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void tearDownBeforeUnbindFields() throws Throwable {
	}

	/**
	 * <p>
	 * setup() 後に実行されるテストメソッドごとのセットアップ メソッドです。
	 * </p>
	 * <p>
	 * testXxx() というメソッドの場合、setUpXxx() という名前で セットアップメソッドを作成しておくと、自動的に実行されます。
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void setUpForEachTestMethod() throws Throwable {
		String targetName = getTargetName();
		if (targetName.length() > 0) {
			invoke("setUp" + targetName);
		}
	}

	/**
	 * <p>
	 * コンテナ終了処理前に実行される終了処理メソッドです。
	 * </p>
	 * <p>
	 * 必要な場合にオーバーライドしてください。
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void tearDownBeforeContainerDestroy() throws Throwable {
	}

	/**
	 * <p>
	 * tearDown() 前に実行されるテストメソッドごとの終了処理メソッド です。
	 * </p>
	 * <p>
	 * testXxx() というメソッドの場合、tearDownXxx() という名前で 終了処理メソッドを作成しておくと、自動的に実行されます。
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void tearDownForEachTestMethod() throws Throwable {
		String targetName = getTargetName();
		if (targetName.length() > 0) {
			invoke("tearDown" + getTargetName());
		}
	}

	protected Servlet getServlet() {
		return servlet_;
	}

	protected void setServlet(Servlet servlet) {
		servlet_ = servlet;
	}

	protected MockServletConfig getServletConfig() {
		return servletConfig_;
	}

	protected void setServletConfig(MockServletConfig servletConfig) {
		servletConfig_ = servletConfig;
	}

	protected MockServletContext getServletContext() {
		return servletContext_;
	}

	protected void setServletContext(MockServletContext servletContext) {
		servletContext_ = servletContext;
	}

	protected MockHttpServletRequest getRequest() {
		return request_;
	}

	protected void setRequest(MockHttpServletRequest request) {
		request_ = request;
	}

	protected MockHttpServletResponse getResponse() {
		return response_;
	}

	protected void setResponse(MockHttpServletResponse response) {
		response_ = response;
	}

	private String getTargetName() {
		return getName().substring(4);
	}

	private void invoke(String methodName) throws Throwable {
		try {
			Method method = ClassUtil.getMethod(getClass(), methodName, null);
			MethodUtil.invoke(method, this, null);
		} catch (NoSuchMethodRuntimeException ignore) {
		}
	}

	private void bindFields() throws Throwable {
		bindedFields_ = new ArrayList();
		for (Class clazz = getClass(); clazz != S2TestCase.class
				&& clazz != null; clazz = clazz.getSuperclass()) {

			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; ++i) {
				bindField(fields[i]);
			}
		}
	}

	private void bindField(Field field) {
		if (isAutoBindable(field)) {
			field.setAccessible(true);
			if (FieldUtil.get(field, this) != null) {
				return;
			}
			String name = normalizeName(field.getName());
			Object component = null;
			if (getContainer().hasComponentDef(name)) {
				Class componentClass = getComponentDef(name)
						.getComponentClass();
				if (componentClass == null) {
					component = getComponent(name);
					if (component != null) {
						componentClass = component.getClass();
					}
				}
				if (componentClass != null
						&& field.getType().isAssignableFrom(componentClass)) {
					if (component == null) {
						component = getComponent(name);
					}
				} else {
					component = null;
				}
			}
			if (component == null
					&& getContainer().hasComponentDef(field.getType())) {
				component = getComponent(field.getType());
			}
			if (component != null) {
				FieldUtil.set(field, this, component);
				bindedFields_.add(field);
			}
		}
	}

	private String normalizeName(String name) {
		return StringUtil.replace(name, "_", "");
	}

	private boolean isAutoBindable(Field field) {
		int modifiers = field.getModifiers();
		return !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)
				&& !field.getType().isPrimitive();
	}

	private void unbindFields() {
		for (int i = 0; i < bindedFields_.size(); ++i) {
			Field field = (Field) bindedFields_.get(i);
			try {
				field.set(this, null);
			} catch (IllegalArgumentException e) {
				System.err.println(e);
			} catch (IllegalAccessException e) {
				System.err.println(e);
			}
		}
	}

	private void runTestTx() throws Throwable {
		TransactionManager tm = null;
		if (needTransaction()) {
			try {
				tm = (TransactionManager) getComponent(TransactionManager.class);
				tm.begin();
			} catch (Throwable t) {
				System.err.println(t);
			}
		}
		try {
			runTest();
		} finally {
			if (tm != null) {
				tm.rollback();
			}
		}
	}

	private boolean needTransaction() {
		return getName().endsWith("Tx");
	}

	private void setupDataSource() {
		try {
			if (container_.hasComponentDef(DATASOURCE_NAME)) {
				dataSource_ = (DataSource) container_
						.getComponent(DATASOURCE_NAME);
			} else if (container_.hasComponentDef(DataSource.class)) {
				dataSource_ = (DataSource) container_
						.getComponent(DataSource.class);
			}
		} catch (Throwable t) {
			System.err.println(t);
		}
	}

	private void tearDownDataSource() {
		dbMetaData_ = null;
		if (connection_ != null) {
			ConnectionUtil.close(connection_);
			connection_ = null;
		}
		dataSource_ = null;
	}
}