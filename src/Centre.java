
public class Centre {
    
    private int x,y;
    private MonBoTablo.Couleur c1 , c2 , c3 , c4 ;

    public Centre( int _x , int _y , MonBoTablo.Couleur _c1 , MonBoTablo.Couleur _c2 , MonBoTablo.Couleur _c3 , MonBoTablo.Couleur _c4){

        this.x= _x ; this.y = _y ; this.c1 = _c1 ;this.c2 = _c2 ; this.c3 = _c3 ; this.c4 = _c4 ;
    }

    public int getX(){ return this.x; }
    public int getY(){ return this.y; }
    public MonBoTablo.Couleur getC1(){ return this.c1; }
    public MonBoTablo.Couleur getC2(){ return this.c2; }
    public MonBoTablo.Couleur getC3(){ return this.c3; }
    public MonBoTablo.Couleur getC4(){ return this.c4; }
    public void affiCentre( Data data){
        System.out.println(" ( x : " + this.x + " Y : " + this.y + " )  Couleurs : " 
                           + this.c1 + "," + this.c2 + "," + this.c3 + "," + this.c4);
    }
}
