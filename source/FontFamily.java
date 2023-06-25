package javaCode;
import java.awt.GraphicsEnvironment;
public class FontFamily {

	public static void main(String[] args) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontName[] = ge.getAvailableFontFamilyNames();
		for(int i = 0;i < fontName.length;i++) {
			System.out.println(fontName[i]);
		}
	}
}
