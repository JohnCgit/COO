package model;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;

import tools.ChessPiecesFactory;
import tools.Introspection;

public class Jeu {

	private List<Pieces> listPieces;
	private Deque<List<Coord>> listMove;
	//private Deque<Coord,Pieces> listCapture;
	private Couleur couleur;
	
	public Jeu(Couleur couleur) 
	{
		this.couleur = couleur;
		listPieces = ChessPiecesFactory.newPieces(couleur);
		listMove = new ArrayDeque<List<Coord>>();
	}
	
	public String toString()
	{
		String res="Le jeu est composé de : \n";
		for (Pieces piece : listPieces){
			res += piece.toString()+'\n';
		}
		return res;
	}
	
	private Pieces findPiece(int x, int y)
	{
		Pieces res = null;
		for (Pieces piece : listPieces){
			if (piece.getX()==x && piece.getY()==y) 
			{
				res=piece;
			}
		}
		return res;
	}
	
	public boolean isPieceHere(int x,int y)
	{
		boolean res = false;
		for (Pieces piece : listPieces){
			if (piece.getX()==x && piece.getY()==y) 
			{
				res=true;
			}
		}
		return res;
	}
	
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal,Type type) {
		boolean res = false;
		if (isPieceHere(xInit,yInit)) {
			Pieces piece = findPiece(xInit, yInit);
			if( type==Type.CASTLING) {
				res=isCastlingOk(xInit, yInit,xFinal, yFinal);
			}
			else 
			{
				res = piece.isMoveOk(xFinal, yFinal,type);
				if(res) 
				{
					List<Coord> lc = piece.Path(xFinal, yFinal);
					for(Coord coord : lc) 
					{
						if(isPieceHere(coord.x,coord.y)) 
						{
							res=false;
						}
					}
				}
			}
		}
		return res;
	}
	
	private boolean isCastlingOk(int xInit, int yInit, int xFinal, int yFinal) {
		boolean res=false;
		Coord KingCoord = getKingCoord();
		if(KingCoord.equals(new Coord(xInit,yInit))) 
		{
			if (getPieceType(xFinal,yFinal).compareTo("Tour")==0) 
			{
				Roi roi = (Roi) findPiece(KingCoord.x,KingCoord.y);
				Tour tour = (Tour) findPiece(xFinal,yFinal);
				if(!roi.getHasMoved()&&!tour.getHasMoved()) 
				{
					res = true;
					int dir = Math.abs(xInit-xFinal) / (xFinal-xInit);
					for(int x=xInit+dir;Math.abs(x-xFinal)>0;x += dir )
					{
						if (isPieceHere(x,yInit))
						{
							res = false;
						}
					}
				}
			}
		}
		return res;
	}

	public boolean setCastling(int xInit, int yInit, int xFinal, int yFinal) {
		boolean res= false;
		int xK,xT;
		int dir = Math.abs(xInit-xFinal) / (xFinal-xInit);
		xK=xInit+2*dir;
		xT=xInit+dir;
		res=move(xInit,yInit,xK,yInit)&&move(xFinal,yFinal,xT,yFinal);
		return res;
	}
	
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		boolean res = false;
		Pieces piece = findPiece(xInit, yInit);
		if(piece!=null) 
		{	
			res=piece.move(xFinal, yFinal);
		}
		List<Coord> move = new LinkedList<Coord>();
		move.add(new Coord(xInit,yInit));
		move.add(new Coord(xFinal,yFinal));
		listMove.push(move);
		return res;
	}
	
	public void setPossibleCapture() {}
	
	public boolean capture(int xCatch, int yCatch) {
		boolean res=false;
		if(this.isPieceHere(xCatch, yCatch)) {
			Pieces piece = findPiece(xCatch, yCatch);
			res= piece.capture();
			
			}
		return res;
		}
	
	public Couleur getPieceColor(int x, int y)
	{
		Couleur res = Couleur.NOIRBLANC;
		if (isPieceHere(x,y)) {
			Pieces piece = findPiece(x,y);
			res = piece.getCouleur();
		}
		return res;
	}
	
	public String getPieceType(int x,int y){
		String res = null;
		if (isPieceHere(x,y)) {
			Pieces piece = findPiece(x,y);
			res = piece.getClass().getSimpleName();
		}
		return res;	
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	/**
	* @return une vue de la liste des pièces en cours
	* ne donnant que des accès en lecture sur des PieceIHM
	* (type piece + couleur + liste de coordonnées)
	*/
	public List<PieceIHM> getPiecesIHM(){
	PieceIHM newPieceIHM = null;
	List<PieceIHM> list = new LinkedList<PieceIHM>();
	for (Pieces piece : listPieces){
	boolean existe = false;
	// si le type de piece existe déjà dans la liste de PieceIHM
	// ajout des coordonnées de la pièce dans la liste de Coord de ce type
	// si elle est toujours en jeu (x et y != -1)
	for ( PieceIHM pieceIHM : list){
	if ((pieceIHM.getTypePiece()).equals(piece.getClass().getSimpleName())){
	existe = true;
	if (piece.getX() != -1){
	pieceIHM.add(new Coord(piece.getX(), piece.getY()));
	}
	}
	}
	// sinon, création d'une nouvelle PieceIHM si la pièce est toujours en jeu
	if (! existe) {
	if (piece.getX() != -1){
	newPieceIHM = new PieceIHM(piece.getClass().getSimpleName(),
	piece.getCouleur());
	newPieceIHM.add(new Coord(piece.getX(), piece.getY()));
	list.add(newPieceIHM);
	}
	}
	}
	return list;
	}
	
	
	
	public void undoMove() {
	
		List<Coord> listCoord = listMove.pop();
		Pieces piece = findPiece(listCoord.get(1).x,listCoord.get(1).y);
		piece.move(listCoord.get(0).x,listCoord.get(0).y); 
}

	public void undoCapture() {
		/*
		 * List<Pieces> pieceIHM = listCapture.pop(); Pieces agresseur =
		 * pieceIHM.get(0); Pieces victime = pieceIHM.get(0); undoMove();
		 */
		
	}
	
	public boolean isPawnPromotion(int xFinal,int yFinal) {
		boolean res=false;
		if (isPieceHere(xFinal,yFinal)) {
			if(getPieceType(xFinal,yFinal).compareTo("Pion")==0) {
				res=(getPieceColor(xFinal,yFinal)==Couleur.BLANC && xFinal==0)||(getPieceColor(xFinal,yFinal)==Couleur.NOIR && xFinal==8-1);
			}
		}
		
		return res;
	}
	
	public boolean pawnPromotion(int xFinal,int yFinal,String type) {
		boolean res = false;
		if(isPawnPromotion(xFinal, yFinal))
		{
			res=true;
			Pieces pionApromouvoir = findPiece(xFinal, yFinal);
		    Couleur pieceCouleur = pionApromouvoir.getCouleur();
		    Coord pieceCoord = new Coord(xFinal,yFinal);
			Pieces pionPromu = (Pieces) Introspection.newInstance(type,
					new Object[] {pieceCouleur, pieceCoord});
			listPieces.add(pionPromu);
			listPieces.remove(pionApromouvoir);
		}
		return res;
	}
	
	public Coord getKingCoord() {
		Coord res = null;
	
		for (Pieces piece : listPieces){
			if (getPieceType(piece.getX(),piece.getY()).compareTo("Roi")==0) 
			{
				res=new Coord(piece.getX(),piece.getY());
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		Jeu jeu = new Jeu(Couleur.BLANC);
		System.out.println(jeu.isMoveOk(0, 6, 0, 4,Type.RIEN));
		System.out.println(jeu.move(0, 6, 0, 4));
		System.out.println(jeu.isMoveOk(1, 6, 1, 4,Type.RIEN));
		System.out.println(jeu.move(1, 6, 1, 4));
	}

	public List<Coord> Path(int xInit, int yInit, int xFinal, int yFinal) {
		return findPiece(xInit,yInit).Path(xFinal, yFinal);
	}
}

