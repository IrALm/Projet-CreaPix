
import java.awt.*;
public class Region {
    
    private Image racine;
    private Color couleurImage;
    private Region NO , NE , SE , SO;

    public Image getImage(){ return this.racine ; }
    public Color getCouleurImage(){ return this.couleurImage ; }
    public Region NO(){ return this.NO ; }
    public Region NE(){ return this.NE ; }
    public Region SE(){ return this.SE ; }
    public Region SO(){ return this.SO ; }
}
