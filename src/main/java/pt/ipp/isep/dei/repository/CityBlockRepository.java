package pt.ipp.isep.dei.repository;

import pt.ipp.isep.dei.domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CityBlockRepository {
    private List<CityBlock> cityBlocks = new ArrayList<>();

    /**
     * Cria uma lista de CityBlocks a partir de uma lista de posições,
     * usando a quantidade especificada de blocos.
     * @param positions lista de posições base
     * @param cityBlocks número de blocos a criar
     * @return lista de CityBlocks criados
     */
    public static List<CityBlock> createBlocks(List<Position> positions, int cityBlocks){
        List<CityBlock> blocks = new ArrayList<>();
        for(int i = 0; i < cityBlocks; i++){
            CityBlock block = new CityBlock(positions.get(i));
            blocks.add(block);
        }
        return blocks;
    }

    /**
     * Gera uma lista de CityBlocks com posições aleatórias em torno das
     * coordenadas (x, y), baseado numa distribuição normal.
     * @param x coordenada x central
     * @param y coordenada y central
     * @param number quantidade de blocos a gerar
     * @return lista de CityBlocks gerados
     */
    public static List<CityBlock> generateBlocks(int x, int y, int number) {
        List<CityBlock> blocks = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < number; i++) {
            Position position = new Position(x,y);
            int dx = (int) Math.round(rand.nextGaussian() * 2);
            int dy = (int) Math.round(rand.nextGaussian() * 2);
            Position pos = new Position(position.getxCoordinate() + dx, position.getyCoordinate() + dy);
            CityBlock cityBlock = new CityBlock(pos);
            blocks.add(cityBlock);
        }

        return blocks;
    }

    /**
     * Adiciona um CityBlock à lista gerenciada por esta instância.
     * @param block bloco a adicionar
     */
    public void addCityBlock(CityBlock block) {
        cityBlocks.add(block);
    }

    /**
     * Retorna a lista atual de CityBlocks armazenados nesta instância.
     * @return lista de CityBlocks
     */
    public List<CityBlock> getCityBlocks() {
        return cityBlocks;
    }
}
