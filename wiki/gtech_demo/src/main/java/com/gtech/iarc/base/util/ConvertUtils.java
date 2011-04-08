package com.gtech.iarc.base.util;

import org.hibernate.Hibernate;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.sql.Blob;
import java.sql.SQLException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Utility class to convert one object to another.
 *
 * <p>
 * <a href="ConvertUtils.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @version $Revision: 1.1 $ $Date: 2006/02/06 05:32:48 $
 */
public class ConvertUtils {
    private static Log log = LogFactory.getLog(ConvertUtils.class);

    /**
     * Method to convert a ResourceBundle to a Map object.
     * @param rb a given resource bundle
     * @return Map a populated map
     */
    public static Map convertBundleToMap(ResourceBundle rb) {
        Map map = new HashMap();

        for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = (String) keys.nextElement();
            map.put(key, rb.getString(key));
        }

        return map;
    }

    /**
     * Method to convert a ResourceBundle to a Properties object.
     * @param rb a given resource bundle
     * @return Properties a populated properties object
     */
    public static Properties convertBundleToProperties(ResourceBundle rb) {
        Properties props = new Properties();

        for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();) {
            String key = (String) keys.nextElement();
            props.put(key, rb.getString(key));
        }

        return props;
    }

    /**
     * Convenience method used by tests to populate an object from a
     * ResourceBundle
     * @param obj an initialized object
     * @param rb a resource bundle
     * @return a populated object
     */
    public static Object populateObject(Object obj, ResourceBundle rb) {
        try {
            if (obj == null) {
                obj = obj.getClass().newInstance();
            }

            Map map = convertBundleToMap(rb);

            BeanUtils.copyProperties(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception occured populating object: " + e.getMessage());
        }

        return obj;
    }

    /**
     * This method converts an Object that implements the java.io.Serializable
     * interface into the java.sql.Blob object.
     *
     * @param object DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Blob convertSerializableObjectToBlob(Object object) {
        return Hibernate.createBlob(SerializationUtils.serialize(
                (Serializable) object));
    }

    /**
     * This method converts the java.sql.Blob into the java.lang.Object.
     *
     * @param blob DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static Object convertBlobToObject(Blob blob) {
        return SerializationUtils.deserialize(toByteArray(blob));
    }

    private static byte[] toByteArray(Blob fromBlob) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            return toByteArrayImpl(fromBlob, baos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    private static byte[] toByteArrayImpl(Blob fromBlob,
        ByteArrayOutputStream baos) throws SQLException, IOException {
        byte[] buf = new byte[4000];
        InputStream is = fromBlob.getBinaryStream();

        try {
            for (;;) {
                int dataSize = is.read(buf);

                if (dataSize == -1) {
                    break;
                }

                baos.write(buf, 0, dataSize);
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        }

        return baos.toByteArray();
    }
}
