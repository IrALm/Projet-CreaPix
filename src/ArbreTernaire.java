import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;

/*
 * Un arbre ternaire représente la region totale de l'image
 * il comprend 3 autres noeuds qui sont des regions triangulaires 
 * dans notre implémentation , une couleur associé à chaque region
 * Ainsi que les coordonnées de chaque coin du triangle
 * et Un noued parent qui nous sera utile lors du recoloriage
 */

public class ArbreTernaire{

    private int x1 , y1, // coin n°1
                x2 , y2, // coin n°2
                x3, y3; // coin n°3
    private Couleur c; // coleur associé à la region
    private ArbreTernaire gauche , milieu , droit , parent;// les noeuds répresentant les regions ainsi que le noeud parent

    /**
     * @Rôle : constructeur de la classe
     * @param _x1
     * @param _y1
     * @param _x2
     * @param _y2
     * @param _x3
     * @param _y3
     * @param _c
     * @param _gauche
     * @param _milieu
     * @param _droit
     * @param _parent
     * @complexité : o(1)
     */
    
    public ArbreTernaire( int _x1 , int _y1 , int _x2 , int _y2 , int _x3 , int _y3 ,
                          Couleur _c , ArbreTernaire _gauche , ArbreTernaire _milieu 
                          , ArbreTernaire _droit , ArbreTernaire _parent){
                        
        this.x1 = _x1 ; this.y1 = _y1 ; this.x2 = _x2 ; this.y2 = _y2 ; this.x3 = _x3 ; this.y3 = _y3;
        this.c = _c ; this.gauche = _gauche ; this.milieu = _milieu ; this.droit = _droit ; this.parent = _parent;
    }

    /**
     * @Rôle : un autre constructeur  pour initialiser un arbre non divisé en 3 regions rectangulaires au départ
     * @complexité : o(1)
     */

    public ArbreTernaire(){
        this.x1 = this.y1 = this.x2 = this.y2 = this.x3 = this.y3 = 0;
        this.gauche = this.milieu = this.droit = null; this.c = null;
    }

    /**
     * @Rôle : renvoie les coordonnées de la region sous formes de chaines
     * @complexité : o(1)
     */

    @Override
    public String toString(){
        return "  ( " + this.x1 + "  " + this.y1 + "  ) ; ( " + this.x2 + "  " + this.y2 + "  ) ; ( " + this.x3 + "  " + this.y3 + " ) : " + this.c;
    }

    /**
     * @Rôle : retourne un arbre vide
     * @complexité : o(1)
     * @return ArbreTernaire
     */

    public static ArbreTernaire vide()
    { 
        return null ;
    }

    /**
     * @Rôle : verifie si un arbre est vide
     * @param ab
     * @return boolean
     * @complexité : o(1)
     */

    public static boolean estVide( ArbreTernaire ab)
    { 
        return ab == null ;
    }

    /**
     * @Rôle : verifie si le noeud est une feuille
     * @param ab
     * @return boolean
     * @complexité : o(1)
     */

    public boolean estFeuille( ArbreTernaire ab )
    {
        return ( ab.gauche == null && ab.milieu == null && ab.droit == null );
    }

    /**
     * @Rôle : calcule l'aire du triangle
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     * @return double
     * @complexité : o(1)
     */

    private double aireDuTriangle(int x1, int y1, int x2, int y2, int x3, int y3) 
    {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }

    /**
     * @Rôle : verifie si le centre donné se trouve dans la region concernée
     * @param p
     * @return boolean
     * @complexité : o(1)
     */

    public boolean estDansAbTer(Centre p ){

        double aireTriangleABC = aireDuTriangle(this.x1, this.y1,this.x2, this.y2, this.x3, this.y3);
        double aireTrianglePAB = aireDuTriangle(p.getX(), p.getY(), this.x1, this.y1,this.x2, this.y2);
        double aireTrianglePBC = aireDuTriangle(p.getX(), p.getY(), this.x2, this.y2, this.x3, this.y3);
        double aireTrianglePCA = aireDuTriangle(p.getX(), p.getY(), this.x3, this.y3, this.x1, this.y1);
        return (aireTriangleABC == aireTrianglePAB + aireTrianglePBC + aireTrianglePCA);
    }

    /**
     * @Rôle : verifie si le reccoloriage p est dans la region concernée
     * @param p
     * @return boolean
     * @complexité : o(1)
     */

