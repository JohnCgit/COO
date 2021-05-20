package model;

import java.util.List;

public abstract class AbstractPiece implements Pieces {

	private Couleur couleur;
	private Coord coord;
	

	public AbstractPiece(Couleur couleur, Coord coord) {
		this.coord = coord;
		this.couleur = couleur;
	}

	public int getX() {
		return coord.x;
	}
	
	public int getY() {
		return coord.y;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public boolean move(int xFinal,int yFinal)
	{
		boolean res = false;
		try {
		coord = new Coord(xFinal,yFinal);
		res = true;
		}
		catch(Exception e) {}
		return res;
	}
	
	public abstract List<Coord> Path(int xFinal,int yFinal);
	
	public boolean capture() {
		
		boolean res = false;
		try {
		if(this.coord==new Coord(0,0)) {
			move(-10,-10);
		}
		else {
			move(-getX(),-getY());
		}
		res = true;
		}
		catch(Exception e) {}
		return res;
	}
	
	public String toString() {
		
		return getClass().getSimpleName()+" ("+coord.x+","+coord.y+")" ;
		
	}
	
	protected abstract boolean isAlgoMoveOk(int xFinal,int yFinal);
	
	public final boolean isMoveOk(int xFinal,int yFinal)
	{
		return xFinal == getX() && yFinal == getY()?false:isAlgoMoveOk(xFinal,yFinal);
	}
	
	public boolean isMoveOk(int xFinal,int yFinal,Type type)
	{
		return isMoveOk(xFinal,yFinal);
	}
}
