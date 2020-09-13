package dominio;

public class parqueo {

    private Integer id;
    private String patente;
    private Integer tiempo;
    private usuario usuario;

    public parqueo(Integer id, String patente, Integer tiempo, dominio.usuario usuario) {
        this.id = id;
        this.patente = patente;
        this.tiempo = tiempo;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public dominio.usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(dominio.usuario usuario) {
        this.usuario = usuario;
    }
}
