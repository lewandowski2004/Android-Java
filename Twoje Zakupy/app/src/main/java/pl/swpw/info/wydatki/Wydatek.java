package pl.swpw.info.wydatki;

/**
 * Created by rzak on 07.04.2018.
 */

public class Wydatek {

    private long id;
    private String data;
    private String nazwa;
    private float cena;

    public Wydatek(long id, String data, String nazwa, float cena) {
        this.id = id;
        this.data = data;
        this.nazwa = nazwa;
        this.cena = cena;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }
}
