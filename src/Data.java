
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 *  Cette classe est utile pour la récupération des données 
 *  à partir du fichier texte fourni 
 */

public class Data {
    
    protected int n , m , k , e; 

    /* n : taille de l'image de depart
       m : nombre des centres
       k : nombre des endroits à recolorier
       e : epaisseur */
    
    protected ArrayList<Centre> LesCentres ;
    protected ArrayList<Recoloriage> LesRecoloriages ;

    /**
     * @Rôle : constructeur interne de la classe
     * @complexité : o(1)
     */

    private Data(){

        this.n = this.m = this.k = this.e = 0;
        this.LesCentres = new ArrayList<>();
        this.LesRecoloriages = new ArrayList<>();
    }

    /**
     * @Rôle : permet de lire le fichier texte
     * @param nomFichier
     * @return une liste des string
     * @complexité : o(n) où n est le nombres des lignes du fichier
     */

    private static ArrayList<String> lecture_du_fichier(String nomFichier) {

        ArrayList<String> liste_de_Lignes = new ArrayList<>();
        BufferedReader lecteur_de_ligne = null;

        try {
                // Ouverture du fichier en utilisant un BufferedReader
                lecteur_de_ligne = new BufferedReader(new FileReader(nomFichier));
                String ligne;

                // Lire chaque ligne du fichier et l'ajouter à l'ArrayList
                while ((ligne = lecteur_de_ligne.readLine()) != null) {
                 liste_de_Lignes.add(ligne);
                }
        } catch (IOException e) {
                e.printStackTrace(); // Gestion d'erreur en cas de problème de lecture du fichier
        } finally {
            try {
                // Fermeture du BufferedReader pour éviter des fuites de mémoire
                if (lecteur_de_ligne != null) {
                        lecteur_de_ligne.close();
                }
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }

        // Retourne l'ArrayList contenant toutes les liste_de_Lignes du fichier
        return liste_de_Lignes;
    }

    /**
     * @Rôle : converti une chaine en entier
     * @param nb
     * @param str
     * @return int
     * @complexité : o(n) où n est la longeur de str
     */

    private static int conversionStringToInt( int nb , String str){

        try {
            str = str.trim(); // élimine les espaces avant et après
            int nombre = Integer.parseInt(str);
            nb = nombre ;
        } catch (NumberFormatException e) {
            System.out.println(str + " n'a pas le format d'un nombre");
        }
        return nb;
    }

    /**
     * @Rôle : converti un str en couleur
     * @param str
     * @return Couleur
     * @complexité : o(1)
     */

    private static Couleur choixColor( String str){

        Couleur c = null ;
        switch (str) {
            case "R":
                c = Couleur.R;
                break;
            case "B":
                c = Couleur.B;
                break;
            case "J":
                c = Couleur.J;
                break;
            case "G":
                c = Couleur.G;    
                break;
            case "N":
                c = Couleur.N;    
            break;
            case "W":
                c = Couleur.W;    
            break; 
            case "V":
                c = Couleur.V;    
            break;
            case "O":
                c = Couleur.O;    
            break;
            case "C":
                c = Couleur.C;    
            break;
            case "D":
                c = Couleur.D;    
            break;
            case "S":
                c = Couleur.S;    
            break;
            case "M":
                c = Couleur.M;    
            break;   
            default:
                System.out.println(" Couleur non répertorié");
            break;
        }
        return c ;
        
    }

    /**
     * @Rôle : recupère les centres dans la liste des chaines où chaque chaine représente une ligne du fichier
     * @param data
     * @param liste
     * @return une liste des centres
     * @complexité : o(m) où m est le nombres des centres
     */

    private static ArrayList<Centre> recupCentres( Data data , ArrayList<String> liste){

        if( data.m != 0){
            for( int i = 2 ; i <= data.m + 1 ; i++){

                String[] centre = liste.get(i).split(",");
                int x = Data.conversionStringToInt(i, centre[0]);
                int y = Data.conversionStringToInt(i, centre[1]);
                y = data.n - y ; // pour les coordonnés en suivant l'abscisse et l'ordonnée comme en math

                if( centre.length == 5){ // pour le version des arbres ternaires
                    for( int j = 2 ; j < centre.length ; j++){
                        centre[j] = centre[j].trim();// élimine les espaces avant et après
                    }
                    Couleur c1 = Data.choixColor(centre[2]);
                    Couleur c2 = Data.choixColor(centre[3]);
                    Couleur c3 = Data.choixColor(centre[4]);
                    data.LesCentres.add(new Centre(x, y, c1, c2, c3, null));
                }
                else{ // pour la version des Quadtree
                    for( int j = 2 ; j < centre.length ; j++){
                        centre[j] = centre[j].trim();// élimine les espaces avant et après
                    }
                    Couleur c1 = Data.choixColor(centre[2]);
                    Couleur c2 = Data.choixColor(centre[3]);
                    Couleur c3 = Data.choixColor(centre[4]);
                    Couleur c4 = Data.choixColor(centre[5]);
                    data.LesCentres.add(new Centre(x, y, c1, c2, c3, c4));
                }
            }
        }
        return data.LesCentres;
    }

    /**
     * @Rôle : recupère les recoloriages dans la liste des chaines où chaque chaine représente une ligne du fichier
     * @param data
     * @param liste
     * @return une liste des recoloriages
     * @complexité : o[(m+4) - (k+m+3)] où m est le nombres des centres et K le nombres des recoloriages
     */

    private static ArrayList<Recoloriage> recupRecoloriages( Data data , ArrayList<String> liste ){

        if( data.k != 0){
            for( int i = data.m + 4 ; i <= data.m + 3+ data.k ; i++){
                String[] recoloriage = liste.get(i).split(",");
                int x = Data.conversionStringToInt(i, recoloriage[0]);
                int y = Data.conversionStringToInt(i, recoloriage[1]);
                y = data.n - y ; // pour les coordonnés en suivant l'abscisse et l'ordonnée comme en math
                recoloriage[2] = recoloriage[2].trim();// élimine les espaces avant et après
                Couleur c = Data.choixColor(recoloriage[2]);
                data.LesRecoloriages.add(new Recoloriage(x, y, c));
            }
        }
        return data.LesRecoloriages;
    }

    /**
     * @Rôle : permet de lire et recupérés les données à partir d'un fichier texte
     * @param nom_fichier
     * @return Data
     * @complexité : o(n) où n est le nombre des lignes du fichier texte
     */

    public static Data Lecture_et_Recuperation_DesDonnes( String nom_fichier ){
        
        Data data = new Data();

        /* Récuperation des lignes du fichier.txt */
        ArrayList<String> lignes = Data.lecture_du_fichier(nom_fichier);
        if(!lignes.isEmpty()){
            /* Récuperation de n , m , k et e */
            data.n = Data.conversionStringToInt(data.n , lignes.get(0));
            if( data. n == 0) {
                System.out.println(" n doit être different de 0 car il est la taille de l'image.Dans ce cas , je lui affecte alors une valeur par défaut : 1000 ");
                data.n = 1000 ;
            }
            if( 1 < lignes.size())
                data.m = Data.conversionStringToInt(data.m , lignes.get(1));
            if(data.m + 2 < lignes.size())
                data.e = Data.conversionStringToInt(data.e , lignes.get(data.m + 2));
            if(data.m + 3 < lignes.size())
                data.k = Data.conversionStringToInt(data.k , lignes.get(data.m + 3));
            /*Récuperer tous les centres*/
            data.LesCentres = Data.recupCentres(data, lignes);
            /*Récuperer tous les récoloriages */
            data.LesRecoloriages = Data.recupRecoloriages(data, lignes);
        }
        else{
            data.n = 10000; // dans le cas où on passe un fichier vide comme argument ,
            // juste pour initialiser la taille de l'image car il ne doit pas etre <= 0
        }
        return data;
    }

    public static void afficheData( Data data){
        System.out.println(" LES DONNEES \n_______________________________________________\n");
        System.out.println(" n = " + data.n + " m = " + data.m + " e = " + data.e
                            + " k = " + data.k );
                            if(!data.LesCentres.isEmpty()){
                                System.out.println(" \n °°°°° Les centres °°°°°° \n_______________________________________________\n");
                                for(Centre c : data.LesCentres) c.affiCentre();
                            }
        if(!data.LesRecoloriages.isEmpty()){
            System.out.println(" \n °°°°° Les recoloriages °°°°° \n_______________________________________________\n");
            for( Recoloriage r : data.LesRecoloriages) r.affiRecoloriage();
            System.out.println(" \n_______________________________________________\n");
        }
    }

}
