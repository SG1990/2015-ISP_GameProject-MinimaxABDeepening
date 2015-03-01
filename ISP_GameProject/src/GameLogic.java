import java.util.Random;


public class GameLogic implements IGameLogic {
    private int x = 0;
    private int y = 0;
	private int playerID;
    private int[][] board;
    
    public GameLogic() {
        //TODO Write your implementation for this method
    }
	
    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        this.playerID = playerID;
        board = new int[x][y];
        for(int i = 0; i < x; i++){
			for(int n = 0; n < y; n++){
				board[i][n] = 0;
			}
    	}
        //TODO Write your implementation for this method
    }
	
    private boolean checkRow(){
    	for(int i = 0; i < x; i++){
			for(int n = 0; n < x; n++){
				//if(board[i][n] == );
			}
			
    	}
    	return false;
    }
    
    
    public Winner gameFinished() {
    	//TODO Write your implementation for this method
    	checkRow();
        
        return Winner.NOT_FINISHED;
    }


    public void insertCoin(int column, int playerID) {
        for(int i = y - 1 ; i >= 0 ; i--)
        {
        	if(board[column][i] == 0)
        	{
        		board[column][i] = playerID;
        		break;
        	}
        }
    }

    public int decideNextMoveR() {
        Random rand = new Random();
        int r;
        
        do
        	r = rand.nextInt(x);
        while(board[r][0] != 0);
        
        return r;
    }
    
    public int decideNextMove() {    	  	
    	int[][] boardCopy = copyArray(board);
    	return miniMaxDecision(boardCopy);
    }   
    
    public int decideNextMoveAB() {
    	int[][] boardCopy = copyArray(board);
    	return alphaBetaSearch(boardCopy, -1000, 1000);
    }
    
    private int miniMaxDecision(int[][] b) {
    	int[] results  = new int[x];
    	for(int i = 0 ; i < x ; i++)
    	{
    		if (b[i][0] != 0){
    			if (playerID == 1)
    				addToBoard(b, i, 2);
    			else
    				addToBoard(b, i, 1);
    			results[i] = minValue(b);
    			removeFromBoard(b, i);
    		}    			
    	}
    	
    	int max = -10;
    	for(int i = 0 ; i < x ; i++)
    		if(max < results[i])
    			max = results[i];
    	
    	return max;
    }
    
    private int maxValue(int[][] b) {
    	if (isTerminal(b))
    		return evaluate(b);
    	else {
    		int[] results  = new int[x];
        	for(int i = 0 ; i < x ; i++)
        	{
        		if (b[i][0] != 0){
        			addToBoard(b, i, playerID);
        			results[i] = minValue(b);
        			removeFromBoard(b, i);
        		}    			
        	}
        	
        	int max = -10;
        	for(int i = 0 ; i < x ; i++)
        		if(max < results[i])
        			max = results[i];
        	
        	return max;
    	}
    }
    
    private int minValue(int[][] b) {
    	if (isTerminal(b))
    		return evaluate(b);
    	else {
    		int[] results  = new int[x];
        	for(int i = 0 ; i < x ; i++)
        	{
        		if (b[i][0] != 0){
        			if (playerID == 1)
        				addToBoard(b, i, 2);
        			else
        				addToBoard(b, i, 1);
        			results[i] = maxValue(b);
        			removeFromBoard(b, i);
        		}    			
        	}
        	
        	int min = 10;
        	for(int i = 0 ; i < x ; i++)
        		if(min > results[i])
        			min = results[i];
        	
        	return min;
    	}
    }
    
    private int alphaBetaSearch(int[][] b, int alpha, int beta) {
    	int[][] results  = new int[x][3];
    	for(int i = 0 ; i < x ; i++)
    	{
    		if (b[i][0] != 0){
    			if (playerID == 1)
    				addToBoard(b, i, 2);
    			else
    				addToBoard(b, i, 1);
    			results[i] = minValueAB(b, alpha, beta);
    			removeFromBoard(b, i);
    		}    			
    	}
    	
    	int max = -10;
    	for(int i = 0 ; i < x ; i++)
    		if(max < results[i][0])
    			max = results[i][0];
    	
    	return max;
    }
    
    private int[] maxValueAB(int[][] b, int alpha, int beta) {
    	if (isTerminal(b))
    		return new int[3]; //TODO
    	else {
    		int[][] results  = new int[x][3];
        	for(int i = 0 ; i < x ; i++)
        	{
        		if (b[i][0] != 0){
        			addToBoard(b, i, playerID);
        			results[i] = minValueAB(b, alpha, beta);
        			removeFromBoard(b, i);
        			if (results[i][0] >= results[i][2]) return results[i];
        			results[i][1] = Math.max(results[i][0], results[i][2]);
        		}    			
        	}
        	
        	int[] max = new int[3];
        	max[0] = -10;
        	for(int i = 0 ; i < x ; i++)
        		if(max[0] < results[i][0])        		
        			max = results[0];        			
        	
        	return max;
    	}
    }
    
    private int[] minValueAB(int[][] b, int alpha, int beta) {
    	if (isTerminal(b))
    		return new int[3]; //TODO
    	else {
    		int[][] results  = new int[x][3];
        	for(int i = 0 ; i < x ; i++)
        	{
        		if (b[i][0] != 0){
        			if (playerID == 1)
        				addToBoard(b, i, 2);
        			else
        				addToBoard(b, i, 1);
        			results[i] = maxValueAB(b, alpha, beta);
        			removeFromBoard(b, i);
        			if (results[i][0] <= results[i][1]) return results[i];
        			results[i][2] = Math.max(results[i][0], results[i][2]); 
        		}    			
        	}
        	
        	int[] min = new int[3];
        	min[0] = 10;
        	for(int i = 0 ; i < x ; i++)
        		if(min[0] > results[i][0])
        			min = results[i];
        	
        	return min;
    	}
    }
    
    private void addToBoard(int[][] b, int col, int id) {
    	 for(int i = b[0].length - 1 ; i >= 0 ; i--)
         {
         	if(board[col][i] == 0)
         	{
         		board[col][i] = id;
         		break;
         	}
         }
    }
    
    private void removeFromBoard(int[][] b, int col) {
   	 for(int i = 0 ; i < b[0].length ; i--)
        {
        	if(board[col][i] != 0)
        	{
        		board[col][i] = 0;
        		break;
        	}
        }
   }
    
    private boolean isTerminal(int[][] b)
    {
    	//TODO
    	
    	return false;
    }
    
    private int evaluate(int[][] b)
    {
    	//TODO
    	return 0;
    }
    
    private int[][] copyArray(int[][] oldArray) {
    	int[][] newArray = new int[oldArray.length][oldArray[0].length];
    	for(int i = 0; i < oldArray.length ; i++)
    		for(int j = 0 ; j < oldArray[i].length ; j++)
    			newArray[i][j] = oldArray[i][j];    	
    	
    	return newArray;
    }
}
