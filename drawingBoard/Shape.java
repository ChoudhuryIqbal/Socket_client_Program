package drawingBoard;

/**
 * class shape
 * @author choudhury_Iqbal
 *
 */
public class Shape {

	private int mX;
	private int X;

	private int mY;
	private int Y;
	private Boolean fill;
	
	private String shape;
	private String color;
	
	
	/**
	 * 
	 * @param mX
	 * @param mY
	 * @param X
	 * @param Y
	 * @param shape
	 * @param color
	 * @param fill
	 */
	public Shape(int mX,int mY, int X, int Y, String shape, String color,Boolean fill) {
		super();
		this.color = color;
		this.X = X;
		this.Y = Y;
		this.mX = mX;
		this.mY =mY;
		this.shape = shape;
		this.fill = fill;
		
	}
/**
 * 
 * @return fill
 */
	public Boolean getFill() {
		return fill;
	}
	/**
	 * 
	 * @param fill
	 * set fil
	 */
	public void setFill(Boolean fill) {
		this.fill = fill;
	}
	public int getmX() {
		return mX;
	}
	public void setmX(int mX) {
		this.mX = mX;
	}
	public int getX() {
		return X;
	}
	public void setX(int X) {
		this.X = X;
	}
	public int getmY() {
		return mY;
	}
	public void setmY(int mY) {
		this.mY = mY;
	}
	public int getY() {
		return Y;
	}
	public void setY(int Y) {
		this.Y = Y;
	}
	public String getShape() {
		return shape;
	}
	public void setShape(String shape) {
		this.shape = shape;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
