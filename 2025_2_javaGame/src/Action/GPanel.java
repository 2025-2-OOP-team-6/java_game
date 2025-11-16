package Action;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.io.File;
import java.io.IOException;

public class GPanel extends JPanel {

    private BufferedImage image;   
    private float scale = 1.0f;

    public GPanel(ImageIcon icon) {
        this.image = toARGB(icon.getImage());
        setBounds(0, 0);
        setOpaque(false); 
    }

    
    public GPanel(String imagePath) {
        try {
            BufferedImage readImg = ImageIO.read(new File(imagePath));
            this.image = toARGB(readImg); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBounds(0, 0);
        setOpaque(false);
    }

    private BufferedImage toARGB(Image img) {
        BufferedImage argbImg = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = argbImg.createGraphics();
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return argbImg;
    }

    public void setBounds(int x, int y) {
        super.setBounds(
                x,
                y,
                (int) (scale * image.getWidth()),
                (int) (scale * image.getHeight())
        );
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    public void setScale(float scale) {
        this.scale = scale;
        // 크기 다시 계산
        setBounds(getX(), getY());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);

        g2.drawImage(image, at, this);
        g2.dispose();
    }

}
