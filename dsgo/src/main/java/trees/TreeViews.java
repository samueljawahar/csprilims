package trees;
/**
 * 						THANK YOU JESUS
 */
import java.io.IOException;
import java.util.Stack;
import trees.TreeBuilder.Node;
public class TreeViews {
	Node root = null;

	public TreeViews() {
		try {
			TreeBuilder t = new TreeBuilder();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		root = TreeBuilder.getRoot();
	}
	
	/**
	 * pre order
	 * @param r
	 */
		public static void printLeftView(Node r) {
			LevelNode aux = null, prevRoot = null, lchild = null, rchild = null;
			Stack<LevelNode> callStack = new Stack<LevelNode>();
			LevelNode root = new LevelNode();
			root.cnode = r;
			root.level = 0;
			callStack.push(root);
	//		System.out.print(root.cnode.data);
			prevRoot = root;
			int level = 1, prevLevel = -1;
			while (!callStack.isEmpty()) {
				aux = callStack.pop();
				level = aux.level;
				/**
				 * Pushing
				 */

				if (aux.level > prevLevel) {
					prevLevel = aux.level;
					System.out.print(" " + aux.cnode.data);
				}
				level++;
				if (aux.cnode.right != null) {
					lchild = new LevelNode();
					lchild.cnode = aux.cnode.right;
					lchild.level = level;
					callStack.push(lchild);
				}
				if (aux.cnode.left != null) {
					rchild = new LevelNode();
					rchild.cnode = aux.cnode.left;
					rchild.level = level;
					callStack.push(rchild);
				}

				prevRoot = aux;
				
			}
		}
	
/**
 * pre order
 * @param r
 */
	public static void printLeftView_1(Node r) {
		LevelNode aux = null, prevRoot = null, lchild = null, rchild = null;
		Stack<LevelNode> callStack = new Stack<LevelNode>();
		LevelNode root = new LevelNode();
		root.cnode = r;
		root.level = 0;
		callStack.push(root);
		System.out.print(root.cnode.data);
		prevRoot = root;
		int level = 1, prevLevel = -1;
		while (!callStack.isEmpty()) {
			aux = callStack.pop();
			level = aux.level;
			/**
			 * Pushing
			 */
			if (((prevRoot.cnode.left != null) && prevRoot.cnode.left.data.equals(aux.cnode.data))
					|| (((prevRoot.cnode.right != null) && prevRoot.cnode.right.data.equals(aux.cnode.data)))) {
				if (aux.level > prevLevel) {
					prevLevel = aux.level;
					System.out.print(" " + aux.cnode.data);
				}

			}
			level++;
			if (aux.cnode.right != null) {
				lchild = new LevelNode();
				lchild.cnode = aux.cnode.right;
				lchild.level = level;
				callStack.push(lchild);
			}
			if (aux.cnode.left != null) {
				rchild = new LevelNode();
				rchild.cnode = aux.cnode.left;
				rchild.level = level;
				callStack.push(rchild);
			}

			prevRoot = aux;

		}
	}

	/**
	 * Printing all nodes in a sub tree with distance
	 * 
	 * @param r
	 * @param d
	 */
	public static void displayAllNodesAt(Node r, int d) {
		LevelNode aux = null, lchild = null, rchild = null;
		int level = 1;
		Stack<LevelNode> callStack = new Stack<LevelNode>();
		LevelNode root = new LevelNode();
		root.cnode = r;
		root.level = 0;
		callStack.push(root);
		while (!callStack.isEmpty()) {
			aux = callStack.pop();
			level = aux.level;
			if (level == d) {
				System.out.print(aux.cnode.data + " ");
				continue;
			}
			level++;
			if (aux.cnode.right != null) {
				lchild = new LevelNode();
				lchild.cnode = aux.cnode.right;
				lchild.level = level;
				callStack.push(lchild);
			}
			if (aux.cnode.left != null) {
				rchild = new LevelNode();
				rchild.cnode = aux.cnode.left;
				rchild.level = level;
				callStack.push(rchild);
			}

		}

	}

	/**
	 * 
	 * @param r
	 * @param s
	 * @param d
	 */
	public static void viewAllsurroundingNodesAt(Node r, Node s, int d) {
		Stack<DFSFrame> callStack = populateInDFS(r, s);
		DFSFrame aux = null;
		int cnt = 0;
		Node node = null;
		aux = callStack.peek();
		int bcnt = 0;
		node = aux.cnode;
		while (!callStack.isEmpty()) {
			if (node != null) {
				displayAllNodesAt(node, d - (bcnt + cnt));
				aux = callStack.pop();
				if (callStack.isEmpty()) {
					return;
				}
				cnt++;
				if (cnt == d) {
					System.out.print(callStack.pop().cnode);
				}
				bcnt = 0;
				node = null;
			}
			if (callStack.isEmpty()) {
				return;
			}
			if (callStack.peek().cnt == 3) {
				cnt--;
			}
			if (callStack.peek().cnode.left == aux.cnode) {
				callStack.peek().cnt = 3;
				bcnt = 1;
				node = callStack.peek().cnode.right;
			}
			if (callStack.peek().cnode.right == aux.cnode) {
				callStack.peek().cnt = 3;
				bcnt = 1;
				node = callStack.peek().cnode.left;
			}

		}
	}

	/**
	 * This is DFS using stack,using cnt to point what node to be explored
	 * 
	 * @param r
	 * @param s
	 * @return
	 */
	public static Stack<DFSFrame> populateInDFS(Node r, Node s) {
		DFSFrame aux = null, root = null, nextNode = null;
		Stack<DFSFrame> callStack = new Stack<DFSFrame>();
		root = new DFSFrame();
		root.cnode = r;
		root.cnt = 0;
		callStack.push(root);
		while (!callStack.isEmpty()) {
			aux = callStack.peek();
			if (aux.cnt > 1) {
				callStack.pop();
				continue;
			}
			if (aux.cnode == s) {
				return callStack;
			}
			if (aux.cnode.left == null && aux.cnode.right == null) {
				callStack.pop();
				continue;
			}
			/**
			 * If the Node maintains the list cnt should be the index of the
			 * list child's to be processed
			 */
			if (aux.cnt == 0) {
				nextNode = new DFSFrame();
				aux.cnt++;
				nextNode.cnode = aux.cnode.left;
				callStack.push(nextNode);
			} else if (aux.cnt == 1) {
				nextNode = new DFSFrame();
				aux.cnt++;
				nextNode.cnode = aux.cnode.right;
				callStack.push(nextNode);
			}
		}
		return callStack;
	}

	static class LevelNode {
		public Node getCnode() {
			return cnode;
		}

		public void setCnode(Node cnode) {
			this.cnode = cnode;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		Node cnode = null;
		int level = 0;
	}

	static class DFSFrame {
		public Node getCnode() {
			return cnode;
		}

		public void setCnode(Node cnode) {
			this.cnode = cnode;
		}

		public int getCnt() {
			return cnt;
		}

		public void setCnt(int cnt) {
			this.cnt = cnt;
		}

		Node cnode = null;
		int cnt = 0;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TreeViews tv = new TreeViews();
		//TreeViews.viewAllsurroundingNodesAt(tv.root, tv.root.left, 2);
		TreeViews.printLeftView(tv.root);
		// TreeViews.populateInDFS(tv.root, tv.root.left.right);
	}

}
