package model;

import java.util.LinkedList;
import java.util.List;

public class Tour extends AbstractPiece {

	public Tour(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces maTour = new Tour(Couleur.NOIR, new Coord(0,0));
	System.out.println(maTour);
	System.out.println(maTour.isMoveOk(0,7));
	System.out.println(maTour.Path(0,7));
	maTour.move(0, 7);
	System.out.println(maTour);
	System.out.println(maTour.isMoveOk(7,7));
	System.out.println(maTour.Path(7,7));
	maTour.move(7, 7);
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




@Override
public List<Coord> Path(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	List<Coord> Path =new LinkedList<Coord>();
	
	
	if(xFinal!=getX()) {
		int xInter=getX();
		while(xInter!=xFinal) {
			xInter+= (xFinal-xInter)/Math.abs(xFinal-xInter);
			
			Path.add(new Coord(xInter,getY()));
		}	
	}
	else {
		int yInter=getY();
		while(yInter!=yFinal) {
			yInter+= (yFinal-yInter)/Math.abs(yFinal-yInter);
			
			Path.add(new Coord(getX(),yInter));
		}	
	}
	return Path;
}





}