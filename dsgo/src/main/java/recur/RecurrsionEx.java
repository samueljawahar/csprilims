package recur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import util.TreeUtil;
import util.TreeUtil.Node;

public class RecurrsionEx {

	RecurrsionEx() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the Height of the Tree:");
		util.TreeUtil.h = sc.nextInt();
		ArrayList<Node> list = new ArrayList<Node>();
		try {
			TreeUtil.constructTreeByIntrative(0, list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list.add(TreeUtil.getRoot());
		TreeUtil.printTree(0, list);
	}

	
	public String getInorderSuccOf(Node root, String ele) {
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
				if(callSt.isEmpty()){
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
	
	public String getPostSuccOf(Node root, String ele) {
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
				if(callSt.isEmpty()){
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

	public String testInorderSuccessor(Node root, String ele) {
		String result = "", leftT = "";
		if (root.getLeft() == null && root.getRight() == null) {
			System.out.println(root.getData());
			return root.getData();
		}
		testInorderSuccessor(root.getLeft(), ele);
		System.out.println(root.getData());
		return root.getData();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RecurrsionEx re = new RecurrsionEx();
		// System.out.println(re.getInorderSuccessor(TreeUtil.getRoot(), "A"));
		System.out.println(
				"===================ENTER ELE=================================================================================");
		Scanner sc = new Scanner(System.in);
		String ele = sc.nextLine();
		while (ele.length() <= 4) {
			System.out.println(ele + " Inorder Successor:" + re.getPostSuccOf(TreeUtil.getRoot(), ele));
			ele = sc.nextLine();
		}
	}

}
