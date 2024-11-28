import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;

/* Le Quadtree répresente la région de l'image
 * Il est répresenté un coin inférieur gauche 
 * et un coin supérieur droit
 * et a une couleur qui lui est associé
 * ainsi que 4 régions et un noued parent pour 
 * nous permettre d'avoir des information utiles lors du recoloriage
*/
public class Quadtree {

    private int firstX , firstY, // coin inférieur gauche
                endX , endY ; // coin supérieur droit
    private Quadtree NO, NE, SE, SO , parent; 
    private Couleur couleur; // couleur associé à la region

    /**
     * @Rôle : Constructeur de la classe Quadtree
     * @param a : abscisse d'origine
     * @param b : ordonée d'origine
     * @param c : abscisse d'extremité
     * @param d : ordonnée d'extremité
     * @param A : région Nord-Ouest
     * @param B : région Nord-Est
     * @param C : région Sud-Est
     * @param D : région Sud-Ouest
     * @param p : Noeud parent des 4 régions
     * @complexité : o(1)
     */

    public Quadtree( int a , int b , int c , int d , Quadtree A , Quadtree B , Quadtree C , Quadtree D , Couleur col , Quadtree p){

        this.firstX = a ; this.firstY = b;
        this.endX = c ; this.endY = d ;
        this.NO = A ; this.NE = B;
        this.SE = C ; this.SO = D;
        this.couleur = col ;
        this.parent = p ;
    }

    /**
     * @Rôle : getter de l'abscisse d'origine
     * @return : firstX
     * @complexité : o(1)
     */

    public int firstX()
    { 
        return this.firstX ; 
    }

    /**
     * @Rôle : getter de l'ordonné d'origine
     * @return : firstY
     * @complexité : o(1)
     */

    public int firstY()
    { 
        return this.firstY ; 
    }

    /**
     * @Rôle : getter de l'abscisse d'extrémité
     * @return : endX
     * @complexité : o(1)
     */

    public int endX()
    { 
        return this.endX ; 
    }

    /**
     * @Rôle : getter de l'ordonnée d'extrémité
     * @return : endY
     * @complexité : o(1)
     */

    public int endY()
    { 
        return this.endY ; 
    }

    /**
     * @Rôle : retourne un Quadtree vide
     * @complexité : o(1)
     * @return Quadtree
     * @complexité : o(1)
     */

    public static Quadtree vide()
    { 
        return null ;
    }

    /**
     * @Rôle : verifie si un Quadtree est vide
     * @param ab
     * @return boolean
     * @complexité : o(1)
     */

    public static boolean estVide( Quadtree ab)
    { 
        return ab == null ;
    }

    /**
     * @Rôle : verifie si un noeud est une feuille
     * @param ab
     * @return boolean
     * @complexité : o(1)
     */

    public boolean estFeuille( Quadtree ab ){
        return ( ab.NO == null && ab.NE == null && ab.SE == null && ab.SO == null);
    }

    /**
     * @Rôle : verifie si le centre est dans la region
     * @param p
     * @return boolean
     * @complexité : o(1)
     */

    public boolean estDansQTree(Centre p){
        return (p.getX() >= this.firstX && p.getX() <= this.endX && p.getY() >= this.firstY && p.getY() <= this.endY);
    }

    /**
     * @Rôle : verifie si le point de recoloriage est dans la region
     * @param p
     * @return boolean
     * @complexité : o(1)
     */

    public boolean estDansQTreeREc(Recoloriage p){
        return (p.getX() >= this.firstX && p.getX() <= this.endX && p.getY() >= this.firstY && p.getY() <= this.endY);
    }

    /**
     * @Rôle : divise une region en 4 sous-regions
     * @param p
     * @complexité : o(1)
     */

    public void diviserEnSousQTree(Centre p){
        
        this.NO = new Quadtree(this.firstX , this.firstY , p.getX() , p.getY() , null , null , null , null , p.getC1(), this);
        this.NE = new Quadtree(p.getX(), this.firstY , this.endX , p.getY() , null , null , null , null , p.getC2(), this);
        this.SE = new Quadtree(p.getX(), p.getY() , this.endX , this.endY,null , null , null , null , p.getC3() , this);
        this.SO = new Quadtree(this.firstX, p.getY(), p.getX(), this.endY, null , null, null, null, p.getC4() , this);
    }

    /**
     * @Rôle : cherche la region auquel le centre p appartient
     * @param p
     * @return Quadtree
     * @complexité : o(logn) car on ne s'interessera qu'à la region où le centre appartient
     *               par ex : si p appartient à la region NO , la methode va parcourir juste NO
     *                        pas besoin de parcour NE , SE et SO
     */

