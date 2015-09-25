import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.util.Enumeration;

public class BinarySearchTree implements TreeSearchAlgorithm, Enumeration {

	public TreeNode root = new TreeNode();
	int totalNodes = 0; //counter to keep track of TreeNodes
	Comparable[] keys;
	Object[] values;
	int index;

	public BinarySearchTree() {

		this.root = null;
	}

	public BinarySearchTree(edu.gwu.algtest.TreeNode node) {
		this.root = node;
	}

	public edu.gwu.algtest.TreeNode getRoot() {
		return this.root;
	}

	public java.lang.Object insert(java.lang.Comparable key, java.lang.Object value) {


		TreeNode newNode = new TreeNode(key, value, null, null, null);
		if (this.getRoot() == null) {
			System.out.println("Inserting root: " + key);
			this.root = newNode;
		}
		else {
			System.out.println("Inserting child: " + key);
			recursiveInsert(this.getRoot(), key, value);
		}
		this.printTree();
		totalNodes++;
		return newNode;
	}

	public void recursiveInsert(TreeNode node, Comparable key, Object value) {
		TreeNode newNode;
		if (key.compareTo(node.key) < 0) { //insert newNode as left child
			if (node.left != null) {
				recursiveInsert(node.left, key, value);
				return;
			}
			else { //newNode goes here
				//System.out.println("Inserting left child: " + key);
				newNode = new TreeNode(key, value, null, null, node);
				node.left = newNode;
			}
		}
		else { //insert newNode as right child
			if (node.right != null) {
				recursiveInsert(node.right, key, value);
				return;
			}
			else { //newNode goes here
				//System.out.println("Inserting right child: " + key);
				newNode = new TreeNode(key, value, null, null, node);
				node.right = newNode;
			}
		}
	}

	public TreeNode findNode(TreeNode node, Comparable key) {
		TreeNode foundNode = null;
		if (key.compareTo(node.key) == 0) { //found matching node, return KeyValue pair
			System.out.println("Found node: " + key);
			foundNode = node;
		}
		else if (key.compareTo(node.key) < 0) { //recursiveSearch continues on left child
			if (node.left != null) {
				return findNode(node.left, key);
			}
		}
		else { //recursiveSearch continues on right child
			if (node.right != null) {
				return findNode(node.right, key);
			}
		}
		return foundNode;
	}

	public java.lang.Object delete(java.lang.Comparable key) {
		
		Object toReturn = null;
		TreeNode node = this.findNode(this.getRoot(), key);

		System.out.println("Deleting: " + key);
		if (node == null) { //case 1: node not found
			System.out.print("KeyValue pair not found");
			return toReturn;
		}
		else if (node == this.getRoot()) { //node is the leaf
				if (node.left == null && node.right == null) {
					System.out.println("Removing node with no children");
					toReturn = node.value;
					this.root = null;
					totalNodes = 0;
					System.out.print("Tree after delete: ");
					this.printTree();
					return toReturn;
					}
				else if (node.left != null && node.right == null) { //node has left child
					System.out.println("Removing node with left child");
					toReturn = node.value;
					this.root = node.left;
					totalNodes = 0;
					System.out.print("Tree after delete: ");
					this.printTree();
				}	
				else if (node.left == null && node.right != null) { //node has right child
					System.out.println("Removing node with right child");
					toReturn = node.value;
					this.root = node.right;
					totalNodes = 0;
					System.out.print("Tree after delete: ");
					this.printTree();
					}
				else { //node has 2 children
					System.out.println("Node has 2 children");
					toReturn = node.value;
					TreeNode temp = node.right;
					if (temp.left != null) {
						while (temp.left != null) { //finds the successor of the node to be deleted
							temp = temp.left;
						}	
						temp.parent.left = null; //removes temp from tree
					}
					else { //no smaller nodes to the left to go to
						temp.parent.right = null;
					}
					node.key = temp.key;
					node.value = temp.value; //replaces node with temp's info
					totalNodes--;
					System.out.print("Tree after delete: ");
					this.printTree();
				}		
			}
		else if (node.left == null && node.right == null) { //case 2: node is a leaf
			if (node.parent.left == node) { //leaf is left child
				System.out.println("Leaf is left child");
				toReturn = node.value;
				node.parent.left = null;
				totalNodes--;
				System.out.print("Tree after delete: ");
				this.printTree();
				return toReturn;
			}
			else { //leaf is right child
				System.out.println("Leaf is right child");
				toReturn = node.value;				
				node.parent.right = null;
				totalNodes--;
				System.out.print("Tree after delete: ");
				this.printTree();
				return toReturn;
			}
		}
		else if (node.left != null && node.right == null) { //case 3: node has left child
				TreeNode temp = node.left;
				TreeNode parent = node.parent;
				toReturn = node.value;
				if (parent.left == node) { //node to delete is left child
					parent.left = temp;
				}
				else { //node to delete is right child
					parent.right = temp;
				}
				totalNodes--;
				System.out.print("Tree after delete: ");
				this.printTree();
				return toReturn;
		}
		else if (node.left == null && node.right != null) { //case 4: node has right child
				TreeNode temp = node.right;
				TreeNode parent = node.parent;
				toReturn = node.value;
				if (node == this.getRoot()) {
					node.right.parent = null;
					node.right = this.root;
				}
				else if (parent.left == node) { //node to delete is left child
					parent.left = temp;
				}
				else { //node to delete is right child
					parent.right = temp;
				}
				totalNodes--;
				System.out.print("Tree after delete: ");
				this.printTree();
				return toReturn;
		}
		else { //case 5: node has 2 children
			System.out.println("Node has 2 children");
			toReturn = node.value;
			TreeNode temp = node.right;
			if (temp.left != null) {
				while (temp.left != null) { //finds the successor of the node to be deleted
					temp = temp.left;
				}
			
				temp.parent.left = null; //removes temp from tree
			}
			else { //no smaller nodes to the left to go to
				temp.parent.right = null;
			}
			node.key = temp.key;
			node.value = temp.value; //replaces node with temp's info
			totalNodes--;
			System.out.print("Tree after delete: ");
			this.printTree();

		}
		return null;
	}

