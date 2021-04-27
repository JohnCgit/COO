package model;

public class Pion extends AbstractPiece {

	public Pion(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces monPion = new Pion(Couleur.NOIR, new Coord(0,0));
	System.out.println();
	System.out.println(monPion.isMoveOk(1,0));
	monPion.move(1, 0);
	System.out.println(monPion);
	System.out.println(monPion.isMoveOk(0,1));
	monPion.move(0, 1);
	System.out.println(monPion.getX());
	System.out.println(monPion.getY());
	System.out.println(monPion.getCouleur());
}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO test de couleur? cf template method?
	boolean res=false;
	if ( xFinal == getX())
	{
		if (getCouleur() == Couleur.BLANC)
		{
			res=(getY()-1 == yFinal)?true:false;
		}
		if (getCouleur() == Couleur.NOIR)
		{
			res=(getY()+1 == yFinal)?true:false;
		}
	}
	return res;
}


}
