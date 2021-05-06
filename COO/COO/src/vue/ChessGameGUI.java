package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import controler.ChessGameControlers;
import model.Coord;
import model.PieceIHM;
import tools.ChessImageProvider;

public class ChessGameGUI extends javax.swing.JFrame
implements java.awt.event.MouseListener, java.awt.event.MouseMotionListener, java.util.Observer{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLayeredPane layeredPane;
	private ChessGameControlers chessGameControler;
	private JPanel chessBoard;
	private JLabel chessPiece;
	private int xAdjustment, yAdjustment;
	private int xInit, yInit, xFinal, yFinal;
	private Dimension dim;
	  
	public ChessGameGUI(String string, ChessGameControlers chessGameControler, Dimension dim) {
		  this.chessGameControler = chessGameControler;
		  this.dim=dim;
		  layeredPane = new JLayeredPane();
		  getContentPane().add(layeredPane);
		  layeredPane.setPreferredSize(dim);
		  layeredPane.addMouseListener(this);
		  layeredPane.addMouseMotionListener(this);
		
		  //Add a chess board to the Layered Pane 
		 
		  chessBoard = new JPanel();
		  layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
		  chessBoard.setLayout( new GridLayout(8, 8) );
		  chessBoard.setPreferredSize( dim );
		  chessBoard.setBounds(0, 0, dim.width, dim.height);
		 
		  for (int i = 0; i < 64; i++) {
		  JPanel square = new JPanel( new BorderLayout() );
		  chessBoard.add( square );
		 
		  int row = (i / 8) % 2;
		  if (row == 0)
		  square.setBackground( i % 2 == 0 ? Color.BLACK : Color.white );
		  else
		  square.setBackground( i % 2 == 0 ? Color.white : Color.BLACK );
		  }
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.print(chessGameControler.getMessage()+'\n');
		List<PieceIHM> piecesIHM = (List<PieceIHM>) arg1;
		for(PieceIHM pieceIHM : piecesIHM) {
			String file = ChessImageProvider.getImageFile(pieceIHM.getTypePiece(),pieceIHM.getCouleur());
			
			for(model.Coord position : pieceIHM.getList()) {
				if(position.x!=-1) {
					JLabel piece = new JLabel(new ImageIcon(file));
					JPanel panel = (JPanel)chessBoard.getComponent(position.x+position.y*8);
					if(panel.getComponentCount()==0)
						panel.add(piece);
					}
				}
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		  if (chessPiece == null) return;
		 chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
		 }

	@Override
	public void mousePressed(MouseEvent e) {
		  chessPiece = null;
		  Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		 
		  if (c instanceof JPanel) 
		  return;
		  
		  
		  Point parentLocation = c.getParent().getLocation();
		  xInit=(int)Math.round(parentLocation.x/(dim.width/8));
		  yInit=(int)Math.round(parentLocation.y/(dim.height/8));
		  if(chessGameControler.isPlayerOK(new Coord (xInit,yInit))) {
			  xAdjustment = parentLocation.x - e.getX();
			  yAdjustment = parentLocation.y - e.getY();
			  chessPiece = (JLabel)c;
			  chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
			  chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
			  layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
		  }
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		  if(chessPiece == null) return;
		  
		  chessPiece.setVisible(false);
		  Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		  Container parent = (Container)c;
		  if(c instanceof JLabel)
		  {
			  parent = c.getParent();
		  }
		  Point parentLocation = parent.getLocation();
		  xFinal=(int)Math.round(parentLocation.x/(dim.width/8));
		  yFinal=(int)Math.round(parentLocation.y/(dim.height/8));
		  boolean res=chessGameControler.move(new Coord(xInit,yInit), new Coord(xFinal,yFinal));
		  if(res) {
			  parent.remove(0);
			  parent.add( chessPiece );
		  }
		 
		  else {
			JPanel panel = (JPanel)chessBoard.getComponent(xInit+yInit*8);
			panel.add(chessPiece);
			  
		  }
		  chessPiece.setVisible(true);
		  }
	
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
	  JFrame frame = new ChessGameGUI(null, null, new Dimension(600, 600));
	  frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
	  frame.pack();
	  frame.setResizable(true);
	  frame.setLocationRelativeTo( null );
	  frame.setVisible(true);
	}
}
