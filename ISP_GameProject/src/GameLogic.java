import java.util.Random;


public class GameLogic implements IGameLogic {
    private int x = 0;  // counting horizontal, starting from left
    private int y = 0;  // counting vertical, starting at bottom
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
    
    private boolean boardIsFull(){
    	boolean isFull = true;
    	for(int i = 0; i < x; i++){
    		if(board[i][y-1] == 0){ isFull = false; }
    	}
    	return isFull;
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
    	Winner msg;
    	if(checkAllDirections(playerID)){ System.out.println("HURRA!");  return Winner.PLAYER2;}
    	
    	if(boardIsFull()){ System.out.println("Tie!"); return Winner.TIE; }
    	else{ System.out.println("Next round..."); msg = Winner.NOT_FINISHED; }
    	
    	mirrorColumns();
    	return msg;
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
}
