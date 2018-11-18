package txtRead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FinalImplement {
	private int filterType;
	private String filePath;
	private String keyContent;
	private String startTime;
	private String endTime;
	private HashMap<String, Integer> map;
	private HashMap<String, Integer> preMap;
	private ArrayList<String> uselessName;
	private TextHandle test;
	private PreTalkCount preTest;

	public FinalImplement(int filterType, String filePath, String keyContent, String startTime, String endTime) {
		this.filterType = filterType;
		this.filePath = filePath;
		this.keyContent = keyContent;
		this.startTime = startTime;
		this.endTime = endTime;
		this.test = new TextHandle(filterType, filePath, keyContent, startTime, endTime);
		this.preTest = new PreTalkCount(filePath, startTime);
		this.map = test.getMap();
		this.preMap = preTest.getMap();
		this.uselessName = new ArrayList<>();
	}
	
	public ArrayList<Entry<String, Integer>> getArrayList() {
		MapHandle sortMap = new MapHandle(map, preMap, preTest.getTotalNum());
		uselessName = sortMap.getUselessName();
		return sortMap.sortMap();
	}
	
	public ArrayList<String> getUselessName() {
		return uselessName;
	}
}
