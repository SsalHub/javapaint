package paint;

import java.awt.*;
import javax.swing.*;

public class FigureEditorFrame extends JFrame {
	public FigureEditorFrame() {
		setTitle("Figure Editor");
		setSize(500, 300);
		
		PanelA pa = new PanelA();
		PanelB pb = new PanelB(pa);
		PanelC pc = new PanelC(pb);
		this.add(pa);		// default layout = BorderLayout.CENTER
		
		this.add(pc, BorderLayout.LINE_START);
		
		setVisible(true);
	}
}
