package model;

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
		if (isMoveOk(xFinal,yFinal))
			{
				coord = new Coord(xFinal,yFinal);
				res = true;
			}
		return res;
	}
	
	public boolean capture() {
		
		return false;
	}
	
	public String toString() {
		
		return getClass().getSimpleName()+" ("+coord.x+","+coord.y+")" ;
		
	}
	
	protected abstract boolean isAlgoMoveOk(int xFinal,int yFinal);
	
	public final boolean isMoveOk(int xFinal,int yFinal)
	{
		if(xFinal == getX() && yFinal == getY())
		{
			return false;
		}
		return isAlgoMoveOk(xFinal,yFinal);
	}
}
