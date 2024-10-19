
public class Recoloriage {
    
    private int x,y;
    private MonBoTablo.Couleur c;

    public Recoloriage( int _x , int _y , MonBoTablo.Couleur _c){

        this.x = _x ; this.y = _y ; this.c = _c;
    }

    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
    public MonBoTablo.Couleur getC(){ return this.c; }
    public void affiRecoloriage( Data data){
        System.out.println(" ( x : " + this.x + " Y : " + this.y + " )  Couleurs : " + this.c );
    }

}
