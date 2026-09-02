package modelo;

import java.time.LocalDateTime;

public class Mensaje {

    private Remitente remitente;
    private String contenido;
    private LocalDateTime fechaHora;

    public Mensaje(Remitente remitente, String contenido) {
        this.remitente = remitente;
        this.contenido = contenido;
        this.fechaHora = LocalDateTime.now();
    }

    public Remitente getRemitente() {
        return remitente;
    }

    public String getContenido() {
        return contenido;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}
