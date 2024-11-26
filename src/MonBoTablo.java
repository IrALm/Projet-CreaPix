import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;


public class MonBoTablo {
    
    
    public static void main(String[] args){

        if(args.length <= 1) 
            System.out.println(" Donnez en paramètre un chemin d'entrée et un chemin de sortie pour les fichiers.... ");

        else{

            System.out.println( "\n\n\n***********  Mon Bô Tablô *********************\n");
            Data data = Data.Lecture_et_Recuperation_DesDonnes(args[0]);
            Data.afficheData(data);
            Image img = new Image(data.n , data.n);
            Quadtree Q = new Quadtree(0, 0, img.width(), img.height(), null, null, null, null, null, null);
            Q = Q.buildQTree(data);

            if(!Quadtree.estVide(Q)){

                System.out.println("\n AFFICHAGE DES COORDONNES DES REGIONS AINSI QUE DES COULEURS \n\n ");
                Q.printQTree();
                System.out.println("\n_______________________________________________\n\n");

                try{
                    BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter(args[1] + File.separator + "sortie.txt"));
                    Q.toText(Q , ecritureFichier);
                    ecritureFichier.close(); // fermeture du fichier
                    System.out.println("\n\n °°°°°°°°° Un fichier ** sortie.txt ** est crée dans le dossier : " + args[1] + File.separator + "°°°°°°°");
                } catch(IOException e){
                    e.printStackTrace();
                }
                Q.toImage(img, Q);
                Q.drawEpaisseur(img, Q, data);
                try{
                    img.save(args[1] + File.separator + "photoVersionSansRecoloriage.png");
                    System.out.println("\n °°°°°°°°° Un fichier ** photoVersionSansRecoloriage.png ** est crée dans le dossier : " + args[1] + File.separator + "°°°°°°°");
                } catch(IOException e){
                    System.out.println(" fichier non crée " + e.getMessage());
                }
                if(!data.LesRecoloriages.isEmpty()){
                    for( Recoloriage rec : data.LesRecoloriages)
                        Q.compressQTree(rec, img, data);
                }
                try{
                    img.save(args[1] + File.separator + "photoVersionAvecRecoloriage.png");
                    System.out.println("\n °°°°°°°°° Un fichier ** photoVersionAvecRecoloriage.png ** est crée dans le dossier : " + args[1] + File.separator + "°°°°°°°\n\n\n");
                } catch(IOException e){
                    System.out.println(" fichier non crée " + e.getMessage());
                }
                try{
                    BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter(args[1] + File.separator + "sortieRecoloriage.txt"));
                    Q.toText(Q , ecritureFichier);
                    ecritureFichier.close(); // fermeture du fichier
                    System.out.println("\n\n °°°°°°°°° Un fichier ** sortieRecoloriage.txt ** est crée dans : " + args[1] + File.separator + "°°°°°°°°°°°°");
                } catch(IOException e){
                    e.printStackTrace();
                }
                System.out.println("\n\n\n¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤ FIN DU PROGRAMME ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤\n\n");
            }

            else{
                System.out.println(" Le Quadtree est vide : càd que le fichier d'entrée ne contient pas d'informations sur les centres");
            }
        }
    }
}
