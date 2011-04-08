package com.gtech.iarc.base.util;

/**
 * Utility for making deep copies (vs. clone()'s shallow copies) of objects. Objects are first serialized 
 * and then deserialized. Error checking is fairly minimal in this implementation. If an object is encountered 
 * that cannot be serialized (or that references an object that cannot be serialized) an error is printed to 
 * System.err and null is returned. Depending on your specific application, it might make more sense to have 
 * copy(...) re-throw the exception.
 * <p>
 * http://javatechniques.com/public/java/docs/basics/faster-deep-copy.html 
 */
public class DeepCopy {
    /**
     * Returns a copy of the object, or null if the object cannot be serialized.
     */
    public static Object copy(Object orig) {
        Object obj = null;
        try {
            // Write the object out to a byte array
            FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
            java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(fbos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Retrieve an input stream from the byte array and read
            // a copy of the object back in. 
            java.io.ObjectInputStream in = new java.io.ObjectInputStream(fbos.getInputStream());
            obj = in.readObject();
        }
        catch(java.io.IOException ex) {
            ex.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }
}

