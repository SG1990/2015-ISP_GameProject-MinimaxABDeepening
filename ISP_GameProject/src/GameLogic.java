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

    public int decideNextMove() {
        Random rand = new Random();
        int r;
        
        do
        	r = rand.nextInt(x);
        while(board[r][0] != 0);
        
        return r;
    }

}
