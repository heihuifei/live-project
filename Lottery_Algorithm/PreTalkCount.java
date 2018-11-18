package txtRead;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileInputStream;

public class PreTalkCount {
	private String filePath;
	private String startTime;
	private String endTime;
//	private Vector<Pair<String, Integer>> vt = new Vector<Pair<String, Integer>>();
	private ArrayList<String> strArray = new ArrayList<>();
	private int textType;
	private String userID;
	private int totalNum;

	public PreTalkCount(String filePath, String keyContent, String endTime) {
		this.filePath = filePath;
		startTime = "1998-01-01 12:48:00";
		this.endTime = endTime;
		userID = null;
		textType = 0;
		totalNum = 0;
	}

	public int getTotalNum() {
		return totalNum;
	}
	
	public HashMap<String, Integer> getMap() {
		setStrArray();
		HashMap<String, Integer> preMap = new HashMap<>();
		CountMinSketch c = new CountMinSketch(0.01, 0.1);
		int i; 
		int toal = 0;
		int tempNum = 0;
		for (String strTemp : strArray) {
			c.updateItem(strTemp, 1);
		}
		for (String strTemp : strArray) {
			if (!preMap.containsKey(strTemp)) {
				tempNum = c.estimateItem(strTemp);
				preMap.put(strTemp, tempNum);
				totalNum += tempNum;
			}
		}
		return preMap;
	}
	
	private ArrayList<String> setStrArray() {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);

			if (file.isFile() && file.exists()) { // 判断文件是否存在
				StringHandle handleString = new StringHandle(startTime, endTime);
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);

				// 遍历文件
				String talkContent = null;
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (textType != 0) {
						if (lineTxt.equals("")) {
							if (userID != null) { 			//如果有发言
								strArray.add(userID);
							}
							talkContent = null;
							continue;
						} else if (handleString.isTalkID(lineTxt)) {
							userID = null;
							textType = 0;
						} else {
							talkContent += lineTxt;
						}
					}
					if (textType == 0) { // id行的处理
						userID = handleString.getNameAndID(lineTxt);
						if (userID != null) {
							if (Pattern.matches("系统消息\\([1][0]+\\)", userID)) {
								userID = null;
							}
						}
						textType = 1;
						// if (userID != null) {
						// System.out.println(userID);
						// }
					}
				}

				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return strArray;
	}

}