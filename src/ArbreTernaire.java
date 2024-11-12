import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;

public class ArbreTernaire{

    private int x1 , y1,
                   x2 , y2,
                   x3, y3;
    private MonBoTablo.Couleur c;
    private ArbreTernaire gauche , milieu , droit , parent;

    public ArbreTernaire( int _x1 , int _y1 , int _x2 , int _y2 , int _x3 , int _y3 ,
                          MonBoTablo.Couleur _c , ArbreTernaire _gauche , ArbreTernaire _milieu 
                          , ArbreTernaire _droit , ArbreTernaire _parent){
                        
        this.x1 = _x1 ; this.y1 = _y1 ; this.x2 = _x2 ; this.y2 = _y2 ; this.x3 = _x3 ; this.y3 = _y3;
        this.c = _c ; this.gauche = _gauche ; this.milieu = _milieu ; this.droit = _droit ; this.parent = _parent;
    }
    public ArbreTernaire(){
        this.x1 = this.y1 = this.x2 = this.y2 = this.x3 = this.y3 = 0;
            this.gauche = this.milieu = this.droit = null; this.c = null;
    }

    @Override
    public String toString(){
        return "  " + this.x1 + "  " + this.y1 + "  " + this.x2 + "  " + this.y2 + "  " + this.x3 + "  " + this.y3 + " " + this.c;
    }

    public static ArbreTernaire vide(){ return null ;}
    public static boolean estVide( ArbreTernaire ab){ return ab == null ;}
    public boolean estFeuille( ArbreTernaire ab ){
        return ( ab.gauche == null && ab.milieu == null && ab.droit == null );
    }

