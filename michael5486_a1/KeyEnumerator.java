import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.util.Enumeration;
import java.util.*;

class KeyEnumerator implements Enumeration {

    Comparable[] keys;
    int fillIndex = 0;
    int index = 0;


    public void createKeysEnumerator(int maxSize, TreeNode node) {
        fillIndex = 0; 
        index = 0;
        keys = new Comparable[maxSize];
        this.recursiveFillArray(node);
    }

    public boolean hasMoreElements ()
    {
        if (index < keys.length) {
            return true;
        }
        else {
            return false;
        }
    }


    public Comparable nextElement ()
    {
        Comparable k = keys[index];
        index++;
        return k;
    }

    public Comparable nextKey(Comparable key) {
        int i;
        for (i = 0; i < keys.length - 1; i++) {
            if (keys[i] == key) {
                break;
            }
        }
        i = i + 1;
        System.out.println("i: " + i);
        if (i < 0 || i > keys.length - 1) {
            return null;
        }
        else { 
            return keys[i];
        }
    }    

   public Comparable prevKey(Comparable key) {
        int i;
        for (i = 0; i < keys.length - 1; i++) {
            if (keys[i] == key) {
                break;
            }
        }
        i--;
        if (i < 0 || i > keys.length - 1) {
            return null;
        }
        else { 
            return keys[i];
        }
    }
    
    
    public Enumeration getKeysEnumeration ()
    {
        index = 0;
        return this;
    }

    public void recursiveFillArray(TreeNode node) {

        if (node == null) {

        }
        else {
            recursiveFillArray(node.left);
            keys[fillIndex] = node.key;
            fillIndex++;
            recursiveFillArray(node.right);
        }
    }
}