package trees;
/**
 * 						THANK YOU JESUS
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class StackUtil {
	public String findkthMaxElement(TreeBuilder.Node root, int k) {
		int loc = 0;
		Stack callSt = new Stack();
		callSt.push(root);
		TreeBuilder.Node aux = null;

		while (!callSt.isEmpty()) {
			aux = (TreeBuilder.Node) callSt.pop();
			if (aux.left == null && aux.right == null) {
				if (!"NULL".equals(aux.data)) {
					loc++;
					if (loc == k) {
						return aux.data;
					}
				}
				if (!callSt.isEmpty()) {
					aux = (TreeBuilder.Node) callSt.pop();
					if (!"NULL".equals(aux.data)) {
						loc++;
						if (loc == k) {
							return aux.data;
						}
					}
				}
				continue;
			}
			if (aux.right != null) {
				callSt.push(aux.right);
			}
			if (aux != null) {
				callSt.push(aux);
			}
			if (aux.left != null) {
				callSt.push(aux.left);
			}

		}

		return "";
	}

	public static void postorderSuccOf(TreeBuilder.Node root, String ele) {
		// Navigate to left,Navigate to Right,Process
		Stack callSt = new Stack();
		callSt.push(root);

		while (!callSt.isEmpty()) {

		}

	}

	/**
	 * Thank you Jesus
	 * 
	 * @param root
	 */
	public static void printinPostorder(TreeBuilder.Node root) {
		/**
		 * The order of stack operations
		 * 1)push root
		 * 2)push right
		 * 3)push left
		 * Now while popping Left-Right-Root a post order
		 * since we are initially having root on the stack we need not do pushing of root
		 */
		Stack callSt = new Stack();
		callSt.push(root);
		TreeBuilder.Node aux = null;
		while (!callSt.isEmpty()) {
			aux = (TreeBuilder.Node) callSt.peek();
			if (aux.right == null && aux.left == null) {
				/**
				 *The popped element is child of TOS or sibling of TOS
				 */
				aux= (TreeBuilder.Node) callSt.pop();
				System.out.print(aux.data);
				/**
				 * Print as long as parent child relation exist between popped element and TOS
				 *  Stop once sibling relation encounter between popped element and TOS
				 */
				while (!callSt.isEmpty() && (((((TreeBuilder.Node) callSt.peek()).right != null)
						&& ((TreeBuilder.Node) callSt.peek()).right.data.equals(aux.data))
						|| ((((TreeBuilder.Node) callSt.peek()).left != null)
								&& ((TreeBuilder.Node) callSt.peek()).left.data.equals(aux.data)))) {
					aux = (TreeBuilder.Node) callSt.pop();
					System.out.print(aux.data);
				}
				continue;
			}
		
			if (aux.right != null) {
				callSt.push(aux.right);
			}
			if (aux.left != null) {
				callSt.push(aux.left);
			}

		}
	}
