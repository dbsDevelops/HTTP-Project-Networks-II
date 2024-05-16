package http.project.networks.ii.gui.fields;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MethodComboBoxListener implements ItemListener {

    private static final String GET_METHOD = "GET";
    private static final String HEAD_METHOD = "HEAD";

    private String method;
    private BodyField bodyField;

    public MethodComboBoxListener(String method, BodyField bodyField) {
        this.method = method;
        this.bodyField = bodyField;
    }

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
