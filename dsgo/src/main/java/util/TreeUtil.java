package util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

public class TreeUtil {

	public static int h = 3;
	static int padingLen = 100;
	static Stack<String> callStk = new Stack<String>();
	static String options[]=null;
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));;
	public static void printNodesAt(int level, ArrayList<Node> list) {
		String padding = " ";
		String auxStr = "";
		String auxStr_r = "";
		int len = (int) (padingLen / Math.pow(2, level));
		for (int i = 0; i < len; i++) {
			auxStr += padding;
		}
		for (int i = 0; i < len - 2; i++) {
			auxStr_r += padding;
		}
		len = list.size();//(int) Math.pow(2, level);
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

	public static String getInorderSuccOf(Node root, String ele) {
		String result = "None";
		String currentTOS = "";
		Stack callSt = new Stack();
		callSt.push(root);

		Node aux = null, aux2 = null;
		while (!callSt.isEmpty()) {

			aux = (Node) callSt.peek();
			if (aux.getLeft() == null && aux.getRight() == null) {
				aux = (Node) callSt.pop();
				if (currentTOS.equals(ele)) {
					result = aux.getData();
					break;
				}
				currentTOS = aux.getData();
				if (callSt.isEmpty()) {
					break;
				}
				aux = (Node) callSt.pop();
				if (currentTOS.equals(ele)) {
					result = aux.getData();
					break;
				}
				currentTOS = aux.getData();

				continue;
			}
			aux = (Node) callSt.pop();
			if (aux.getRight() != null) {
				callSt.push(aux.getRight());
			}
			callSt.push(aux);
			if (aux.getLeft() != null) {
				callSt.push(aux.getLeft());
			}
		}

		return result;
	}

	public static String getPostSuccOf(Node root, String ele) {
		String result = "None";
		String currentTOS = "";
		Stack callSt = new Stack();
		callSt.push(root);
		Node aux = null, aux2 = null;
		while (!callSt.isEmpty()) {

			aux = (Node) callSt.peek();
			if (aux.getLeft() == null && aux.getRight() == null) {
				aux = (Node) callSt.pop();
				if (currentTOS.equals(ele)) {
					result = aux.getData();
					break;
				}
				currentTOS = aux.getData();
				if (callSt.isEmpty()) {
					break;
				}
				aux = (Node) callSt.pop();

				if (aux.getLeft() == null && aux.getRight() == null) {

					if (currentTOS.equals(ele)) {
						result = aux.getData();
						break;
					}
					if (callSt.isEmpty()) {
						break;
					}
					currentTOS = aux.getData();
					aux = (Node) callSt.pop();
					if (currentTOS.equals(ele)) {
						result = aux.getData();
						break;
					}
					currentTOS = aux.getData();
					if (((Node) callSt.peek()).right.data.equals(currentTOS)) {
						aux = (Node) callSt.pop();
						currentTOS = aux.getData();
					}
					continue;
				}
				callSt.push(aux);
				if (aux.getRight() != null) {
					callSt.push(aux.getRight());
				}
				if (aux.getLeft() != null) {
					callSt.push(aux.getLeft());
				}

				continue;
			}
			aux = (Node) callSt.pop();
			if (aux.right.data.equals(currentTOS)) {
				continue;
			}
			callSt.push(aux);
			if (aux.getRight() != null) {
				callSt.push(aux.getRight());
			}
			if (aux.getLeft() != null) {
				callSt.push(aux.getLeft());
			}
		}

		return result;
	}

	public static String getPreSuccOf(Node root, String ele) {
		String result = "None";
		String currentTOS = "";
		Stack callSt = new Stack();
		callSt.push(root);

		Node aux = null, aux2 = null;
		while (!callSt.isEmpty()) {

			aux = (Node) callSt.peek();
			if (aux.getLeft() == null && aux.getRight() == null) {
				aux = (Node) callSt.pop();
				if (currentTOS.equals(ele)) {
					result = aux.getData();
					break;
				}

				currentTOS = aux.getData();

				continue;
			}
			aux = (Node) callSt.pop();
			if (aux.getRight() != null) {
				callSt.push(aux.getRight());
			}
			if (aux.getLeft() != null) {
				callSt.push(aux.getLeft());
			}
			if (currentTOS.equals(ele)) {
				result = aux.getData();
				break;
			}
			currentTOS = aux.getData();
		}

		return result;
	}

	public TreeUtil() throws IOException {
		// TODO Auto-generated constructor stub
		System.out.print("Enter the Height of the Tree:");
		h = Integer.parseInt(br.readLine());
		padingLen = (int) Math.pow(2, h);
		padingLen = padingLen * h;
		ArrayList<Node> list = new ArrayList<Node>();
		// constructTree(0, list);
		System.out.print("Interactive :");
		String option=br.readLine();
		if(option.equalsIgnoreCase("N")){
			System.out.print("Enter options:");
			options=br.readLine().split(" ");
			constructTreeByNonIntrative(0, list);
		}else{
			constructTreeByIntrative(0, list);
		}
		
		list = new ArrayList<Node>();
		list.add(root);
		printTree(0, list);
		System.out.println("\n");
	}

	public static void main(String[] args) throws IOException {
		
		
		
		System.out.print("Enter the Height of the Tree:");
		h = Integer.parseInt(br.readLine());
		System.out.print("Interactive :");
		String option=br.readLine();
	 
		padingLen = (int) Math.pow(2, h);
		padingLen = padingLen * h;
		ArrayList<Node> list = new ArrayList<Node>();
		// constructTree(0, list);
		if(option.equalsIgnoreCase("N")){
			System.out.print("Enter options:");
			options=br.readLine().split(" ");
			constructTreeByNonIntrative(0, list);
		}else{
			constructTreeByIntrative(0, list);
		}
		list = new ArrayList<Node>();
		list.add(root);
		printTree(0, list);
		System.out.println("\n");
		System.out.println("In order :=" + pritnInInorder(root));
		System.out.println("In PreOrder:=" + printInPreorder(root));
		System.out.println("In PostOrder:=" + printInPostorder(root));
		Node enode = new Node();
		System.out.print("Enter the Node to Know Inorder Successor:");
		String nq =br.readLine();
		enode.setData(nq);
		StringBuilder match = new StringBuilder();
		getInorderSuccOf(root, nq, match);
		if (match.toString().trim().equalsIgnoreCase("Yes")) {
			System.out.println("NO Inorder Successor for this node:");
			// return;
		} else {
			System.out.println("Inorder succssor of " + nq + " is:=" + match.toString());
		}

		System.out.println("STACK-->" + getInorderSuccOf(root, nq));

		System.out.print("Enter the Node to Know PreOreder Successor:");
		nq =br.readLine();
		enode.setData(nq);
		match = new StringBuilder();
		getPreorderSuccOf(root, nq, match);
		if (match.toString().trim().equalsIgnoreCase("Yes")) {
			System.out.println("NO PreOrder Successor for this node:");
		} else {
			// getInorderSuccOf
			System.out.println("PreOrder succssor of " + nq + " is:=" + match.toString());
		}
		System.out.println("STACK-->" + getPreSuccOf(root, nq));

		System.out.print("Enter the Node to Know PostOPrder Successor:");
		nq = br.readLine();
		enode.setData(nq);
		match = new StringBuilder();
		getPostorderSuccOf(root, nq, match);
		if (match.toString().trim().equalsIgnoreCase("Yes")) {
			System.out.println("NO PostOPrder Successor for this node:");
		} else {
			// getInorderSuccOf
			System.out.println("PostOPrder succssor of " + nq + " is:=" + match.toString());
		}

		System.out.println("STACK-->" + getPostSuccOf(root, nq));
		// System.out.println("Inorder succssor of " + enode.getData() + "
		// is:="+ getInorderSuccesorOf(enode, root, root, null, new
		// StateVar()));
	}

	static int cnt = 0;

	public static int getH() {
		return h;
	}

	public static void setH(int h) {
		TreeUtil.h = h;
	}

	public static Node getRoot() {
		return root;
	}

	public static void setRoot(Node root) {
		TreeUtil.root = root;
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
			root.setData("ooo");
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
				next.setData(labels[cnt] + "_" + qt);
			} else {
				next.setData(labels[cnt]);
			}
			aux.setLeft(next);
			nextList.add(next);
			next = new Node();
			qt = (cnt + 1) / 26;
			cnt = (cnt + 1) % 26;
			if (qt > 0) {
				next.setData(labels[cnt] + "_" + qt);
			} else {
				next.setData(labels[cnt]);
			}
			aux.setRight(next);
			nextList.add(next);
			cnt = (cnt + 1) % 26;
		}
		constructTree(level + 1, nextList);
	}
