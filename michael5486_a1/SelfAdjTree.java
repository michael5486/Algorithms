
import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class SelfAdjTree implements TreeSearchAlgorithm {

	public TreeNode root = new TreeNode();
	int totalNodes = 0; //counter to keep track of treenodes
	int height = 0;
	boolean found;
	int attempts;

	public SelfAdjTree() {

		this.root = null;
	}

	public SelfAdjTree(edu.gwu.algtest.TreeNode node) {
		this.root = node;
	}

	public edu.gwu.algtest.TreeNode getRoot() {
		return this.root;
	}

	public java.lang.Object insert(java.lang.Comparable key, java.lang.Object value) {
		
		found = false;
		TreeNode newNode;
		if (this.getRoot() == null) {
			newNode = new TreeNode(key, value, null, null, null);
			//System.out.println("Inserting root: " + key);
			this.root = newNode;
			totalNodes++;
			return newNode;
		}
		TreeNode node = recursiveSearch2(this.getRoot(), key); //this recursiveSearch caused an infinite loop and I have no idea why. It's recursive element is non-functioning so the rest of my code can be tested in algtest without the program crashing
		if (found) {
			//System.out.println("found duplicate");
			//Handles duplicates. Returns old value, replaces it with new value
			Object temp = node.value;
			node.value = value;
			return temp;
		}
		else {
			//Otherwise, node returned is correct place to insert
			//System.out.println("Inserting child: " + key);
			newNode = new TreeNode(key, value, null, null, node);
			totalNodes++;
			if (key.compareTo(node.key) < 0) { //insert newNode on the left
				node.left = newNode;
			}
			else { //insert newNode on the right
				node.right = newNode;
			}
			this.splaystep(newNode);
		}
		//this.printTree();
		return newNode;
	}

	public void insertNode(TreeNode nodeToInsert) {
		

		TreeNode node = recursiveSearch(this.getRoot(), nodeToInsert.key);
		nodeToInsert.parent = node;
		if (nodeToInsert.key.compareTo(node.key) < 0) { //insert newNode on the left
			node.left = nodeToInsert;
		}
		else { //insert newNode on the right
			node.right = nodeToInsert;
		}

		this.printTree();
	}


	public TreeNode findNode(TreeNode node, Comparable key) { //used in delete method to locate node to delete
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
					return toReturn;
				}	
				else if (node.left == null && node.right != null) { //node has right child
					System.out.println("Removing node with right child");
					toReturn = node.value;
					this.root = node.right;
					totalNodes = 0;
					System.out.print("Tree after delete: ");
					this.printTree();
					return toReturn;				
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
					System.out.println("toReturn: " + toReturn);				
					System.out.print("Tree after delete: ");
					this.printTree();
					return toReturn;					
				}		
			}
		else if (node.left == null && node.right == null) { //case 2: node is a leaf
			if (node.parent.left == node) { //leaf is left child
				System.out.println("Leaf is left child");
				toReturn = node.value;
				node.parent.left = null;
				totalNodes--;
				System.out.println("toReturn: " + toReturn);				
				System.out.print("Tree after delete: ");
				this.printTree();
				return toReturn;
			}
			else { //leaf is right child
				System.out.println("Leaf is right child");
				toReturn = node.value;
				System.out.println("toReturn: " + toReturn);				
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
			return toReturn;
		}

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
		
		found = false;
		System.out.println("Setting found to false");
		TreeNode node = recursiveSearch(this.getRoot(), key);

		if (found) {
			//move node to root using splaystep
			this.splaystep(node);
			ComparableKeyValuePair pair = new ComparableKeyValuePair(node.key, node.value);
			return pair;
		}
		else {
			return null;
		}
	}

	public TreeNode recursiveSearch(TreeNode node, Comparable key) {
	
		System.out.println("Searching for key: " + key);
		if (key.compareTo(node.key) == 0) {
			System.out.println("Setting found to true");
			found = true;
			return node;
		}
		//otherwise, traverse further
		if (key.compareTo(node.key) < 0) {
			if (node.left == null) {
				return node;
			}
			else {
				return recursiveSearch(node.left, key);
			}
		}
		else {
			if (node.right == null) {
				return node;
			}
			else {
				return recursiveSearch(node.right, key);
			}
		}
	}

		public TreeNode recursiveSearch2(TreeNode node, Comparable key) {
	
		System.out.println("Searching for key: " + key);
		if (key.compareTo(node.key) == 0) {
			System.out.println("Setting found to true");
			found = true;
			return node;
		}
		//otherwise, traverse further
		if (key.compareTo(node.key) < 0) {
			if (node.left == null) {
				return node;
			}
			else {
				return node;
			}
		}
		else {
			if (node.right == null) {
				return node;
			}
			else {
				return node;
			}
		}
	}

	public void splaystep(TreeNode node) {


		TreeNode parent = node.parent;
		//TreeNode parentparent = node.parent.parent;

		System.out.println("Performing splaystep on: " + node.key);

		if (node == this.getRoot()) {
			System.out.println("Node is already root");
			return;
		}
		if (node == this.getRoot().left || node == this.getRoot().right) { //case 1
			if (node == this.getRoot().left) { //case 1a: target node is root's left child
				System.out.println("case 1a: ");
				TreeNode temp = node.right;
				this.root.left = null;
				node.right = this.getRoot();
				node.parent = null;
				this.root = node;
				if (temp != null) {
					this.insertNode(temp);
				}
				this.printTree();
				return;
			}
			else { //case 1b: target node is root's right child
				System.out.println("case 1b: ");
				TreeNode temp = node.left;
				this.root.right = null;
				node.left = this.getRoot();
				this.getRoot().parent = node;
				node.parent = null;
				this.root = node;
				if (temp != null) {
					this.insertNode(temp);
				}
				this.printTree();
				return;
			}
		}
		else if (this.isLeftChild(node) && this.isLeftChild(node.parent)) { //case2: target and target's parents are left children
				System.out.println("case 2: ");
				TreeNode temp, child;
				child = this.root.left;
				temp = child.right;
				this.root.left = null;
				child.right = this.root;
				this.root = child;
				if (temp != null) {
					this.insertNode(temp);
				}

				this.printTree();
				System.out.println("Root: " + this.getRoot());
				//if node is not the root, splaystep again
				if (this.root != node) {
					System.out.println("Splaystep again");
					this.splaystep(node);
				}


		}
		else if (this.isRightChild(node) && this.isRightChild(node.parent)) { //case 3: target and target's parent are right children
				System.out.println("case 3: ");
				TreeNode temp, child;
				child = this.root.right;
				temp = child.left;
				this.root.right = null;
				child.left = this.root;
				this.root = child;
				if (temp != null) {
					this.insertNode(temp);
				}

				this.printTree();
				System.out.println("Root: " + this.getRoot());
				//if node is not the root, splaystep again
				if (this.root != node) {
					System.out.println("Splaystep again");
					this.splaystep(node);
				}


		}
		else if (this.isRightChild(node) && this.isLeftChild(node.parent)) { //case 4: target is right child, parent is left child
				System.out.println("case 4: ");
				TreeNode temp, child;
				temp = node.left;
				node.left = parent;
				parent.parent.left = node;
				if (temp != null) {
					this.insertNode(temp);
				}
				//if node is not the root, splaystep again
				if (this.root != node) {
					System.out.println("Splaystep again");
					this.splaystep(node);
				}

		}
		else if (this.isLeftChild(node) && this.isRightChild(node.parent)) { //case 5: target is left child, parent is right child
				System.out.println("case 5: ");
				TreeNode temp, child;
				temp = node.right;
				node.right = parent;
				parent.parent.right = node;
				if (temp != null) {
					this.insertNode(temp);
				}
				//if node is not the root, splaystep again
				if (this.root != node) {
					System.out.println("Splaystep again");
					this.splaystep(node);
				}
		}

	}

	public boolean isLeftChild(TreeNode node) { //used in the splaystep method to determine which case applies

		TreeNode parent = node.parent;
		if (parent == null) {
			return false;
		}
		else   if (parent.left == node) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isRightChild(TreeNode node) { //used in the splaystep method to determine which case applies

		TreeNode parent = node.parent;
		if (parent == null) {
			return false;
		}		
		else if (parent.right == node) {
			return true;
		}
		else {
			return false;
		}
	}

	public java.util.Enumeration getKeys() {
		KeyEnumerator keys = new KeyEnumerator();
		System.out.println("Totalnodes: " + totalNodes);
		keys.createKeysEnumerator(totalNodes, this.getRoot());
		return keys;

	}

	public java.util.Enumeration getValues() {
		ValueEnumerator values = new ValueEnumerator();
		System.out.println("Totalnodes: " + totalNodes);
		values.createValuesEnumerator(totalNodes, this.getRoot());
		return values;
	}

	public int getCurrentSize() {
		return totalNodes;
	}


	public void initialize(int maxSize) {
		System.out.println("Initializing new tree...");
		this.root = null;
		totalNodes = 0; //counter to keep track of treenodes

	}

	public String getName() {
		return "michael5486's implementation of SelfAdjTree";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

	public java.lang.Comparable successor(java.lang.Comparable key) {
		
		System.out.println("Key: " + key);
		KeyEnumerator keys = new KeyEnumerator();
		keys.createKeysEnumerator(totalNodes, this.getRoot());
		System.out.println("Successor: " + keys.nextKey(key));
    	return keys.nextKey(key);   
    }

    public java.lang.Comparable predecessor(java.lang.Comparable key) {

		System.out.println("Key: " + key);		
		KeyEnumerator keys = new KeyEnumerator();
		keys.createKeysEnumerator(totalNodes, this.getRoot());
		System.out.println("Predecessor: " + keys.prevKey(key));
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
    	SelfAdjTree tree = new SelfAdjTree();
    	tree.insert(4, 4);
    	tree.insert(2, 2);
    	tree.insert(3, 3);
    	tree.insert(1, 1);
    	//tree.insert(1, 1);
    	//tree.insert(6, 6);

    	//System.out.println(tree.getRoot().toString());
    	tree.printTree();
    	//System.out.println("Search for 2: " + tree.search(2));
    	//tree.splaystep(tree.recursiveSearch(tree.getRoot(), 6));
    	System.out.println("Root: " + tree.getRoot());


    }


}