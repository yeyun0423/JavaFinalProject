package game.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * ImagePanelView: 배경 이미지를 표시하는 JPanel 클래스입니다.
 */
public class ImagePanelView extends JPanel {
    private Image image;

    public ImagePanelView(String imagePath) {
        URL imgURL = getClass().getResource(imagePath);
        if (imgURL != null) {
            this.image = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Couldn't find file: " + imagePath);
            this.image = null;
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
