package model.cineplex;

import model.commons.Entity;

/**
 * Represents a cineplex that belongs to a cineplex.
 *
 * @version 1.0
 * @since 2017-10-20
 */
public class Cinema extends Entity {

    /**
     * This cineplex's code.
     */
    private String code;

    /**
     * This cineplex's type, which can be regular, platinum or executive.
     */
    private CinemaType type;

    /**
     * This cineplex's layout consisting of cells.
     */
    private CinemaLayout layout;

    /**
     * Creates a cineplex with the given code, type and layout.
     *
     * @param code The code of this cineplex.
     * @param type The type of thi cineplex.
     * @param layout The layout of this cineplex.
     */
    public Cinema(String code, CinemaType type, CinemaLayout layout) {
        this.code = code;
        this.type = type;
        this.layout = layout;
    }

    /**
     * Gets this cineplex's code.
     *
     * @return this cineplex's code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Changes this cineplex's code.
     *
     * @param code this cineplex's new code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets this cineplex's type.
     *
     * @return this cineplex's type.
     */
    public CinemaType getType() {
        return type;
    }

    /**
     * CHanges this cineplex's type.
     *
     * @param type this cineplex's new type.
     */
    public void setType(CinemaType type) {
        this.type = type;
    }

    /**
     * Gets this cineplex's layout.
     *
     * @return this cineplex's layout.
     */
    public CinemaLayout getLayout() {
        return layout;
    }

    /**
     * CHanges this cineplex's layout.
     *
     * @param layout this cineplex's new layout.
     */
    public void setLayout(CinemaLayout layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return type + " Hall " + code;
    }
}
