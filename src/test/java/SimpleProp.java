import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class SimpleProp {
    public void setValue(float value) {
        System.out.println("Cool " + value);
    }

    public static void main(String[] args) throws Exception {
        SimpleProp prop = new SimpleProp();
        PropertyDescriptor desc = new PropertyDescriptor("value", prop.getClass(), null, "setValue");
        Method writer = desc.getWriteMethod();
        Object o = writer.invoke(prop, Float.valueOf(2.0f));
        System.out.println("writing 2.0f to SimpleProp prop returns " + o); // null ?????????????
        
        // was soll uns dieses Bespiel sagen?
        /*
Determines if the class or interface represented by this Class object is either the same as, 
or is a superclass or superinterface of, the class or interface represented by the specified Class parameter. 
It returns true if so;
otherwise it returns false.
 
If this Class object represents a primitive type, this method returns true 
if the specified Class parameter is exactly this Class object; 
otherwise it returns false. 

Float isAssignableFrom (float) ==> false , dh. Float.class ist nicht float.class
                                           und float.class ist auch nicht subclass von float.class
         */
        System.out.println(Float.class.isAssignableFrom(float.class)); // false
        
    }
}
