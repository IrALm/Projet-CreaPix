import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TableauArbreTernaire {
    
    
    public static void main(String[] args){

        if(args.length <= 1) 
            System.out.println(" Donnez un chemin d'entrée et un chemin de sortie pour les fichiers.... ");
        else{
            System.out.println( "\n\n\n***********  Mon Plû Bô Tablô ******************\n");
            Data data = Data.Lecture_et_Recuperation_DesDonnes(args[0]);
            Data.afficheData(data);
            Image img = new Image(data.n , data.n);
            ArbreTernaire ab = new ArbreTernaire();
            ab = ab.buildAbTer(img, data);
            if(!ArbreTernaire.estVide(ab)){
                System.out.println("\n AFFICHAGE DES COORDONNES DES REGIONS AINSI QUE DES COULEURS \n\n ");
                ab.printAbTer();
                System.out.println("\n_______________________________________________\n\n");
                ab.toImage(img, ab);
                ab.drawEpaisseur(img, ab, data);
                try{
                    BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter(args[1] + File.separator + "sortieVersion2.txt"));
                    ab.toText(ab , ecritureFichier);
                    ecritureFichier.close(); // fermeture du fichier
                    System.out.println("\n\n °°°°°°°°° Un fichier ** sortieVesion2.txt ** est crée dans : " + args[1] + File.separator +"°°°°°°°°°\n");
                } catch(IOException e){
                    e.printStackTrace();
                }
                try{
                    img.save(args[1] + File.separator + "photoVersionSansRecoloriageVersion2.png");
                    System.out.println("\n\n °°°°°°°°° Un fichier ** photoVersionSansRecoloriageVersion2.png ** est crée dans : " + args[1] + File.separator +"°°°°°°°°°\n");

                } catch(IOException e){
                    System.out.println(" fichier non crée " + e.getMessage());
                }
                if(!data.LesRecoloriages.isEmpty()){
                    for( Recoloriage rec : data.LesRecoloriages)
                        ab.compressQTree(rec, img, data);
                }
                try{
                    img.save(args[1] + File.separator + "photoVersionAvecRecoloriageVersion2.png");
                    System.out.println("\n\n °°°°°°°°° Un fichier ** photoVersionAvecRecoloriageVersion2.png ** est crée dans : " + args[1] + File.separator +"°°°°°°°°°\n");

                } catch(IOException e){
                    System.out.println(" fichier non crée " + e.getMessage());
                }
                try{
                    BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter(args[1] + File.separator + "sortieRecoloriageVersion2.txt"));
                    ab.toText(ab , ecritureFichier);
                    ecritureFichier.close(); // fermeture du fichier
                    System.out.println("\n\n °°°°°°°°° Un fichier ** sortieRecoloriageVersion2.txt ** est crée dans : " + args[1] + File.separator +"°°°°°°°°°\n");

                } catch(IOException e){
                    e.printStackTrace();
                }
                System.out.println("\n\n\n¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤ FIN DU PROGRAMME ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n\n");

            }
            else{
                System.out.println(" L'Arbre Ternaire est vide : càd que le fichier d'entrée ne contient pas d'informations sur les centres");
            }
        }
    }
}
