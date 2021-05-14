package model;

import java.util.LinkedList;
import java.util.List;

public class Roi extends AbstractPiece implements LazyAss{

	private boolean HasMoved = false;
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
	System.out.println(monRoi.isMoveOk(0,2));
	monRoi.move(0, 1);
	System.out.println(monRoi.getX());
	System.out.println(monRoi.getY());
	System.out.println(monRoi.getCouleur());
}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO Auto-generated method stub
	boolean res=false;
	if ( Math.abs(xFinal-getX()) <= 1 && Math.abs(yFinal-getY()) <= 1)
	{
		res = true;
	}
	return res;
}



public boolean isMoveOk(int xFinal, int yFinal, Type type) {
	
	boolean res=false;
	if(type==Type.CASTLING) {
		
		res=!getHasMoved();
	}
	else //type==Rien
	{
		res=isMoveOk(xFinal,yFinal);
	}
	
	
	return res;
}

@Override
public List<Coord> Path(int xFinal, int yFinal) {
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


@Override
public boolean getHasMoved() {
	return HasMoved;
}




@Override
public void setHasMoved(boolean b) {
	HasMoved = b;
}

public boolean move(int xFinal,int yFinal)
{
	boolean res=super.move(xFinal, yFinal);
	if(!getHasMoved())
		setHasMoved(true);
	return res;
}
}