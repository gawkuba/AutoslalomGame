package p02.pres;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ImageRenderer extends JLabel implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value instanceof Image) {
            setIcon(new ImageIcon((Image) value));
        } else {
            setIcon(null);
        }
        return this;
    }
}