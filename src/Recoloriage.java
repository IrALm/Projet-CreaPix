
public class Recoloriage {
    
    private int x,y;
    private Couleur c;

    /**
     * @Rôle : constructeur de la classe
     * @param _x
     * @param _y
     * @param _c
     */

    public Recoloriage( int _x , int _y , Couleur _c){

        this.x = _x ; this.y = _y ; this.c = _c;
    }

    /* Getters pour accéder aux attributs */

    public int getX(){ 
        return this.x; 
    }
    public int getY(){ 
        return this.y; 
    }
    public Couleur getC(){ 
        return this.c; 
    }

    public void affiRecoloriage(){
        System.out.println(" ( x : " + this.x + " Y : " + this.y + " )  Couleurs : " + this.c );
    }

}
