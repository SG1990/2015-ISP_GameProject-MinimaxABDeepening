public class GameLogicAaron implements IGameLogic {
	private int x = 0;  // counting horizontal, starting from left
    private int y = 0;  // counting vertical, starting at top
    private int playerID;
    private int[][] board;
    private static int depth;
    private final int maxDepth = 8; //Sample value. Needs to be determined by experimentation 
    private final int minTokenToWin = 4;
    private final int valueOfWin = 500; //Should be less than 1000 now, but greater than any result of evaluate()
    
    public GameLogicAaron() {}
	
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
					if(inARowCount >= minTokenToWin){ return true; }
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
					if(inARowCount >= minTokenToWin){ return true; }
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
    					if(inARowCount >= minTokenToWin){ return true; }
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
    					if(inARowCount >= minTokenToWin){ return true; }
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
    					if(inARowCount >= minTokenToWin){ return true; }
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
    					if(inARowCount >= minTokenToWin){ return true; }
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
    	
    	if(boardIsFull(board)){ System.out.println("TIE!"); return Winner.TIE; }
    	else{ System.out.println("Next round..."); return Winner.NOT_FINISHED; }
    }
    
    private boolean boardIsFull(int[][] b){
    	boolean isFull = true;
    	for(int i = 0; i < x; i++){
    		if(b[i][0] == 0){ isFull = false; }
    	}
    	return isFull;
    }
    
    
    private boolean checkLocal(int id, int col, int x, int row, int y, int[][] board){
    	boolean win = true;
    	for(int i = 1; i < minTokenToWin; i++){
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
    		if(board[col][i] != 0){ row = i; break;} 
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
    	
    	if(boardIsFull(board)){ return Winner.TIE; }
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
    
    public int decideNextMoveAB() {
    	int[][] boardCopy = copyArray(board);
    	depth = 0;
    	System.out.println("Commencing Alpha-Beta");
    	return alphaBetaSearch(boardCopy, -1000, 1000);
    }
    
    public int decideNextMove() {
    	int[][] boardCopy = copyArray(board);
    	depth = 0;
    	System.out.println("Commencing Cut-Off Alpha-Beta");
    	return alphaBetaSearchCutOff(boardCopy, -1000, 1000);
    }
        
    private int alphaBetaSearch(int[][] b, int alpha, int beta) {
    	int[][] results  = new int[x][3];
    	for(int i = 0 ; i < x ; i++)
    		results[i][0] = -1000;
    	for(int i = 0 ; i < x ; i++)
    	{
    		if (b[i][0] == 0){
    			addToBoard(b, i, playerID);
    			results[i] = minValueAB(b, alpha, beta, i);
    			removeFromBoard(b, i);
    		}    			
    	}
    	
    	int max = -1000;
    	int move = -1;
    	for(int i = 0 ; i < x ; i++)
    		if(max < results[i][0])
    		{
    			max = results[i][0];
    			move = i;
    		}    			
    	
    	return move;
    }
    
    private int[] maxValueAB(int[][] b, int alpha, int beta, int col) {
    	int id = 0;
    	if (playerID == 1)
    		id = 2;
    	else id = 1;
    	
    	Winner isWin = gameFinishedMM(id, col, b);
    	switch(isWin)
    	{
    		case PLAYER1:
    		case PLAYER2:    			
    			return new int[] {-1, alpha, beta};    			
    		case TIE:
    			return new int[] {0, alpha, beta};    
    			
    		default:
    			int[][] results  = new int[x][3];
    			for(int i = 0 ; i < x ; i++)
    	    		results[i][0] = -1000;
            	for(int i = 0 ; i < x ; i++)
            	{
            		if (b[i][0] == 0){
            			addToBoard(b, i, playerID);
            			depth++;
            			results[i] = minValueAB(b, alpha, beta, i);
            			depth--;
            			removeFromBoard(b, i);
            			if (results[i][0] >= beta) return results[i];
            			results[i][1] = Math.max(results[i][0], alpha);
            			results[i][2] = beta;
            		}    			
            	}
            	
            	int[] max = new int[3];
            	max[0] = -1000;
            	for(int i = 0 ; i < x ; i++)
            		if(max[0] < results[i][0])        		
            			max = results[i];        			
            	
            	return max;
    	}
    }
    
    private int[] minValueAB(int[][] b, int alpha, int beta, int col) {
    	Winner isWin = gameFinishedMM(playerID, col, b);
    	switch(isWin)
    	{
    		case PLAYER1:
    		case PLAYER2:
    			return new int[] {1, alpha, beta};    			
    		case TIE:
    			return new int[] {0, alpha, beta};
    			
    		default:
    			int[][] results  = new int[x][3];
    			for(int i = 0 ; i < x ; i++)
    	    		results[i][0] = 1000;
            	for(int i = 0 ; i < x ; i++)
            	{
            		if (b[i][0] == 0){
            			if (playerID == 1)
            				addToBoard(b, i, 2);
            			else
            				addToBoard(b, i, 1);
            			depth++;
            			results[i] = maxValueAB(b, alpha, beta, i);
            			depth--;
            			removeFromBoard(b, i);
            			if (results[i][0] <= alpha) return results[i];
            			results[i][2] = Math.min(results[i][0], beta); 
            			results[i][1] = alpha;
            		}    			
            	}
            	
            	int[] min = new int[3];
            	min[0] = 1000;
            	for(int i = 0 ; i < x ; i++)
            		if(min[0] > results[i][0])
            			min = results[i];
            	
            	return min;
    	}
    }
    
    private int alphaBetaSearchCutOff(int[][] b, int alpha, int beta) {
    	int[][] results  = new int[x][3];
    	for(int i = 0 ; i < x ; i++)
    		results[i][0] = -1000;
    	for(int i = 0 ; i < x ; i++)
    	{
    		if (b[i][0] == 0){
    			addToBoard(b, i, playerID);
    			results[i] = minValueABCO(b, alpha, beta, i);
    			removeFromBoard(b, i);
    		}    			
    	}
    	
    	int max = -1000;
    	int move = -1;
    	for(int i = 0 ; i < x ; i++)
    		if(max < results[i][0])
    		{
    			max = results[i][0];
    			move = i;
    		}    			
    	
    	return move;
    }    
    
    private int[] maxValueABCO(int[][] b, int alpha, int beta, int col) {
    	int id = 0;
    	if (playerID == 1)
    		id = 2;
    	else id = 1;    	
    	
    	Winner isWin = gameFinishedMM(id, col, b);
    	switch(isWin)
    	{
    		case PLAYER1:
    		case PLAYER2:    			
    			return new int[] {-valueOfWin, alpha, beta};    		
    		case TIE:
    			return new int[] {0, alpha, beta};    
    			
    		default:
    			if(depth >= maxDepth)
    				return  new int[] {evaluate(id, col, b), alpha, beta}; 
    			else
    			{
    				int[][] results  = new int[x][3];
        			for(int i = 0 ; i < x ; i++)
        	    		results[i][0] = -1000;
                	for(int i = 0 ; i < x ; i++)
                	{
                		if (b[i][0] == 0){
                			addToBoard(b, i, playerID);
                			depth++;
                			results[i] = minValueABCO(b, alpha, beta, i);
                			depth--;
                			removeFromBoard(b, i);
                			if (results[i][0] >= beta) return results[i];
                			results[i][1] = Math.max(results[i][0], alpha);
                			results[i][2] = beta;
                		}    			
                	}
                	
                	int[] max = new int[3];
                	max[0] = -1000;
                	for(int i = 0 ; i < x ; i++)
                		if(max[0] < results[i][0])        		
                			max = results[i];        			
                	
                	return max;
    			}    			
    	}
    }
    
    private int[] minValueABCO(int[][] b, int alpha, int beta, int col) {
    	Winner isWin = gameFinishedMM(playerID, col, b);
    	switch(isWin)
    	{
    		case PLAYER1:
    		case PLAYER2:
    			return new int[] {valueOfWin, alpha, beta};    			
    		case TIE:
    			return new int[] {0, alpha, beta};
    			
    		default:
    			if(depth >= maxDepth)
    				return  new int[] {evaluate(playerID, col, b), alpha, beta}; 
    			else
    			{
    				int[][] results  = new int[x][3];
        			for(int i = 0 ; i < x ; i++)
        	    		results[i][0] = 1000;
                	for(int i = 0 ; i < x ; i++)
                	{
                		if (b[i][0] == 0){
                			if (playerID == 1)
                				addToBoard(b, i, 2);
                			else
                				addToBoard(b, i, 1);
                			depth++;
                			results[i] = maxValueABCO(b, alpha, beta, i);
                			depth--;
                			removeFromBoard(b, i);
                			if (results[i][0] <= alpha) return results[i];
                			results[i][2] = Math.min(results[i][0], beta); 
                			results[i][1] = alpha;
                		}    			
                	}
                	
                	int[] min = new int[3];
                	min[0] = 1000;
                	for(int i = 0 ; i < x ; i++)
                		if(min[0] > results[i][0])
                			min = results[i];
                	
                	return min;
    			}
    	}
    }
    
    private int countLocalCluster(int id, int col, int x, int row, int y, int[][] board){
    	int counter = 0;
    	for(int i = 1; i < minTokenToWin; i++){
    		try{
    			if(board[col + (x * i)][row + (y * i)] != id){ break; }
    		}catch(IndexOutOfBoundsException e){ break; }
    		counter++;
    	}
    	return counter;    	
    }    

    private int evaluate(int id, int col, int[][] board){
    	int row = -1; //getRow
    	for(int i = 0; i < y; i++){
    		if(board[col][i] != 0){ row = i; break;} 
    	}
    	
    	return 1 +
    	countLocalCluster(id, col, 0 , row, 1 , board) +
    	countLocalCluster(id, col, 0 , row, -1 , board) +
    	countLocalCluster(id, col, 1 , row, 0 , board) +
    	countLocalCluster(id, col, -1 , row, 0 , board) +
    	
    	countLocalCluster(id, col, 1 , row, 1 , board) +
    	countLocalCluster(id, col, 1 , row, -1 , board) +
    	countLocalCluster(id, col, -1 , row, 1 , board) +
    	countLocalCluster(id, col, -1 , row, -1 , board);
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
