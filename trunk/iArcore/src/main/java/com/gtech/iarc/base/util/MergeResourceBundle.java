package com.gtech.iarc.base.util;



import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.ResourceBundle;

/**
 * The MergeResourceBundle can be used to merge/overwrite a new
 * ResourceBundle from two different ResourceBundles.
 * Example: The keys from ResourceBundle.properties should be
 * overwritten/extended by ResourceBundle-ext.properties.
 * So ResourceBundle.properties must be used as the "DefaultBundle"
 * and ResourceBundle-ext.properties must be set as the "OverwriteByBundle"�.
 *
 */
public class MergeResourceBundle extends ResourceBundle {

	private ResourceBundle defaultBundle;
	private ResourceBundle overwriteByBundle;

	public MergeResourceBundle(ResourceBundle defaultBundle,
	                           ResourceBundle overwriteByBundle) {

		if (defaultBundle == null
		    || overwriteByBundle == null) {
			throw new InvalidParameterException("defaultBundle and/or overwriteByBundle is null!!!");
		}

		this.defaultBundle = defaultBundle;
		this.overwriteByBundle = overwriteByBundle;

	}

	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#getKeys()
	 */
	public Enumeration getKeys() {

		HashSet set = new HashSet();

		set.addAll(Collections.list(defaultBundle.getKeys()));
		set.addAll(Collections.list(overwriteByBundle.getKeys()));

		return Collections.enumeration(set);
	}


	/* (non-Javadoc)
	 * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
	 */
	protected Object handleGetObject(String key) {
		Object ret = null;

		try {
			ret = overwriteByBundle.getObject(key);
		} catch (Exception e) {
			// no action necessary because the handleGetObject don't throw an
			// exception it returns null and the concrete accessor method, e.g.
			// getString throws the Exception
		}

		if (ret == null) {
			// nothing found in the overwriteByBundle so try to get the value from the
			// the defaultBundle
			try {
				ret = defaultBundle.getObject(key);
			} catch (Exception e) {
				// no action necessary because the handleGetObject don't throw an
				// exception it returns null and the concrete accessor method, e.g.
				// getString throws the Exception
			}
		}

		return ret;
	}

}
