package model;

public class Cavalier extends AbstractPiece {

	public Cavalier(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces monCavalier = new Cavalier(Couleur.NOIR, new Coord(0,0));
	System.out.println(monCavalier);
	System.out.println(monCavalier.isMoveOk(1,0));
	monCavalier.move(1, 0);
	System.out.println(monCavalier);
	System.out.println(monCavalier.isMoveOk(0,1));
	monCavalier.move(0, 1);
	System.out.println(monCavalier.getX());
	System.out.println(monCavalier.getY());
	System.out.println(monCavalier.getCouleur());
}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	boolean res=false;
	if((Math.abs(xFinal-getX()) == 2 && Math.abs(yFinal-getY()) == 1)
	|| 
	(Math.abs(xFinal-getX()) == 1 && Math.abs(yFinal-getY()) == 2)) {
		res=true;
	}
	return res;
}


}