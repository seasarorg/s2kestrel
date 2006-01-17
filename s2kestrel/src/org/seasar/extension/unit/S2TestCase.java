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
 * JUnit��TestCase���g�������AS2���g�����e�X�g���y���������Ȃ����߂� �N���X�ł��B
 * </p>
 * 
 * <p>
 * �e�e�X�g���\�b�h�����s����ۂɂ́A�ȉ��̏��Ԃŏ����������Ȃ��܂��B �e�X�g���\�b�h�����֌W���鏈��������܂��̂ŁA�����ł�testXxx()�Ƃ���
 * �e�X�g���\�b�h�����s����Ɖ��肵�܂��B
 * </p>
 * 
 * <ol>
 * <li>S2Container�̃C���X�^���X���쐬���܂��B</li>
 * <li>setUp()�����s���܂��B</li>
 * <li>setUpXxx�Ƃ������O�̃��\�b�h������Ύ��s���܂��B</li>
 * <li>S2Container�����������܂��B</li>
 * <li>"j2ee.dataSource"�Ƃ������O�̃f�[�^�\�[�X���R���e�i����擾���܂��B</li>
 * <li>setUpAfterContainerInit()�����s���܂��B</li>
 * <li>�t�B�[���h��DI�������Ȃ��܂��B</li>
 * <li>testXxx()�����s���܂��B</li>
 * <li>tearDownBeforeContainerDestroy()�����s���܂��B</li>
 * <li>�f�[�^�\�[�X���J�����܂��B</li>
 * <li>S2Container�̏I�������������Ȃ��܂��B</li>
 * <li>tearDownXxx()�Ƃ������O�̃��\�b�h������Ύ��s���܂��B</li>
 * <li>tearDown()�����s���܂��B</li>
 * </ol>
 * 
 * <p>
 * setUp()���A�܂���setUpXxx()����include("j2ee.dicon")���s���A�e�X�g
 * ���\�b�h���̍Ō��"Tx"�ƕt����ƁA�e�X�g���\�b�h�̎��s�O�Ƀg�����U�N �V�������J�n���A�e�X�g���\�b�h�̏I����Ƀg�����U�N�V���������[���o�b�N
 * ���܂��BDB�̃��R�[�h��ύX����悤�ȃe�X�g�̏ꍇ�ɂ��̋@�\�𗘗p����ƁA �N���[���A�b�v�������s�v�ɂȂ�܂��B
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
	 * �V���A���C�[�[�V������L���ɂ��邽�߂̃f�t�H���g�R���X�g���N�^�ł��B
	 * </p>
	 * <p>
	 * <code>TestCase#setName()</code>
	 * ���\�b�h���Ăяo����邱�ƂȂ����̃R���X�g���N�^���g���邱�Ƃ͈Ӑ}���Ă��܂���B
	 * </p>
	 *  
	 */
	public S2TestCase() {
	}

	/**
	 * <p>
	 * �w�肳�ꂽ���O�Ńe�X�g�P�[�X���쐬���܂��B
	 * </p>
	 * 
	 * @param name
	 *            �e�X�g�P�[�X�̖��O
	 */
	public S2TestCase(String name) {
		super(name);
	}

	/**
	 * <p>
	 * �e�X�g�P�[�X���ۗL����S2�R���e�i���擾���܂��B
	 * </p>
	 * 
	 * @return S2�R���e�i
	 */
	public S2Container getContainer() {
		return container_;
	}

	/**
	 * <p>
	 * �R���|�[�l���g�����w�肵��S2�R���e�i����R���|�[�l���g���擾 ���܂��B
	 * </p>
	 * <p>
	 * �w�肵�����O�����R���|�[�l���g���o�^����Ă��Ȃ��ꍇ��null�� �Ԃ��܂��B
	 * </p>
	 * 
	 * @param componentName
	 *            �擾����R���|�[�l���g��
	 * @return �w�肵�����O�����R���|�[�l���g
	 * @see org.seasar.framework.container.S2Container#getComponent(java.lang.Object)
	 */
	public Object getComponent(String componentName) {
		return container_.getComponent(componentName);
	}

	/**
	 * <p>
	 * �N���X���w�肵��S2�R���e�i����R���|�[�l���g���擾���܂��B
	 * </p>
	 * <p>
	 * �C���^�[�t�F�[�X���w�肵���ꍇ�͂��̃C���^�[�t�F�[�X���������� ����R���|�[�l���g�A�N���X���w�肵���ꍇ�͂��̃N���X�����̃N���X��
	 * �e�Ɏ��q�N���X�̃R���|�[�l���g���擾���܂��B
	 * </p>
	 * <p>
	 * �w�肵���N���X�̃R���|�[�l���g���o�^����Ă��Ȃ��ꍇ��null�� �Ԃ��܂��B
	 * </p>
	 * 
	 * @param componentClass
	 *            �擾����N���X
	 * @return �w�肵���N���X�̃R���|�[�l���g
	 * @see org.seasar.framework.container.S2Container#getComponent(java.lang.Object)
	 */
	public Object getComponent(Class componentClass) {
		return container_.getComponent(componentClass);
	}

	/**
	 * <p>
	 * �R���|�[�l���g�����w�肵��S2�R���e�i����R���|�[�l���g��`�� �擾���܂��B
	 * </p>
	 * 
	 * @param componentName
	 *            �擾����R���|�[�l���g��
	 * @return �w�肵�����O�����R���|�[�l���g��`
	 * @see org.seasar.framework.container.S2Container#getComponentDef(java.lang.Object)
	 */
	public ComponentDef getComponentDef(String componentName) {
		return container_.getComponentDef(componentName);
	}

	/**
	 * <p>
	 * �N���X���w�肵��S2�R���e�i����R���|�[�l���g��`���擾���܂��B
	 * </p>
	 * 
	 * @param componentClass
	 *            �擾����N���X
	 * @return �w�肵���N���X�̃R���|�[�l���g��`
	 * @see org.seasar.framework.container.S2Container#getComponentDef(java.lang.Object)
	 */
	public ComponentDef getComponentDef(Class componentClass) {
		return container_.getComponentDef(componentClass);
	}

	/**
	 * <p>
	 * �N���X��S2�R���e�i�ɃR���|�[�l���g��`�Ƃ��ēo�^���܂��B
	 * </p>
	 * 
	 * @param componentClass
	 *            �R���|�[�l���g�̃N���X
	 * @see org.seasar.framework.container.S2Container#register(java.lang.Class)
	 */
	public void register(Class componentClass) {
		container_.register(componentClass);
	}

	/**
	 * <p>
	 * �N���X��S2�R���e�i�ɖ��O�t���R���|�[�l���g��`�Ƃ��ēo�^ ���܂��B
	 * </p>
	 * 
	 * @param componentClass
	 *            �R���|�[�l���g�̃N���X
	 * @param componentName
	 *            �R���|�[�l���g�̖��O
	 * @see org.seasar.framework.container.S2Container#register(java.lang.Class,
	 *      java.lang.String)
	 */
	public void register(Class componentClass, String componentName) {
		container_.register(componentClass, componentName);
	}

	/**
	 * <p>
	 * �I�u�W�F�N�g��S2�R���e�i�ɃR���|�[�l���g�Ƃ��ēo�^���܂��B
	 * </p>
	 * <p>
	 * �L�[�̓I�u�W�F�N�g�̃N���X�ɂȂ�܂��B
	 * </p>
	 * 
	 * @param component
	 *            �R���|�[�l���g�Ƃ��ēo�^����I�u�W�F�N�g
	 * @see org.seasar.framework.container.S2Container#register(java.lang.Object)
	 */
	public void register(Object component) {
		container_.register(component);
	}

	/**
	 * <p>
	 * �I�u�W�F�N�g��S2�R���e�i�ɖ��O�t���R���|�[�l���g�Ƃ��ēo�^ ���܂��B
	 * </p>
	 * 
	 * @param component
	 *            �R���|�[�l���g�Ƃ��ēo�^����I�u�W�F�N�g
	 * @param componentName
	 *            �R���|�[�l���g��
	 * @see org.seasar.framework.container.S2Container#register(java.lang.Object,
	 *      java.lang.String)
	 */
	public void register(Object component, String componentName) {
		container_.register(component, componentName);
	}

	/**
	 * <p>
	 * S2�R���e�i�ɃR���|�[�l���g��`��o�^���܂��B
	 * </p>
	 * 
	 * @param componentDef
	 *            �o�^����R���|�[�l���g��`
	 * @see org.seasar.framework.container.S2Container#register(org.seasar.framework.container.ComponentDef)
	 */
	public void register(ComponentDef componentDef) {
		container_.register(componentDef);
	}

	/**
	 * <p>
	 * �ݒ�t�@�C���̃p�X���w�肵�Ďq�R���e�i��include���܂��B
	 * </p>
	 * <p>
	 * �p�X��CLASSPATH�Ŏw�肳��Ă���f�B���N�g�������[�g�Ƃ��� �ݒ�t�@�C���̐�΃p�X���A�t�@�C�����݂̂��w�肵�܂��B
	 * </p>
	 * <p>
	 * �t�@�C�����݂̂̏ꍇ�A�e�X�g�P�[�X�Ɠ����p�b�P�[�W�ɂ������ �Ƃ��܂��B
	 * </p>
	 * 
	 * @param path
	 *            �q�R���e�i�̐ݒ�t�@�C���̃p�X
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
	 * �f�[�^�\�[�X���擾���܂��B
	 * </p>
	 * 
	 * @return �f�[�^�\�[�X
	 */
	public DataSource getDataSource() {
		if (dataSource_ == null) {
			throw new EmptyRuntimeException("dataSource");
		}
		return dataSource_;
	}

	/**
	 * <p>
	 * �f�[�^�\�[�X����R�l�N�V�������擾���܂��B
	 * </p>
	 * 
	 * @return �f�[�^�\�[�X����擾�����R�l�N�V����
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
	 * �R�l�N�V�����̃f�[�^�x�[�X���^�f�[�^���擾���܂��B
	 * </p>
	 * 
	 * @return �R�l�N�V�����̃f�[�^�x�[�X���^�f�[�^
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
	 * Excel�t�@�C����ǂ݁ADataSet���쐬���܂��B
	 * </p>
	 * <p>
	 * �V�[�g�����e�[�u�����A��s�ڂ��J�������A��s�ڈȍ~���f�[�^ �Ƃ��ēǂݍ��݂܂��B
	 * </p>
	 * 
	 * <p>
	 * �p�X��CLASSPATH�Ŏw�肳��Ă���f�B���N�g�������[�g�Ƃ��� �ݒ�t�@�C���̐�΃p�X���A�t�@�C�����݂̂��w�肵�܂��B�t�@�C����
	 * �݂̂̏ꍇ�A�e�X�g�P�[�X�Ɠ����p�b�P�[�W�ɂ�����̂Ƃ��܂��B
	 * </p>
	 * 
	 * @param path
	 *            Excel�t�@�C���̃p�X
	 * @return Excel�t�@�C���̓��e����쐬����DataSet
	 * @see org.seasar.extension.dataset.impl.XlsReader#read()
	 */
	public DataSet readXls(String path) {
		DataReader reader = new XlsReader(convertPath(path));
		return reader.read();
	}

	/**
	 * <p>
	 * DataSet�̓��e����AExcel�t�@�C�����쐬���܂��B
	 * </p>
	 * <p>
	 * �V�[�g���Ƀe�[�u�����A��s�ڂɃJ�������A��s�ڈȍ~�Ƀf�[�^ ���������݂܂��B
	 * </p>
	 * 
	 * <p>
	 * �p�X��CLASSPATH�Ŏw�肳��Ă���f�B���N�g�������[�g�Ƃ��� �ݒ�t�@�C���̐�΃p�X���A�t�@�C�����݂̂��w�肵�܂��B�t�@�C��
	 * ���݂̂̏ꍇ�A�e�X�g�P�[�X�Ɠ����p�b�P�[�W�ɂ�����̂Ƃ��܂��B
	 * </p>
	 * 
	 * @param path
	 *            Excel�t�@�C���̃p�X
	 * @param dataSet
	 *            Excel�t�@�C���ɏ������ޓ��e��DataSet
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
	 * DataSet��DB�ɏ������݂܂��B
	 * </p>
	 * 
	 * @param dataSet
	 *            �f�[�^�x�[�X�ɏ������ޓ��e��DataSet
	 * @see org.seasar.extension.dataset.impl.SqlWriter#write(DataSet)
	 */
	public void writeDb(DataSet dataSet) {
		DataWriter writer = new SqlWriter(getDataSource());
		writer.write(dataSet);
	}

	/**
	 * <p>
	 * DB���烌�R�[�h��ǂݍ��݁ADataTable���쐬���܂��B
	 * </p>
	 * 
	 * @param table
	 *            �ǂݍ��ރe�[�u����
	 * @return �ǂݍ��񂾓��e����쐬����DataTable
	 * @see org.seasar.extension.dataset.impl.SqlTableReader#read()
	 */
	public DataTable readDbByTable(String table) {
		return readDbByTable(table, null);
	}

	/**
	 * <p>
	 * DB���烌�R�[�h��ǂݍ��݁ADataTable���쐬���܂��B
	 * </p>
	 * <p>
	 * �ǂݍ��ރ��R�[�h��condition�̏����𖞂������R�[�h�ł��B condition�ɂ�" WHERE "�������Z�b�g���Ă��������B
	 * </p>
	 * 
	 * @param table
	 *            �ǂݍ��ރe�[�u����
	 * @param condition
	 *            ������(WHERE�̌��)
	 * @return �ǂݍ��񂾓��e����쐬����DataTable
	 * @see org.seasar.extension.dataset.impl.SqlTableReader#read()
	 */
	public DataTable readDbByTable(String table, String condition) {
		SqlTableReader reader = new SqlTableReader(getDataSource());
		reader.setTable(table, condition);
		return reader.read();
	}

	/**
	 * <p>
	 * DB����SQL���̎��s���ʂ��擾���ADataTable���쐬���܂��B
	 * </p>
	 * <p>
	 * �쐬����DataTable�̃e�[�u������tableName�ɂȂ�܂��B
	 * </p>
	 * 
	 * @param sql
	 *            ���s����SQL��
	 * @param tableName
	 *            �쐬����DataTable�̃e�[�u����
	 * @return �ǂݏo�������e��DataTable
	 * @see org.seasar.extension.dataset.impl.SqlTableReader#read()
	 */
	public DataTable readDbBySql(String sql, String tableName) {
		SqlTableReader reader = new SqlTableReader(getDataSource());
		reader.setSql(sql, tableName);
		return reader.read();
	}

	/**
	 * <p>
	 * Excel�t�@�C����ǂݍ��݁ADB�ɏ������݂܂��B
	 * </p>
	 * <p>
	 * �V�[�g�����e�[�u�����A��s�ڂ��J�������A��s�ڈȍ~���f�[�^ �Ƃ��ēǂݍ��݂܂��B
	 * </p>
	 * 
	 * <p>
	 * �p�X��CLASSPATH�Ŏw�肳��Ă���f�B���N�g�������[�g�Ƃ��� �ݒ�t�@�C���̐�΃p�X���A�t�@�C�����݂̂��w�肵�܂��B�t�@�C����
	 * �݂̂̏ꍇ�A�e�X�g�P�[�X�Ɠ����p�b�P�[�W�ɂ�����̂Ƃ��܂��B
	 * </p>
	 * 
	 * @param path
	 *            Excel�t�@�C���̃p�X
	 * @see org.seasar.extension.dataset.impl.XlsReader#read()
	 * @see org.seasar.extension.dataset.impl.SqlWriter#write(DataSet)
	 */
	public void readXlsWriteDb(String path) {
		writeDb(readXls(path));
	}

	/**
	 * <p>
	 * Excel�t�@�C����ǂݍ��݁ADB�ɏ������݂܂��B
	 * </p>
	 * <p>
	 * �V�[�g�����e�[�u�����A��s�ڂ��J�������A��s�ڈȍ~���f�[�^ �Ƃ��ēǂݍ��݂܂��B
	 * </p>
	 * 
	 * <p>
	 * Excel�̓��e��DB�̃��R�[�h�ƂŎ�L�[����v������̂�����΁A ���̃��R�[�h���폜������ɏ������݂܂��B
	 * </p>
	 * 
	 * <p>
	 * �p�X��CLASSPATH�Ŏw�肳��Ă���f�B���N�g�������[�g�Ƃ��� �ݒ�t�@�C���̐�΃p�X���A�t�@�C�����݂̂��w�肵�܂��B�t�@�C����
	 * �݂̂̏ꍇ�A�e�X�g�P�[�X�Ɠ����p�b�P�[�W�ɂ�����̂Ƃ��܂��B
	 * </p>
	 * 
	 * @param path
	 *            Excel�t�@�C���̃p�X
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
	 * Excel�t�@�C����ǂݍ��݁ADB�ɏ������݂܂��B
	 * </p>
	 * <p>
	 * �V�[�g�����e�[�u�����A��s�ڂ��J�������A��s�ڈȍ~���f�[�^ �Ƃ��ēǂݍ��݂܂��B
	 * </p>
	 * 
	 * <p>
	 * �ΏۂƂȂ�e�[�u���̃��R�[�h��S�č폜������ɏ������݂܂��B
	 * </p>
	 * 
	 * <p>
	 * �p�X��CLASSPATH�Ŏw�肳��Ă���f�B���N�g�������[�g�Ƃ��� �ݒ�t�@�C���̐�΃p�X���A�t�@�C�����݂̂��w�肵�܂��B�t�@�C����
	 * �݂̂̏ꍇ�A�e�X�g�P�[�X�Ɠ����p�b�P�[�W�ɂ�����̂Ƃ��܂��B
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
	 * DataSet�ɑΉ�����DB�̃��R�[�h��ǂݍ��݁ADataSet���쐬���܂� �B
	 * </p>
	 * 
	 * @param dataSet
	 *            �Ώ�DB�ɑΉ�����DataSet
	 * @return �ŐV��Ԃ�DataSet
	 * @see org.seasar.extension.dataset.impl.SqlReloadReader#read()
	 */
	public DataSet reload(DataSet dataSet) {
		return new SqlReloadReader(getDataSource(), dataSet).read();
	}

	/**
	 * <p>
	 * DataTable�ɑΉ�����DB�̃��R�[�h��ǂݍ��݁ADataTable���쐬 ���܂��B
	 * </p>
	 * 
	 * @param table
	 *            �Ώ�DB�ɑΉ�����DataTable
	 * @return �ŐV��Ԃ�DataTable
	 * @see org.seasar.extension.dataset.impl.SqlReloadTableReader#read()
	 */
	public DataTable reload(DataTable table) {
		return new SqlReloadTableReader(getDataSource(), table).read();
	}

	/**
	 * <p>
	 * DataSet�ɑΉ�����DB�̃��R�[�h���폜���܂��B
	 * </p>
	 * 
	 * @param dataSet
	 *            �Ώ�DB�ɑΉ�����DataSet
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
	 * DB����w�肷��e�[�u���̑S���R�[�h���폜���܂��B
	 * </p>
	 * 
	 * @param tableName
	 *            �폜�Ώۂ̃e�[�u����
	 */
	public void deleteTable(String tableName) {
		UpdateHandler handler = new BasicUpdateHandler(getDataSource(),
				"DELETE FROM " + tableName);
		handler.execute(null);
	}

	/**
	 * <p>
	 * DataSet���m���r���܂��B
	 * </p>
	 * <p>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param expected
	 *            �\���l
	 * @param actual
	 *            ���ےl
	 */
	public void assertEquals(DataSet expected, DataSet actual) {
		assertEquals(null, expected, actual);
	}

	/**
	 * <p>
	 * DataSet���m���r���܂��B
	 * </p>
	 * <p>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param message
	 *            assert���s���̃��b�Z�[�W
	 * @param expected
	 *            �\���l
	 * @param actual
	 *            ���ےl
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
	 * DataTable���m���r���܂��B
	 * </p>
	 * <p>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param expected
	 *            �\���l
	 * @param actual
	 *            ���ےl
	 */
	public void assertEquals(DataTable expected, DataTable actual) {
		assertEquals(null, expected, actual);
	}

	/**
	 * </p>
	 * DataTable���m���r���܂��B
	 * </p>
	 * <p>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param message
	 *            assert���s���̃��b�Z�[�W
	 * @param expected
	 *            �\���l
	 * @param actual
	 *            ���ےl
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
	 * �I�u�W�F�N�g��DataSet�Ɣ�r���܂��B
	 * </p>
	 * <p>
	 * �I�u�W�F�N�g�́ABean�AMap�ABean��List�AMap��List�̂����ꂩ �łȂ���΂Ȃ�܂���B
	 * </p>
	 * 
	 * </p>
	 * Bean�̏ꍇ�̓v���p�e�B�����AMap�̏ꍇ�̓L�[���J�������Ƃ��� ��r���܂��B <br>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param expected
	 *            �\���l
	 * @param actual
	 *            ���ےl
	 */
	public void assertEquals(DataSet expected, Object actual) {
		assertEquals(null, expected, actual);
	}

	/**
	 * <p>
	 * �I�u�W�F�N�g��DataSet�Ɣ�r���܂��B
	 * </p>
	 * <p>
	 * �I�u�W�F�N�g�́ABean�AMap�ABean��List�AMap��List�̂����ꂩ �łȂ���΂Ȃ�܂���B
	 * </p>
	 * 
	 * <p>
	 * Bean�̏ꍇ�̓v���p�e�B�����AMap�̏ꍇ�̓L�[���J�������Ƃ��� ��r���܂��B <br>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param message
	 *            assert���s���̃��b�Z�[�W
	 * @param expected
	 *            �\���l
	 * @param actual
	 *            ���ےl
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
	 * Map��DataSet�Ɣ�r���܂��B
	 * </p>
	 * <p>
	 * Map�̃L�[���J�������Ƃ��Ĕ�r���܂��B <br>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param message
	 *            assert���s���̃��b�Z�[�W
	 * @param expected
	 *            �\���l
	 * @param map
	 *            ���ےl
	 */
	protected void assertMapEquals(String message, DataSet expected, Map map) {

		MapReader reader = new MapReader(map);
		assertEquals(message, expected, reader.read());
	}

	/**
	 * <p>
	 * Map��List��DataSet�Ɣ�r���܂��B
	 * </p>
	 * <p>
	 * Map�̃L�[���J�������Ƃ��Ĕ�r���܂��B <br>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param message
	 *            assert���s���̃��b�Z�[�W
	 * @param expected
	 *            �\���l
	 * @param list
	 *            ���ےl
	 */
	protected void assertMapListEquals(String message, DataSet expected,
			List list) {

		MapListReader reader = new MapListReader(list);
		assertEquals(message, expected, reader.read());
	}

	/**
	 * <p>
	 * Bean��DataSet�Ɣ�r���܂��B
	 * </p>
	 * <p>
	 * Bean�̃v���p�e�B�����J�������Ƃ��Ĕ�r���܂��B <br>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param message
	 *            assert���s���̃��b�Z�[�W
	 * @param expected
	 *            �\���l
	 * @param bean
	 *            ���ےl
	 */
	protected void assertBeanEquals(String message, DataSet expected,
			Object bean) {

		BeanReader reader = new BeanReader(bean);
		assertEquals(message, expected, reader.read());
	}

	/**
	 * <p>
	 * Bean��List��DataSet�Ɣ�r���܂��B
	 * </p>
	 * <p>
	 * Bean�̃v���p�e�B�����J�������Ƃ��Ĕ�r���܂��B <br>
	 * �J�����̕��я��͔�r�ɉe�����܂���B <br>
	 * ���l�͑S��BigDecimal�Ƃ��Ĕ�r���܂��B
	 * </p>
	 * 
	 * @param message
	 *            assert���s���̃��b�Z�[�W
	 * @param expected
	 *            �\���l
	 * @param list
	 *            ���ےl
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
	 * �R���e�i���Z�b�g�A�b�v���郁�\�b�h�ł��B
	 * </p>
	 * <p>
	 * �K�v�ȏꍇ�ɃI�[�o�[���C�h���Ă��������B
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
	 * �R���e�i��������Ɏ��s�����Z�b�g�A�b�v���\�b�h�ł��B
	 * </p>
	 * <p>
	 * �K�v�ȏꍇ�ɃI�[�o�[���C�h���Ă��������B
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void setUpAfterContainerInit() throws Throwable {
	}

	/**
	 * <p>
	 * �t�B�[���h���ݒ肳�ꂽ��Ɏ��s�����Z�b�g�A�b�v���\�b�h�ł��B
	 * </p>
	 * <p>
	 * �K�v�ȏꍇ�ɃI�[�o�[���C�h���Ă��������B
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void setUpAfterBindFields() throws Throwable {
	}

	/**
	 * <p>
	 * �t�B�[���h�̐ݒ肪���������O�Ɏ��s����鏈���I�����\�b�h�ł��B
	 * </p>
	 * <p>
	 * �K�v�ȏꍇ�ɃI�[�o�[���C�h���Ă��������B
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void tearDownBeforeUnbindFields() throws Throwable {
	}

	/**
	 * <p>
	 * setup() ��Ɏ��s�����e�X�g���\�b�h���Ƃ̃Z�b�g�A�b�v ���\�b�h�ł��B
	 * </p>
	 * <p>
	 * testXxx() �Ƃ������\�b�h�̏ꍇ�AsetUpXxx() �Ƃ������O�� �Z�b�g�A�b�v���\�b�h���쐬���Ă����ƁA�����I�Ɏ��s����܂��B
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
	 * �R���e�i�I�������O�Ɏ��s�����I���������\�b�h�ł��B
	 * </p>
	 * <p>
	 * �K�v�ȏꍇ�ɃI�[�o�[���C�h���Ă��������B
	 * </p>
	 * 
	 * @throws Throwable
	 */
	protected void tearDownBeforeContainerDestroy() throws Throwable {
	}

	/**
	 * <p>
	 * tearDown() �O�Ɏ��s�����e�X�g���\�b�h���Ƃ̏I���������\�b�h �ł��B
	 * </p>
	 * <p>
	 * testXxx() �Ƃ������\�b�h�̏ꍇ�AtearDownXxx() �Ƃ������O�� �I���������\�b�h���쐬���Ă����ƁA�����I�Ɏ��s����܂��B
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