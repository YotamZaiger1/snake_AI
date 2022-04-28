package AI;

import Game.GraphicSystem;
import Game.SnakePlayer;
import Main.GUIGraphics;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Evolution {
    public static final double mutationRate = 0.4;
    public static final double mutationStrength = 0.4;
    public static final int populationSize = 2_000;
    public static final int copiesOfLastBest = 10;
    public static final int takeTop = 10;
    public static final int stupidPopulationSize = populationSize / 4; // number of new random agents that added in each generation
    public static final int turnsToLive = 300;
    public static final int boardSizeX = 20;
    public static final int boardSizeY = 20;
    public static final int trainBoardSizeX = 20;
    public static final int trainBoardSizeY = 20;
    final static int inputSize = AiInput.inputSize;
    final static int outputSize = 3;
    final static int[] layerSizes = new int[]{inputSize, 16, 16, outputSize};

    public static void main(String[] args) {
        train(Integer.MAX_VALUE);
    }


    public static void train(int generations) {
        // TODO: improve

        NeuralNetwork[] population = NeuralNetwork.generateFirstPopulation(populationSize, layerSizes);
        for (int gen = 0; gen < generations; gen++) {

            int[] agentsFitness = new int[populationSize];
            for (int i = 0; i < population.length; i++) {
                AiInput input = new AiInput(population[i], turnsToLive);

                SnakePlayer agent = new SnakePlayer(trainBoardSizeX, trainBoardSizeY, 0, input);

                agent.play();
                agentsFitness[i] = agent.getFitness();
            }

            PriorityQueue<Integer> bestAgentsIndices = new PriorityQueue<>(Comparator.comparingInt(o -> -agentsFitness[o]));
            for (int i = 0; i < populationSize; i++) {
                bestAgentsIndices.add(i);
            }

            NeuralNetwork[] bestAgents = new NeuralNetwork[takeTop];
            for (int i = 0; i < takeTop; i++) {
                bestAgents[i] = population[bestAgentsIndices.poll()];
            }


            // make new generation:
            population = new NeuralNetwork[populationSize];
            int index = 0;
            for (NeuralNetwork network : bestAgents) {
                for (int i = 0; i < copiesOfLastBest; i++) {
                    population[index] = network.clone();
                    index++;
                }
            }
            NeuralNetwork[] newAgents = NeuralNetwork.generateFirstPopulation(stupidPopulationSize, layerSizes);
            for (NeuralNetwork network : newAgents) {
                population[index] = network;
                index++;
            }
            for (int i = index; i < populationSize; i++) {
                NeuralNetwork parentAgent = bestAgents[i % takeTop];
                population[i] = parentAgent.mutated(mutationRate, mutationStrength);
            }

            if (gen % 1 == 0) {
                GraphicSystem graphicSystem = new GUIGraphics(500, 500, null, true);
                (new SnakePlayer(boardSizeX, boardSizeY, 70, new AiInput(population[0], 300), graphicSystem)).play();
                try {
                    NeuralNetwork.saveNetwork(population[0], "res/test.tmp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void playAIFromFile(String fileName) throws IOException, ClassNotFoundException {
        playAIFromFile(fileName, 500, 500, 70);
    }

    public static void playAIFromFile(String fileName, int screenWidth, int screenHeight, long milliDelay) throws IOException, ClassNotFoundException {
        NeuralNetwork neuralNetwork = NeuralNetwork.loadNetwork(fileName);
        AiInput input = new AiInput(neuralNetwork, Integer.MAX_VALUE);
        GraphicSystem graphicSystem = new GUIGraphics(screenWidth, screenHeight, null, false);

        SnakePlayer snakePlayer = new SnakePlayer(boardSizeX, boardSizeY, milliDelay, input, graphicSystem);
        snakePlayer.play();
    }

}
