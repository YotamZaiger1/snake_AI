package AI;

import Game.Snake;

public class Evolution {
    final int populationSize;
    final NeuralNetwork[] population;

    final int[] layerSizes;
    final double mutationRate;
    final double mutationStrength;


    public Evolution(NeuralNetwork[] population, double mutationRate, double mutationStrength){
        this.population = population;
        this.populationSize = population.length;
        this.layerSizes = population[0].getLayerSizes();

        this.mutationRate = mutationRate;
        this.mutationStrength = mutationStrength;
    }


    public void train(int generations){

    }

    public int fitness(Snake snake) {
        //fitness is based on length and lifetime
        int size = snake.getSize();
        int turnsAlive = snake.getTurnsAlive();
        if (size < 10)
            return (int) (turnsAlive * turnsAlive * Math.pow(2, size));

        //grows slower after 10 to stop fitness from getting stupidly big
        int fitness = turnsAlive * turnsAlive;
        fitness *= Math.pow(2, 10);
        fitness *= size - 9;
        return fitness;
    }

}
