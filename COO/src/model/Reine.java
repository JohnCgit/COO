package model;

public class Reine extends AbstractPiece {

	public Reine(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces maReine= new Reine(Couleur.NOIR, new Coord(0,0));
	System.out.println(maReine);
	System.out.println(maReine.isMoveOk(1,0));
	maReine.move(1, 0);
	System.out.println(maReine);
	System.out.println(maReine.isMoveOk(0,1));
	maReine.move(0, 1);
	System.out.println(maReine.getX());
	System.out.println(maReine.getY());
	System.out.println(maReine.getCouleur());
}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	boolean res=false;
	if ( Math.abs(getX()-xFinal) == Math.abs(getY()-yFinal))
	{
		res = true;
	}
	if ( xFinal == getX() || yFinal == getY() )
	{
		res=true;
	}
	return res;
}


}
