package drawingBoard;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import java.awt.*;
/**
 * 
 * @author choudhury_Iqbal
 *
 */
public class DrawingBoard extends JPanel implements MouseListener,MouseMotionListener,ActionListener
{
	
	
	  int moveX;
	  int moveY;
	  int InitX;
	  int InitY;
	  int initFx;
      int initFY;

     int toDraw = 0;
	 Boolean fill= false;

	 Button oval;
	 Button Rect;
	 Button clear;
	 

	 JRadioButton fullFill;
	 JRadioButton outline;
    JComboBox<String> comboBox;
    List<Shape> shape = new ArrayList<Shape>();
    
    
    //----------------------------------------------------------------------------------
//main method
	public static void main(String[] args)
	{
		DrawingBoard drawingBoard = new DrawingBoard();
		drawingBoard.init();
		
		
		JFrame jf = new JFrame();
		jf.add(drawingBoard);
		jf.setBounds(120, 120, 600, 600);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//--------------------------------------------------------------------------------------

	//init method
	public void init()
	{

		String[] colors = new String[] { "Red","Green", "Blue"};

		setLayout(null);
		oval = new Button("Oval");
		Rect = new Button("Rectangle");
		clear = new Button("Clear");

		oval.setBounds(10,160,70,30);
		Rect.setBounds(10,120,70,30);
		clear.setBounds(10, 490, 130, 30);

		oval.addActionListener(this);
		Rect.addActionListener(this);
		clear.addActionListener(this);

		add(Rect);
		add(oval);
		add(clear);


		fullFill = new JRadioButton("Full ColorFill");
		outline = new JRadioButton("OutLine Color");

		fullFill.setBounds(10, 340, 120, 30);
		outline.setBounds(10, 370, 120, 30);

		fullFill.addActionListener(this);
		outline.addActionListener(this);
		outline.setSelected(true);

		add(fullFill);
		add(outline);

		comboBox = new JComboBox<String>(colors);
		comboBox.setBounds(10, 410, 100, 30);


		add(comboBox);

		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	//-------------------------------------------------------
	
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setFont(new Font("TimesRoman", Font.BOLD, 50));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g.drawString("Shapes", 20,120);
		g.drawLine(150, 70, 150, getHeight());
		g.drawLine(0, 70, getWidth(), 70);


		drawPrevious(g);
		switch ((String) comboBox.getSelectedItem()) {
		case "Red":
			g.setColor(Color.RED);
			break;
		
		case "Green":
			g.setColor(Color.GREEN);
			break;

		case "Blue":
			g.setColor(Color.BLUE);
			break;
		
		default:
			break;
		}

		switch(toDraw)
		{
		case 0:
			if(!fill)
				g.drawRect(InitX, InitY, moveX, moveY);
			else
				g.fillRect(InitX, InitY, moveX, moveY);
			break;
		case 1:
			if(!fill)
				g.drawOval(InitX, InitY, moveX, moveY);
			else
				g.fillOval(InitX, InitY, moveX, moveY);
			break;
		case 2:
			g.drawLine(InitX, InitY, moveX, moveY);
			break;
		}

	}
	//----------------------------------------------------------------------------

	private void drawPrevious(Graphics g) {

		for(int i=0; i<shape.size(); i++)
		{
			drawShape(g, shape.get(i));
		}

	}

	
	//---------------------------------------------------------------------------------
	private void drawShape(Graphics g, Shape s)
	{
		switch(s.getColor())
		{
		case "Red":
			g.setColor(Color.RED);
			break;
		case "Green":
			g.setColor(Color.GREEN);
			break;

		case "Blue":
			g.setColor(Color.BLUE);
			break;
		default:
			break;
		}

		switch (s.getShape())
		{
		case 0+"":
			if(!s.getFill())
				g.drawRect(s.getmX(), s.getmY(), s.getX(), s.getY());
			else
				g.fillRect(s.getmX(), s.getmY(), s.getX(), s.getY());
		break;
		case 1+"":
			if(!s.getFill())
				g.drawOval(s.getmX(), s.getmY(), s.getX(), s.getY());
			else
				g.fillOval(s.getmX(), s.getmY(), s.getX(), s.getY());
		break;
		case 2+"":
			g.drawLine(s.getmX(), s.getmY(), s.getX(), s.getY());
		break;
		}

	}
//-------------------------------------------------------------------------------

	private void removeLas()
	{
		if(shape.size()>0)
			shape.remove(shape.size()-1);

		InitX= 0;
		InitY = 0;
		initFx = 0;
		initFY = 0;
		moveX = 0;
		moveX = 0;
		repaint();
	}
	//-----------------------------------------------------------------------------
	public void mouseDragged(MouseEvent e) {

		if(e.getPoint().x>=150 && e.getPoint().y >=70)
		{
			if(toDraw!=2)
			{
				moveX = (e.getPoint().x - InitX);
				moveY = (e.getPoint().y - InitY);
				if(e.getPoint().x - initFx < 0)
				{
					InitX = e.getPoint().x;
					moveX = initFx - InitX;
				}
				if(e.getPoint().y - initFY < 0)
				{
					InitY = e.getPoint().y;
					moveY = initFY - InitY;
				}
				repaint();
			}
			else
			{
				moveX = (e.getPoint().x );
				moveY = (e.getPoint().y );
				repaint();
			}
		}
		else
		{
			if(e.getPoint().x<=150 && e.getPoint().y>=70)
			{
				if(toDraw!=2)
				{
					moveY = (e.getPoint().y - InitY);
					if(e.getPoint().y - initFY < 0)
					{
						InitY = e.getPoint().y;
						moveY = initFY - InitY;
					}
					repaint();
				}
				else
				{
					moveY = (e.getPoint().y );
					repaint();
				}
			}
			else if(e.getPoint().y<=70 && e.getPoint().x>=150 )
			{
				if(toDraw!=2)
				{
					moveX = (e.getPoint().x - InitX);
					if(e.getPoint().x - initFx < 0)
					{
						InitX = e.getPoint().x;
						moveX = initFx - InitX;
					}
					repaint();
				}
				else
				{
					moveX = (e.getPoint().x );
					repaint();
				}
			}
			else
			{

			}

		}
	}
//-----------------------------------------------------------------
	public void mouseMoved(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent e)
	{


	}

	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}
//---------------------------------------------------------------------------------
	public void mousePressed(MouseEvent e) {

		if(toDraw==2)
		{
			InitX = e.getX();
			InitY = e.getY();
			moveX = e.getX();
			moveY = e.getY();
			initFx = e.getX();
			initFY = e.getY();
		}
		else
		{
			InitX = e.getX();
			InitY = e.getY();
			moveX = 0;
			moveY = 0;
			initFx = e.getX();
			initFY = e.getY();
		}
		repaint();
	}
	//------------------------------------------------------

	public void mouseReleased(MouseEvent e) {

		Shape shape1 = new Shape(InitX, InitY, moveX, moveY, toDraw+"",(String)comboBox.getSelectedItem(),fill);
		shape.add(shape1);

	}
//----------------------------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==Rect)
		{
			toDraw = 0;
		}

		if(e.getSource()==oval)
		{
			toDraw = 1;
		}
		

		if(e.getSource() == fullFill)
		{
			fill = true;
			outline.setSelected(false);
		}
		if(e.getSource() == outline)
		{
			fill = false;
			fullFill.setSelected(false);
		}
		if(e.getSource()==clear)
		{
			removeLas();
		}

	}
	
}
