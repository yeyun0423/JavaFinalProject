package puzzle.View;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String imagePath) {
        URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            this.image = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Couldn't find file: " + imagePath);
            this.image = null;  // 이미지를 찾을 수 없을 경우
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
