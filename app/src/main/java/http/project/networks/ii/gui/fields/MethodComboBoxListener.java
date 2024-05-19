package http.project.networks.ii.gui.fields;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * A listener for the method combo box
 */
public class MethodComboBoxListener implements ItemListener {

    private static final String GET_METHOD = "GET";
    private static final String HEAD_METHOD = "HEAD";

    private String method;
    private BodyField bodyField;

    /**
     * Create a new MethodComboBoxListener
     * @param method the method
     * @param bodyField the body field
     */
    public MethodComboBoxListener(String method, BodyField bodyField) {
        this.method = method;
        this.bodyField = bodyField;
    }

    /**
     * Handle an item state changed event
     * @param e the event
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if ((e.getStateChange() == ItemEvent.SELECTED)
            && (this.method.equals(GET_METHOD) || this.method.equals(HEAD_METHOD))) {
            bodyField.getBodyContentTextArea().setEnabled(false);
        } else {
            bodyField.getBodyContentTextArea().setEnabled(true);
        }
    }
}
