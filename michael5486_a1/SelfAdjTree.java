import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class SelfAdjTree implements TreeSearchAlgorithm {

	public TreeNode root = new TreeNode();
	public static int count = 0; //creates unique ID for each treenode

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
		return null;
	}

	public void recursiveInsert(TreeNode node, Comparable key, Object value) {

	}

	public java.lang.Object delete(java.lang.Comparable key) {
		return null;
	}

	public edu.gwu.algtest.ComparableKeyValuePair maximum() {

		return null;
		//return ComparableKeyValuePair
	}

	public edu.gwu.algtest.ComparableKeyValuePair minimum() {
			return null;
		//return ComparableKeyValuePair
	}

	public edu.gwu.algtest.ComparableKeyValuePair search(java.lang.Comparable key) {
		
		return recursiveSearch(this.getRoot(), key);
	}

	public ComparableKeyValuePair recursiveSearch(TreeNode node, Comparable key) {
		return null;
	}

	public java.util.Enumeration getKeys() {
		return null;
		//getKeys should return an Enumeration that can iterate through the keys.

	}

	public java.util.Enumeration getValues() {
		return null;
		//getValues should return an Enumeration that can iterate through values.

	}

	public int getCurrentSize() {
		return 0;
	}


	public void initialize(int maxSize) {


	}

	public String getName() {
		return "michael5486's implementation of SelfAdjTree";
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
    	SelfAdjTree tree = new SelfAdjTree();
    	tree.insert(4, 4);
    	tree.insert(5, 5);
    	tree.insert(3, 3);
    	//System.out.println(tree.getRoot().toString());
    	tree.printTree();
    	System.out.println("Search for 4: " + tree.search(4));

    }


}