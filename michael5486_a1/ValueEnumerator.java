import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;
import java.util.Enumeration;
import java.util.*;

class ValueEnumerator implements Enumeration {

    Object[] values;
    int fillIndex = 0;
    int index = 0;


    public void createValuesEnumerator(int maxSize, TreeNode node) {
        values = new Object[maxSize];
        this.recursiveFillArray(node);
    }

    public boolean hasMoreElements ()
    {
        if (index < values.length) {
            return true;
        }
        else {
            return false;
        }
    }


    public Object nextElement ()
    {
        Object v = values[index];
        index++;
        return v;
    }
    
    
    public Enumeration getValuesEnumeration ()
    {
        index = 0;
        return this;
    }

    public void recursiveFillArray(TreeNode node) {

        if (node == null) {

        }
        else {
            recursiveFillArray(node.left);
            values[fillIndex] = node.value;
            fillIndex++;
            recursiveFillArray(node.right);
        }
    }
}