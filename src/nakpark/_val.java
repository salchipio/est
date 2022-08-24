package nakpark;

import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class _val {
    
    public void placakey(KeyEvent evt, JTextField x) {
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
            x.replaceSelection("");
        }
        if (x.getText().length() >= 8) {
            evt.consume();
        }
    }

    public void textokeynom(KeyEvent evt, JTextField x) {
        if (x.getText().length() >= 20) {
            evt.consume();
            x.replaceSelection("");
        }
    }

    public void textokey(KeyEvent evt, JTextField x) {
        if (x.getText().length() >= 50) {
            evt.consume();
            x.replaceSelection("");
        }
    }

    public void textolargokey(KeyEvent evt, JTextArea x) {
        if (x.getText().length() >= 300) {
            evt.consume();
            x.replaceSelection("");
        }
    }

    public void numerooncekey(KeyEvent evt, JTextField x) {
        char n = evt.getKeyChar();
        if (Character.isLetter(n) || x.getText().length() >= 11) {
            x.replaceSelection("");
            evt.consume();
        }
    }

    public void numeronuevekey(KeyEvent evt, JTextField x) {
        char n = evt.getKeyChar();
        if (Character.isLetter(n) || x.getText().length() >= 9) {
            x.replaceSelection("");
            evt.consume();
        }
    }

    public void numerosietekey(KeyEvent evt, JTextField x) {
        char n = evt.getKeyChar();
        if (Character.isLetter(n) || x.getText().length() >= 7) {
            x.replaceSelection("");
            evt.consume();
        }
    }

    public void numerocuatrokey(KeyEvent evt, JTextField x) {
        char n = evt.getKeyChar();
        if (Character.isLetter(n) || x.getText().length() >= 4) {
            x.replaceSelection("");
            evt.consume();
        }
    }
}