    public boolean estDansAbTerREc(Recoloriage p){
        
        double aireTriangleABC = aireDuTriangle(this.x1, this.y1,this.x2, this.y2, this.x3, this.y3);
        double aireTrianglePAB = aireDuTriangle(p.getX(), p.getY(), this.x1, this.y1,this.x2, this.y2);
        double aireTrianglePBC = aireDuTriangle(p.getX(), p.getY(), this.x2, this.y2, this.x3, this.y3);
        double aireTrianglePCA = aireDuTriangle(p.getX(), p.getY(), this.x3, this.y3, this.x1, this.y1);
        return (aireTriangleABC == aireTrianglePAB + aireTrianglePBC + aireTrianglePCA);
    }

    /**
     * @Rôle : diviser la region où le centre se trouve en 3 sous-régions triangulaires
     * @param p
     * @complexité : o(1)
     */

    public void diviserEnSousAbTer( Centre p){
        
        this.gauche = new ArbreTernaire(this.x3, this.y3, this.x1, this.y1, p.getX(), p.getY() , p.getC3(),null,null,null ,this);
        this.milieu = new ArbreTernaire(this.x2, this.y2, this.x3, this.y3, p.getX(), p.getY(), p.getC2() ,null,null,null, this);
        this.droit = new ArbreTernaire(this.x1, this.y1, this.x2, this.y2, p.getX(), p.getY(), p.getC1() ,null,null,null ,this);
    }
    
    /**
     * @Rôle : cherche la region auquel le centre p appartient
     * @param p
     * @param img
     * @return Arbre ternaire
     * @complexité : o(logn) car on ne s'interessera qu'à la region où le centre appartient
     *               par ex : si p appartient à la region gauche , la methode va parcourir juste gauche
     *                        pas besoin de parcour milieu et droit
     */

    public ArbreTernaire searchAbTer( Centre p , Image img ){

        /* 
         * Si l'arbre est encore ( i.e que la region est encore rectangulaire),
         * je le divise en 3 sous-regions triangulaires à partir du centre du rectangle :
         * La region de droite sera alors la plus grande region car elle ira
         * du coin (0,0) au coin ( n ,n ) coordonnées max de l'image et de ( 0, 0) à (0,n)
         */
        if( this.x1 == 0 && this.y1 == 0 && this.x2 == 0 && this.y2 ==0 && this.x3 == 0 && this.y3 ==  0 ){
            if( p.getX() >= 0 && p.getX() <= img.width() && p.getY() >= 0 && p.getY() <= img.height()){
                this.droit = new ArbreTernaire(0, 0, img.height(), img.height(), 0, img.height(), Couleur.W, null, null, null, this);
                this.milieu = new ArbreTernaire(img.height(), img.height(), img.height(), 0, img.width()/2, img.height()/2,Couleur.W , null, null, null, this);
                this.gauche = new ArbreTernaire(img.height(), 0, 0, 0, img.width()/2, img.height()/2, Couleur.W, null, null, null, this);
            }
            this.x1 = 5 ; this.y1 = 54 ; this.x2 = 6 ; this.y2 = 9 ; this.x3 = 4 ; this.y3 = 12; // pour que la condition ne soit valable qu'une fois
        }
        if( this.estFeuille(this)){
            return this ;
        }
        if(this.gauche.estDansAbTer(p)){
            return this.gauche.searchAbTer(p , img);
        }
        else if( this.milieu.estDansAbTer(p)){
            return this.milieu.searchAbTer(p , img);
        } 
        else if( this.droit.estDansAbTer(p)){
            return this.droit.searchAbTer(p , img);
        }
        return  null ; // càd le point n'existe pas
    }

    /**
     * @Rôle :trouve la région auquel p appartient ensuite la divise en 3 sous-regions triangulaires
     * @param img
     * @param p
     * @return ArbreTernaire
     * @complexité : o(logn) : complexité de searchAbTer. 
     *                       les autres méthodes utilisées par celle-ci sont en o(1)
     */

    public ArbreTernaire addAbTer(Image img , Centre p){

        ArbreTernaire NoeudAinserer = searchAbTer(p,img);
        if( ArbreTernaire.estVide(NoeudAinserer)){
            return this ; // le point est hors limite du cadre de l'arbre
        }
        if(this.estFeuille(NoeudAinserer)){
           NoeudAinserer.diviserEnSousAbTer(p);
        }
        return this;

    }