/**
 * This is based on PRE-ORDER Traversal
 * @param root
 * @param n1
 * @param n2
 * @return
 */
	public String getLowestCommonAncestor(TreeBuilder.Node root, String n1, String n2) {
		String result = "";
		Stack<TreeBuilder.Node> callStack = new Stack<TreeBuilder.Node>();
		callStack.push(root);
		String rootStr = root.data;
		TreeBuilder.Node aux = null, next = null;
		String currentRoot = "";
		int cnt = 0;

		if ((rootStr.equals(n1)) || (rootStr.equals(n2))) {
			return "It is root..No root";
		}

		while (!callStack.isEmpty()) {
			aux = callStack.peek();
			if (aux.left == null && aux.right == null) {
				aux = callStack.pop();

				/**
				 * This condition is to check the popped element is direct child of the TOS or
				 * not(in case the root of this node has right child,i.e its sibling)
				 */
				while (!callStack.isEmpty() && (((callStack.peek().right != null)
						&& (callStack.peek().right.data.equals(aux.data)))
						|| ((callStack.peek().left != null) && (callStack.peek().left.data.equals(aux.data))))) {

					aux = callStack.pop();
					if (aux.data.equals(currentRoot)) {
						next = callStack.peek();
						/**
						 * This condition is to check the popped element is direct child of the TOS or
						 * not(in case the root of this node has right child,i.e its sibling)
						 */
						if (next != null && ((next.left != null) && (next.left.data.equals(aux.data))
								|| (next.right != null) && (next.right.data.equals(aux.data)))) {
							currentRoot = next.data;
						} else {
							/**
							 * if the popped element has the sibling then the sibling is at Top-Of-Stack(TOS)
							 * therefore the parent of the popped and its sibling is in the stack just below sibling
							 * therefore pop the sibling get the Parent Info and then Push back the sibling since it is not yet processed
							 */
							next = callStack.pop();
							currentRoot = callStack.peek().data;
							callStack.push(next);
						}
					}
				}
				continue;
			}

			if (aux.right != null) {
				if ((aux.right.data.equals(n1)) || (aux.right.data.equals(n2))) {
					if (cnt == 0) {
						cnt++;
						currentRoot = aux.data;
					} else {
						/**
						 * No need to evaluate the subtree of the second node
						 */
						return currentRoot;
					}
				}
				callStack.push(aux.right);
			}
			if (aux.left != null) {
				if ((aux.left.data.equals(n1)) || (aux.left.data.equals(n2))) {
					if (cnt == 0) {
						cnt++;
						currentRoot = aux.data;
					} else {
						/**
						 * No need to evaluate the subtree of the second node
						 */
						return currentRoot;
					}
				}
				callStack.push(aux.left);
			}

		}

		return result;
	}

	class DSFframe {
		TreeBuilder.Node node;
		ArrayList<String> visited = new ArrayList<String>();
	}
	/**
	 * This is based on the Depth-First-Search 
	 * @param root
	 * @param n1
	 * @param n2
	 * @return
	 */
	public String getNearestCommonAncestor(TreeBuilder.Node root, String n1, String n2) {
		String result = "";
		Stack<DSFframe> callStack = new Stack<DSFframe>();
		DSFframe aux = null, aux2 = null;
		DSFframe rootf = new DSFframe();
		rootf.node = root;

		callStack.push(rootf);
		int cnt = 0;
		String currentRoot = "";
		while (!callStack.isEmpty()) {
			aux = callStack.peek();

			if (aux.node.left == null && aux.node.right == null) {
				aux = callStack.pop();
				if (aux.node.data.equalsIgnoreCase(n1) || aux.node.data.equalsIgnoreCase(n2)) {
					if (cnt == 0) {
						/**
						 * This the first time the node in Question has pass by 
						 */
						currentRoot = callStack.peek().node.data;
						cnt++;
					} else {
						return currentRoot;
					}
				}
				continue;
			}
			if (aux.node.left != null && !aux.visited.contains(aux.node.left.data)) {
				aux.visited.add(aux.node.left.data);
				aux2 = new DSFframe();
				aux2.node = aux.node.left;
				callStack.push(aux2);
				continue;
			}
			if (aux.node.right != null && !aux.visited.contains(aux.node.right.data)) {
				aux.visited.add(aux.node.right.data);
				aux2 = new DSFframe();
				aux2.node = aux.node.right;
				callStack.push(aux2);
				continue;
			}
			// both visited
			if (((aux.node.left != null && aux.visited.contains(aux.node.left.data))
					&& (aux.node.right != null && aux.visited.contains(aux.node.right.data)))
					|| (aux.node.left == null || aux.node.right == null)) {

				aux = callStack.pop();
				if (currentRoot.equals(aux.node.data)) {
					/**
					 * Adjustment of root since the other one is not yet encountered 
					 */
					currentRoot = callStack.peek().node.data;
				}
				if (aux.node.data.equalsIgnoreCase(n1) || aux.node.data.equalsIgnoreCase(n2)) {
					if (cnt == 0) {
						/**
						 * This the first time the node in Question has pass by 
						 */
						currentRoot = callStack.peek().node.data;
						cnt++;
					} else {
						return currentRoot;
					}
				}

			}

		}

		return result;
	}

	public void printinPreOrder(TreeBuilder.Node root) {
		Stack callSt = new Stack();
		callSt.push(root);
		TreeBuilder.Node aux = null;

		while (!callSt.isEmpty()) {
			aux = (TreeBuilder.Node) callSt.pop();
			if (!"NULL".equals(aux.data)) {
				System.out.print(aux.data);
			}

			if (aux.right != null) {
				callSt.push(aux.right);
			}
			if (aux.left != null) {
				callSt.push(aux.left);
			}

		}

	}

	public void printinInrder(TreeBuilder.Node root) {
		Stack callSt = new Stack();
		callSt.push(root);
		TreeBuilder.Node aux = null;

		while (!callSt.isEmpty()) {

			aux = (TreeBuilder.Node) callSt.pop();

			if (aux.left == null && aux.right == null) {
				/**
				 * once again checking the right pointer to generalize
				 */
				while (!callSt.isEmpty() && aux.right == null) {
					System.out.print(aux.data);
					aux = (TreeBuilder.Node) callSt.pop();
				}
				/**
				 * Printing the root whose right sub tree not yet explored
				 */
				System.out.print(aux.data);
				continue;
			}

			/**
			 * Left ==null can be printed at insertion time only
			 */
			if (aux.left == null) {
				System.out.print(aux.data);
				if (aux.right != null) {
					callSt.push(aux.right);
				}
			} else {

				if (aux.right != null) {
					callSt.push(aux.right);
				}
				if (aux != null) {
					callSt.push(aux);
				}
				if (aux.left != null) {
					callSt.push(aux.left);
				}
			}
		}

	}

	public static void testCase1() {

		// TODO Auto-generated method stub
		TreeBuilder tu = null;
		try {
			tu = new TreeBuilder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StackUtil se = new StackUtil();
		System.out.print("The post order:");
		se.printinPostorder(tu.root);

		System.out.print("\nThe pre order:");
		se.printinPreOrder(tu.root);

		System.out.print("\nThe In order:");
		se.printinInrder(tu.root);

		// System.out.println("\nThe kth Max values ..." +
		// se.findkthMaxElement(tu.root, 3));

	}

	public static void testCase2() {
		TreeBuilder tu = null;
		try {
			tu = new TreeBuilder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StackUtil se = new StackUtil();
		Scanner sc = new Scanner(System.in);
		String n1 = "", n2 = "";
		System.out.println("Enter node 1");
		n1 = sc.nextLine();
		System.out.println("Enter node 2");
		n2 = sc.nextLine();

		System.out.println("The Nearsert common root of " + se.getNearestCommonAncestor(tu.root, n1, n2));
	}

	public static void testCase3() {

		TreeBuilder tu = null;
		try {
			tu = new TreeBuilder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StackUtil se = new StackUtil();
		Scanner sc = new Scanner(System.in);
		String n1 = "", n2 = "";
		System.out.println("Enter node 1");
		n1 = sc.nextLine();
		System.out.println("Enter node 2");
		n2 = sc.nextLine();
		System.out.println("The getLowestCommonAncestor of " + se.getLowestCommonAncestor(tu.root, n1, n2));
		
		System.out.println("The getNearestCommonAncestor of " + se.getNearestCommonAncestor(tu.root, n1, n2));

	}
public static void testPostOreder() {


	TreeBuilder tu = null;
	try {
		tu = new TreeBuilder();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	StackUtil se = new StackUtil();
	System.out.println("The post Order is:");
	se.printinPostorder(tu.root);

}
	public static void main(String[] args) {
		testCase3();
		System.out.println("\nDone..");
	}

}
