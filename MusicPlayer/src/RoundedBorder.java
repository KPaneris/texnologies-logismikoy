import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

class RoundedBorder extends AbstractBorder {
    private final int radius;
    private final Color borderColor;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.borderColor = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);  // Set the border color
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(10, 20, 10, 20); // Adjust padding as needed
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
