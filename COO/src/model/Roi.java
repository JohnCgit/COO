package model;

public class Roi extends AbstractPiece {

	public Roi(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces monRoi = new Roi(Couleur.NOIR, new Coord(0,0));
	System.out.println(monRoi);
	System.out.println(monRoi.isMoveOk(1,0));
	monRoi.move(1, 0);
	System.out.println(monRoi);
	System.out.println(monRoi.isMoveOk(0,1));
	monRoi.move(0, 1);
	System.out.println(monRoi.getX());
	System.out.println(monRoi.getY());
	System.out.println(monRoi.getCouleur());
}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	boolean res=false;
	if ( Math.abs(xFinal-getX()) <= 1 || Math.abs(yFinal-getY()) <= 1)
	{
		res = true;
	}
	return res;
}


}