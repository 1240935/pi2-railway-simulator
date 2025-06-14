package pt.ipp.isep.dei.domain;
import java.io.Serializable;

/**
 * Enum representing different locomotive models with their respective characteristics.
 * <p>
 * Each model contains specific data describing its performance, operational year,
 * costs, and type.
 * </p>
 * <p>
 * Attributes per model:
 * <ul>
 *   <li><b>power</b>: Qualitative power rating</li>
 *   <li><b>acceleration</b>: Qualitative acceleration rating (e.g. "FAST", "VERY_FAST")</li>
 *   <li><b>topSpeed</b>: Maximum speed achievable by the locomotive</li>
 *   <li><b>operationStartYear</b>: Year the locomotive model began operation</li>
 *   <li><b>fuelCost</b>: Annual fuel cost</li>
 *   <li><b>maintenancePYear</b>: Annual maintenance cost</li>
 *   <li><b>cost</b>: Initial purchase cost of the locomotive</li>
 *   <li><b>locomotiveType</b>: Type of locomotive (Steam, Diesel, Electric)</li>
 * </ul>
 * </p>
 *
 * <p>
 * This enum can be used to retrieve information on locomotive models,
 * such as for filtering available models by type, cost, or operational year.
 * </p>
 */

public enum LocomotiveModel implements Serializable {

