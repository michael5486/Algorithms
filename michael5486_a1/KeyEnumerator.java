
import java.util.*;

class KeyEnumerator implements Enumeration {

    Comparable[] keys;
    
    int index = 0;

    public void createKeysEnumerator(int maxSize) {
        keys = new Comparable[maxSize];
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


    public Object nextElement ()
    {
        Comparable k = keys[index];
        index++;
        return k;
    }
    
    
    public Enumeration getKeysEnumeration ()
    {
        index = 0;
        return this;
    }
}