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
		// TODO Auto-generated method stub
		return false;
	}

	public boolean jeuCourantEnEchec() {
		boolean res = false;
		check = !check;
		Jeu jeuC = jeuCourant?jeuBlanc:jeuNoir;
		Jeu jeunC = !jeuCourant?jeuBlanc:jeuNoir;
		Coord kingPos=jeuC.getKingCoord();
		jeuCourant = !jeuCourant;
		for(PieceIHM piece : jeunC.getPiecesIHM()) {
			for(Coord coord : piece.getList()) {
				if(isMoveOk(coord.x,coord.y,kingPos.x,kingPos.y)) {
					res=true;
				}
			}
		}
		jeuCourant = !jeuCourant;
		check = !check;
		return res;
	}
	
	public boolean isMoveOk(int xInit, int yInit, int xFinal, int yFinal) {
		Couleur couleurPiece = getPieceColor(xInit,yInit);
		boolean res = false;
		if(jeuCourant&&couleurPiece==Couleur.BLANC||!jeuCourant&&couleurPiece==Couleur.NOIR) {
			if(xFinal < 8 && xFinal > -1 && yFinal > -1 && yFinal < 8) {
				if(!(xInit == xFinal && yInit == yFinal)) {
					Jeu jeuC = jeuCourant?jeuBlanc:jeuNoir;
					res = jeuC.isMoveOk(xInit, yInit, xFinal, yFinal);
					if(res) {
						
						Jeu jeunC = !jeuCourant?jeuBlanc:jeuNoir;
						List<Coord> lc=jeuC.Path(xInit, yInit, xFinal, yFinal);
						for(Coord coord : lc) {
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
		// TODO Auto-generated method stub
		boolean res=false;
		res=jeuCourant?jeuBlanc.move(xInit, yInit, xFinal, yFinal):jeuNoir.move(xInit, yInit, xFinal, yFinal);
		if(res) {
			Jeu jeunC = !jeuCourant?jeuBlanc:jeuNoir;
			if(jeunC.isPieceHere(xFinal, yFinal)) {
				jeunC.capture(xFinal, yFinal);
				this.setMessage("OK : deplacement + capture ");
			}
			else {
				this.setMessage("OK : deplacement simple");
			}
		}
		if(jeuCourantEnEchec()){
			res = false;
			this.setMessage("KO : se deplacement met/laisse le joueur "+this.getColorCurrentPlayer()+" en echec");
			if(jeuCourant){jeuBlanc.undoMove();}
			else{jeuNoir.undoMove();}
		}
		return res;
	}

	public void switchJoueur() {
		// TODO Auto-generated method stub
		jeuCourant=!jeuCourant;
		if(jeuCourantEnEchec())
		{
			this.setMessage("Le joueur "+this.getColorCurrentPlayer()+" est en echec");
			
		}
	}
	
	public static void main(String[] args) {
		Echiquier jeu = new Echiquier();
		jeu.move(3, 7, 4, 4);
		jeu.switchJoueur();
		jeu.move(3, 0, 3, 4);
		jeu.switchJoueur();
		System.out.println(jeu.isMoveOk(4, 4, 0, 4));
		System.out.println(jeu.isMoveOk(4, 4, 3, 4));
		jeu.move(4, 4, 3, 4);
		System.out.println(jeu.getMessage());
		
	}

}