    public Quadtree searchQtree( Centre p ){

        if(!this.estDansQTree(p)){
            return null;
        }
        if( this.estFeuille(this)){
            return this ;
        }
        if(this.NO.estDansQTree(p)){
            return this.NO.searchQtree(p);
        } 
        else if( this.NE.estDansQTree(p)){
            return this.NE.searchQtree(p);
        } 
        else if( this.SE.estDansQTree(p)){
            return this.SE.searchQtree(p);
        } 
        else if( this.SO.estDansQTree(p)){
            return this.SO.searchQtree(p);
        }
        return  null ; // càd le point n'existe pas
    }

    /**
     * @Rôle : trouve la région auquel p appartient ensuite la divise en 4 sous-regions
     * @param p
     * @return Quadtree
     * @complexité : o(logn) : complexité de searchQtree. 
     *                       les autres méthodes utilisées par celle-ci sont en o(1) 
     */

    public Quadtree addQTree(Centre p){

        Quadtree NoeudAinserer = searchQtree(p);
        if( Quadtree.estVide(NoeudAinserer)){
            return this ; // le point est hors limite du cadre de l'arbre
        }
        if(this.estFeuille(NoeudAinserer)){
            NoeudAinserer.diviserEnSousQTree(p);
        }
        return this;

    }

    /**
     * @Rôle : à partir de deux précedentes reconstruit le Quadtree
     * @param img
     * @param data
     * @return Quadtree
     * @complexité : o(n) où n est la taille de la liste des centres
     */

    public Quadtree buildQTree(Data data){
        Quadtree arbreConstruit = vide();

        if(!data.LesCentres.isEmpty()){
            for(Centre c : data.LesCentres){
                arbreConstruit = addQTree(c);
            }
        }
        return arbreConstruit;
    }

    /**
     * @Rôle : genere un fichier texte contenant les informations sur les couleurs contenues dans l'arbre
     * @param arbre
     * @param ecriture
     * @throws IOException
     * @complexité : o(n) où n est la hauteur ou la profondeur de l'arbre
     */

    public void toText( Quadtree arbre , BufferedWriter ecriture ) throws IOException{

        if(Quadtree.estVide(arbre)){
            return;
        }
        if( this.estFeuille(arbre)){
            System.out.print(  arbre.couleur);
            ecriture.write(arbre.couleur.name());
        }else{
            System.out.print("(");
            ecriture.write("(");
            if(!Quadtree.estVide(arbre.NO)) toText(arbre.NO , ecriture);
            if(!Quadtree.estVide(arbre.NE)) toText(arbre.NE , ecriture);
            if(!Quadtree.estVide(arbre.SE)) toText(arbre.SE , ecriture);
            if(!Quadtree.estVide(arbre.SO)) toText(arbre.SO , ecriture);
            System.out.print(")");
            ecriture.write(")");
        }
    }

    /**
     * @Rôle : dessine le segment sur l'image
     * @param img
     * @param Q
     * @param x
     * @param y
     * @param data
     * @complexité : o( |x2-x1| + |y2 - y1|)
     */

    public static void drawSegment(Image img, Quadtree Q, Data data) {
        if (img == null || Q == null || data == null) {
            System.out.println("Erreur : Paramètres invalides pour dessiner un segment.");
            return;
        }
    
        Graphics2D segment = img.getImage().createGraphics();
    
        // Définir la couleur et l'épaisseur des segments
        segment.setColor(Color.BLACK); // couleur des segments
        segment.setStroke(new BasicStroke(data.e)); // l'épaisseur du segment
    
        segment.drawLine(Q.firstX(), Q.firstY(), Q.endX(), Q.firstY()); // Segment horizontal
        segment.drawLine(Q.endX(), Q.firstY(), Q.endX(), Q.endY()); // Segment vertical
        segment.drawLine(Q.firstX(), Q.endY(), Q.endX(), Q.endY()); // Segment horizontal
        segment.drawLine(Q.firstX(), Q.firstY(), Q.firstX(), Q.endY()); // Segment vertical
        
        segment.dispose(); // Libérer les ressources utilisées par Graphics2D
    }

    /**
     * @Rôle : genere une Image à partir des informations contenues dans l'arbre
     * @param img
     * @param Q
     * @complexité : o(n) où n est la hauteur ou la profondeur de l'arbre
     */

    public  void toImage(Image img , Quadtree Q ){

        if(this.estFeuille(Q)){
            if( Q.couleur == Couleur.R){
                img.setRectangle(Q.firstX, Q.endX, Q.firstY, Q.endY, Color.RED);
            } else if(Q.couleur == Couleur.B){
                img.setRectangle(Q.firstX, Q.endX, Q.firstY, Q.endY, Color.BLUE);
            } else if(Q.couleur == Couleur.J){
                img.setRectangle(Q.firstX, Q.endX, Q.firstY, Q.endY, Color.YELLOW);
            } else if(Q.couleur == Couleur.G){
                img.setRectangle(Q.firstX, Q.endX, Q.firstY, Q.endY, Color.LIGHT_GRAY);
            } else if(Q.couleur == Couleur.N){
                img.setRectangle(Q.firstX, Q.endX , Q.firstY, Q.endY, Color.BLACK);
            }
        }
        else{
             toImage(img, Q.NO);
             toImage(img, Q.NE);
             toImage(img, Q.SO);
             toImage(img, Q.SE);
        }
        
    }

