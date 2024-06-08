package game.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * HintView: 힌트 이미지를 표시하는 JDialog 클래스입니다.
 */
public class HintView extends JDialog {
    public HintView(Frame owner) {
        super(owner, "Hint", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        ImagePanelView hintPanel = new ImagePanelView("/resource/images/puzzleGame1.jpg");
        add(hintPanel);

        setSize(400, 400);
        setLocationRelativeTo(owner);
    }
}
