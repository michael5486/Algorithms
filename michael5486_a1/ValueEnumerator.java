
import java.util.*;

class ValueEnumerator implements Enumeration {

    Object[] values;
    
    int index = 0;

    public void createValuesEnumerator(int maxSize) {
        values = new Object[maxSize];
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
}