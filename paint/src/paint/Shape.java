package paint;

import java.awt.Point;

public class Shape {
	private int type;
	private Point start, end, center;
	private int width, height;
	
	public Shape(int type, Point start, Point end) {
		this.type = type;
		this.start = start;
		this.end = end;
		this.width = Math.abs(this.start.x - this.end.x);
		this.height = Math.abs(this.start.y - this.end.y);
		this.center = new Point(start.x+width/2, start.y+height/2);
	}
	public int getType() {
		return this.type;
	}
	
	public int getX() {
		return Math.min(this.start.x, this.end.x);
	}
	
	public int getY() {
		return Math.min(this.start.y, this.end.y);
	}

	public Point getStart() {
		return start;
	}
	
	public Point getEnd() {
		return end;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setStart(Point point) {
		start = point;
		setWidth();
		setHeight();
		center.move(start.x+width/2, start.y+height/2);
	}
	
	public void setEnd(Point point) {
		end = point;
		setWidth();
		setHeight();
		center.move(start.x+width/2, start.y+height/2);
	}
	
	public void setCenter(Point point) {
		center = point;
		start.move(center.x-width/2, center.y-height/2);
		end.move(center.x+width/2, center.y+height/2);
	}

	public void setWidth() {
		this.width = Math.abs(this.start.x - this.end.x);
	}
	
	public void setHeight() {
		this.height = Math.abs(this.start.y - this.end.y);
	}
}