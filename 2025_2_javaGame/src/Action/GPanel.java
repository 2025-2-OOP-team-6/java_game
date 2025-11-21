package Action;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Util.Position;

import javax.swing.ImageIcon;

import java.io.File;
import java.io.IOException;

public class GPanel extends JPanel {

    private BufferedImage image;   
    private float scale = 1.0f;
    private String imagePath;
    public GPanel(ImageIcon icon) {
        this.image = toARGB(icon.getImage());
        setBounds(0, 0);
        setOpaque(false); 
    }
    
    private Position pos = new Position(0,0);

    
    public GPanel(String imagePath) {
        try {
            BufferedImage readImg = ImageIO.read(new File(imagePath));
            this.image = toARGB(readImg); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.imagePath = imagePath;
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
        if (x!=0) {
        	pos.x = x;
            pos.y = y;
        }
        
    }
    
    public void darken(int amount) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgba = image.getRGB(x, y);

                int a = (rgba >> 24) & 0xFF;
                int r = (rgba >> 16) & 0xFF;
                int g = (rgba >> 8)  & 0xFF;
                int b = rgba & 0xFF;

                // amount 만큼 밝기 조절 (양수=밝게, 음수=어둡게)
                r = Math.min(255, Math.max(0, r + amount));
                g = Math.min(255, Math.max(0, g + amount));
                b = Math.min(255, Math.max(0, b + amount));

                int newRGBA = (a << 24) | (r << 16) | (g << 8) | b;
                image.setRGB(x, y, newRGBA);
            }
        }

        repaint();
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
    
    public String getImagePath() {
    	return imagePath;
    }

    public void setImage(String imagePath) {
    	try {
            BufferedImage readImg = ImageIO.read(new File(imagePath));
            this.image = toARGB(readImg); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.imagePath = imagePath;
        setBounds(pos.x, pos.y);
    }
}
