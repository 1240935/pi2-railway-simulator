package pt.ipp.isep.dei.domain;

public enum CargoType {

    ALUMINUM(35000),
    AUTOS(49000),
    BAUXITE(28000),
    CATTLE(37000),
    CEMENT(38000),
    CHEMICALS(37000),
    COAL(28000),
    COFFEE(37000),
    COTTON(36000),
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
    WEAPONS(38000)
    ;

    private final int value;

    CargoType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