    /**
     * @Rôle :à partir de deux précedentes reconstruit l'Arbre Ternaire
     * @param img
     * @param data
     * @return ArbreTernaire
     * @complexité : o(n) où n est la taille de la liste des centres
     */
    public ArbreTernaire  buildAbTer( Image img , Data data){
        ArbreTernaire arbreConstruit = vide();
        if(!data.LesCentres.isEmpty()){
            for(Centre c : data.LesCentres){
                arbreConstruit = addAbTer(img , c);
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

    /**
     * @Rôle : genere une Image à partir des informations contenues dans l'arbre
     * @param img
     * @param ab
     * @complexité : o(n) où n est la hauteur ou la profondeur de l'arbre
     */

    public  void toImage(Image img , ArbreTernaire ab ){

        if(this.estFeuille(ab)){
            if( ab.c == Couleur.R){
               img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.RED);
            } else if(ab.c == Couleur.B){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.BLUE);
            } else if(ab.c == Couleur.J){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.YELLOW);
            } else if(ab.c == Couleur.G){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.LIGHT_GRAY); 
            } else if(ab.c == Couleur.N){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.BLACK);
            }else if(ab.c == Couleur.W){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.WHITE);
            }else if(ab.c == Couleur.V){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.GREEN);
            }else if(ab.c == Couleur.O){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.ORANGE);
            }else if(ab.c == Couleur.C){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.CYAN);
            }else if(ab.c == Couleur.D){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.DARK_GRAY);
            }else if(ab.c == Couleur.S){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.PINK);
            }else if(ab.c == Couleur.M){
                img.setTriangle(ab.x1, ab.y1, ab.x2, ab.y2, ab.x3, ab.y3, Color.MAGENTA);
            }
            
        }
        else{
             toImage(img, ab.gauche);
             toImage(img, ab.milieu);
             toImage(img, ab.droit);
        }
        
    }

    /**
     * @Rôle : dessine le segment sur l'image
     * @param img
     * @param ab
     * @param data
     * @complexité : o( |x2-x1| + |y2 - y1| + |x3 - x1| + |y3 - y1|)
     */

    public static void drawSegment(Image img, ArbreTernaire ab, Data data) {
        if (img == null || ab == null || data == null) {
            System.out.println("Erreur : Paramètres invalides pour dessiner un segment.");
            return;
        }

        Graphics2D segment = img.getImage().createGraphics();
    
        // Définir la couleur et l'épaisseur des segments
        segment.setColor(Color.BLACK); // couleur des segments
        segment.setStroke(new BasicStroke(data.e)); // l'épaisseur du segment
    
        // Dessiner un segment horizontal et vertical en croix au point P
        segment.drawLine(ab.x1 , ab.y1 , ab.x2 , ab.y2); // Segment oblique
        segment.drawLine(ab.x2,ab.y2 , ab.x3 , ab.y3); // Segment oblique
        segment.drawLine(ab.x3,ab.y3, ab.x1 , ab.y1); // Segment oblique
        
        segment.dispose(); // Libérer les ressources utilisées par Graphics2D
    }

    /**
     * @Rôle : dessine les segments sur l'image générée
     * @param img
     * @param ab
     * @param data
     * @complexité : o(n) où n est la hauteur ou la profondeur de l'arbre
     */

    public void drawEpaisseur(Image img ,ArbreTernaire ab , Data data ){

        if(this.estFeuille(ab)){

            ArbreTernaire.drawSegment(img, ab, data);
        }
        else{
            drawEpaisseur(img, ab.gauche, data);
            drawEpaisseur(img, ab.milieu, data);
            drawEpaisseur(img, ab.droit, data);
        }
    }

     /**
     * @Rôle : cherche la region auquel le récoloriage p appartient
     * @param p
     * @return ArbreTernaire
     * @complexité : o(logn) car on ne s'interessera qu'à la region où le centre appartient
     *               par ex : si p appartient à la region droite , la methode va parcourir juste droite
     *                        pas besoin de parcour gauche  et milieu
     */

    public ArbreTernaire reColor( Recoloriage p){

       
        if( this.estFeuille(this)){
            return this ;
        }
        if(this.gauche.estDansAbTerREc(p)){
            return this.gauche.reColor(p);
        } 
        else if( this.milieu.estDansAbTerREc(p)){
            return this.milieu.reColor(p);
        } 
        else if( this.droit.estDansAbTerREc(p)){
            return this.droit.reColor(p);
        } 
        return  null ; // càd le point n'existe pas
    }

    /**
     * @Rôle : fait la compression de l'image avant le recoloriage si possible
     * @param p
     * @param img
     * @param data
     * @complexité : même que pour la méthode drawEpaisseur
     */

    public void compressQTree( Recoloriage p , Image img , Data data){

        ArbreTernaire NoeudArecolorier = reColor(p);
        NoeudArecolorier.c = p.getC();
        if(    NoeudArecolorier.c == NoeudArecolorier.parent.gauche.c
            && NoeudArecolorier.c == NoeudArecolorier.parent.milieu.c
            && NoeudArecolorier.c == NoeudArecolorier.parent.droit.c
            && estFeuille(NoeudArecolorier.parent.gauche)
            && estFeuille(NoeudArecolorier.parent.milieu)
            && estFeuille(NoeudArecolorier.parent.droit)
            ){ 
            
            NoeudArecolorier.parent.gauche = null;
            NoeudArecolorier.parent.milieu = null;
            NoeudArecolorier.parent.droit = null;
            NoeudArecolorier.parent.c = NoeudArecolorier.c ;

            if( NoeudArecolorier.parent.c == Couleur.R){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.RED);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
            
            } else if(NoeudArecolorier.parent.c == Couleur.B){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.BLUE);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
            
            } else if(NoeudArecolorier.parent.c == Couleur.J){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.YELLOW);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
            
            } else if(NoeudArecolorier.parent.c == Couleur.G){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.LIGHT_GRAY);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
            
            } else if(NoeudArecolorier.parent.c == Couleur.N){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.BLACK);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }else if(NoeudArecolorier.parent.c == Couleur.W){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.WHITE);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }else if(NoeudArecolorier.parent.c == Couleur.V){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.GREEN);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }else if(NoeudArecolorier.parent.c == Couleur.O){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.ORANGE);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }else if(NoeudArecolorier.parent.c == Couleur.C){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.CYAN);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }else if(NoeudArecolorier.parent.c == Couleur.D){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.DARK_GRAY);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }else if(NoeudArecolorier.parent.c == Couleur.S){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.PINK);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }else if(NoeudArecolorier.parent.c == Couleur.M){
                img.setTriangle(NoeudArecolorier.parent.x1, NoeudArecolorier.parent.y1, NoeudArecolorier.parent.x2, NoeudArecolorier.parent.y2, NoeudArecolorier.parent.x3, NoeudArecolorier.parent.y3, Color.MAGENTA);
                drawEpaisseur(img, NoeudArecolorier.parent, data);
                
            }
        }
        else{
            if( this.estFeuille(NoeudArecolorier)){

                if( NoeudArecolorier.c == Couleur.R){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.RED);
                    drawEpaisseur(img, NoeudArecolorier, data);
                } else if(NoeudArecolorier.c == Couleur.B){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.BLUE);
                    drawEpaisseur(img, NoeudArecolorier, data);
                } else if(NoeudArecolorier.c == Couleur.J){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.YELLOW);
                    drawEpaisseur(img, NoeudArecolorier, data);
                } else if(NoeudArecolorier.c == Couleur.G){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.LIGHT_GRAY);
                    drawEpaisseur(img, NoeudArecolorier, data);
                } else if(NoeudArecolorier.c == Couleur.N){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.BLACK);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }else if(NoeudArecolorier.c == Couleur.W){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.WHITE);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }else if(NoeudArecolorier.c == Couleur.V){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.GREEN);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }else if(NoeudArecolorier.c == Couleur.O){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.ORANGE);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }else if(NoeudArecolorier.c == Couleur.C){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.CYAN);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }else if(NoeudArecolorier.c == Couleur.D){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.DARK_GRAY);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }else if(NoeudArecolorier.c == Couleur.S){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.PINK);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }else if(NoeudArecolorier.c == Couleur.M){
                    img.setTriangle(NoeudArecolorier.x1, NoeudArecolorier.y1, NoeudArecolorier.x2, NoeudArecolorier.y2, NoeudArecolorier.x3, NoeudArecolorier.y3, Color.MAGENTA);
                    drawEpaisseur(img, NoeudArecolorier, data);
                }
    
            }
        }
    }
    public void printAbTer(){
        if(!ArbreTernaire.estVide(this)){
            System.out.println(this.toString() + "\n");
            if( !ArbreTernaire.estVide(this.gauche)){
                this.gauche.printAbTer();
            }
            if( !ArbreTernaire.estVide(this.milieu)){
                this.milieu.printAbTer();
            }
            if( !ArbreTernaire.estVide(this.droit)){
                this.droit.printAbTer();
            }

        }
    }

    
    
}