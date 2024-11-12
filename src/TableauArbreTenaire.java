import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TableauArbreTenaire {
    
    
    public static void main(String[] args){

        if(args.length <= 0) System.out.println(" Aucun fichier recu en entrée .... ");
        else{
            Data data = Data.Lecture_et_Recuperation_DesDonnes(args[0]);
            Data.afficheData(data);
            Image img = new Image(data.n , data.n);
            ArbreTernaire ab = new ArbreTernaire();
            ab.buildAbTer(img, data);
            ab.printAbTer();
            ab.toImage(img, ab);
            ab.drawEpaisseur(img, ab, data);
           try{
                BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter("sortieVersion2.txt"));
                ab.toText(ab , ecritureFichier);
                ecritureFichier.close(); // fermeture du fichier
                System.out.println(" Un fichier sortieVesion2.txt est crée");
            } catch(IOException e){
                e.printStackTrace();
            }
            try{
                img.save("photoVersionSansRecoloriageVersion2.png");
            } catch(IOException e){
                System.out.println(" fichier non crée " + e.getMessage());
            }
           
            for( Recoloriage rec : data.LesRecoloriages)
                ab.compressQTree(rec, img, data);
            try{
                img.save("photoVersionAvecRecoloriageVersion2.png");
            } catch(IOException e){
                System.out.println(" fichier non crée " + e.getMessage());
            }
            try{
                BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter("sortieRecoloriageVersion2.txt"));
                ab.toText(ab , ecritureFichier);
                ecritureFichier.close(); // fermeture du fichier
                System.out.println(" Un fichier sortieRecoloriageVersion2.txt est crée");
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
