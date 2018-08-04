package pl.swpw.info.wydatki;

/**
 * Created by rzak on 07.04.2018.
 */

public class Wspolne {

    static String mies, dz;

    public static String zmienDate(int rok, int miesiac, int dzien){
        if ((miesiac+1)<10)
            mies="0" + Integer.toString(miesiac+1);
        else
            mies=Integer.toString(miesiac+1);

        if (dzien<10)
            dz="0"+Integer.toString(dzien);
        else
            dz=Integer.toString(dzien);

        return (rok+"-"+mies+"-"+dz);
    }

}
