package org.seasar.extension.dataset.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.seasar.extension.dataset.DataReader;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataSetConstants;
import org.seasar.extension.dataset.DataTable;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.Base64Util;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.TimestampConversionUtil;
import org.seasar.kestrel.lang.BooleanUtil;

/**
 * @author higa
 * @author Satoshi Kimura
 *
 */
public class XlsReader implements DataReader, DataSetConstants {

	private DataSet dataSet_;
	private HSSFWorkbook workbook_;
	private HSSFDataFormat dataFormat_;

	public XlsReader(String path) {
		this(ResourceUtil.getResourceAsStream(path));
	}

	public XlsReader(InputStream in) {
		try {
			workbook_ = new HSSFWorkbook(in);
		} catch (IOException ex) {
			throw new IORuntimeException(ex);
		}
		dataFormat_ = workbook_.createDataFormat();
		dataSet_ = new DataSetImpl();
		for (int i = 0; i < workbook_.getNumberOfSheets(); ++i) {
			createTable(workbook_.getSheetName(i), workbook_.getSheetAt(i));
		}
	}

	/**
	 * @see org.seasar.extension.dataset.DataReader#read()
	 */
	public DataSet read() {
		return dataSet_;
	}

	private DataTable createTable(String sheetName, HSSFSheet sheet) {
		DataTable table = dataSet_.addTable(sheetName);
		int rowCount = sheet.getLastRowNum();
		if (rowCount >= 0) {
			setupColumns(table, sheet.getRow(0));
			if (rowCount > 0) {
				setupRows(table, sheet);
			}
		}
		return table;
	}

	private void setupColumns(DataTable table, HSSFRow row) {
		for (int i = 0;; ++i) {
			HSSFCell nameCell = row.getCell((short) i);
			if (nameCell == null) {
				break;
			}
			String nameCellValue = nameCell.getStringCellValue().trim();
		    if (nameCellValue.length() == 0) {
		      break;
		    }
		    table.addColumn(nameCellValue);
		}
	}

	private void setupRows(DataTable table, HSSFSheet sheet) {
		for (int i = 1;; ++i) {
			HSSFRow row = sheet.getRow((short) i);
			if (row == null) {
				break;
			}
			setupRow(table, row);
		}
	}

	private void setupRow(DataTable table, HSSFRow row) {
		DataRow dataRow = table.addRow();
		for (int i = 0; i < table.getColumnSize(); ++i) {
			HSSFCell cell = row.getCell((short) i);
			Object value = getValue(cell);
			dataRow.setValue(i, value);
		}
	}

	public boolean isCellBase64Formatted(HSSFCell cell) {
		HSSFCellStyle cs = cell.getCellStyle();
		short dfNum = cs.getDataFormat();
		return BASE64_FORMAT.equals(dataFormat_.getFormat(dfNum));
	}

	public boolean isCellDateFormatted(HSSFCell cell) {
		HSSFCellStyle cs = cell.getCellStyle();
		short dfNum = cs.getDataFormat();
		String format = dataFormat_.getFormat(dfNum);
		if (StringUtil.isEmpty(format)) {
			return false;
		}
		if (format.indexOf('/') > 0
			|| format.indexOf('y') > 0
			|| format.indexOf('m') > 0
			|| format.indexOf('d') > 0) {
			return true;
		}
		return false;
	}

	public Object getValue(HSSFCell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC :
				if (isCellDateFormatted(cell)) {
					return TimestampConversionUtil.toTimestamp(
						cell.getDateCellValue());
				}
				return new BigDecimal(cell.getNumericCellValue());
			case HSSFCell.CELL_TYPE_STRING :
				String s = cell.getStringCellValue();
				if (s != null) {
					s = StringUtil.rtrim(s);
				}
				if ("".equals(s)) {
					s = null;
				}
				if (isCellBase64Formatted(cell)) {
					return Base64Util.decode(s);
				}
				return s;
			case HSSFCell.CELL_TYPE_BOOLEAN :
                boolean b = cell.getBooleanCellValue();
                return BooleanUtil.valueOf(b);
			default :
				return null;
		}
	}
}
