
public class Centre {
    
    private int x,y;
    private Couleur c1 , c2 , c3 , c4 ;

    /**
     * @Rôle : constructeur de la classe
     * @param _x
     * @param _y
     * @param _c1
     * @param _c2
     * @param _c3
     * @param _c4
     */

    public Centre( int _x , int _y , Couleur _c1 , Couleur _c2 , Couleur _c3 , Couleur _c4){

        this.x= _x ; this.y = _y ; this.c1 = _c1 ;this.c2 = _c2 ; this.c3 = _c3 ; this.c4 = _c4 ;
    }


    /* Getters pour accéder aux attributs */

    public int getX(){ 
        return this.x; 
    }
    public int getY(){ 
        return this.y; 
    }
    public Couleur getC1(){ 
        return this.c1; 
    }
    public Couleur getC2(){ 
        return this.c2; 
    }
    public Couleur getC3(){ 
        return this.c3; 
    }
    public Couleur getC4(){ 
        return this.c4; 
    }

    public void affiCentre(){
        System.out.println(" ( x : " + this.x + " Y : " + this.y + " )  Couleurs : " 
                           + this.c1 + "," + this.c2 + "," + this.c3 + "," + this.c4);
    }
}