    TREVITHICK_1("EXTREMELY_POOR", "EXTREMELY_POOR", 10, 1800, 7543, 6000, 10000, LocomotiveType.STEAM),
    STEPHENSON_ROCKET("BELOW_AVERAGE", "EXTREMELY_POOR", 15, 1829, 7992, 6000, 16000, LocomotiveType.STEAM),
    JOHN_BULL("BELOW_AVERAGE", "POOR", 25, 1831, 9256, 7000, 23000, LocomotiveType.STEAM),
    DEWITT_CLINTON("POOR", "EXTREMELY_POOR", 20, 1833, 7187, 5000, 18000, LocomotiveType.STEAM),
    PRUSSIAN("BELOW_AVERAGE", "EXTREMELY_POOR", 30, 1837, 10822, 8000, 35000, LocomotiveType.STEAM),
    AMERICAN_C("ABOVE_AVERAGE", "POOR", 42, 1848, 12606, 5000, 46000, LocomotiveType.STEAM),
    IRON_DUKE("BELOW_AVERAGE", "POOR", 54, 1855, 30736, 9000, 78000, LocomotiveType.STEAM),
    A3("GOOD", "POOR", 30, 1865, 0, 6000, 67000, LocomotiveType.STEAM),
    EIGHT_WHEELER("ABOVE_AVERAGE", "BELOW_AVERAGE", 48, 1868, 17808, 5000, 59000, LocomotiveType.STEAM),
    VULCAN("BELOW_AVERAGE", "VERY_POOR", 30, 1872, 6393, 4000, 32000, LocomotiveType.STEAM),
    CONSOLIDATION("GOOD", "BELOW_AVERAGE", 40, 1877, 19512, 8000, 51000, LocomotiveType.STEAM),
    TRUCK_SHAY("GOOD", "POOR", 15, 1882, 18227, 17000, 43000, LocomotiveType.STEAM),
    MASTODON("POOR", "VERY_POOR", 45, 1890, 22395, 13000, 60000, LocomotiveType.STEAM),
    TEN_WHEELER("BELOW_AVERAGE", "BELOW_AVERAGE", 50, 1892, 21727, 11000, 66000, LocomotiveType.STEAM),
    MOGUL("AVERAGE", "AVERAGE", 50, 1895, 25131, 12000, 83000, LocomotiveType.STEAM),
    BOBO("GOOD", "POOR", 55, 1895, 33116, 6000, 85000, LocomotiveType.ELECTRIC),
    ATLANTIC("ABOVE_AVERAGE", "FAST", 80, 1902, 43556, 18000, 93000, LocomotiveType.STEAM),
    CAMELBACK("VERY_GOOD", "POOR", 30, 1905, 15000, 9000, 75000, LocomotiveType.STEAM),
    PACIFIC("AVERAGE", "ABOVE_AVERAGE", 95, 1908, 62516, 21000, 119000, LocomotiveType.STEAM),
    CLASS_G10("AVERAGE", "ABOVE_AVERAGE", 50, 1910, 50521, 38000, 98000, LocomotiveType.STEAM),
    PRAIRIE("GOOD", "BELOW_AVERAGE", 60, 1912, 34157, 11000, 85000, LocomotiveType.STEAM),
    D16SB("VERY_GOOD", "POOR", 45, 1914, 21024, 9000, 65000, LocomotiveType.STEAM),
    CLASS_13H("GOOD", "ABOVE_AVERAGE", 40, 1917, 46289, 36000, 102000, LocomotiveType.STEAM),
    USRA("GOOD", "POOR", 40, 1918, 29993, 19000, 90000, LocomotiveType.STEAM),
    MIKADO("GOOD", "ABOVE_AVERAGE", 55, 1919, 51072, 32000, 133000, LocomotiveType.STEAM),
    BE_II("VERY_GOOD", "FAST", 35, 1920, 14701, 11000, 61000, LocomotiveType.ELECTRIC),
    CLASS_B12("ABOVE_AVERAGE", "FAST", 71, 1923, 32407, 14000, 146000, LocomotiveType.STEAM),
    EE("GOOD", "VERY_POOR", 30, 1923, 11880, 7000, 47000, LocomotiveType.ELECTRIC),
    CLASS_1045("ABOVE_AVERAGE", "FAST", 40, 1927, 11636, 6000, 95000, LocomotiveType.ELECTRIC),
    USRA_II("ABOVE_AVERAGE", "BELOW_AVERAGE", 45, 1930, 32236, 18000, 98000, LocomotiveType.STEAM),
    CLASS_A4_MALLARD("BELOW_AVERAGE", "ABOVE_AVERAGE", 126, 1935, 55136, 19000, 200000, LocomotiveType.STEAM),
    GG1("OUTSTANDING", "ABOVE_AVERAGE", 100, 1935, 42721, 19000, 285000, LocomotiveType.ELECTRIC),
    CLASS_E18("GOOD", "FAST", 93, 1936, 27493, 16000, 97000, LocomotiveType.ELECTRIC),
    HUDSON("AVERAGE", "BELOW_AVERAGE", 90, 1937, 54017, 25000, 210000, LocomotiveType.STEAM),
    DAYLIGHT("BELOW_AVERAGE", "AVERAGE", 80, 1937, 63611, 30000, 230000, LocomotiveType.STEAM),
    J3A_STREAMLINER("POOR", "ABOVE_AVERAGE", 103, 1938, 112234, 28000, 255000, LocomotiveType.STEAM),
    AE("ABOVE_AVERAGE", "FAST", 68, 1939, 47906, 24000, 210000, LocomotiveType.ELECTRIC),
    CLASS_1020("VERY_GOOD", "FAST", 56, 1941, 21545, 18000, 119000, LocomotiveType.ELECTRIC),
    BIG_BOY("POOR", "ABOVE_AVERAGE", 68, 1941, 88040, 75000, 375000, LocomotiveType.STEAM),
    T1("GOOD", "FAST", 85, 1945, 78213, 28000, 284000,LocomotiveType.STEAM ),
    F3AB ("ABOVE_AVERAGE", "FAST", 85, 1945, 52150, 16000, 265000, LocomotiveType.DIESEL),
    ALCO_PA1("AVERAGE", "AVERAGE", 90, 1946, 52800, 16000, 210000, LocomotiveType.DIESEL),
    F9("ABOVE_AVERAGE", "ABOVE_AVERAGE", 110, 1949, 63000, 18000, 337000, LocomotiveType.DIESEL),
    GP9("GOOD", "AVERAGE", 71, 1954, 48028, 11000, 235000, LocomotiveType.DIESEL),
    E69("BELOW_AVERAGE", "VERY_FAST", 31, 1955, 12837, 8000, 86000, LocomotiveType.ELECTRIC),
    TGVX_TSC("BELOW_AVERAGE", "ABOVE_AVERAGE", 155, 1957, 71672, 80000, 850000, LocomotiveType.ELECTRIC),
    GP18("VERY_GOOD", "ABOVE_AVERAGE", 83, 1958, 55131, 15000, 245000, LocomotiveType.DIESEL),
    V200("BELOW_AVERAGE", "ABOVE_AVERAGE", 87, 1959, 53878, 19000, 160000, LocomotiveType.DIESEL),
    PENN_E44("AVERAGE", "FAST", 70, 1960, 37971, 22000, 370000, LocomotiveType.ELECTRIC),
    CLASS_55_DELTIC("VERY_GOOD", "BELOW_AVERAGE", 100, 1961, 52155, 15000, 480000, LocomotiveType.DIESEL),
    SHINKANSEN_BULLET("AVERAGE", "VIRTUALLY_INSTANT", 130, 1966, 66680, 66000, 650000, LocomotiveType.ELECTRIC),
    FP45("ABOVE_AVERAGE", "ABOVE_AVERAGE", 106, 1968, 65740, 14000, 366000, LocomotiveType.DIESEL),
    SD45("AVERAGE","AVERAGE",65,1972,36009,21000,280000, LocomotiveType.DIESEL),
    SDP40("POOR","ABOVE_AVERAGE",103,1973,61966,18000,292000, LocomotiveType.DIESEL),
    E60CP("AVERAGE","VERY_FAST",85,1973,38043,19000,310000,LocomotiveType.ELECTRIC),
    TGV_TSC("AVERAGE","FAST",145,1973,81664,72000,750000,LocomotiveType.ELECTRIC),
    CLASS_E111("GOOD","AVERAGE",85,1974,38016,17000,390000,LocomotiveType.ELECTRIC),
    E656_FS("ABOVE_AVERAGE","AVERAGE",93,1975,37734,17000,226000,LocomotiveType.ELECTRIC),
    DASH_9("GOOD", "ABOVE_AVERAGE", 70, 1993, 68410, 18000, 478000, LocomotiveType.DIESEL),
    AMD_103("ABOVE_AVERAGE", "VERY_FAST", 105, 1993, 88068, 25000, 425000, LocomotiveType.DIESEL),
    THALYS_BULLET("BELOW_AVERAGE", "VIRTUALLY_INSTANT", 186, 1994, 111756, 78000, 1000000, LocomotiveType.ELECTRIC),
    EUROSTAR_TSC("VERY_GOOD", "ULTRA_FAST", 175, 1994, 96135, 65000, 1600000, LocomotiveType.ELECTRIC),
    CLASS_232("VERY_GOOD", "VERY_FAST", 75, 1997, 72625, 35000, 492000, LocomotiveType.DIESEL),
    DB18_201_TSC("ABOVE_AVERAGE", "VERY_FAST", 120, 1998, 144560, 75000, 400000, LocomotiveType.STEAM),
    BRENNER_TSC("GOOD", "ABOVE AVERAGE", 140, 1999, 69292, 170000, 1250000, LocomotiveType.ELECTRIC),
    MAG_LEV_TBX_1("ABOVE AVERAGE", "INSTANT", 280, 2008, 305609, 200000, 2500000, LocomotiveType.ELECTRIC);

