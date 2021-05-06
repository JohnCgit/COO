package model;

import java.util.LinkedList;
import java.util.List;

public class Fou extends AbstractPiece {

	public Fou(Couleur couleur, Coord coord) {
		super(couleur,coord);
		// TODO Auto-generated constructor stub
	}

	


public static void main(String[] args) 
{	
	Pieces monFou = new Fou(Couleur.NOIR, new Coord(2,0));
	System.out.println(monFou);
	System.out.println(monFou.Path(5,3));
	monFou.move(5,3);
	System.out.println(monFou.Path(7,1));
	System.out.println(monFou);
	System.out.println(monFou.isMoveOk(7,1));
	monFou.move(7, 1);
	System.out.println(monFou);
	System.out.println(monFou.Path(3,5));
	System.out.println(monFou.isMoveOk(3,5));
	monFou.move(3, 5);
	System.out.println(monFou);
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




@Override
public List<Coord> Path(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	List<Coord> Path =new LinkedList<Coord>();
	int xInter=getX();
	int yInter=getY();
	while(xInter!=xFinal) {
		xInter+= (xFinal-xInter)/Math.abs(xFinal-xInter);
		yInter+= (yFinal-yInter)/Math.abs(yFinal-yInter);
		Path.add(new Coord(xInter,yInter));
	}
	return Path;
}


}