    private double aireDuTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }
    public boolean estDansAbTer(Centre p ){

        double aireTriangleABC = aireDuTriangle(this.x1, this.y1,this.x2, this.y2, this.x3, this.y3);
        double aireTrianglePAB = aireDuTriangle(p.getX(), p.getY(), this.x1, this.y1,this.x2, this.y2);
        double aireTrianglePBC = aireDuTriangle(p.getX(), p.getY(), this.x2, this.y2, this.x3, this.y3);
        double aireTrianglePCA = aireDuTriangle(p.getX(), p.getY(), this.x3, this.y3, this.x1, this.y1);
        return (aireTriangleABC == aireTrianglePAB + aireTrianglePBC + aireTrianglePCA);
    }
    public boolean estDansAbTerREc(Recoloriage p){
        
        double aireTriangleABC = aireDuTriangle(this.x1, this.y1,this.x2, this.y2, this.x3, this.y3);
        double aireTrianglePAB = aireDuTriangle(p.getX(), p.getY(), this.x1, this.y1,this.x2, this.y2);
        double aireTrianglePBC = aireDuTriangle(p.getX(), p.getY(), this.x2, this.y2, this.x3, this.y3);
        double aireTrianglePCA = aireDuTriangle(p.getX(), p.getY(), this.x3, this.y3, this.x1, this.y1);
        return (aireTriangleABC == aireTrianglePAB + aireTrianglePBC + aireTrianglePCA);
    }
    public void diviserEnSousAbTer(Image img , Centre p){
        
        if( this.x1 == 0 && this.y1 == 0 && this.x2 == 0 && this.y2 ==0 && this.x3 == 0 && this.y3 ==  0 ){
            if( p.getX() >= 0 && p.getX() <= img.width() && p.getY() >= 0 && p.getY() <= img.height()){
                this.gauche = new ArbreTernaire(0, img.height(), img.height(), img.height(), p.getX(), p.getY(), p.getC1(), null, null, null, this);
                this.milieu = new ArbreTernaire(img.height(), img.height(), img.height(), 0, p.getX(), p.getY(), p.getC2(), null, null, null, this);
                this.droit = new ArbreTernaire(img.height(), 0, 0, 0, p.getX(), p.getY(), p.getC3(), null, null, null, this);
            }
        }
        else{
            this.gauche = new ArbreTernaire(this.x1, this.y1, this.x2, this.y2, p.getX(), p.getY(), p.getC1() ,null,null,null ,this);
            this.milieu = new ArbreTernaire(this.x2, this.y2, this.x3, this.y3, p.getX(), p.getY(), p.getC2() ,null,null,null, this);
            this.droit = new ArbreTernaire(this.x3, this.y3, this.x1, this.y1, p.getX(), p.getY() , p.getC3(),null,null,null ,this);
    
        }
    }
    

    public ArbreTernaire searchAbTer( Centre p ){

        
            if(!this.estDansAbTer(p)){
                return null;
            }
            if( this.estFeuille(this)){
                return this ;
            }
            if( !ArbreTernaire.estVide(this.gauche)){
                if(this.gauche.estDansAbTer(p)){
                    return this.gauche.searchAbTer(p);
                }
            }
            if( !ArbreTernaire.estVide(this.milieu)){
                if( this.milieu.estDansAbTer(p)){
                    return this.milieu.searchAbTer(p);
                } 
            }
            if( !ArbreTernaire.estVide(this.droit)){
                if( this.droit.estDansAbTer(p)){
                    return this.droit.searchAbTer(p);
                }
            }
        
        return  null ; // càd le point n'existe pas
    }

    public ArbreTernaire addAbTer(Image img , Centre p){

        ArbreTernaire NoeudAinserer = searchAbTer(p);
        if( ArbreTernaire.estVide(NoeudAinserer)){
            return this ; // le point est hors limite du cadre de l'arbre
        }
        if(this.estFeuille(NoeudAinserer)){
           NoeudAinserer.diviserEnSousAbTer(img , p);
        }
        return this;

    }

    public ArbreTernaire  buildAbTer( Image img , Data data){
        ArbreTernaire arbreConstruit = vide();
        for(Centre c : data.LesCentres){
            arbreConstruit = addAbTer(img , c);
        }
        return arbreConstruit;
        
    }

    public void toText( ArbreTernaire arbre , BufferedWriter ecriture ) throws IOException{

        if(ArbreTernaire.estVide(arbre)){
            return;
        }
        if( this.estFeuille(arbre)){
            System.out.print(  arbre.c);
            ecriture.write(arbre.c.name());
        }else{
            System.out.print("(");
            ecriture.write("(");
            if(!ArbreTernaire.estVide(arbre.gauche)) toText(arbre.gauche , ecriture);
            if(!ArbreTernaire.estVide(arbre.milieu)) toText(arbre.milieu , ecriture);
            if(!ArbreTernaire.estVide(arbre.droit)) toText(arbre.droit , ecriture);
            System.out.print(")");
            ecriture.write(")");
        }
    }

    public  void toImage(Image img , ArbreTernaire ab ){

        if(this.estFeuille(ab)){
            if( ab.c == MonBoTablo.Couleur.R){
               img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.RED);
            } else if(ab.c == MonBoTablo.Couleur.B){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.BLUE);
            } else if(ab.c == MonBoTablo.Couleur.J){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.YELLOW);
            } else if(ab.c == MonBoTablo.Couleur.G){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.LIGHT_GRAY); 
            } else if(ab.c == MonBoTablo.Couleur.N){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.BLACK);
            }
        }
        else{
             toImage(img, ab.gauche);
             toImage(img, ab.milieu);
             toImage(img, ab.droit);
        }
        
    }
    public static void drawSegment(Image img, ArbreTernaire ab, int x , int y, Data data) {
        if (img == null || ab == null || data == null) {
            System.out.println("Erreur : Paramètres invalides pour dessiner un segment.");
            return;
        }

        Graphics2D segment = img.getImage().createGraphics();
    
        // Définir la couleur et l'épaisseur des segments
        segment.setColor(Color.BLACK); // couleur des segments
        segment.setStroke(new BasicStroke(data.e)); // l'épaisseur du segment
    
        // Dessiner un segment horizontal et vertical en croix au point P
        segment.drawLine(x,y,ab.x1 , ab.y1); // Segment oblique
        segment.drawLine(x,y,ab.x2,ab.y2); // Segment oblique
        
        segment.dispose(); // Libérer les ressources utilisées par Graphics2D
    }
    public void drawEpaisseur(Image img ,ArbreTernaire ab , Data data ){

        if(this.estFeuille(ab)){

            ArbreTernaire.drawSegment(img, ab, ab.x3, ab.y3, data);
        }
        else{
            drawEpaisseur(img, ab.gauche, data);
            drawEpaisseur(img, ab.milieu, data);
            drawEpaisseur(img, ab.droit, data);
        }
    }

    public ArbreTernaire reColor( Recoloriage p){

        if(!this.estDansAbTerREc(p)){
            return null;
        }
        if( this.estFeuille(this)){
            return this ;
        }
        if( !ArbreTernaire.estVide(this.gauche)){
            if(this.gauche.estDansAbTerREc(p)){
                return this.gauche.reColor(p);
            } else if( this.milieu.estDansAbTerREc(p)){
                return this.milieu.reColor(p);
            } else if( this.droit.estDansAbTerREc(p)){
                return this.droit.reColor(p);
            } 
        }
        return  null ; // càd le point n'existe pas
    }


    public void compressQTree( Recoloriage p , Image img , Data data){

        ArbreTernaire NoeudArecolorier = reColor(p);
        NoeudArecolorier.c = p.getC();
        if(    NoeudArecolorier.c == NoeudArecolorier.parent.gauche.c
            && NoeudArecolorier.c == NoeudArecolorier.parent.milieu.c
            && NoeudArecolorier.c == NoeudArecolorier.parent.droit.c
            ){
            
            NoeudArecolorier.parent.gauche = null;
            NoeudArecolorier.parent.milieu = null;
            NoeudArecolorier.parent.droit = null;
            NoeudArecolorier.parent.c = NoeudArecolorier.c ;

            if( NoeudArecolorier.parent.c == MonBoTablo.Couleur.R){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.RED);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
            
            } else if(NoeudArecolorier.parent.c == MonBoTablo.Couleur.B){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.BLUE);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
            
            } else if(NoeudArecolorier.parent.c == MonBoTablo.Couleur.J){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.YELLOW);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
            
            } else if(NoeudArecolorier.parent.c == MonBoTablo.Couleur.G){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.LIGHT_GRAY);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
            
            } else if(NoeudArecolorier.parent.c == MonBoTablo.Couleur.N){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.BLACK);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }
        }
        else{
            if( this.estFeuille(NoeudArecolorier)){

                if( NoeudArecolorier.c == MonBoTablo.Couleur.R){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.RED);
                    drawEpaisseur(img, NoeudArecolorier, data);
                } else if(NoeudArecolorier.c == MonBoTablo.Couleur.B){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.BLUE);
                    drawEpaisseur(img, NoeudArecolorier, data);
                } else if(NoeudArecolorier.c == MonBoTablo.Couleur.J){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.YELLOW);
                    drawEpaisseur(img, NoeudArecolorier, data);
                } else if(NoeudArecolorier.c == MonBoTablo.Couleur.G){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.LIGHT_GRAY);
                    drawEpaisseur(img, NoeudArecolorier, data);
                } else if(NoeudArecolorier.c == MonBoTablo.Couleur.N){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.BLACK);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }
    
            }
        }
        
        
        
        
        
    }
    public void printAbTer(){
        if(!ArbreTernaire.estVide(this)){
            System.out.println( " Les coordonnées + couleurs : " + this.toString());
            if( !ArbreTernaire.estVide(this.gauche)){
                System.out.println(" Sous-arbre gauche");   
                this.gauche.printAbTer();
            }
            if( !ArbreTernaire.estVide(this.milieu)){
                System.out.println(" Sous-arbre milieu");
                this.milieu.printAbTer();
            }
            if( !ArbreTernaire.estVide(this.droit)){
                System.out.println(" Sous-arbre droit");
                this.droit.printAbTer();
            }

        }
    }

    
    
}