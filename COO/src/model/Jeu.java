package model;

import java.util.LinkedList;
import java.util.List;
import tools.ChessPiecesFactory;

public class Jeu {

	private List<Pieces> listPieces;
	private Couleur couleur;
	private boolean castling = true;
	
	public Jeu(Couleur couleur) 
	{
		this.couleur = couleur;
		listPieces = ChessPiecesFactory.newPieces(couleur);
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
	
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		boolean res = false;
		if (isPieceHere(xInit,yInit)) {
			Pieces piece = findPiece(xInit, yInit);
			res = piece.isMoveOk(xFinal, yFinal);
		}
		return res;
	}
	
	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		boolean res = false;
		if (isMoveOk(xInit,yInit,xFinal,yFinal)) {
			Pieces piece = findPiece(xInit, yInit);
			piece.move(xFinal, yFinal);
			res = true; 
		}
		return res;
	}
	
	public void setPossibleCapture() {}
	
	public boolean capture(int xCatch, int yCatch) {return false;}
	
	public Couleur getPieceColor(int x, int y)
	{
		Couleur res = Couleur.NOIRBLANC;
		if (isPieceHere(x,y)) {
			Pieces piece = findPiece(x,y);
			res = piece.getCouleur();
		}
		return res;
	}
	
	public java.lang.String getPieceType(int x,int y){
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
	
	public void setCastling() {
	}
	
	public void undoMove() {
		
	}
	
	public void undoCapture() {
		
	}
	public static void main(String[] args) {
		Jeu jeu = new Jeu(Couleur.BLANC);
		System.out.println(jeu);
	}
}

