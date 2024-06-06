package puzzle.View;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String imagePath) {
        this.image = new ImageIcon(getClass().getResource(imagePath)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
