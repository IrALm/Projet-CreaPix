import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;


public class MonBoTablo {
    
    public  static enum Couleur {
        R,
        B,
        J,
        G,
        N
    }
    
    public static void main(String[] args){
        if(args.length <= 0) System.out.println(" Aucun fichier recu en entrée .... ");
        else{
            Data data = Data.Lecture_et_Recuperation_DesDonnes(args[0]);
            //Data.afficheData(data);
            Image img = new Image(data.n , data.n);
            Quadtree Q = new Quadtree(0, 0, img.width(), img.height(), null, null, null, null, null, null);
            Q.buildQTree(img , data);
            try{
                BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter("sortie.txt"));
                Q.toText(Q , ecritureFichier);
                ecritureFichier.close(); // fermeture du fichier
                System.out.println(" Un fichier sortie.txt est crée");
            } catch(IOException e){
                e.printStackTrace();
            }
            Q.printQTree();
            Q.toImage(img, Q, data);
            try{
                img.save("photoVersionSansRecoloriage.png");
            } catch(IOException e){
                System.out.println(" fichier non crée " + e.getMessage());
            }
            for( Recoloriage rec : data.LesRecoloriages)
                Q.compressQTree(rec, img, data);
            try{
                img.save("photoVersionAvecRecoloriage.png");
            } catch(IOException e){
                System.out.println(" fichier non crée " + e.getMessage());
            }

            
            
            
            
        }
        

    }
}
