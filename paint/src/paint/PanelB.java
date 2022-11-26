package paint;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PanelB extends JPanel{
	JButton buttonRect, buttonStraight, buttonOval, buttonCopy, buttonDelete, buttonSave, buttonLoad;
	PanelA pa;
	
	public PanelB(PanelA pa) {
		this.pa = pa;
		this.setBackground(Color.BLUE);
		this.setLayout(new GridLayout(7, 1, 5, 5));
		buttonRect = new JButton("사각");
		buttonStraight = new JButton("직선");
		buttonOval = new JButton("타원");
		buttonCopy = new JButton("복사");
		buttonDelete = new JButton("삭제");
		buttonSave = new JButton("저장");
		buttonLoad = new JButton("불러오기");
		
		buttonRect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pa.shapeType = ShapeType.RECTANGLE;
			}
		});
		buttonStraight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pa.shapeType = ShapeType.STRAIGHT;
			}
		});
		buttonOval.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pa.shapeType = ShapeType.OVAL;
			}
		});
		buttonCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pa.selected_idx != -1) {
					Shape selected = pa.shapes.get(pa.selected_idx);
					pa.shapes.add(new Shape(selected.getType(), new Point(selected.getStart().x+10, selected.getStart().y+10), new Point(selected.getEnd().x+10, selected.getEnd().y+10)));
					pa.selected_idx = -1;
					pa.repaint();
				}
			}
		});
		buttonDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pa.selected_idx != -1) {
					Shape selected = pa.shapes.remove(pa.selected_idx);
					pa.selected_idx = -1;
					pa.repaint();
				}
			}
		});
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		buttonLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		this.add(buttonRect);
		this.add(buttonStraight);
		this.add(buttonOval);
		this.add(buttonCopy);
		this.add(buttonDelete);
		this.add(buttonSave);
		this.add(buttonLoad);
	}
}
