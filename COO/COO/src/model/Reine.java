package model;

import java.util.LinkedList;
import java.util.List;

public class Reine extends AbstractPiece {

	public Reine(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces maReine= new Reine(Couleur.NOIR, new Coord(3,0));
	System.out.println(maReine);
	System.out.println(maReine.isMoveOk(3,3));
	System.out.println(maReine.Path(3,3));
	maReine.move(3, 3);
	System.out.println(maReine);
	System.out.println(maReine.isMoveOk(4,2));
	System.out.println(maReine.Path(4,2));
	maReine.move(4, 2);
	System.out.println(maReine);
	System.out.println(maReine.isMoveOk(7,2));
	System.out.println(maReine.Path(7,2));
	maReine.move(7, 2);
	System.out.println(maReine);
	System.out.println(maReine.isMoveOk(2,7));
	System.out.println(maReine.Path(2,7));
	maReine.move(2,7);
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




@Override
public List<Coord> Path(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	List<Coord> Path =new LinkedList<Coord>();
	int xInit = getX();
	int yInit = getY();
	
	if(xInit==xFinal) {
		int yInter=yInit;
		while(yInter!=yFinal) {
			yInter+= (yFinal-yInter)/Math.abs(yFinal-yInter);
			
			Path.add(new Coord(xInit,yInter));
		}	
		
	}
	
	else if(yInit==yFinal){
		int xInter=xInit;
		while(xInter!=xFinal) {
			xInter+= (xFinal-xInter)/Math.abs(xFinal-xInter);
			
			Path.add(new Coord(xInter,yInit));
		}	
		
		
	}
	else {
		int xInter=xInit;
		int yInter=yInit;
		while(xInter!=xFinal) {
			xInter+= (xFinal-xInter)/Math.abs(xFinal-xInter);
			yInter+= (yFinal-yInter)/Math.abs(yFinal-yInter);
			Path.add(new Coord(xInter,yInter));
		}
	}
	return Path;
}

}

