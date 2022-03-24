package AI;

import Game.GraphicSystem;
import Game.Pair;

import Game.SnakePlayer;
import Main.GUIGraphics;

public class Evolution {
    final static int inputSize = 8+1+8;
    final static int outputSize = 3;

    final static int[] layerSizes = new int[]{inputSize, 16, 8, outputSize};
    public static final double mutationRate = 0.2;
    public static final double mutationStrength = 0.2;
    public static final int populationSize = 1000;

    public static final int turnsToLive = 300;
    public static final int boardSizeX = 20;
    public static final int boardSizeY = 20;


    public static final Pair[] ALL_DIRECTIONS = new Pair[]{
            new Pair(0, 1),
            new Pair(1,1),
            new Pair(1,0),
            new Pair(1,-1),
            new Pair(0,-1),
            new Pair(-1,-1),
            new Pair(-1,0),
            new Pair(-1,1)
    };

    public static void main(String[] args) {
        NeuralNetwork[] population;

        population = NeuralNetwork.generateFirstPopulation(populationSize, layerSizes);
        for (int i = 0; i < population.length; i++) {
            AiInput input = new AiInput(population[i], turnsToLive);

            SnakePlayer agent;
            if (i == 0){
                GraphicSystem graphicSystem = new GUIGraphics(500, 500, input);
                agent = new SnakePlayer(boardSizeX, boardSizeY, 100, input, graphicSystem);
            }
            else {
                agent = new SnakePlayer(boardSizeX, boardSizeY, 0, input);
            }
            agent.run();
            System.out.println(agent.snake.getSize());
        }


    }


    public void train(int generations){

    }



}