    private final String power;
    private final String acceleration;
    private final int topSpeed;
    private final int operationStartYear;
    private final int fuelCost;
    private final int maintenancePYear;
    private final int cost;
    private final LocomotiveType locomotiveType;

    /**
     * Constructs a LocomotiveModel enum constant with specified attributes.
     *
     * @param power Qualitative power rating of the locomotive.
     * @param acceleration Qualitative acceleration rating.
     * @param topSpeed Maximum speed in km/h.
     * @param operationStartYear Year the model began operation.
     * @param fuelCost Fuel cost per usage.
     * @param maintenancePYear Yearly maintenance cost.
     * @param cost Purchase price.
     * @param locomotiveType The type of locomotive (steam, diesel, electric).
     */
    LocomotiveModel(String power, String acceleration, int topSpeed, int operationStartYear, int fuelCost,
                    int maintenancePYear, int cost, LocomotiveType locomotiveType){
        this.power = power;
        this.acceleration = acceleration;
        this.topSpeed = topSpeed;
        this.operationStartYear = operationStartYear;
        this.fuelCost = fuelCost;
        this.maintenancePYear = maintenancePYear;
        this.cost = cost;
        this.locomotiveType = locomotiveType;
    }

    /** @return Qualitative power rating of the locomotive */
    public String getPower() {
        return power;
    }

    /** @return Qualitative acceleration rating */
    public String getAcceleration() {
        return acceleration;
    }

    /** @return Maximum speed in km/h */
    public int getTopSpeed() {
        return topSpeed;
    }

    /** @return Year the locomotive model began operation */
    public int getOperationStartYear() {
        return operationStartYear;
    }

    /** @return Fuel cost per usage unit */
    public int getFuelCost() {
        return fuelCost;
    }

    /** @return Yearly maintenance cost */
    public int getMaintenancePYear() {
        return maintenancePYear;
    }

    /** @return Purchase price of the locomotive */
    public int getCost() {
        return cost;
    }

    /**
     * @return Locomotive type (e.g., STEAM, DIESEL, ELECTRIC)
     */
    public LocomotiveType getLocomotiveType() {
        return locomotiveType;
    }

    /**
     * Alias for getLocomotiveType().
     * @return Locomotive type.
     */
    public LocomotiveType getType() {
        return locomotiveType;
    }

    /**
     * Alias for getOperationStartYear().
     * @return Year the locomotive model began operation.
     */
    public int getStartYear() {
        return operationStartYear;
    }
}
