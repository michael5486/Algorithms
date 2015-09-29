import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.util.Enumeration;

public class BinarySearchTree implements TreeSearchAlgorithm {

	public TreeNode root = new TreeNode();
	int totalNodes = 0; //counter to keep track of TreeNodes
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

	public java.lang.Object insert(java.lang.Comparable key, java.lang.Object value) { //inserts the node, and recursively finds the proper position for every child node

		TreeNode newNode = new TreeNode(key, value, null, null, null);
		if (this.getRoot() == null) {
			//System.out.println("Inserting root: " + key);
			this.root = newNode;
			totalNodes++;

		}
		else {
			//System.out.println("Inserting child: " + key);
			totalNodes++;
			recursiveInsert(this.getRoot(), key, value);
		}
	//	this.printTree();
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

	public TreeNode findNode(TreeNode node, Comparable key) { //used in delete method
		TreeNode foundNode = null;
		if (key.compareTo(node.key) == 0) { //found matching node, return KeyValue pair
			//System.out.println("Found node: " + key);
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
		/*This method is has all the necessary cases, and also has another 
		seperate case for when the node being deleted is the root.
		This is because in order to delere, I go the a node's parent.
		The root doesn't have a parent, so I had to create seperate cases to avoid
		getting a nullPointerException*/
		Object toReturn = null;
		TreeNode node = this.findNode(this.getRoot(), key);

		//System.out.println("Deleting: " + key);
		if (node == null) { //case 1: node not found
			//System.out.print("KeyValue pair not found");
			return toReturn;
		}
		else if (node == this.getRoot()) { //node is the leaf
				if (node.left == null && node.right == null) {
					//System.out.println("Removing node with no children");
					toReturn = node.value;
					this.root = null;
					totalNodes--;
				//	System.out.print("Tree after delete: ");
					this.printTree();
					return toReturn;
					}
				else if (node.left != null && node.right == null) { //node has left child
				//	System.out.println("Removing node with left child");
					toReturn = node.value;
					this.root = node.left;
					totalNodes--;
				//	System.out.print("Tree after delete: ");
					this.printTree();
					return toReturn;
				}	
				else if (node.left == null && node.right != null) { //node has right child
				//	System.out.println("Removing node with right child");
					toReturn = node.value;
					this.root = node.right;
					totalNodes--;
				//	System.out.print("Tree after delete: ");
					//this.printTree();
					return toReturn;				
					}
				else { //node has 2 children
				//	System.out.println("Node has 2 children");
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
				//	System.out.print("Tree after delete: ");
				//	this.printTree();
					return toReturn;					
				}		
			}
		else if (node.left == null && node.right == null) { //case 2: node is a leaf
			if (node.parent.left == node) { //leaf is left child
			//	System.out.println("Leaf is left child");
				toReturn = node.value;
				node.parent.left = null;
				totalNodes--;
				//this.printTree();
				return toReturn;
			}
			else { //leaf is right child
			//	System.out.println("Leaf is right child");
				toReturn = node.value;
				node.parent.right = null;
				totalNodes--;
				//this.printTree();
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
			//	this.printTree();
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
			//	this.printTree();
				return toReturn;
		}
		else { //case 5: node has 2 children
		//	System.out.println("Node has 2 children");
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
			//this.printTree();
			return toReturn;
		}
	}

	public edu.gwu.algtest.ComparableKeyValuePair maximum() { //finds the rightmost node
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

	public edu.gwu.algtest.ComparableKeyValuePair minimum() { //finds the leftmost node
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

	public edu.gwu.algtest.ComparableKeyValuePair search(java.lang.Comparable key) { //calls recursiveSearch with the root node
		return recursiveSearch(this.getRoot(), key);
	}

	public ComparableKeyValuePair recursiveSearch(TreeNode node, Comparable key) {
		ComparableKeyValuePair pair = null;

		if (key.compareTo(node.key) == 0) { //found matching node, return KeyValue pair
		//	System.out.println("Found node: " + key);
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

	public java.util.Enumeration getKeys() { //calls methods in a separate class where the Enumeration is created, populated with nodes, and ultimately returned
		KeyEnumerator keys = new KeyEnumerator();
		keys.createKeysEnumerator(totalNodes, this.getRoot());
		return keys;

	}

	public java.util.Enumeration getValues() { //also another class, very similar to KeyEnumerations
		ValueEnumerator values = new ValueEnumerator();
		values.createValuesEnumerator(totalNodes, this.getRoot());
		return values;
	}

	public int getCurrentSize() { //global variable totalNodes is incremented whenever a node is inserted, and decremented whenever a node is deleted. This way, totalNodes accurately reflects the total count of nodes in the BST
		return totalNodes;
	}

	public void initialize(int maxSize) { //resets all global variables to prepare for a new BST
		//System.out.println("Initializing new tree...");
		this.root = null;
		totalNodes = 0; //counter to keep track of TreeNodes
		index = 0;
	}

	public String getName() {
		return "michael5486's implementation of BinarySearchTree";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

	public java.lang.Comparable successor(java.lang.Comparable key) { //iterates through the Enumeration, finding the next key in order
		
		//System.out.println("Key: " + key);
		KeyEnumerator keys = new KeyEnumerator();
		keys.createKeysEnumerator(totalNodes, this.getRoot());
		//System.out.println("Successor: " + keys.nextKey(key));
    	return keys.nextKey(key);
    }

    public java.lang.Comparable predecessor(java.lang.Comparable key) { //iterates through the enuemration, finding the previous key in order
		//System.out.println("Key: " + key);		
		KeyEnumerator keys = new KeyEnumerator();
		keys.createKeysEnumerator(totalNodes, this.getRoot());
		//System.out.println("Predecessor: " + keys.prevKey(key));
    	return keys.prevKey(key);
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
    	//These are various testing methods I used to create and debug my tree  
    	BinarySearchTree tree = new BinarySearchTree();
    	tree.insert("10", "10");
    	tree.insert("11", "11");
    	tree.insert("12", "12");
    	tree.insert("13", "13");
    	tree.insert("14", "14");
    	tree.insert("15", "15");
    	tree.insert("16", "16");
       	tree.insert("17", "17");
    	tree.insert("18", "18");
    	tree.insert("19", "19");

    	//System.out.println(tree.getRoot().toString());
    	//tree.printTree();
    	System.out.println("Search for A: " + tree.search("A"));
    	System.out.println("Node C: " + tree.findNode(tree.getRoot(), "C"));
    	System.out.println("Maximum: " + tree.maximum());
    	System.out.println("Minimum: " + tree.minimum());
    	System.out.println("Current size: " + tree.getCurrentSize());
    	//System.out.println("Parent of A: " + tree.findNode(tree.getRoot(), "A").parent);
    	//tree.delete("10");
    	tree.delete("12");
    	//System.out.println("Test: " + tree.delete("19"));
    	//tree.delete("G");
    	/*tree.delete("18");
    	System.out.println("Node: " + tree.getRoot());
    	//System.out.println();
    	//System.out.print("Keys: ");
    	System.out.println("Search for 10: " + tree.search("10"));
    	System.out.println("Search for 12: " + tree.search("12"));
    	System.out.println("Search for 19: " + tree.search("19"));*/
        
        ValueEnumerator ds = new ValueEnumerator();
        Enumeration e = tree.getValues();
        System.out.println("Enumerator: ");
        while (e.hasMoreElements()) {
            String s = (String) e.nextElement();
            System.out.println (s);
        }

        KeyEnumerator ds2 = new KeyEnumerator();
        Enumeration e2 = tree.getKeys();
        System.out.println("Enumerator keys: ");
        while (e2.hasMoreElements()) {
            String s = (String) e2.nextElement();
            System.out.println (s);
        }

        System.out.println("Successor of 10: " + tree.successor(10));
        System.out.println("Predeccessor of 19: " + tree.predecessor(19));

	}
    
}