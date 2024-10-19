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
            Data.afficheData(data);
        }

    }
}
