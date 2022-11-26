package paint;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class PanelA extends JPanel {
	int shapeType = -1, selected_idx = -1;
	Point start = null, end = null;
	ArrayList<Shape> shapes;
	boolean isPainting = false;
	int action;
	
	public PanelA() {
		shapes = new ArrayList<Shape>();
		setBackground(Color.YELLOW);
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Point point = e.getPoint();
				int idx = getPressedShapeIndex(point);
				if(idx != -1)
					selected_idx = idx;
				else
					selected_idx = -1;
				start = null;
				end = null;
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int clickedShapeIdx = getPressedShapeIndex(point);
				int edgeclicked = selected_idx != -1 ? isEdgeClicked(point) : 0;
				if(selected_idx != -1 && (selected_idx == clickedShapeIdx || edgeclicked != 0)) {
					start = null;
					end = null;
					isPainting = false;
					Shape selected = shapes.get(selected_idx);
					
					switch(edgeclicked) {
					case -1:
						action = Action.DRAG_START;
						break;
					case 1:
						action = Action.DRAG_END;
						break;
					case 0:
						action = Action.MOVE;
						break;
					}
				} else {
					action = Action.DRAW;
					start = e.getPoint();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(isPainting) {
					shapes.add(new Shape(shapeType, start, end));
					isPainting = false;
				}
			}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point point = e.getPoint();
				int clickedShapeIdx = getPressedShapeIndex(point);
				
				Shape selected = selected_idx != -1 ? shapes.get(selected_idx) : null;
				switch(action) {
				case Action.DRAW:
					selected_idx = -1;
					end = e.getPoint();
					isPainting = true;
					break;
				case Action.MOVE:
					selected.setCenter(point);
					break;
				case Action.DRAG_START:
					selected.setStart(point);
					break;
				case Action.DRAG_END:
					selected.setEnd(point);
					break;
				}
				repaint();
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				if(selected_idx != -1 && isEdgeClicked(e.getPoint()) != 0)
					setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				else
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		
		
		setLayout(null);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(selected_idx != -1) {
			Shape shape = shapes.get(selected_idx);
			g.fillRect(shape.getStart().x-2, shape.getStart().y-2, 4, 4); 
			g.fillRect(shape.getEnd().x-2, shape.getEnd().y-2, 4, 4);
		}
		
		if(shapeType == -1)
			return;
		
		g.setColor(Color.BLUE);
		
		for(int i=0; i<shapes.size(); i++) {
			Shape shape = shapes.get(i);
			
			switch(shape.getType()) {
			case ShapeType.RECTANGLE:
				g.drawRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
				break;
			case ShapeType.STRAIGHT:
				g.drawLine(shape.getStart().x, shape.getStart().y, shape.getEnd().x, shape.getEnd().y);
				break;
			case ShapeType.OVAL:
				g.drawOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
				break;
			}
		}
		if(start != null && end != null) {
	        int x = Math.min(start.x, end.x);
	        int y = Math.min(start.y, end.y);
	        int width = Math.abs(start.x - end.x);
	        int height = Math.abs(start.y - end.y);
			
			switch(shapeType) {
			case ShapeType.RECTANGLE:
				g.drawRect(x, y, width, height);
				break;
			case ShapeType.STRAIGHT:
				g.drawLine(start.x, start.y, end.x, end.y);
				break;
			case ShapeType.OVAL:
				g.drawOval(x, y, width, height);
				break;
			}
		}
	}
	
	private int getPressedShapeIndex(Point point) {
		for(int i=shapes.size()-1; 0<=i; i--) {
			if(isPointEntered(shapes.get(i), point)) {
				return i;
			}
		}
		return -1;
	}
	
	private int isEdgeClicked(Point point) {
		Shape selected = shapes.get(selected_idx);
		int start_x = selected.getStart().x, start_y = selected.getStart().y;
		int end_x = selected.getEnd().x, end_y = selected.getEnd().y;
		
		if((start_x-2 < point.x && point.x < start_x+2) && (start_y-2 < point.y && point.y < start_y+2)) {
			return -1;
		}
		if((end_x-2 < point.x && point.x < end_x+2 && end_y-2 < point.y && point.y < end_y+2)) {
			return 1;
		}
		return 0;
	}
	
	private boolean isPointEntered(Shape target, Point point) {
		return (target.getStart().x < point.x && point.y < target.getEnd().x) && (target.getStart().y < point.y && point.y < target.getEnd().y) ? true : false;
	}
	
	private class Action {
		public final static int DRAW = 0, MOVE = 1, DRAG_START = 2, DRAG_END = 3;
	}
}
