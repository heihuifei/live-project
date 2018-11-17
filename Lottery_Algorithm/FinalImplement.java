package txtRead;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class FinalImplement {
	private int filterType;
	private String filePath;
	private String keyContent;
	private String startTime;
	private String endTime;

	public FinalImplement(int filterType, String filePath, String keyContent, String startTime, String endTime) {
		this.filterType = filterType;
		this.filePath = filePath;
		this.keyContent = keyContent;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public ArrayList<Entry<String, Integer>> getArrayList() {
		TextHandle test = new TextHandle(filterType, filePath, keyContent, startTime, endTime);
		PreTalkCount preTest = new PreTalkCount(filePath, keyContent, endTime);
		Map<String, Integer> map = test.getMap();
		Map<String, Integer> preMap = preTest.getMap();
		MapHandle sortMap = new MapHandle(map, preMap, preTest.getTotalNum());
		
		return sortMap.sortMap(map);
	}
}
