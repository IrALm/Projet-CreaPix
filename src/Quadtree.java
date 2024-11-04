import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;

public class Quadtree {

    private int firstX , firstY,
                endX , endY ;
    private Quadtree NO, NE, SE, SO , parent; 
    private MonBoTablo.Couleur couleur;

    public Quadtree( int a , int b , int c , int d , Quadtree A , Quadtree B , Quadtree C , Quadtree D , MonBoTablo.Couleur col , Quadtree p){

        this.firstX = a ; this.firstY = b;
        this.endX = c ; this.endY = d ;
        this.NO = A ; this.NE = B;
        this.SE = C ; this.SO = D;
        this.couleur = col ;
        this.parent = p ;
    }

    public int firstX(){ return this.firstX ; }
    public int firstY(){ return this.firstY ; }
    public int endX(){ return this.endX ; }
    public int endY(){ return this.endY ; }
    @Override
    public Quadtree clone(){
        return new Quadtree(this.firstX , this.firstY , this.endX , this.endY , this.NO , this.NE , this.SE , this.SO , this.couleur , this.parent);
    }
    @Override
    public String toString(){
        return "  " + this.firstX + "  " + this.firstY + "  " + this.endX + "  " + this.endY + "  " + this.couleur;
    }

    public static Quadtree vide(){ return null ;}
    public static boolean estVide( Quadtree ab){ return ab == null ;}
    public boolean estFeuille( Quadtree ab ){
        return ( ab.NO == null && ab.NE == null && ab.SE == null && ab.SO == null);
    }
    public boolean estDansQTree(Centre p){
        return (p.getX() >= this.firstX && p.getX() <= this.endX && p.getY() >= this.firstY && p.getY() <= this.endY);
    }
    public boolean estDansQTreeREc(Recoloriage p){
        return (p.getX() >= this.firstX && p.getX() <= this.endX && p.getY() >= this.firstY && p.getY() <= this.endY);
    }
    public void diviserEnSousQTree(Centre p){
        
        this.NO = new Quadtree(this.firstX, p.getY(), p.getX(), this.endY, null , null, null, null, p.getC1() , this);
        this.NE = new Quadtree(p.getX(), p.getY() , this.endX , this.endY,null , null , null , null , p.getC2() , this);
        this.SE = new Quadtree(p.getX(), this.firstY , this.endX , p.getY() , null , null , null , null , p.getC3(), this);
        this.SO = new Quadtree(this.firstX , this.firstY , p.getX() , p.getY() , null , null , null , null , p.getC4(), this);
    }

    public Quadtree searchQtree( Centre p ){

        if(!this.estDansQTree(p)){
            return null;
        }
        if( this.estFeuille(this)){
            return this ;
        }
        if( !Quadtree.estVide(this.NO)){
            if(this.NO.estDansQTree(p)){
                return this.NO.searchQtree(p);
            } else if( this.NE.estDansQTree(p)){
                return this.NE.searchQtree(p);
            } else if( this.SE.estDansQTree(p)){
                return this.SE.searchQtree(p);
            } else if( this.SO.estDansQTree(p)){
                return this.SO.searchQtree(p);
            }
        }
        return  null ; // càd le point n'existe pas
    }

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