    /**
     * @Rôle : dessine les segments sur l'image générée
     * @param img
     * @param Q
     * @param data
     * @complexité : o(n) où n est la hauteur ou la profondeur de l'arbre
     */

    public void drawEpaisseur(Image img , Quadtree Q , Data data ){

        if(this.estFeuille(Q)){

            Quadtree.drawSegment(img, Q,data);
        }
        else{
            drawEpaisseur(img, Q.NO, data);
            drawEpaisseur(img, Q.NE, data);
            drawEpaisseur(img, Q.SO, data);
            drawEpaisseur(img, Q.SE, data);
        }
    }

     /**
     * @Rôle : cherche la region auquel le récoloriage p appartient
     * @param p
     * @return Quadtree
     * @complexité : o(logn) car on ne s'interessera qu'à la region où le centre appartient
     *               par ex : si p appartient à la region NO , la methode va parcourir juste NO
     *                        pas besoin de parcour NE , SE et SO
     */

    public Quadtree reColor( Recoloriage p){

        if(!this.estDansQTreeREc(p)){
            return null;
        }
        if( this.estFeuille(this)){
            return this ;
        }
        if(this.NO.estDansQTreeREc(p)){
            return this.NO.reColor(p);
        } 
        else if( this.NE.estDansQTreeREc(p)){
            return this.NE.reColor(p);
        } 
        else if( this.SE.estDansQTreeREc(p)){
            return this.SE.reColor(p);
        } 
        else if( this.SO.estDansQTreeREc(p)){
            return this.SO.reColor(p);
        }
        return  null ; // càd le point n'existe pas
    }

    /**
     * @Rôle : fait la compression de l'image avant le recoloriage 
     * @param p
     * @param img
     * @param data
     * @complexité : même que pour la méthode drawEpaisseur
     */

    public void compressQTree( Recoloriage p , Image img , Data data){

        Quadtree NoeudArecolorier = reColor(p);
        if(!Quadtree.estVide(NoeudArecolorier)){
            NoeudArecolorier.couleur = p.getC();
            if(    NoeudArecolorier.couleur == NoeudArecolorier.parent.NO.couleur
                && NoeudArecolorier.couleur == NoeudArecolorier.parent.NE.couleur
                && NoeudArecolorier.couleur == NoeudArecolorier.parent.SE.couleur
                && NoeudArecolorier.couleur == NoeudArecolorier.parent.SO.couleur
                && estFeuille(NoeudArecolorier.parent.NO)
                && estFeuille(NoeudArecolorier.parent.NE)
                && estFeuille(NoeudArecolorier.parent.SE)
                && estFeuille(NoeudArecolorier.parent.SO)
            ){
                
                NoeudArecolorier.parent.NO = null;
                NoeudArecolorier.parent.NE = null;
                NoeudArecolorier.parent.SE = null;
                NoeudArecolorier.parent.SO = null;
                NoeudArecolorier.parent.couleur = NoeudArecolorier.couleur ;

                if( NoeudArecolorier.parent.couleur == Couleur.R){
                    img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX, NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.RED);
                    drawSegment(img, NoeudArecolorier.parent, data);
                
                } else if(NoeudArecolorier.parent.couleur == Couleur.B){
                    img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX, NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.BLUE);
                    drawSegment(img, NoeudArecolorier.parent, data);
                
                } else if(NoeudArecolorier.parent.couleur == Couleur.J){
                    img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX, NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.YELLOW);
                    drawSegment(img, NoeudArecolorier.parent, data);
                
                } else if(NoeudArecolorier.parent.couleur == Couleur.G){
                    img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX, NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.LIGHT_GRAY);
                    drawSegment(img, NoeudArecolorier.parent, data);
                
                } else if(NoeudArecolorier.parent.couleur == Couleur.N){
                    img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX , NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.BLACK);
                    drawSegment(img, NoeudArecolorier.parent, data);
                    
                }
            }
            else{
                if( this.estFeuille(NoeudArecolorier)){

                    if( NoeudArecolorier.couleur == Couleur.R){
                        img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX, NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.RED);
                        drawSegment(img, NoeudArecolorier, data);
                    } else if(NoeudArecolorier.couleur == Couleur.B){
                        img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX, NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.BLUE);
                        drawSegment(img, NoeudArecolorier, data);
                    } else if(NoeudArecolorier.couleur == Couleur.J){
                        img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX, NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.YELLOW);
                        drawSegment(img, NoeudArecolorier, data);
                    } else if(NoeudArecolorier.couleur == Couleur.G){
                        img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX, NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.LIGHT_GRAY);
                        drawSegment(img, NoeudArecolorier, data);
                    } else if(NoeudArecolorier.couleur == Couleur.N){
                        img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX , NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.BLACK);
                        drawSegment(img, NoeudArecolorier, data);
                    }
                }
            }
        }
    }
}