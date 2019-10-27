package util;

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
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class JsonParser {
	File jsonFile = null;
	Stack<String> keyStack = new Stack<String>();
	Stack<Character> typeStack = new Stack<Character>();
	LinkedHashMap<String, String> dataBase = new LinkedHashMap<String, String>();
	char startChars[] = { '[', '{' };
	HashMap<Character, Character> endChMap = new HashMap<Character, Character>();
	String previousData = "{";

	public JsonParser(String fileNmae) {
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
					if (sb.length() >= 1000) {

					}
				}
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
				typeStack.push(jsonFragment.charAt(i));
				aux = jsonFragment.substring(1 + i);
				if (aux.trim().length() == 0) {
					break;
				}
				closedCurlybracesIndex = aux.indexOf(endChMap.get(startChars[1]));
				closedBracketIndex = aux.indexOf(endChMap.get(startChars[0]));
				openedCurlybracesIndex = aux.indexOf(startChars[1]);
				openedBracketsIndex = aux.indexOf(startChars[0]);
				min = getMinof(openedCurlybracesIndex, closedCurlybracesIndex, openedBracketsIndex, closedBracketIndex);
				/*
				 * OPEN OPEN
				 */
				if (openedCurlybracesIndex == min || openedBracketsIndex == min) {
					aux = aux.substring(1, min);
					if (keyStack.isEmpty()) {
						continue;
					}
					tos = keyStack.peek();
					/*
					 * JSON Object
					 */
					if (typeStack.peek() == '{') {
						Map<String, String> cfgMap = new LinkedHashMap<String, String>();
						key = getKeyandMap(aux, cfgMap);
						updateAndContinue(cfgMap);
					}
					/*
					 * JSON Array
					 */
					if (typeStack.peek() == '[') {
						ArrayList<String> cfg = new ArrayList<String>();
						key = getKeyandMap(aux, cfg);
						updateAndContinue(cfg);
					}
					if (key.trim().length() != 0) {
						keyStack.push(tos + "." + key);
					}
					i += min;
				}
				/*
				 * OPEN CLOSE
				 */
				if (closedBracketIndex == min || closedCurlybracesIndex == min) {
					aux = aux.substring(0, min);
					if (keyStack.isEmpty()) {
						continue;
					}
					tos = keyStack.peek();
					if (typeStack.peek() == '{') {
						Map<String, String> cfgMap = new LinkedHashMap<String, String>();
						key = getKeyandMap(aux, cfgMap);
						updateAndComplete(cfgMap);
					}
					/*
					 * JSON Array
					 */
					if (typeStack.peek() == '[') {
						ArrayList<String> cfg = new ArrayList<String>();
						key = getKeyandMap(aux, cfg);
						updateAndComplete(cfg);
					}

					i += min;
				}

			}

			/*
			 * jsonFragment starts with } or ]
			 */
			if ('}' == jsonFragment.charAt(i) || ']' == jsonFragment.charAt(i)) {
				aux = aux.substring(1);
				if (aux.trim().length() == 0) {
					break;
				}
				closedCurlybracesIndex = aux.indexOf(endChMap.get(startChars[1]));
				closedBracketIndex = aux.indexOf(endChMap.get(startChars[0]));
				openedCurlybracesIndex = aux.indexOf(startChars[1]);
				openedBracketsIndex = aux.indexOf(startChars[0]);
				min = getMinof(openedCurlybracesIndex, closedCurlybracesIndex, openedBracketsIndex, closedBracketIndex);
				/*
				 * CLOSE OPEN
				 */
				if (openedCurlybracesIndex == min || openedBracketsIndex == min) {
					aux = aux.substring(1, min);
					if (keyStack.isEmpty()) {
						continue;
					}
					tos = keyStack.peek();
					/*
					 * JSON Object
					 */
					if (typeStack.peek() == '{') {
						typeStack.pop();
						Map<String, String> cfgMap = new LinkedHashMap<String, String>();
						key = getKeyandMap(aux, cfgMap);
						updateAndContinue(cfgMap);
					}
					/*
					 * JSON Array
					 */
					if (typeStack.peek() == '[') {
						typeStack.pop();
						ArrayList<String> cfg = new ArrayList<String>();
						key = getKeyandMap(aux, cfg);
						updateAndContinue(cfg);
					}
					if (key.trim().length() != 0) {
						keyStack.push(tos + "." + key);
					}
					i += min;
				}
				/*
				 * CLOSE CLOSE
				 */
				if (closedBracketIndex == min || closedCurlybracesIndex == min) {
					aux = aux.substring(0, min);
					if (keyStack.isEmpty()) {
						continue;
					}
					tos = keyStack.peek();
					if (typeStack.peek() == '{') {
						Map<String, String> cfgMap = new LinkedHashMap<String, String>();
						key = getKeyandMap(aux, cfgMap);
						updateAndComplete(cfgMap);
					}
					/*
					 * JSON Array
					 */
					if (typeStack.peek() == '[') {
						ArrayList<String> cfg = new ArrayList<String>();
						key = getKeyandMap(aux, cfg);
						updateAndComplete(cfg);
					}
					if (key.trim().length() != 0) {
						keyStack.push(tos + "." + key);
					}
					i += min;
				}

			}
		}
		previousData = jsonFragment.substring(i);
	}

	private void updateAndComplete(ArrayList<String> cfg) {
		if (cfg.size() == 0) {
			return;
		}
		String tos = keyStack.peek(), nextKey = "", aux = "";
		int len = cfg.size();
		for (int i = 0; i < len; i++) {
			dataBase.put(tos + "[" + i + "]", cfg.get(i));
		}

	}

	private void updateAndContinue(ArrayList<String> cfg) {
		if (cfg.size() == 0) {
			return;
		}
		String tos = keyStack.peek(), nextKey = "", aux = "";
		int len = cfg.size();
		for (int i = 0; i < len; i++) {
			dataBase.put(tos, cfg.get(i));
		}

	}

	private void updateAndContinue(Map<String, String> cfg) {
		if (cfg.size() == 0) {
			return;
		}
		String tos = keyStack.peek(), nextKey = "", aux = "";
		Iterator iterator = cfg.keySet().iterator();
		while (iterator.hasNext()) {
			aux = (String) iterator.next();
			nextKey = tos + "." + aux;
			dataBase.put(nextKey, cfg.get(aux));
		}
	}

	private void updateAndComplete(Map<String, String> cfg) {
		if (cfg.size() == 0) {
			return;
		}
		String tos = keyStack.pop(), nextKey = "", aux = "";
		Iterator iterator = cfg.keySet().iterator();
		while (iterator.hasNext()) {
			aux = (String) iterator.next();
			nextKey = tos + "." + aux;
			dataBase.put(nextKey, cfg.get(aux));
		}
	}

	private String getKeyandMap(String jsonFragment, ArrayList<String> cfg) {
		int colenIndex = jsonFragment.indexOf(':');
		String key = "";
		if (colenIndex != -1) {
			String arrayData = jsonFragment.substring(0, colenIndex);
			String data[] = arrayData.split(",");
			int len = data.length;
			for (int i = 0; i < len - 1; i++) {
				if (data[i].trim().length() != 0) {
					cfg.add(data[i].trim());
				}
			}
			key = data[len - 1].trim();
		} else {

			String data[] = jsonFragment.split(",");
			int len = data.length;
			for (int i = 0; i < len; i++) {
				if (data[i].trim().length() != 0) {
					cfg.add(data[i].trim());
				}
			}

		}
		return key;
	}

	private String getKeyandMap(String jsonFragment, Map<String, String> cfg) {
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
			if (dqCnt == 1 || dqCnt == 3) {
				sb.append(jasonFragmentChar[j]);
			}
			if (sb.toString().trim().length() == 0) {
				continue;
			}
			if (dqCnt == 2) {
				sb.append(jasonFragmentChar[j]);
				key = sb.toString();
				sb.delete(0, sb.length());
			}
			if (dqCnt == 4) {
				sb.append(jasonFragmentChar[j]);
				val = sb.toString();
				sb.delete(0, sb.length());
				cfg.put(key, val);
				key = "";
				val = "";
			}
		}
		if (val.trim().length() != 0) {
			return "";
		}

		return key;
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
		JsonParser jp = new JsonParser("");
	}

}
