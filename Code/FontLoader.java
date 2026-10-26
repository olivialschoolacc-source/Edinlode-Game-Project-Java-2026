package Code;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
    public static Font loadCustomFont(String resourcePath, float fontSize) {
        try {
            InputStream is = FontLoader.class.getResourceAsStream(resourcePath);
            if (is == null) {
                throw new IOException("Font resource not found: " + resourcePath);
            }
            
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, is);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            return customFont.deriveFont(fontSize);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            System.err.println("Could not load custom font, falling back to default.");
            return new Font("Times New Roman", Font.PLAIN, (int) fontSize);
        }
    }
}