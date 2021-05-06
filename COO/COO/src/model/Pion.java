package model;

import java.util.LinkedList;
import java.util.List;

public class Pion extends AbstractPiece implements Pions {

	private boolean hasMoved=false;
	
	public Pion(Couleur couleur, Coord coord) {
		super(couleur,coord);
	}

	


public static void main(String[] args) 
{	
	Pieces monPion = new Pion(Couleur.NOIR, new Coord(1,1));
	System.out.println(monPion);
	System.out.println(monPion.Path(1, 2));
	System.out.println(monPion.Path(1, 3));
	System.out.println(monPion.Path(2, 2));
	System.out.println(monPion.Path(2, 0));
}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO test de couleur? cf template method?
	boolean res=false;
	if ( xFinal == getX())
	{
		if (!hasMoved) { // torpi
			if (getCouleur() == Couleur.BLANC)
			{
				res=((getY()-2 == yFinal)||(getY()-1 == yFinal))?true:false;
			}
			if (getCouleur() == Couleur.NOIR)
			{
				res=((getY()+2 == yFinal)||(getY()+1 == yFinal))?true:false;
			}
			if(res)
			{
				hasMoved=true;
			}
			
		}
		else {
			if (getCouleur() == Couleur.BLANC)
			{
				res=(getY()-1 == yFinal)?true:false;
			}
			if (getCouleur() == Couleur.NOIR)
			{
				res=(getY()+1 == yFinal)?true:false;
			}
		}
	}
	else {
		res=isMoveDiagOk(xFinal,yFinal);
	}
	return res;
}




@Override
public boolean isMoveDiagOk(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	return false;
}




@Override
public List<Coord> Path(int xFinal, int yFinal) {
	List<Coord> Path =new LinkedList<Coord>();
	int xInter=getX();
	int yInter=getY();
	while(yInter!=yFinal) {
		if(xInter!=xFinal) {
			xInter+= (xFinal-xInter)/Math.abs(xFinal-xInter);
		}
		yInter+= (yFinal-yInter)/Math.abs(yFinal-yInter);
		Path.add(new Coord(xInter,yInter));
	}
	return Path;
}


}