    public Quadtree buildQTree( Image img , Data data){
        Quadtree arbreConstruit = vide();
        for(Centre c : data.LesCentres){
            arbreConstruit = addQTree(c);
        }
        return arbreConstruit;
        
    }

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
            if(!Quadtree.estVide(arbre.NO)) toText(arbre.SO , ecriture);
            if(!Quadtree.estVide(arbre.NE)) toText(arbre.SE , ecriture);
            if(!Quadtree.estVide(arbre.SE)) toText(arbre.NE , ecriture);
            if(!Quadtree.estVide(arbre.SO)) toText(arbre.NO , ecriture);
            System.out.print(")");
            ecriture.write(")");
        }
    }

    public static void drawSegment(Image img, Quadtree Q, int x , int y, Data data) {
        if (img == null || Q == null || data == null) {
            System.out.println("Erreur : Paramètres invalides pour dessiner un segment.");
            return;
        }
    
        Graphics2D segment = img.getImage().createGraphics();
    
        // Définir la couleur et l'épaisseur des segments
        segment.setColor(Color.BLACK); // couleur des segments
        segment.setStroke(new BasicStroke(data.e)); // l'épaisseur du segment
    
        // Dessiner un segment horizontal et vertical en croix au point P
        segment.drawLine(Q.firstX(), y, Q.endX(), y); // Segment horizontal
        segment.drawLine(x, Q.firstY(), x, Q.endY()); // Segment vertical
        
        segment.dispose(); // Libérer les ressources utilisées par Graphics2D
    }

    public  void toImage(Image img , Quadtree Q , Data data){

        if(this.estFeuille(Q)){
            if( Q.couleur == MonBoTablo.Couleur.R){
                img.setRectangle(Q.firstX, Q.endX, Q.firstY, Q.endY, Color.RED);
                Quadtree.drawSegment(img, Q, Q.endX, Q.firstY, data);
            } else if(Q.couleur == MonBoTablo.Couleur.B){
                img.setRectangle(Q.firstX, Q.endX, Q.firstY, Q.endY, Color.BLUE);
               Quadtree.drawSegment(img, Q, Q.endX, Q.firstY, data);
            } else if(Q.couleur == MonBoTablo.Couleur.J){
                img.setRectangle(Q.firstX, Q.endX, Q.firstY, Q.endY, Color.YELLOW);
                Quadtree.drawSegment(img, Q, Q.endX, Q.firstY, data);
            } else if(Q.couleur == MonBoTablo.Couleur.G){
                img.setRectangle(Q.firstX, Q.endX, Q.firstY, Q.endY, Color.LIGHT_GRAY);
                Quadtree.drawSegment(img, Q, Q.endX, Q.firstY, data);
            } else if(Q.couleur == MonBoTablo.Couleur.N){
                img.setRectangle(Q.firstX, Q.endX , Q.firstY, Q.endY, Color.BLACK);
                Quadtree.drawSegment(img, Q, Q.endX, Q.firstY, data);
            }
        }
        else{
             toImage(img, Q.NO, data);
             toImage(img, Q.NE, data);
             toImage(img, Q.SO, data);
             toImage(img, Q.SE, data);
        }
        
    }
   

    public Quadtree reColor( Recoloriage p){

        if(!this.estDansQTreeREc(p)){
            return null;
        }
        if( this.estFeuille(this)){
            return this ;
        }
        if( !Quadtree.estVide(this.NO)){
            if(this.NO.estDansQTreeREc(p)){
                return this.NO.reColor(p);
            } else if( this.NE.estDansQTreeREc(p)){
                return this.NE.reColor(p);
            } else if( this.SE.estDansQTreeREc(p)){
                return this.SE.reColor(p);
            } else if( this.SO.estDansQTreeREc(p)){
                return this.SO.reColor(p);
            }
        }
        return  null ; // càd le point n'existe pas
    }


    public void compressQTree( Recoloriage p , Image img , Data data){

        Quadtree NoeudArecolorier = reColor(p);
        NoeudArecolorier.couleur = p.getC();
        if(    NoeudArecolorier.couleur == NoeudArecolorier.parent.NO.couleur
            && NoeudArecolorier.couleur == NoeudArecolorier.parent.NE.couleur
            && NoeudArecolorier.couleur == NoeudArecolorier.parent.SE.couleur
            && NoeudArecolorier.couleur == NoeudArecolorier.parent.SO.couleur){
            
            NoeudArecolorier.parent.NO = null;
            NoeudArecolorier.parent.NE = null;
            NoeudArecolorier.parent.SE = null;
            NoeudArecolorier.parent.SO = null;
            NoeudArecolorier.parent.couleur = NoeudArecolorier.couleur ;

            if( NoeudArecolorier.parent.couleur == MonBoTablo.Couleur.R){
                img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX, NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.RED);
             
            } else if(NoeudArecolorier.parent.couleur == MonBoTablo.Couleur.B){
                img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX, NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.BLUE);
               
            } else if(NoeudArecolorier.parent.couleur == MonBoTablo.Couleur.J){
                img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX, NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.YELLOW);
                
            } else if(NoeudArecolorier.parent.couleur == MonBoTablo.Couleur.G){
                img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX, NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.LIGHT_GRAY);
               
            } else if(NoeudArecolorier.parent.couleur == MonBoTablo.Couleur.N){
                img.setRectangle(NoeudArecolorier.parent.firstX, NoeudArecolorier.parent.endX , NoeudArecolorier.parent.firstY, NoeudArecolorier.parent.endY, Color.BLACK);
                
                
            }
        }
        else{
            if( this.estFeuille(NoeudArecolorier)){

                if( NoeudArecolorier.couleur == MonBoTablo.Couleur.R){
                    img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX, NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.RED);
                } else if(NoeudArecolorier.couleur == MonBoTablo.Couleur.B){
                    img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX, NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.BLUE);
                } else if(NoeudArecolorier.couleur == MonBoTablo.Couleur.J){
                    img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX, NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.YELLOW);
                } else if(NoeudArecolorier.couleur == MonBoTablo.Couleur.G){
                    img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX, NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.LIGHT_GRAY);
                } else if(NoeudArecolorier.couleur == MonBoTablo.Couleur.N){
                    img.setRectangle(NoeudArecolorier.firstX, NoeudArecolorier.endX , NoeudArecolorier.firstY, NoeudArecolorier.endY, Color.BLACK);
                }
    
            }
        }
        
        
        
        
        
    }

    public void printQTree(){
        if(!Quadtree.estVide(this)){
            System.out.println( " Les coordonnées + couleurs : " + this.toString());
            if( !Quadtree.estVide(this.NO)){
                this.NO.printQTree();
                this.NE.printQTree();
                this.SE.printQTree();
                this.SO.printQTree();
            }
        }
    }

    
    
}