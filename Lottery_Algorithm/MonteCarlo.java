package txtRead;

import java.util.Map;

/**
 * Created by Hawk on 2018-11-17.
 */
public class MonteCarlo {
    private static final int MAXN = 1 << 20;
    private int[] x;

    public MonteCarlo() {
        x = new int[MAXN];
    }

    public Map<String, Integer> rand(Map<String, Integer> map) {
        x[0] = (int)(Math.random()*100 + 1);  // 随机种子(可以用日期产生)
        /* 保证m与a互质 */
        int size = map.size();
        int m = 20 * size;
        int a = 21;  // a = 4p + 1
        int b = 15;  // b = 2q + 1
       
        int i = 1;
        int weight = 0;
        for (Map.Entry<String, Integer> en : map.entrySet()) {
			//System.out.println(en.getKey() + "=" + en.getValue());
			x[i] = ( a * x[i-1] + b ) % m;
			if (en.getValue() < 0) {			//发言为空，剔出抽奖名单
				i++;
				continue;
			} else if (en.getValue() > 0) {		//恶意刷屏，降低权重
				weight = en.getValue();
				if (weight > 30) {
					weight = 30;
				}
				x[i] = x[i] * ((30 - weight) / 30);
			}
			map.put(en.getKey(), x[i]);
			//System.out.println(en.getKey() + "=" + en.getValue());
			i++;
		}
		return map;
    }

}
