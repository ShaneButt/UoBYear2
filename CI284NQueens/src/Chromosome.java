public class Chromosome implements Comparable<Chromosome> {
    private int MaxSize; // Standard board size
    private int[] Queens; // Contains location of each queen
    private double Fitness; // 'fitness' attribute towards best answer
    private int Conflictions; // number of times it conflicts with others
    private boolean IsSelected; // selected for the breeding cycle
    private double SelectionChance; // chance of being selected for the breed cycle

    public Chromosome(int size)
    {
        MaxSize = size;
        Queens = new int[MaxSize];
        Fitness = 0.0;
        Conflictions = 0;
        IsSelected = false;
        SelectionChance = 0.0;
    }

    /* Calculates the fitness by computing the conflictions;
     *
     */
    public void CalculateFitness(String[][] uBoard)
    {
        int row = 0;
        int col = 0;
        int tempRow = 0;
        int tempCol = 0;

        int[] diagX = new int[] {-1, 1, -1, 1}; // diagonal checking
        int[] diagY = new int[] {-1, 1, 1, -1};

        boolean computed = false;
        int conflictions = 0;

        plotQueens(uBoard);

        // Crawl through each Queen and calculate the fitness / compute conflictions
        for(int i = 0; i < MaxSize-1; i++)
        {
            row = i;
            col = Queens[i];

            // Check queen diagonals
            for(int z = 0; z < 4; z++) // there are 4 diagonals
            {
                tempRow = row;
                tempCol = col; // Coordinate store in temp values
                computed = false;

                while(!computed) // crawl through each diagonal
                {
                    tempRow += diagX[z];
                    tempCol += diagY[z];

                    if( (tempRow < 0 || tempRow >= MaxSize)
                        || (tempCol < 0 || tempCol >= MaxSize)) // Exceeding board length
                    {
                        computed = true;
                    }
                    else
                    {
                        if(uBoard[tempRow][tempCol].equals("Q")) //
                        {
                            conflictions++;
                        }
                    }
                }
            }
        }
        this.Conflictions = conflictions;
    }

    /* Plots the queens to the board
     * @param board: the current board;
     */
    public void plotQueens(String[][] board)
    {
        for(int i =0; i < MaxSize; i++)
            board[i][Queens[i]] = "Q";
    }

    public void clearBoard(String[][] board)
    {
        for(int x = 0; x < MaxSize; x++)
        {
            for(int y = 0; y < MaxSize; y++)
            {
                board[x][y] = " . ";
            }
        }
    }

    public void init()
    {
        for(int queen = 0; queen < MaxSize; queen++)
            Queens[queen] = queen;
    }

    public int getMaxSize() {
        return MaxSize;
    }

    public void setMaxSize(int maxSize) {
        MaxSize = maxSize;
    }

    public int[] getQueens() {
        return Queens;
    }

    public void setQueen(int index, int pos) {
        Queens[index] = pos;
    }

    public double getFitness() {
        return Fitness;
    }

    public void setFitness(double fitness) {
        Fitness = fitness;
    }

    public int getConflictions() {
        return Conflictions;
    }

    public void setConflictions(int conflictions) {
        Conflictions = conflictions;
    }

    public boolean isSelected() {
        return IsSelected;
    }

    public void setSelected(boolean selected) {
        IsSelected = selected;
    }

    public double getSelectionChance() {
        return SelectionChance;
    }

    public void setSelectionChance(double selectionChance) {
        SelectionChance = selectionChance;
    }

    public int compareTo(Chromosome chrom)
    {
        return this.Conflictions - chrom.getConflictions();
    }

}
