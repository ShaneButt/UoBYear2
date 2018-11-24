import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {

    // Starting parameters of the Genetic Algorithm
    private int MaxSize; // Chess board width
    private int PopulationSize; // Population start size
    private int MaxCycles; // Number of tests to perform
    private double MatingChance; // The chance that two chromosomes will mate
    private double MutationRate; // Simple mutation rate, 0.0 < rate < 1.0
    private int MinSelection; // Minimum number of parents selected to breed
    private int MaxSelection; // Maximum number of parents selected to breed.
    private int NumOffspring; // Number of offspring generated per generation/cycle

    private int MinRerolls; // Used to randomize the starting chromosomes;
    private int MaxRerolls;

    private int nextMutate;
    private ArrayList<Chromosome> population;
    private ArrayList<Chromosome> answers;
    private Random rand;
    private int children;
    private int mutations;
    private int cycle;
    private int populationSize;
    String Board[][];

    /* Initialises the Genetic Algorithm with input parameters;
     *
     */
    public GeneticAlgorithm()
    {
        System.out.println("Please input the size of the board");
        Scanner sc = new Scanner(System.in);
        MaxSize = sc.nextInt();
        System.out.println("Please input the population size");
        PopulationSize = sc.nextInt();

        MaxCycles = 800;

        MatingChance = 0.6;
        MutationRate = 0.002;

        MinSelection = (int) (PopulationSize/4); // 1/4 min
        MaxSelection = (int) ((PopulationSize / 4) * 3); // 3/4 max

        NumOffspring = (int) (PopulationSize / 2); // half the population

        MinRerolls = 8;
        MaxRerolls = 18;

        cycle = 0;
        populationSize = 0;
        Board = new String[MaxSize][MaxSize];

        System.out.println("Please enter your start queen's position");
    }

    public boolean AlgorithmStart()
    {
        population = new ArrayList<Chromosome>();
        answers = new ArrayList<Chromosome>();
        rand = new Random();
        nextMutate = 0;
        children = 0;
        mutations = 0;
        cycle = 0;
        populationSize = 0;
        boolean complete = false;
        Chromosome current = null;
        nextMutate = getRandomNum(0, (int) Math.round(1.0/MutationRate));

        //initialise();

        while(!complete)
        {
            populationSize = population.size();
            for(int i = 0; i < populationSize; i++)
            {
                current = population.get(i);
                if(current.getConflictions()==0) // is the answer found
                {
                    complete = true;
                }

                if(cycle == MaxCycles) // have we reached max cycle limit?
                {
                    complete = true;
                }

                //getFitness()
                //rouletteSelection();
                //mating();
                //prepareNextCycle();
                cycle++;
                System.out.println("Cycle: " + cycle);
            }
        }

        if(cycle >= MaxCycles)
        {
            System.out.println("Could not find solution within given cycle limit");
            complete = false;
        }
        else
        {
            populationSize = population.size();
            for(int i = 0; i < populationSize; i++)
            {
                current = population.get(i);
                if(current.getConflictions() == 0)
                {
                    answers.add(current);
                    //printAnswer(current);
                }
            }
        }

        System.out.println("Finished...");
        System.out.println("Completed in: " + cycle + " cycles");
        System.out.println("Encountered " + mutations + " mutations in " + children + " children");
        return complete;
    }

    public void Mating()
    {
        int retRand = 0;
        int parentA, parentB = 0;
        int indA, indB = 0;
        Chromosome chromoA, chromoB = null;

        for(int i = 0; i < NumOffspring; i++)
        {
            //parentA = ChooseParent();
            retRand = getRandomNum(0, 100);
            if(retRand <= MatingChance * 100);
            {
                //parentB = ChooseParent(parentA);
                chromoA = new Chromosome(MaxSize);
                chromoB = new Chromosome(MaxSize);
                population.add(chromoA);
                indA = population.indexOf(chromoA);
                population.add(chromoB);
                indB = population.indexOf(chromoB);

                //partialMapXOver
                //PartialMapXOver(parentA, parentB, indA, indB);

                if(children - 1 == nextMutate)
                {
                    // Mutate(indA, 1);
                }
                else if(children == nextMutate)
                {
                    // Mutate(indB, 1);
                }

                population.get(indA).CalculateFitness(Board);

            }
        }
    }

    public int getRandomNum(int x, int y)
    {
        return (int) Math.round((y-x) * rand.nextDouble() + x);
    }


    public int getPopulationSize() {
        return PopulationSize;
    }

    public int getMaxCycles() {
        return MaxCycles;
    }

    public double getMatingChance() {
        return MatingChance;
    }

    public double getMutationRate() {
        return MutationRate;
    }

    public int getMinSelection() {
        return MinSelection;
    }

    public int getMaxSelection() {
        return MaxSelection;
    }

    public int getNumOffspring() {
        return NumOffspring;
    }

    public int getMinRerolls() {
        return MinRerolls;
    }

    public int getMaxRerolls() {
        return MaxRerolls;
    }

    public ArrayList<Chromosome> getPopulation() {
        return population;
    }

    public ArrayList<Chromosome> getAnswers() {
        return answers;
    }

    public void PrintBoard()
    {
        System.out.println("_A__B__C__D__E__F__G__H_");
    }

}
