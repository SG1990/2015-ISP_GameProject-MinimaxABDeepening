import java.util.Random;


public class GameLogic implements IGameLogic {
    private int x = 0;  // counting horizontal, starting from left
    private int y = 0;  // counting vertical, starting at top
    private int playerID;
    private int[][] board;
    private static int depth;
    
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
    }
    
    
    private boolean checkAllDirections(int playerID){
    	int inARowCount = 0;
    	
    	// horizontal check
    	for(int i = 0; i < y; i++){
			for(int n = 0; n < x; n++){
				if(board[n][i] == playerID){
					inARowCount++;
					if(inARowCount >= 4){ return true; }
				}
				else{ inARowCount = 0; }
			}
			inARowCount = 0;
    	}
    	
    	// vertical check
    	for(int i = 0; i < x; i++){
			for(int n = 0; n < y; n++){
				if(board[i][n] == playerID){
					inARowCount++;
					if(inARowCount >= 4){ return true; }
				}
				else{ inARowCount = 0; }
			}
			inARowCount = 0;
    	}
    	
    	
    	// diagonal in this / direction
    	int diagonals = x + y - 1;
    	int fieldsInDiagonal = -1;
    	for(int i = 0; i < diagonals; i++){
    		if( i < x ){
    			fieldsInDiagonal = 0;
    			while(i + fieldsInDiagonal < x && 0 + fieldsInDiagonal < y){ //###
        			fieldsInDiagonal++;
        		}
    			for(int n = 0; n < fieldsInDiagonal; n++){
    				if(board[i + n][0 + n] == playerID){ //###
    					inARowCount++;
    					if(inARowCount >= 4){ return true; }
    				}
    				else{ inARowCount = 0; }	
    			}
    			inARowCount = 0;
    		}
    		else {
    			fieldsInDiagonal = 0;
    			while(0 + fieldsInDiagonal < x && i - x + fieldsInDiagonal < y){ //###
        			fieldsInDiagonal++;
        		}
    			for(int n = 0; n < fieldsInDiagonal; n++){
    				if(board[0 + n][i - x + n] == playerID){ //###
    					inARowCount++;
    					if(inARowCount >= 4){ return true; }
    				}
    				else{ inARowCount = 0; }	
    			}
    			inARowCount = 0;
    		}
    	}
    	
    	// diagonal in this \ direction
    	for(int i = diagonals -1; i >= 0; i--){
    		if( i < x ){
    			fieldsInDiagonal = 0;
    			while(i - fieldsInDiagonal >= 0 && 0 + fieldsInDiagonal < y){ //###
        			fieldsInDiagonal++;
        		}
    			for(int n = 0; n < fieldsInDiagonal; n++){
    				if(board[i - n][0 + n] == playerID){ //###
    					inARowCount++;
    					if(inARowCount >= 4){ return true; }
    				}
    				else{ inARowCount = 0; }	
    			}
    			inARowCount = 0;
    		}
    		else {
    			fieldsInDiagonal = 0;
    			while(x - 1 - fieldsInDiagonal >= 0 && i + x + fieldsInDiagonal < y){ //###
        			fieldsInDiagonal++;
        		}
    			for(int n = 0; n < fieldsInDiagonal; n++){
    				if(board[x - 1 - n][i - x + n] == playerID){ //###
    					inARowCount++;
    					if(inARowCount >= 4){ return true; }
    				}
    				else{ inARowCount = 0; }	
    			}
    			inARowCount = 0;
    		}
    	}
    	return false;
    }
    
    private void mirrorColumns(){
    	int[][] tmpBoard = new int[x][y];
    	for(int i = 0; i < x; i++){
			for(int n = 0; n < y; n++){
				tmpBoard[i][n] = board[i][n];
			}
    	}
    	for(int i = 0; i < x; i++){
			for(int n = 0; n < y; n++){
				board[i][y - 1 - n] = tmpBoard[i][n];
			}
    	}
    }
    
    public Winner gameFinished() {
    	mirrorColumns();
    	if(checkAllDirections(1)){ System.out.println("YEPPI!"); return Winner.PLAYER1; }
    	if(checkAllDirections(2)){ System.out.println("HURRA!"); return Winner.PLAYER2; }
    	mirrorColumns();
    	
    	if(boardIsFull()){ System.out.println("TIE!"); return Winner.TIE; }
    	else{ System.out.println("Next round..."); return Winner.NOT_FINISHED; }
    }
    
    private boolean boardIsFull(){
    	boolean isFull = true;
    	for(int i = 0; i < x; i++){
    		if(board[i][0] == 0){ isFull = false; }
    	}
    	return isFull;
    }
    
    
    private boolean checkLocal(int id, int col, int x, int row, int y, int[][] board){
    	boolean win = true;
    	for(int i = 1; i < 3; i++){
    		try{
    			if(board[col + (x * i)][row + (y * i)] != id){ win = false; break;}
    		}catch(IndexOutOfBoundsException e){ win = false; break;}
    	}
    	return win;    	
    }
    

    /**
     * 
     * @param id, which player
     * @param col, last column added
     * @param board
     * @return
     */
    private Winner gameFinishedMM(int id, int col, int[][] board) {
    	int row = -1; //getRow
    	for(int i = 0; i < y; i++){
    		if(board[col][i] != 0){ row = i;} 
    	}
    	
    	boolean win = false;
    	if(checkLocal(id, col, 0 , row, 1 , board)){ win = true; }
    	if(win == false && checkLocal(id, col, 0 , row, -1 , board)){ win = true; }
    	if(win == false && checkLocal(id, col, 1 , row, 0 , board)){ win = true; }
    	if(win == false && checkLocal(id, col, -1 , row, 0 , board)){ win = true; }
    	
    	if(win == false && checkLocal(id, col, 1 , row, 1 , board)){ win = true; }
    	if(win == false && checkLocal(id, col, 1 , row, -1 , board)){ win = true; }
    	if(win == false && checkLocal(id, col, -1 , row, 1 , board)){ win = true; }
    	if(win == false && checkLocal(id, col, -1 , row, -1 , board)){ win = true; }
    	
    	if(win == true && id == 1){ return Winner.PLAYER1; }
    	if(win == true && id == 2){ return Winner.PLAYER2; }
    	
    	if(boardIsFull()){ return Winner.TIE; }
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

    public int decideNextMove() {
        Random rand = new Random();
        int r;
        
        do
        	r = rand.nextInt(x);
        while(board[r][0] != 0);
        
        return r;
    }
    
    public int decideNextMoveMM() {    	  	
    	int[][] boardCopy = copyArray(board);
    	System.out.println("Commencing minimax");
    	depth = 0;
    	return miniMaxDecision(boardCopy);
    }   
    
    public int decideNextMoveAB() {
    	int[][] boardCopy = copyArray(board);
    	return alphaBetaSearch(boardCopy, -1000, 1000);
    }
    
    private int miniMaxDecision(int[][] b) {
    	int[] results  = new int[x];
    	for(int i = 0 ; i < x ; i++)
    		results[i] = -10;
    	for(int i = 0 ; i < x ; i++)
    	{
    		if (b[i][0] == 0){
    				addToBoard(b, i, playerID);
    			depth++;
    			results[i] = minValue(b, i);
    			depth--;
    			removeFromBoard(b, i);
    		}    			
    	}
    	
    	int max = -10;
    	int move = -1;
    	for(int i = 0 ; i < x ; i++)
    		if(max < results[i])
    		{
    			max = results[i];
    			move = i;
    		}
    			
    	
    	return move;
    }
    
    private int maxValue(int[][] b, int col) {
    	System.out.println("ply = " + depth);
    	int id = 0;
    	if (playerID == 1)
    		id = 2;
    	else id = 1;
    	
    	Winner isWin = gameFinishedMM(id, col, b);
    	switch(isWin)
    	{
    		case PLAYER1:
    		case PLAYER2:
    			return -1;    			
    		case TIE:
    			return 0;
    			
    		default:
    			int[] results  = new int[x];
    			for(int i = 0 ; i < x ; i++)
    	    		results[i] = -10;
            	for(int i = 0 ; i < x ; i++)
            	{
            		if (b[i][0] == 0){
            			depth++;
            			addToBoard(b, i, playerID);
            			results[i] = minValue(b, i);
            			depth--;
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
    
    private int minValue(int[][] b, int col) {  
    	System.out.println("ply = " + depth);
    	Winner isWin = gameFinishedMM(playerID, col, b);
    	switch(isWin)
    	{
    		case PLAYER1:
    		case PLAYER2:
    			return 1;    			
    		case TIE:
    			return 0;
    			
    		default:
    			int[] results  = new int[x];
    			for(int i = 0 ; i < x ; i++)
    	    		results[i] = 10;
            	for(int i = 0 ; i < x ; i++)
            	{
            		if (b[i][0] == 0){
            			if (playerID == 1)
            				addToBoard(b, i, 2);
            			else
            				addToBoard(b, i, 1);
            			depth++;
            			results[i] = maxValue(b, i);
            			depth--;
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
    			results[i] = minValueAB(b, alpha, beta, i);
    			removeFromBoard(b, i);
    		}    			
    	}
    	
    	int max = -10;
    	for(int i = 0 ; i < x ; i++)
    		if(max < results[i][0])
    			max = results[i][0];
    	
    	return max;
    }
    
    private int[] maxValueAB(int[][] b, int alpha, int beta, int col) {
    	int id = 0;
    	if (playerID == 1)
    		id = 2;
    	else id = 1;
    	
    	Winner isWin = gameFinishedMM(id, col, b);
    	switch(isWin)
    	{
    		case PLAYER2:    			
    			return new int[] {-1, alpha, beta};    			
    		case TIE:
    			return new int[] {0, alpha, beta};    
    			
    		default:
    			int[][] results  = new int[x][3];
            	for(int i = 0 ; i < x ; i++)
            	{
            		if (b[i][0] != 0){
            			addToBoard(b, i, playerID);
            			results[i] = minValueAB(b, alpha, beta, i);
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
    
    private int[] minValueAB(int[][] b, int alpha, int beta, int col) {
    	Winner isWin = gameFinishedMM(playerID, col, b);
    	switch(isWin)
    	{
    		case PLAYER1:
    			return new int[] {1, alpha, beta};    			
    		case TIE:
    			return new int[] {0, alpha, beta};
    			
    		default:
    			int[][] results  = new int[x][3];
            	for(int i = 0 ; i < x ; i++)
            	{
            		if (b[i][0] != 0){
            			if (playerID == 1)
            				addToBoard(b, i, 2);
            			else
            				addToBoard(b, i, 1);
            			results[i] = maxValueAB(b, alpha, beta, i);
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
         	if(b[col][i] == 0)
         	{
         		b[col][i] = id;
         		break;
         	}
         }
    }
    
    private void removeFromBoard(int[][] b, int col) {
   	 for(int i = 0 ; i < b[0].length ; i++)
        {
        	if(b[col][i] != 0)
        	{
        		b[col][i] = 0;
        		break;
        	}
        }
   }
    
    private int[][] copyArray(int[][] oldArray) {
    	int[][] newArray = new int[oldArray.length][oldArray[0].length];
    	for(int i = 0; i < oldArray.length ; i++)
    		for(int j = 0 ; j < oldArray[i].length ; j++)
    			newArray[i][j] = oldArray[i][j];    	
    	
    	return newArray;
    }
}
