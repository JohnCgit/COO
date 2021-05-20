package model;

import java.util.List;

public class Echiquier {

	private Jeu jeuBlanc;
	private Jeu jeuNoir;
	private boolean jeuCourant; // true:Blanc & false:Noir
	private String message;
	private boolean check = false;

	public Echiquier() {
		jeuBlanc = new Jeu(Couleur.BLANC);
		jeuNoir = new Jeu(Couleur.NOIR);
		jeuCourant = true;
		message = "Début de partie!";
	}
	
	@Override
	public String toString() {
		return "Echiquier [jeuBlanc=" + jeuBlanc + ", jeuNoir=" + jeuNoir + "]";
	}

	private void setMessage(String message) {
		this.message = message;
	}

	public Couleur getColorCurrentPlayer() {
		// TODO Auto-generated method stub
		return jeuCourant?Couleur.BLANC:Couleur.NOIR;
	}

	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	public Couleur getPieceColor(int x, int y) {
		// TODO Auto-generated method stub
		Couleur res = null;
		if(jeuBlanc.isPieceHere(x,y)) {
			res=Couleur.BLANC;
		}
		if(jeuNoir.isPieceHere(x,y)) {
			res=Couleur.NOIR;
		}
		return res;
	}

	public Object getPiecesIHM() {
		// TODO Auto-generated method stub
		List<PieceIHM> res = jeuBlanc.getPiecesIHM();
		res.addAll(jeuNoir.getPiecesIHM());
		return res;
	}

	public boolean isEnd() {
		boolean res = true;
		Jeu jeuC = jeuCourant?jeuBlanc:jeuNoir;
		Jeu jeunC = !jeuCourant?jeuBlanc:jeuNoir;
		check = !check;
		for(PieceIHM piece : jeuC.getPiecesIHM()) {
			for(Coord coord : piece.getList()) {
				if(coord.x>=0) {
					for(int x=0;x<8;x++) {
						for(int y=0;y<8;y++) {
							if(isMoveOk(coord.x,coord.y,x,y)) {
								if(move(coord.x,coord.y,x,y)) {
									res=false;
									
									if(MoveType(coord.x,coord.y,x,y)==Type.CAPTURE) {
										jeunC.undoCapture();
									}
									jeuC.undoMove();
									
									break;
								}
							}
						}
						if (!res)
							break;
					}
					if (!res)
						break;
				}
				
			}
			if (!res)
				break;
		}
		check = !check;
		return res;
	}

	public boolean isCheck(Coord coordCheck) {
		boolean res = false;
		check = !check;
		Jeu jeunC = !jeuCourant?jeuBlanc:jeuNoir;
		jeuCourant = !jeuCourant;
		for(PieceIHM piece : jeunC.getPiecesIHM()) {
			for(Coord coord : piece.getList()) {
				if(isMoveOk(coord.x,coord.y,coordCheck.x,coordCheck.y)) {
					res=true;
				}
			}
		}
		jeuCourant = !jeuCourant;
		check = !check;
		return res;
	}
	
	public Type MoveType(int xInit, int yInit, int xFinal, int yFinal) {
		Couleur couleurPieceI = getPieceColor(xInit,yInit);
		Couleur couleurPieceF = getPieceColor(xFinal,yFinal);
		Type res = Type.RIEN;
		if(couleurPieceI!=couleurPieceF && couleurPieceF!=null)
		{
			res = Type.CAPTURE;
		}
		else if(couleurPieceI==couleurPieceF && couleurPieceF!=null)
		{
			res = Type.CASTLING;
		}

		return res;
	}
	
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		Couleur couleurPiece = getPieceColor(xInit,yInit);
		boolean res = false;
		if(jeuCourant&&couleurPiece==Couleur.BLANC||!jeuCourant&&couleurPiece==Couleur.NOIR) { // on vérifie qe la couleur de la pièce est bien celle du jeu courant
			if(xFinal < 8 && xFinal > -1 && yFinal > -1 && yFinal < 8) { // On vérifie si on reste sur le plateau
				if(!(xInit == xFinal && yInit == yFinal)) {
					Jeu jeuC = jeuCourant?jeuBlanc:jeuNoir;
					Type type = MoveType(xInit, yInit, xFinal, yFinal);
					res = jeuC.isMoveOk(xInit, yInit, xFinal, yFinal,type); // On vérifie si au niveau du jeu le déplacement est légal
					if(res) {
						
						Jeu jeunC = !jeuCourant?jeuBlanc:jeuNoir;
						List<Coord> lc=jeuC.Path(xInit, yInit, xFinal, yFinal);
						for(Coord coord : lc) {
							if(type==Type.CASTLING) {
								if(isCheck(coord))
								{
									res=false;
									this.setMessage("KO : Roque impossible par echec");
								}
							}
							if(jeunC.isPieceHere(coord.x, coord.y)) {
								if(!(coord.x==xFinal&&coord.y==yFinal)) {
									res=false;
									if(!check)
										this.setMessage("KO : la destination est bloquée par une pièce adverse");
									break;
								}
							}
						}
						
					}
					else {
						if(!check)
							this.setMessage("KO : ce mouvement n'est pas possible");
					}
				}
			}
		}
			
		else {
			if(!check)	
				this.setMessage("KO : c'est au joueur "+ getColorCurrentPlayer()+" de jouer");
		}
		
		return res;
	}

	public boolean move(int xInit, int yInit, int xFinal, int yFinal) {
		boolean res=false;
		String message="";
		Jeu jeunC = !jeuCourant?jeuBlanc:jeuNoir;
		Jeu jeuC = jeuCourant?jeuBlanc:jeuNoir;
		Type type = MoveType(xInit, yInit, xFinal, yFinal);
		if(type==Type.CASTLING)
		{
				res=jeuC.setCastling(xInit, yInit, xFinal, yFinal);
				message="OK : Roque ";
		}
		else 
		{
			res=jeuC.move(xInit, yInit, xFinal, yFinal);
			if(res) {
				
				if(jeunC.isPieceHere(xFinal, yFinal)) {
					jeunC.capture(xFinal, yFinal);
					message="OK : deplacement + capture ";}
				else {
					message="OK : deplacement simple";
				}
			
				if(isCheck(jeuC.getKingCoord())){
					res = false;
					message="KO : se deplacement met/laisse le joueur "+this.getColorCurrentPlayer()+" en echec";
					if(type==Type.CAPTURE) {
						jeunC.undoCapture();
					}
					jeuC.undoMove();
				}
			}
			else {
				message="KO : collision";
			}
			
		}
		
		if(res&&jeuC.isPawnPromotion(xFinal, yFinal))
		{
			message = message + "+ Promotion X:"+xFinal+" Y:"+yFinal;
		}
		if(!check) {
			this.setMessage(message);
		}
		return res;
	}

	public void switchJoueur() {
		jeuCourant=!jeuCourant;
		Jeu jeuC = jeuCourant?jeuBlanc:jeuNoir;
		if(isCheck(jeuC.getKingCoord()))
		{
			if(this.isEnd()) {
				this.setMessage("Le joueur "+this.getColorCurrentPlayer()+" est en echec et mat");
			}
			else{
				this.setMessage("Le joueur "+this.getColorCurrentPlayer()+" est en echec");
			}
			
		}
	}
	
	public static void main(String[] args) {
	}

	
	public void promotion(String s, int x, int y) {
		Jeu jeunC = !jeuCourant?jeuBlanc:jeuNoir;
		jeunC.pawnPromotion(x, y, s);
	}

}
