package Render;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.HashMap;

public final class ImageLoader
{
    private static final HashMap<String, ImageIcon> CACHE = new HashMap<>();

    private ImageLoader()
    {

    }


    public static ImageIcon loadImageIcon(final String path)
    {
        if (path == null)
        {
            throw new IllegalArgumentException("path is null");
        }

        ImageIcon icon = CACHE.get(path);
        if (icon != null)
        {
            return icon;
        }

        icon = new ImageIcon(path);

        // 로드 실패 체크 (폭/높이가 0 이하면 잘못된 이미지)
        if (icon.getIconWidth() <= 0 || icon.getIconHeight() <= 0)
        {
            throw new RuntimeException("Error: can not load image (" + path + ")");
        }

        CACHE.put(path, icon);
        return icon;
    }

    public static ImageIcon loadScaledImageIcon(final String path,
                                                final int width,
                                                final int height)
    {
        if (width <= 0 || height <= 0)
        {
            throw new IllegalArgumentException("width and height must be positive");
        }

        ImageIcon baseIcon = loadImageIcon(path);
        Image original = baseIcon.getImage();
        Image scaled = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(scaled);
    }

    public static ImageIcon load(final String path)
    {
        return loadImageIcon(path);
    }

    public static ImageIcon loadScaled(final String path,
                                       final int width,
                                       final int height)
    {
        return loadScaledImageIcon(path, width, height);
    }
}
