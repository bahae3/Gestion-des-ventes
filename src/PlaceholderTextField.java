import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField implements FocusListener {
    private String placeholder;

    public PlaceholderTextField(String text) {
        this.placeholder = text;
        setForeground(Color.GRAY); // Set placeholder text color
        setText(placeholder); // Initially set the text to the placeholder
        addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (getText().equals(placeholder)) {
            setText("");
            setForeground(Color.BLACK); // Change text color when focused
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().isEmpty()) {
            setForeground(Color.GRAY); // Reset placeholder text color
            setText(placeholder);
        }
    }
}
