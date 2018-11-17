package txtRead;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHandle {
	private static final String TALK_TIME = "[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}\\s[0-9]{2}\\:[0-9]{2}\\:[0-9]{2}";
	private static final String NAME_AND_ID = ".*[\\(\\<].*[\\)\\>]";
//	private String lineText;
	private int textType;
	private String startTime;
	private String endTime;
	
	public StringHandle(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	/**
	 * 
	 * @param lineText
	 * 		文本聊天记录
	 * @return
	 * 		是否用户id所在行
	 */
	public boolean isTalkID(String lineText) {
		if (Pattern.matches(".*\\(.*\\)", lineText) || Pattern.matches(".*\\<.*\\>", lineText))
			return Pattern.matches(TALK_TIME + "\\s" + NAME_AND_ID, lineText);
		return false;
	}
	
	public String getNameAndID(String lineText) {
		Pattern p = Pattern.compile("(" + TALK_TIME + ")" + "\\s" + "(" + NAME_AND_ID + ")");
		//System.out.println(p.pattern());
		Matcher m = p.matcher(lineText);
		if (m.find()) {
			//System.out.println(m.group(0));
			if (isNeedTime(m.group(1))) {
				return m.group(2);
			}
		} else {
			System.out.println("匹配失败");
		}
		return null;
	}
	
	public boolean hasKey(String str, String lineText) {
		if (lineText == null) {
			return false;
		}
		return Pattern.matches(".*#" + str + "#.*", lineText);
	}
	
	private boolean isNeedTime(String nowTime) {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 try {
	            Date nowDate = format.parse(nowTime);
	            Date startDate = format.parse(startTime);
	            Date endDate = format.parse(endTime);
	            if (nowDate.getTime() >= startDate.getTime() && nowDate.getTime()<= endDate.getTime()) {
	            	return true;
	            }
	            else {
	            	return false;
	            }
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
		return true;

	}
}
