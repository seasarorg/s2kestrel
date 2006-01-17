package org.seasar.extension.unit;

import java.util.List;
import java.util.Map;

/**
 * @author higa
 *
 */
public class MapListReader extends MapReader {

	/**
	 * @param map
	 */
	public MapListReader(List list) {
		setupColumns((Map) list.get(0));
		for (int i = 0; i < list.size(); ++i) {
			Map map = (Map) list.get(i);
			setupRow(map);
		}
	}

}
