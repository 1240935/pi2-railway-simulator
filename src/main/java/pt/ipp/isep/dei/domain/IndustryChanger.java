package pt.ipp.isep.dei.domain;

public enum IndustryChanger {
    //Tipos
    WAR(1),
    VACCINATIONCAMPAIGN(1),
    SCHOOLINGPROGRAM(1);

    private int effect;

    IndustryChanger(int effect){
        this.effect = effect;
    }
}
