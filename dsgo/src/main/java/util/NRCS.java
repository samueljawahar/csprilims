package util;


//This is to extract the non repeated charterer String
 
public class NRCS {
	public String getNRCStr(String data) {
		String result = "", aux = "";
		int table[] = new int[257];
		int len = data.length();
		int ascii = 0, prevLimit = len + 2;
		int j = 0;
		boolean isComplete = true;
		for (j = len - 1; j >= 0; j--) {
			ascii = (int) data.charAt(j);
			if (table[ascii] != 0 && table[ascii] < prevLimit) {
				isComplete = false;
				aux = data.substring(j + 1);
				if (aux.length() > result.length()) {
					result = aux;
				}
				data = data.substring(0, table[ascii]);
				len = data.length();
				prevLimit = table[ascii];
			}
			table[ascii] = j;
		}
		if (isComplete) {
			return data;
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NRCS nrcs = new NRCS();
		System.out.println(nrcs.getNRCStr("asdfghjklacvb"));
	}

}
