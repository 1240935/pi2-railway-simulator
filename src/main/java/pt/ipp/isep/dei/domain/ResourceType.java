package pt.ipp.isep.dei.domain;

import java.io.Serializable;

/**
 * Enumeration representing various types of resources handled within the railway system.
 * <p>
 * Each resource type has an associated integer value representing its base value or weight (unit-dependent),
 * which can be used in calculations such as cargo value, transportation cost, or capacity.
 * </p>
 *
 * <p>Examples of resource types include raw materials (e.g., COAL, IRON), manufactured goods (e.g., AUTOS, CHEMICALS),
 * agricultural products (e.g., GRAIN, DAIRY), and special cargo (e.g., PASSENGERS, TROOPS).</p>
 */
public enum ResourceType implements Serializable {

    ALUMINUM(35000),
    AUTOS(49000),
    BAUXITE(28000),
    CATTLE(37000),
    CEMENT(38000),
    CHEMICALS(37000),
    COAL(28000),
    COFFEE(37000),
    COTTON(36000),
    DAIRY(0),
    DIESEL(39000),
    FERTILIZER(35000),
    FOOD(49000),
    GOODS(42000),
    GRAIN(28000),
    GRAVEL(28000),
    IRON(28000),
    LOGS(28000),
    LUMBER(35000),
    MAIL(65000),
    MILK(51000),
    OIL(37000),
    PAPER(46000),
    PASSENGERS(56000),
    PRODUCE(37000),
    PULPWOOD(42000),
    RUBBER(28000),
    STEEL(35000),
    SUGAR(37000),
    TIRES(35000),
    URANIUM(43000),
    WASTE(48000),
    WOOL(36000),
    ALCOHOL(40000),
    AMMUNITION(38000),
    TROOPS(56000),
    WEAPONS(38000);

    private int value;

    /**
     * Constructs a ResourceType with the specified integer value.
     *
     * @param value Integer value associated with the resource type.
     */
    ResourceType(int value){
        this.value = value;
    }

    /**
     * Returns the integer value associated with this resource type.
     *
     * @return the value of the resource type
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the name of the resource type with the first letter capitalized
     * and the rest in lowercase.
     *
     * @return formatted name of the resource type
     */
    public String getName() {
        String lower = name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    /**
     * Sets the value associated with this resource type.
     *
     * @param value the new integer value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
}
