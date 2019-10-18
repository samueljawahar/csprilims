import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Stack;

public class AcaciaArkJSONParser {
	File jsonFile = null;
	Stack<String> keyStack = new Stack<String>();
	Stack<Character> brac_curly_Stack = new Stack<Character>();
	LinkedHashMap<String, String> dataBase = new LinkedHashMap<String, String>();
	ArrayList<String> elements = new ArrayList<String>();
	char startChars[] = { '[', '{' };
	HashMap<Character, Character> endChMap = new HashMap<Character, Character>();
	String previousData = "{";

	public AcaciaArkJSONParser(String fileNmae) {
		jsonFile = new File(fileNmae);
		endChMap.put('{', '}');
		endChMap.put('[', ']');
		keyStack.push("");
		parse();
		System.out.println("**********************DB*********************");
		printDataBase();
	}

	private void printDataBase() {
		Set set = dataBase.keySet();
		Iterator iterator = set.iterator();
		String key = "", val = "";
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			val = dataBase.get(key);
			System.out.println(key + "=" + val);
		}

	}

	private void parse() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile)));
			String line = null;
			StringBuilder sb = new StringBuilder();
			try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					sb.append(line);
				}
				System.out.println("***************************************");
				processData(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processData(String jsonData) {
		String jsonFragment = previousData + jsonData;
		jsonFragment = jsonFragment.trim();
		int len = jsonFragment.length();
		StringBuilder sb = new StringBuilder();
		String aux = "", data[] = null, tos = "", key = "";
		int min = Integer.MAX_VALUE, i = 0;
		int openedCurlybracesIndex = 0, closedCurlybracesIndex = 0, openedBracketsIndex = 0, closedBracketIndex = 0;
		for (; i < len; i++) {
			/*
			 * jsonFragment starts with { or [
			 */
			if (startChars[0] == jsonFragment.charAt(i) || startChars[1] == jsonFragment.charAt(i)) {
				brac_curly_Stack.push(jsonFragment.charAt(i));
				if (elements.size() != 0) {
					keyStack.push(elements.get(elements.size() - 1));
				} else {
					keyStack.push("");
				}
				elements.clear();

				System.out.println(jsonFragment.charAt(i));
				aux = jsonFragment.substring(1 + i);
				closedCurlybracesIndex = aux.indexOf(endChMap.get(startChars[1]));
				closedBracketIndex = aux.indexOf(endChMap.get(startChars[0]));
				openedCurlybracesIndex = aux.indexOf(startChars[1]);
				openedBracketsIndex = aux.indexOf(startChars[0]);
				min = getMinof(openedCurlybracesIndex, closedCurlybracesIndex, openedBracketsIndex, closedBracketIndex);
				if (closedCurlybracesIndex != -1 || closedBracketIndex != -1 || openedCurlybracesIndex != -1
						|| openedBracketsIndex != -1) {
					elements.addAll(getStringAsTokens(aux.substring(0, min)));
					if (brac_curly_Stack.peek() == '{') {
						updateDBwithMap(elements, keyStack.peek());
					}
					if (brac_curly_Stack.peek() == '[') {
						updateDBwithArray(elements, keyStack.peek());
					}
				}
				i = i + min;
				continue;
			}

			/*
			 * jsonFragment starts with } or ]
			 */
			if ('}' == jsonFragment.charAt(i) || ']' == jsonFragment.charAt(i)) {
				System.out.println(jsonFragment.charAt(i));
				aux = jsonFragment.substring(1 + i);
				closedCurlybracesIndex = aux.indexOf(endChMap.get(startChars[1]));
				closedBracketIndex = aux.indexOf(endChMap.get(startChars[0]));
				openedCurlybracesIndex = aux.indexOf(startChars[1]);
				openedBracketsIndex = aux.indexOf(startChars[0]);
				min = getMinof(openedCurlybracesIndex, closedCurlybracesIndex, openedBracketsIndex, closedBracketIndex);
				if (closedCurlybracesIndex != -1 || closedBracketIndex != -1 || openedCurlybracesIndex != -1
						|| openedBracketsIndex != -1) {
					if (brac_curly_Stack.peek() == '{') {
						updateDBwithMap(elements, keyStack.peek());
					}
					if (brac_curly_Stack.peek() == '[') {
						updateDBwithArray(elements, keyStack.peek());
					}
				}

				i = i + min;
				brac_curly_Stack.pop();
				keyStack.pop();
			}
		}
	}

	private ArrayList<String> getStringAsTokens(String jsonFragment) {
		ArrayList<String> result = new ArrayList<String>();

		StringBuilder sb = new StringBuilder();
		char jasonFragmentChar[] = jsonFragment.toCharArray();
		String key = "", val = "";
		int cfgLen = jasonFragmentChar.length, dqCnt = 0;
		for (int j = 0; j < cfgLen; j++) {
			if ((int) jasonFragmentChar[j] == 92) {
				sb.append(jasonFragmentChar[j]).append(jasonFragmentChar[j + 1]);
				j++;
				continue;
			}
			if (jasonFragmentChar[j] == '"') {
				dqCnt++;
			}
			if (dqCnt == 1) {
				sb.append(jasonFragmentChar[j]);
			}
			if (sb.toString().trim().length() == 0) {
				continue;
			}
			if (dqCnt == 2) {
				sb.append(jasonFragmentChar[j]);
				key = sb.toString();
				result.add(key);
				dqCnt = 0;
				sb.delete(0, sb.length());
			}
		}
		return result;
	}

	public void updateDBwithArray(ArrayList<String> elements, String key) {
		int len = elements.size();
		for (int i = 0; i < len; i++) {
			dataBase.put(key + "[" + i + "]", elements.get(i));
		}

	}

	public void updateDBwithMap(ArrayList<String> elements, String key) {
		int len = elements.size();
		for (int i = 0; i + 1 < len; i = i + 2) {
			dataBase.put(elements.get(i), elements.get(i + 1));
		}

	}

	private int getMinof(int openedCurlybracesIndex, int closedCurlybracesIndex, int openedBracketsIndex,
			int closedBracketIndex) {
		int min = Integer.MAX_VALUE;
		if (openedCurlybracesIndex >= 0 && (min > openedCurlybracesIndex)) {
			min = openedCurlybracesIndex;
		}
		if (closedCurlybracesIndex >= 0 && (min > closedCurlybracesIndex)) {
			min = closedCurlybracesIndex;
		}
		if (openedBracketsIndex >= 0 && (min > openedBracketsIndex)) {
			min = openedBracketsIndex;
		}
		if (closedBracketIndex >= 0 && (min > closedBracketIndex)) {
			min = closedBracketIndex;
		}
		return min;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Jesus is Lord:Romans-10:9");
		AcaciaArkJSONParser jp = new AcaciaArkJSONParser(
				"/home/samuel/Documents/workspace-spring-tool-suite-4-4.1.1.RELEASE/dataStructures/example2.txt");
	}

}
