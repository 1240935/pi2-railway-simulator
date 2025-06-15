package pt.ipp.isep.dei.dto;

import pt.ipp.isep.dei.domain.Position;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing a city block configuration.
 * Contains coordinates, number of blocks, optional randomization,
 * and a list of specific positions.
 */
public class CityBlockDTO {

    /**
     * The x-coordinate of the city block.
     */
    private int xCoordinate;

    /**
     * The y-coordinate of the city block.
     */
    private int yCoordinate;

    /**
     * The number of blocks to generate.
     */
    private int blocks;

    /**
     * Indicates whether block placement should be randomized.
     */
    private boolean randomize;

    /**
     * A list of predefined positions for the blocks.
     */
    private List<Position> positions;

    /**
     * Gets the x-coordinate of the city block.
     *
     * @return the x-coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }

    /**
     * Sets the x-coordinate of the city block.
     *
     * @param xCoordinate the x-coordinate to set
     */
    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Gets the y-coordinate of the city block.
     *
     * @return the y-coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * Sets the y-coordinate of the city block.
     *
     * @param yCoordinate the y-coordinate to set
     */
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets the number of blocks.
     *
     * @return the number of blocks
     */
    public int getBlocks() {
        return blocks;
    }

    /**
     * Sets the number of blocks.
     *
     * @param blocks the number of blocks to set
     */
    public void setBlocks(int blocks) {
        this.blocks = blocks;
    }

    /**
     * Checks if block placement should be randomized.
     *
     * @return {@code true} if randomization is enabled, {@code false} otherwise
     */
    public boolean isRandomize() {
        return randomize;
    }

    /**
     * Sets whether block placement should be randomized.
     *
     * @param randomize {@code true} to enable randomization, {@code false} to disable
     */
    public void setRandomize(boolean randomize) {
        this.randomize = randomize;
    }

    /**
     * Gets the list of predefined positions for the blocks.
     *
     * @return the list of {@link Position} objects
     */
    public List<Position> getPositions() {
        return positions;
    }

    /**
     * Sets the list of predefined positions for the blocks.
     *
     * @param positions the list of {@link Position} objects to set
     */
    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
