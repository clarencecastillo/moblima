package model.cinema;

import java.util.UUID;
import model.commons.Entity;

public class Cinema extends Entity {

    private String code;
    private Cineplex cineplex;
    private CinemaType type;
    private CinemaLayout layout;

    public Cinema(String code, Cineplex cineplex,
                  CinemaType type, CinemaLayout layout) {
        this.code = code;
        this.cineplex = cineplex;
        this.type = type;
        this.layout = layout;
    }

    public String getCode() {
        return code;
    }

    public Cineplex getCineplex() {
        return cineplex;
    }

    public CinemaType getType() {
        return type;
    }

    public CinemaLayout getLayout() {
        return layout;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCineplex(Cineplex cineplex) {
        this.cineplex = cineplex;
    }

    public void setType(CinemaType type) {
        this.type = type;
    }

    public void setLayout(CinemaLayout layout) {
        this.layout = layout;
    }
}
