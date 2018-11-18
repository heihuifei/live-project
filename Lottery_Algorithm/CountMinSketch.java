package txtRead;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CountMinSketch {
	private static final long LONG_PRIME = 4294967311l;
	private int width;
	private int depth;
	/**
	 * eps(for error), 0.01 < eps < 1 the smaller the berrer
	 */
	private double eps;
	/**
	 * gammga(probability for accuracy), 0 < gamma < 1 the bigger the better
	 */
	private double gamma;
	/**
	 * used in generation of hash funtion
	 */
	private int aj;
	private int bj;
	private int total;
	/**
	 * array of arrays of counters
	 */
	//private HashMap<String, Integer> C = new HashMap<>();
	private int[][] C;
	private int[][] C2;
	/**
	 * array of hash values for particular item contians two element arrays {aj,
	 * bj}
	 */
	private int[][] hashes;

	public CountMinSketch(double eps, double gamma) {
		this.eps = eps;
		this.gamma = gamma;
		width = (int) Math.ceil(Math.exp(1.00) / eps);
		depth = (int) Math.ceil(Math.log(1.00 / gamma));
		total = 0;
		C = new int[depth][width];
		C2 = new int[depth][width];
		initHashes();
	}

	/**
	 * update item(int or String) by count c
	 * 
	 * @param item
	 * @param c
	 */
	public void updateItem(long item, int c) {
		total = total + c;
		int hashval = 0;
		for (int i = 0; i < depth; i++) {
			hashval = (int) (((long) hashes[i][0] * item + hashes[i][1]) % LONG_PRIME % width);
			if (hashval >= 0) {
				C[i][hashval] = C[i][hashval] + c;
			} else {
				hashval = hashval * -1;
				if (hashval < 0) {
					hashval = 0;
				}
				C2[i][hashval] = C2[i][hashval] + c;
			}
		}
	}

	public void updateItem(String item, int c) {
		long hashval = hashString(item);
		updateItem(hashval, c);
	}

	/**
	 * esimate count of item
	 * 
	 * @param item
	 * @return count
	 */
	public int estimateItem(long item) {
		int minval = Integer.MAX_VALUE;
		int hashval = 0;
		for (int i = 0; i < depth; i++) {
			hashval = (int) (((long) hashes[i][0] * item + hashes[i][1]) % LONG_PRIME % width);
			if (hashval >= 0) {
				minval = minval < C[i][hashval] ? minval : C[i][hashval];
			} else {
				hashval = hashval * -1;
				if (hashval < 0) {
					hashval = 0;
				}
				minval = minval < C2[i][hashval] ? minval : C2[i][hashval];
			}
		}
		return minval;
	}

	public int estimateItem(String item) {
		long hashval = hashString(item);
		return estimateItem(hashval);
	}

	public long getTotalCount() {
		return total;
	}

	/**
	 * generates a hash value for a String same as djb2 hash function
	 * 
	 * @param str
	 * @return hash value of str
	 */
	public long hashString(String str) {
		long hash = 5381;
		int strLength = str.length();
		char[] strTemp = str.toCharArray();
		for (int i = 0; i < strLength; i++) {
			hash = ((hash << 5) + hash) + (int) (strTemp[i]);
		}
		return hash;
	}

	/**
	 * generate "new" aj, bj
	 * 
	 * @param hashes
	 * @param i
	 */
	private void generateAjBj(int[][] hashes, int i) {
		Random random = new Random(new Date().getTime());
		hashes[i][0] = (int) ((double) (random.nextInt()) * (double) (LONG_PRIME) / (double) (2e31 - 1) + 1);
	}

	private void initHashes() {
		hashes = new int[depth][];
		for (int i = 0; i < depth; i++) {
			hashes[i] = new int[2];
			generateAjBj(hashes, i);
		}
	}

}
