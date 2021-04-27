package model;

public class Fou extends AbstractPiece {

	public Fou(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces monFou = new Fou(Couleur.NOIR, new Coord(0,0));
	System.out.println(monFou);
	System.out.println(monFou.isMoveOk(1,0));
	monFou.move(1, 0);
	System.out.println(monFou);
	System.out.println(monFou.isMoveOk(0,1));
	monFou.move(0, 1);
	System.out.println(monFou.getX());
	System.out.println(monFou.getY());
	System.out.println(monFou.getCouleur());
}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	boolean res=false;
	if ( Math.abs(getX()-xFinal) == Math.abs(getY()-yFinal))
	{
		res = true;
	}
	return res;
}


}