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
    public static final double mutationStrength = 0.4;
    public static final int populationSize = 1000;
    public static final int copiesOfLastBest = 50;

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
//        NeuralNetwork[] population;
//
//        population = NeuralNetwork.generateFirstPopulation(populationSize, layerSizes);
//        for (int i = 0; i < population.length; i++) {
//            AiInput input = new AiInput(population[i], turnsToLive);
//
//            SnakePlayer agent;
//            if (i == 0){
//                GraphicSystem graphicSystem = new GUIGraphics(500, 500, input);
//                agent = new SnakePlayer(boardSizeX, boardSizeY, 100, input, graphicSystem);
//            }
//            else {
//                agent = new SnakePlayer(boardSizeX, boardSizeY, 0, input);
//            }
//            agent.run();
//            System.out.println(agent.snake.getSize());
//        }

        train(30);

    }


    public static void train(int generations){
        // TODO: improve
        NeuralNetwork[] population = NeuralNetwork.generateFirstPopulation(populationSize, layerSizes);
        for (int gen = 0; gen < generations; gen++) {
            int[] agentsFitness = new int[populationSize];
            for (int i = 0; i < population.length; i++) {
                AiInput input = new AiInput(population[i], turnsToLive);

                SnakePlayer agent;
                if (i == 0) {
                    GraphicSystem graphicSystem = new GUIGraphics(500, 500, input);
                    agent = new SnakePlayer(boardSizeX, boardSizeY, 100, input, graphicSystem);
                } else {
                    agent = new SnakePlayer(boardSizeX, boardSizeY, 0, input);
                }

                agent.play();  // todo: maybe threads. check for thread bugs???
                agentsFitness[i] = agent.getFitness();
            }

            NeuralNetwork bestAgent = population[0];
            int bestFitness = Integer.MIN_VALUE;
            for (int i = 0; i < populationSize; i++) {
                int fitness = agentsFitness[i];
                if (fitness > bestFitness) {
                    bestFitness = fitness;
                    bestAgent = population[i];
                }
            }
            System.out.println(bestFitness);

            population = new NeuralNetwork[populationSize];
            for (int i = 0; i < populationSize; i++) {
                if (i < copiesOfLastBest) {
                    population[i] = bestAgent.clone();
                } else {
                    population[i] = bestAgent.mutated(mutationRate, mutationStrength);
                }
            }
        }
    }



}
