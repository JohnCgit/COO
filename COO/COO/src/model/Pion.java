package model;

import java.util.LinkedList;
import java.util.List;

public class Pion extends AbstractPiece implements LazyAss  {

	private boolean HasMoved=false;
	
	public Pion(Couleur couleur, Coord coord) {
		super(couleur,coord);
	}

	


public static void main(String[] args) 
{	
	Pieces monPion = new Pion(Couleur.NOIR, new Coord(1,1));
	System.out.println(monPion.isMoveOk(1,3));
	monPion.move(1, 3);
	System.out.println(monPion.isMoveOk(1,4));
	monPion.move(1, 4);
	System.out.println(monPion.isMoveOk(2,4,Type.CAPTURE));

}




@Override
public boolean isAlgoMoveOk(int xFinal, int yFinal) {
	// TODO test de couleur? cf template method?
	boolean res=false;
	if ( xFinal == getX())
	{
		if (!HasMoved) { // torpi
			if (getCouleur() == Couleur.BLANC)
			{
				res=((getY()-2 == yFinal)||(getY()-1 == yFinal))?true:false;
			}
			if (getCouleur() == Couleur.NOIR)
			{
				res=((getY()+2 == yFinal)||(getY()+1 == yFinal))?true:false;
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
	return res;
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

@Override

public boolean isMoveOk(int xFinal, int yFinal, Type type) {
	
	boolean res=false;
	if(type==Type.CAPTURE) {
		
		if (getCouleur() == Couleur.BLANC)
		{
			res=(getY()-1 == yFinal && Math.abs(getX()-xFinal)==1)?true:false;
		}
		if (getCouleur() == Couleur.NOIR)
		{
			res=(getY()+1 == yFinal && Math.abs(getX()-xFinal)==1)?true:false;
		}
	}
	else //type==Rien
	{
		res=isMoveOk(xFinal,yFinal);
	}
	
	
	return res;
}




@Override
public void setHasMoved(boolean b) { //TODO : peut etre utile pour undo move dans la mesure ou on sache que c'est bien le premier move du pion
	HasMoved= b;	
}

@Override
public boolean getHasMoved() {
	return HasMoved;
}

public boolean move(int xFinal,int yFinal)
{
	boolean res=super.move(xFinal, yFinal);
	if(!getHasMoved())
		setHasMoved(true);
	return res;
}
}
