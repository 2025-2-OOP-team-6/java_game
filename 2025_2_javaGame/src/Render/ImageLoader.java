package Render;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.HashMap;

/**
 * 이미지 로드 & 캐싱 유틸리티
 * - 같은 경로의 이미지를 여러 번 요청해도 한 번만 로드하고 재사용
 * - 필요하면 스케일링된 ImageIcon도 생성
 *
 * 사용 예)
 *   ImageIcon icon = ImageLoader.loadImageIcon("..//assets//battle//player.png");
 *   ImageIcon scaled = ImageLoader.loadScaledImageIcon("..//assets//battle//player.png", 200, 200);
 *
 *   // 호환용
 *   ImageIcon icon2   = ImageLoader.load("..//assets//battle//player.png");
 *   ImageIcon scaled2 = ImageLoader.loadScaled("..//assets//battle//player.png", 200, 200);
 */
public final class ImageLoader
{
    private static final HashMap<String, ImageIcon> CACHE = new HashMap<>();

    private ImageLoader()
    {
        // 유틸 클래스이므로 인스턴스 생성 금지
    }

    // ─────────────────────────────
    // 실제 기본 메서드
    // ─────────────────────────────
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

    // ─────────────────────────────
    // 호환용 메서드 (기존 코드용)
    // ─────────────────────────────

    /** 기존 코드에서 사용한 이름: ImageLoader.load(...) */
    public static ImageIcon load(final String path)
    {
        return loadImageIcon(path);
    }

    /** 기존 코드에서 사용한 이름: ImageLoader.loadScaled(...) */
    public static ImageIcon loadScaled(final String path,
                                       final int width,
                                       final int height)
    {
        return loadScaledImageIcon(path, width, height);
    }
}