static int index=0;
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
			root.setData("ooo");
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
			System.out.print("Wanted to create Left Node of \"" + aux.data + "\"(Y/N):"+option);
			if (option.equalsIgnoreCase("Y")) {
				next = new Node();
				if (qt > 0) {
					next.setData(labels[cnt] + "_" + qt);
				} else {
					next.setData(labels[cnt]);
				}

				aux.setLeft(next);
				System.out.println("Added Left Node to the Parent:" + aux.data);
				nextList.add(next);
			} else {
			//	next = new Node();
				// next.setData("NULL");
				// aux.setLeft(next);
				// nextList.add(next);
			}
			option = options[index++];
			System.out.print("Wanted to create Right Node of  \"" + aux.data + "\"(Y/N):"+option);
			if (option.equalsIgnoreCase("Y")) {
				next = new Node();
				qt += (cnt + 1) / 26;
				cnt = (cnt + 1) % 26;
				if (qt > 0) {
					next.setData(labels[cnt] + "_" + qt);
				} else {
					next.setData(labels[cnt]);
				}

				aux.setRight(next);
				System.out.println("Added Right Node to the Parent:" + aux.data);
				nextList.add(next);
			} else {
			//	next = new Node();
				//qt += (cnt + 1) / 26;
				//	cnt = (cnt + 1) % 26;
				// next.setData("NULL");
				// aux.setRight(next);
				// nextList.add(next);
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
			root.setData("ooo");
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
					next.setData(labels[cnt] + "_" + qt);
				} else {
					next.setData(labels[cnt]);
				}

				aux.setLeft(next);
				System.out.println("Added Left Node to the Parent:" + aux.data);
				nextList.add(next);
			} else {
			//	next = new Node();
				// next.setData("NULL");
				// aux.setLeft(next);
				// nextList.add(next);
			}
			System.out.print("Wanted to create Right Node of  \"" + aux.data + "\"(Y/N):");
			options = br.readLine();
			if (options.equalsIgnoreCase("Y")) {
				next = new Node();
				qt += (cnt + 1) / 26;
				cnt = (cnt + 1) % 26;
				if (qt > 0) {
					next.setData(labels[cnt] + "_" + qt);
				} else {
					next.setData(labels[cnt]);
				}

				aux.setRight(next);
				System.out.println("Added Right Node to the Parent:" + aux.data);
				nextList.add(next);
			} else {
				//next = new Node();
			//	qt += (cnt + 1) / 26;
				//cnt = (cnt + 1) % 26;
				// next.setData("NULL");
				// aux.setRight(next);
				// nextList.add(next);
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
		Node dummy=new Node();
		dummy.data="NULL";
		for (int i = 0; i < len; i++) {
			aux = list.get(i);
			if (aux == null) {
				continue;
			}
			if (aux.getLeft() != null) {
				nextList.add(aux.getLeft());
			}else{
				nextList.add(dummy);
			}
			if (aux.getRight() != null) {
				nextList.add(aux.getRight());
			}else{
				nextList.add(dummy);
			}
		}
		printTree(level + 1, nextList);
	}

	public static String pritnInInorder(Node root) {
		if (root == null) {
			return "";
		}
		if (root.left == null && root.right == null) {
			if (root.data.equals("NULL")) {
				return "";
			}
			return root.data;
		}
		if (root.data.equals("NULL")) {
			return pritnInInorder(root.left) + pritnInInorder(root.right);
		} else {
			return pritnInInorder(root.left) + root.data + pritnInInorder(root.right);
		}
	}

	public static String printInPreorder(Node root) {
		if (root == null) {
			return "";
		}
		if (root.left == null && root.right == null) {
			if (root.data.equals("NULL")) {
				return "";
			}
			return root.data;
		}
		if (root.data.equals("NULL")) {
			return printInPreorder(root.left) + printInPreorder(root.right);
		} else {
			return root.data + printInPreorder(root.left) + printInPreorder(root.right);
		}
	}

	public static String printInPostorder(Node root) {
		if (root == null) {
			return "";
		}
		if (root.left == null && root.right == null) {
			if (root.data.equals("NULL")) {
				return "";
			}
			return root.data;
		}
		if (root.data.equals("NULL")) {
			return printInPostorder(root.left) + printInPostorder(root.right);
		} else {
			return printInPostorder(root.left) + printInPostorder(root.right) + root.data;
		}

	}

	public static void getInorderSuccOf(Node currentNode, String node, StringBuilder match) {
		if (currentNode == null) {
			if (!(match.toString().length() > 0)) {
				match.append("No Successor");
			}
			return;
		}
		if ((currentNode.left == null && currentNode.right == null) || (currentNode.left == null)
				|| (currentNode.right == null)) {
			if (match.toString().contains("Yes")) {
				match.delete(0, match.length());
				match.append(currentNode.data);
			}
			if (currentNode.data.equals(node)) {
				match.append("Yes");
			}
			return;
		}
		String aux = "";
		if (currentNode.left != null && !currentNode.left.equals("NULL")) {
			getInorderSuccOf(currentNode.left, node, match);
		}

		aux = currentNode.data;
		if (match.toString().contains("Yes")) {
			match.delete(0, match.length());
			match.append(aux);
		}
		if (aux.equals(node)) {
			match.append("Yes");
		}
		if (currentNode.right != null && !currentNode.right.data.equals("NULL")) {
			getInorderSuccOf(currentNode.right, node, match);
		}
	}

	public static void getPreorderSuccOf(Node currentNode, String node, StringBuilder match) {

		// the current node is matching or not
		if (((currentNode != null) && currentNode.data.equals("NULL")) || (currentNode == null)) {
			return;
		}
		String aux = "";
		aux = currentNode.data;
		if (match.toString().contains("Yes")) {
			match.delete(0, match.length());
			match.append(aux);
		}

		if (aux.equals(node)) {
			match.append("Yes");
		}
		getPreorderSuccOf(currentNode.left, node, match);
		getPreorderSuccOf(currentNode.right, node, match);
		if (currentNode == null) {
			if (!(match.toString().length() > 0)) {
				match.append("No Successor");
			}
			return;
		}
	}

	public static void getPostorderSuccOf(Node currentNode, String node, StringBuilder match) {
		String aux = "";
		if (currentNode == null) {
			if (!(match.toString().length() > 0)) {
				match.append("No Successor");
			}
			return;
		}

		if ((currentNode.left == null && currentNode.right == null)) {
			aux = currentNode.data;
			if (match.toString().contains("Yes")) {
				match.delete(0, match.length());
				match.append(aux);
			}

			if (aux.equals(node)) {
				match.append("Yes");
			}
			return;
		}

		getPostorderSuccOf(currentNode.left, node, match);
		getPostorderSuccOf(currentNode.right, node, match);
		// the current node is matching or not
		aux = currentNode.data;
		if (match.toString().contains("Yes")) {
			match.delete(0, match.length());
			match.append(aux);
		}

		if (aux.equals(node)) {
			match.append("Yes");
		}

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
