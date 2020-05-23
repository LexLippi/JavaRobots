package gui;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.prefs.Preferences;
import java.util.ResourceBundle;

public class ProgramPreferences {
    private Preferences prefs;
    private ResourceBundle defaultBundle;


    public ProgramPreferences(){
        prefs = Preferences.userRoot().node("pref");
        try {
            defaultBundle = ResourceBundle.getBundle("Bundles.Bundle",
                            new Locale(System.getProperty("user.language"), System.getProperty("user.country")));
        } catch (MissingResourceException e){
            defaultBundle = ResourceBundle.getBundle("Bundles.Bundle",
                            new Locale("en", "EN"));
        }

    }

    enum Bundles{
        RU, EN;
        static ResourceBundle toBundle(Bundles bundleKey){
            return ResourceBundle.getBundle("Bundles.Bundle",
                    new Locale(bundleKey.toString().toLowerCase(), bundleKey.toString()));
        }
    }

    private int bundleToInt(ResourceBundle bundle){
        return Bundles.valueOf(bundle.getLocale().toString().toUpperCase()).ordinal();
    }

    public ResourceBundle getBundle(){
        int intDefBundle = bundleToInt(defaultBundle);
        return Bundles.toBundle(Bundles.values()[prefs.getInt("bundle", intDefBundle )]);
    }

    public void setBundle(ResourceBundle bundle){
        prefs.putInt("bundle", bundleToInt(bundle));
    }
}
