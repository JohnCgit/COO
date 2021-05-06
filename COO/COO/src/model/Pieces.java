package model;

import java.util.List;

public interface Pieces {

	boolean capture();
	Couleur getCouleur();
	int getX();
	int getY();
	boolean isMoveOk(int xFinal, int yFinal);
	boolean move(int xFinal, int yFinal);
	List<Coord> Path(int xFinal, int yFinal);
}
