package com.ebstrada.aggregation.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class Localizer {
    
    public static Locale currentLocale = new Locale("en");
    
    private static final Logger logger = Logger.getLogger(Localizer.class);

    public static ResourceBundle bundle = 
	    ResourceBundle.getBundle("com.ebstrada.aggregation.i18n.resources.messages", currentLocale);
    
    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static String localize(String key) {
	try {
	    return bundle.getString(key);
	} catch ( Exception ex ) {
	    logger.warn(ex.getLocalizedMessage(), ex);
	    logger.warn("Missing translation: " + key);
	    return key;
	}
    }

    public static void setCurrentLocale(Locale currentLocale) {
        Localizer.currentLocale = currentLocale;
        try {
            ResourceBundle.getBundle("com.ebstrada.aggregation.i18n.resources.messages", currentLocale);
        } catch ( Exception ex ) {
            ResourceBundle.getBundle("com.ebstrada.aggregation.i18n.resources.messages", new Locale("en"));
        }
    }

}
