package model.cinema;

import model.commons.Entity;

/**
 Represents a cinema that belongs to a cineplex.
 @author Castillo Clarence Fitzgerald Gumtang
 @version 1.0
 @since 2017-10-20
 */
public class Cinema extends Entity {

    /**
     * This cinema's code.
     */
    private String code;

    /**
     * This cinema's type, which can be regular, platinum or executive.
     */
    private CinemaType type;

    /**
     * This cinema's layout consisting of cells.
     */
    private CinemaLayout layout;

    /**
     * Creates a cinema with the given code, type and layout.
     * @param code
     * @param type
     * @param layout
     */
    public Cinema(String code, CinemaType type, CinemaLayout layout) {
        this.code = code;
        this.type = type;
        this.layout = layout;
    }

    /**
     * Gets this cinema's code.
     * @return this cinema's code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets this cinema's type.
     * @return this cinema's type.
     */
    public CinemaType getType() {
        return type;
    }

    /**
     * Gets this cinema's layout.
     * @return this cinema's layout.
     */
    public CinemaLayout getLayout() {
        return layout;
    }

    /**
     * Changes this cinema's code.
     * @param code this cinema's new code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * CHanges this cinema's type.
     * @param type this cinema's new type.
     */
    public void setType(CinemaType type) {
        this.type = type;
    }

    /**
     * CHanges this cinema's layout.
     * @param layout this cinema's new layout.
     */
    public void setLayout(CinemaLayout layout) {
        this.layout = layout;
    }
}
