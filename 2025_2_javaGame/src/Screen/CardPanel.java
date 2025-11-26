package Screen;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

public class CardPanel extends JPanel {

    public CardPanel() {
        this(BoxLayout.Y_AXIS);
        
        setOpaque(false);
    }

    public CardPanel(int axis) {
        setLayout(new BoxLayout(this, axis));
        setOpaque(false);
        
    }

    public void addImage(String imgPath) {
        ImageIcon icon = new ImageIcon(imgPath);
        JLabel imgLabel = new JLabel(icon);
        add(imgLabel);
    }

    public void addCenteredImage(String imgPath) {
        ImageIcon icon = new ImageIcon(imgPath);
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(imgLabel);
    }

    public void addScaledImage(String imgPath, int width, int height) {
        Image img = new ImageIcon(imgPath).getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(scaled));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(imgLabel);
    }

    public void addBorder(Color color, int thickness) {
        this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(color, thickness),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }
    
    public void addText(String text) {
        JLabel label = new JLabel(text);
        add(label);
    }

    public void addText(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        add(label);
    }

    public void addCenteredText(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);
    }

    public void addLeftText(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(label);
    }

    public void addText(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        add(label);
    }

    public void addText(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        add(label);
    }

}
