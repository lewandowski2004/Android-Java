package pl.swpw.info.wydatki;

public class Zakup {

    private long id;
    private String nazwa;

    public Zakup(long id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
