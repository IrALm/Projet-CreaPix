import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;


public class MonBoTablo {
    
    
    public static void main(String[] args){

        if(args.length <= 2) 
            System.out.println(" Veuillez donner en paramètre : \n - Un numéro de la variante à exécuter( 1 ou 2) \n - un chemin pour le fichier d'entrés \n - et un chemin pour le dossier des fichiers de sortie \n ");

        else{
        
            int variante = Integer.parseInt(args[0]);
            if(variante == 1){

                System.out.println( "\n\n\n***********  Mon Bô Tablô Variante 1 : Quadtree *********************\n");
                Data data = Data.Lecture_et_Recuperation_DesDonnes(args[1]);
                Data.afficheData(data);
                Image img = new Image(data.n , data.n);
                Quadtree Q = new Quadtree(0, 0, img.width(), img.height(), null, null, null, null, null, null);
                Q = Q.buildQTree(data);

                if(!Quadtree.estVide(Q)){


                    try{
                        BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter(args[2] + File.separator + "sortie.txt"));
                        Q.toText(Q , ecritureFichier);
                        ecritureFichier.close(); // fermeture du fichier
                        System.out.println("\n\n  Un fichier ** sortie.txt ** est crée dans le dossier : " + args[2] + File.separator + " ");
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                    Q.toImage(img, Q);
                    Q.drawEpaisseur(img, Q, data);
                    try{
                        img.save(args[2] + File.separator + "photoVersionSansRecoloriage.png");
                        System.out.println("\n  Un fichier ** photoVersionSansRecoloriage.png ** est crée dans le dossier : " + args[2] + File.separator + " ");
                    } catch(IOException e){
                        System.out.println(" fichier non crée " + e.getMessage());
                    }
                    if(!data.LesRecoloriages.isEmpty()){
                        for( Recoloriage rec : data.LesRecoloriages)
                            Q.compressQTree(rec, img, data);
                    }
                    try{
                        img.save(args[2] + File.separator + "photoVersionAvecRecoloriage.png");
                        System.out.println("\n  Un fichier ** photoVersionAvecRecoloriage.png ** est crée dans le dossier : " + args[2] + File.separator + " ");
                    } catch(IOException e){
                        System.out.println(" fichier non crée " + e.getMessage());
                    }
                    try{
                        BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter(args[2] + File.separator + "sortieRecoloriage.txt"));
                        Q.toText(Q , ecritureFichier);
                        ecritureFichier.close(); // fermeture du fichier
                        System.out.println("\n\n  Un fichier ** sortieRecoloriage.txt ** est crée dans : " + args[2] + File.separator + " ");
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("\n\n\n FIN DU PROGRAMME \n\n");
                }

                else{
                    System.out.println(" Le Quadtree est vide : càd que le fichier d'entrée ne contient pas d'informations sur les centres");
                }
            }
            else if( variante == 2){
                System.out.println( "\n\n\n***********  Mon  Bô Tablô Variante 2 : Arbre ternaire ******************\n");
                Data data = Data.Lecture_et_Recuperation_DesDonnes(args[1]);
                Data.afficheData(data);
                Image img = new Image(data.n , data.n);
                ArbreTernaire ab = new ArbreTernaire();
                ab = ab.buildAbTer(img, data);
                if(!ArbreTernaire.estVide(ab)){

                    ab.toImage(img, ab);
                    ab.drawEpaisseur(img, ab, data);
                    try{
                        BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter(args[2] + File.separator + "sortieVersion2.txt"));
                        ab.toText(ab , ecritureFichier);
                        ecritureFichier.close(); // fermeture du fichier
                        System.out.println("\n\n  Un fichier ** sortieVesion2.txt ** est crée dans : " + args[2] + File.separator +" ");
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                    try{
                        img.save(args[2] + File.separator + "photoVersionSansRecoloriageVersion2.png");
                        System.out.println("\n\n  Un fichier ** photoVersionSansRecoloriageVersion2.png ** est crée dans : " + args[2] + File.separator +" ");

                    } catch(IOException e){
                        System.out.println(" fichier non crée " + e.getMessage());
                    }
                    if(!data.LesRecoloriages.isEmpty()){
                        for( Recoloriage rec : data.LesRecoloriages)
                            ab.compressQTree(rec, img, data);
                    }
                    try{
                        img.save(args[2] + File.separator + "photoVersionAvecRecoloriageVersion2.png");
                        System.out.println("\n\n Un fichier ** photoVersionAvecRecoloriageVersion2.png ** est crée dans : " + args[2] + File.separator +" ");

                    } catch(IOException e){
                        System.out.println(" fichier non crée " + e.getMessage());
                    }
                    try{
                        BufferedWriter ecritureFichier = new BufferedWriter(new FileWriter(args[2] + File.separator + "sortieRecoloriageVersion2.txt"));
                        ab.toText(ab , ecritureFichier);
                        ecritureFichier.close(); // fermeture du fichier
                        System.out.println("\n\n  Un fichier ** sortieRecoloriageVersion2.txt ** est crée dans : " + args[2] + File.separator +" ");

                    } catch(IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("\n\n\n FIN DU PROGRAMME \n\n");

                }
                else{
                    System.out.println(" L'Arbre Ternaire est vide : càd que le fichier d'entrée ne contient pas d'informations sur les centres");
                }
            }
            else{
                System.out.println(" La variante n'existe pas");
            }
        }
    }
}
