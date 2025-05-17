package pt.ipp.isep.dei.domain;

public enum IndustryChanger {
    //Tipos
    WAR(1),
    VACCINATION_CAMPAIGN(1),
    SCHOOLING_PROGRAM(1);

    private int effect;

    IndustryChanger(int effect){
        this.effect = effect;
    }
}
