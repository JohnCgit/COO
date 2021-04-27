package model;

public class Tour extends AbstractPiece {

	public Tour(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces maTour = new Tour(Couleur.NOIR, new Coord(0,0));
	System.out.println(maTour);
	System.out.println(maTour.isMoveOk(1,0));
	maTour.move(1, 0);
	System.out.println(maTour);
	System.out.println(maTour.isMoveOk(1,1));
	System.out.println(maTour.getX());
	System.out.println(maTour.getY());
	System.out.println(maTour.getCouleur());
}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	boolean res=false;
	if ( xFinal == getX() || yFinal == getY() ){
		res=true;
	}
	return res;
}





}