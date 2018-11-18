package txtRead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapHandle {
	private HashMap<String, Integer> hashMap;
	private HashMap<String, Integer> preMap;
	private int totalNum;
	private ArrayList<String> uselessName;
	
	public MapHandle(HashMap<String, Integer> map, HashMap<String, Integer> preMap, int totalNum) {
		// TODO Auto-generated constructor stub
		hashMap = map;
		this.preMap = preMap;
		this.totalNum = totalNum;
		this.uselessName = new ArrayList<>();
	}

	public ArrayList<Entry<String, Integer>> sortMap() {
		int size = hashMap.size();
		randomMap();
		
		ArrayList<Entry<String, Integer>> list = new ArrayList<>(hashMap.entrySet());
		
		//进行排序
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());//升序，前边加负号变为降序
            }
        });
        
        //遍历排序后map
        int i = 0;
        Iterator<Entry<String, Integer>> iterator = list.iterator();
        for(Entry<String, Integer> m : list){
            //System.out.println(m.getKey()+"="+m.getValue());
            if (preMap.containsKey(m.getKey())) {
            	i += (int)(((double)preMap.get(m.getKey()) / (double)totalNum) * size * 0.3);
            }
        	hashMap.put(m.getKey(), i);
            i++;
            //System.out.println(m.getKey()+"="+m.getValue());
        }
        return list;
	}

	public ArrayList<String> getUselessName() {
		return uselessName;
	}
	
	private void randomMap() {
		MonteCarlo m = new MonteCarlo();
		m.rand(hashMap);
		uselessName = m.getUselessName();
	}
}
