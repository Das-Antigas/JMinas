/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package darth.jminas;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author luciano
 */
public class Language {

    private static Language uniqueInstance;
    Locale locale;
    ResourceBundle messages;

    private Language() {
        this.locale = new Locale("pt", "BR");
        this.loadResourceFile();
    }

    private void loadResourceFile() {
        messages = ResourceBundle.getBundle("messages", this.locale);
    }

    public String getString(String key) {
        return this.messages.getString(key);
    }

    public static synchronized Language getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Language();
        }

        return uniqueInstance;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        this.loadResourceFile();
    }

}
