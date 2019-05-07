package trees;
/**
 * 						THANK YOU JESUS
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class TreeBuilder {

	public static int h = 3;
	static int padingLen = 100;
	static Stack<String> callStk = new Stack<String>();
	static String options[] = null;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));;

	public static String getDynamicPadding(int len) {
		String pad = " ";
		for (int i = 0; i < len; i++) {
			pad += pad;
		}
		return pad;
	}


	public static void printNodesAt(int level, ArrayList<Node> list) {
		String padding = " ";
		String auxStr = "";
		String auxStr_r = "";
		int len = (int) (padingLen / Math.pow(2, level));
		for (int i = 0; i < len; i++) {
			auxStr += padding;
		}
		for (int i = 0; i < len-(level-2); i++) {
			auxStr_r += padding;
		}
		len = list.size();// (int) Math.pow(2, level);
		Node aux = null;
		for (int i = 1; i <= len; i++) {
			aux = list.get(i - 1);
			if (aux == null) {
				System.out.print(auxStr + auxStr);
			} else if (aux.getData().equals("NULL")) {
				System.out.print(auxStr + auxStr);
			} else {
				System.out.print(auxStr + aux.getData() + auxStr_r);
			}
		}

		System.out.println("\n\n");
	}

	public TreeBuilder() throws IOException {
		// TODO Auto-generated constructor stub
		System.out.print("Enter the Height of the Tree:");
		h = Integer.parseInt(br.readLine());
		padingLen = (int) Math.pow(2, h);
		padingLen = padingLen * h;
		ArrayList<Node> list = new ArrayList<Node>();
		System.out.print("Interactive(Y/N) :");
		String option = br.readLine();
		if (option.equalsIgnoreCase("N")) {
			System.out.print("Enter options(ynyny):");
			options = br.readLine().split(" ");
			constructTreeByNonIntrative(0, list);
		} else {
			constructTreeByIntrative(0, list);
		}

		list = new ArrayList<Node>();
		list.add(root);
		printTree(0, list);
		System.out.println("\n");
	}

	public static void main(String[] args) throws IOException {
		new TreeBuilder();
	}

	static int cnt = 0;

	public static int getH() {
		return h;
	}

	public static void setH(int h) {
		TreeBuilder.h = h;
	}

	public static Node getRoot() {
		return root;
	}

	public static void setRoot(Node root) {
		TreeBuilder.root = root;
	}

	public static Node root = new Node();

	/**
	 * For each Node assign the right and left of the tree Node do this level by
	 * level keep track latest level node list
	 *
	 * @param level
	 * @param list
	 */
	public static void constructTree(int level, ArrayList<Node> list) {
		if (level == 0) {
			root.setData("oo");
			list.add(root);
			constructTree(level + 1, list);
		}
		if (level == h - 1) {
			return;
		}
		String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		ArrayList<Node> nextList = new ArrayList<Node>();
		int len = list.size();
		int qt = 0;
		Node aux = null, next = null;
		// cnt = (cnt + 1) % 26;
		for (int i = 0; i < len; i++) {
			aux = list.get(i);
			next = new Node();
			qt = cnt / 26;
			if (qt > 0) {
				next.setData(labels[cnt] + "" + qt);
			} else {
				next.setData(labels[cnt]);
			}
			aux.setLeft(next);
			nextList.add(next);
			next = new Node();
			qt = (cnt + 1) / 26;
			cnt = (cnt + 1) % 26;
			if (qt > 0) {
				next.setData(labels[cnt] + "" + qt);
			} else {
				next.setData(labels[cnt]);
			}
			aux.setRight(next);
			nextList.add(next);
			cnt = (cnt + 1) % 26;
		}
		constructTree(level + 1, nextList);
	}

	static int index = 0;

	/**
	 * For each Node assign the right and left of the tree Node do this level by
	 * level keep track latest level node list
	 *
	 * @param level
	 * @param list
	 */
	public static void constructTreeByNonIntrative(int level, ArrayList<Node> list) {
		ArrayList<Node> nextList = new ArrayList<Node>();
		String option = "";
		if (level == 0) {
			root.setData("oo");
			list.add(root);
			constructTreeByNonIntrative(level + 1, list);
			return;
		}
		if (level == h) {
			return;
		}
		String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };

		int len = list.size();
		Node aux = null, next = null;
		int qt = 0;
		for (int i = 0; i < len; i++) {
			aux = list.get(i);
			option = options[index++];
			if (option.equalsIgnoreCase("Y")) {
				next = new Node();
				if (qt > 0) {
					next.setData(labels[cnt] + "" + qt);
				} else {
					next.setData(labels[cnt]);
				}

				aux.setLeft(next);
				nextList.add(next);
			} else {
			}
			option = options[index++];
			if (option.equalsIgnoreCase("Y")) {
				next = new Node();
				qt += (cnt + 1) / 26;
				cnt = (cnt + 1) % 26;
				if (qt > 0) {
					next.setData(labels[cnt] + "" + qt);
				} else {
					next.setData(labels[cnt]);
				}

				aux.setRight(next);
				nextList.add(next);
			} else {
			}
			qt += (cnt + 1) / 26;
			cnt = (cnt + 1) % 26;

		}
		constructTreeByNonIntrative(level + 1, nextList);
	}

	/**
	 * For each Node assign the right and left of the tree Node do this level by
	 * level keep track latest level node list
	 *
	 * @param level
	 * @param list
	 * @throws IOException
	 */
	public static void constructTreeByIntrative(int level, ArrayList<Node> list) throws IOException {
		ArrayList<Node> nextList = new ArrayList<Node>();
		String options = "";
		if (level == 0) {
			root.setData("oo");
			list.add(root);
			constructTreeByIntrative(level + 1, list);
			return;
		}
		if (level == h) {
			return;
		}
		String labels[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };

		int len = list.size();
		Node aux = null, next = null;
		int qt = 0;
		for (int i = 0; i < len; i++) {
			aux = list.get(i);
			System.out.print("Wanted to create Left Node of \"" + aux.data + "\"(Y/N):");
			options = br.readLine();
			if (options.equalsIgnoreCase("Y")) {
				next = new Node();
				if (qt > 0) {
					next.setData(labels[cnt] + "" + qt);
				} else {
					next.setData(labels[cnt]);
				}

				aux.setLeft(next);
				System.out.println("Added Left Node to the Parent:" + aux.data);
				nextList.add(next);
			} else {
			}
			System.out.print("Wanted to create Right Node of  \"" + aux.data + "\"(Y/N):");
			options = br.readLine();
			if (options.equalsIgnoreCase("Y")) {
				next = new Node();
				qt += (cnt + 1) / 26;
				cnt = (cnt + 1) % 26;
				if (qt > 0) {
					next.setData(labels[cnt] + "" + qt);
				} else {
					next.setData(labels[cnt]);
				}

				aux.setRight(next);
				System.out.println("Added Right Node to the Parent:" + aux.data);
				nextList.add(next);
			} else {
			}
			qt += (cnt + 1) / 26;
			cnt = (cnt + 1) % 26;

		}
		constructTreeByIntrative(level + 1, nextList);
	}

	public static void printTree(int level, ArrayList<Node> list) {
		if (level == h) {
			return;
		}
		printNodesAt(level, list);
		ArrayList<Node> nextList = new ArrayList<Node>();
		int len = list.size();
		Node aux = null;
		Node dummy = new Node();
		dummy.data = "NULL";
		for (int i = 0; i < len; i++) {
			aux = list.get(i);
			if (aux == null) {
				continue;
			}
			if (aux.getLeft() != null) {
				nextList.add(aux.getLeft());
			} else {
				nextList.add(dummy);
			}
			if (aux.getRight() != null) {
				nextList.add(aux.getRight());
			} else {
				nextList.add(dummy);
			}
		}
		printTree(level + 1, nextList);
	}

	/**
	 * A tree Node is a key role player
	 */
	public static class Node {

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public String data = "";
		public Node left = null;
		public Node right = null;

		public String toString() {
			return data;
		}
	}
}