	public edu.gwu.algtest.ComparableKeyValuePair maximum() {
		TreeNode node = this.getRoot();
		if (node == null) {
			return null;
		}
		while (node.right != null) {
			node = node.right;
		}
		ComparableKeyValuePair pair = new ComparableKeyValuePair(node.key, node.value);
		return pair;
	}

	public edu.gwu.algtest.ComparableKeyValuePair minimum() {
		TreeNode node = this.getRoot();
		if (node == null) {
			return null;
		}
		while (node.left != null) {
			node = node.left;
		}
		ComparableKeyValuePair pair = new ComparableKeyValuePair(node.key, node.value);
		return pair;
	}

	public edu.gwu.algtest.ComparableKeyValuePair search(java.lang.Comparable key) {
		System.out.println("Key: " + key);
		return recursiveSearch(this.getRoot(), key);
	}

	public ComparableKeyValuePair recursiveSearch(TreeNode node, Comparable key) {
		ComparableKeyValuePair pair = null;

		if (key.compareTo(node.key) == 0) { //found matching node, return KeyValue pair
			System.out.println("Found node: " + key);
			pair = new ComparableKeyValuePair(key, node.value);
		}
		else if (key.compareTo(node.key) < 0) { //recursiveSearch continues on left child
			if (node.left != null) {
				return recursiveSearch(node.left, key);
			}
		}
		else { //recursiveSearch continues on right child
			if (node.right != null) {
				return recursiveSearch(node.right, key);
			}
		}
		return pair;
	}

	public java.util.Enumeration getKeys() {
		//getKeys should return an Enumeration that can iterate through the keys.
		/*this.createEnums();
		Enumeration e = keys;
		return e;*/
		return null;
	}

	public java.util.Enumeration getValues() {
		//getValues should return an Enumeration that can iterate through values.
		/*this.createEnums();
		Enumeration e = values;
		return e;*/
		return null;
	}

	public Object[] getmyKeys() {
		this.createEnums();
		return values;
	}

	public void createEnums() {
		index = 0;
		keys = new Comparable[this.getCurrentSize()];
		values = new Object[this.getCurrentSize()];
		recursiveCreateEnums(this.getRoot());

	}

	public void recursiveCreateEnums(TreeNode node) {

    	if (node == null) {

    	}
    	else {
    		recursiveCreateEnums(node.left);
    		keys[index] = node.key;
    		values[index] = node.value;
    		index++;
    		recursiveCreateEnums(node.right);
    	}
	}

	public Object nextElement() {
		return null;
	}

	public boolean hasMoreElements() {
		return false;
	}

	public int getCurrentSize() {
		return totalNodes;
	}

	public void initialize(int maxSize) {
		System.out.println("Initializing new tree...");
		this.root = null;
		int totalNodes = 0; //counter to keep track of TreeNodes
		Comparable[] keys = null;
		Object[] values = null;
		int index = 0;
	}


	public String getName() {
		return "michael5486's implementation of BinarySearchTree";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

	public java.lang.Comparable successor(java.lang.Comparable key) {
    	return null;
    }

    public java.lang.Comparable predecessor(java.lang.Comparable key) {
    	return null;
    }


    public void printTree() { //used to test the correctness of my BST
    	System.out.print("Inorder traversal of tree: ");
    	this.printTree(this.getRoot());
    }

    public void printTree(TreeNode node) {

    	if (node == null) {

    	}
    	else {
    		printTree(node.left);
    		System.out.print(" " + node.value + " ");
    		printTree(node.right);
    	}
    }

    public static void main(String[] args) {
    	BinarySearchTree tree = new BinarySearchTree();
    	tree.insert("D", "D");
    	tree.insert("E", "E");
    	/*tree.insert("B", "B");
    	tree.insert("F", "F");
       	tree.insert("A", "A");
    	tree.insert("C", "C");
    	tree.insert("E", "G");*/

    	//System.out.println(tree.getRoot().toString());
    	//tree.printTree();
    	System.out.println("Search for A: " + tree.search("A"));
    	System.out.println("Node C: " + tree.findNode(tree.getRoot(), "C"));
    	System.out.println("Maximum: " + tree.maximum());
    	System.out.println("Minimum: " + tree.minimum());
    	System.out.println("Current size: " + tree.getCurrentSize());
    	//System.out.println("Parent of A: " + tree.findNode(tree.getRoot(), "A").parent);
    	//tree.delete("A");
    	//tree.delete("A");
    	//tree.delete("B");
    	tree.delete("D");
    	//System.out.println();
    	//System.out.print("Keys: ");


    }
}