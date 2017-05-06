import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class UCS {

  private class TilePos {
		public int x;
		public int y;
		
		public TilePos(int x, int y) {
			this.x=x;
			this.y=y;
		}
		
	}
	public static  int cost = 0;
	public final static int size=3;
	private int[][] puzzle;	private int width;	private TilePos empty;
	
	
	public UCS() {
		
		puzzle = new int[size][size];
		int cnt=1;
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				puzzle[i][j]=cnt;
				cnt++;
			}
		} 
		
		width=Integer.toString(cnt).length();
		empty = new TilePos(size-1,size-1);
		puzzle[empty.x][empty.y]=0;
	}
	
	public final static UCS GOAL=new UCS();
	
	
	public UCS(UCS toClone) {
		this();  
		for(TilePos p: allTilePos()) { 
			puzzle[p.x][p.y] = toClone.tile(p);
		}
		empty = toClone.getBlank();
	}

	public List<TilePos> allTilePos() {
		ArrayList<TilePos> out = new ArrayList<TilePos>();
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				out.add(new TilePos(i,j));
			}
		}
		return out;
	}

	
	public int tile(TilePos p) {
		return puzzle[p.x][p.y];
	}
	
	
	public TilePos getBlank() {
		return empty;
	}
	
	
	public TilePos whereIs(int x) {
		for(TilePos p: allTilePos()) { 
			if( tile(p) == x ) {
				return p;
			}
		}
		return null;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof UCS) {
			for(TilePos p: allTilePos()) { 
				if( this.tile(p) != ((UCS) o).tile(p)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	
	@Override 
	public int hashCode() {
		int out=0;
		for(TilePos p: allTilePos()) {
			out= (out*size*size) + this.tile(p);
		}
		return out;
	}
	
	
	public void show() {
		
		for(int i=0; i<size; i++) {
			System.out.print("\n");
			for(int j=0; j<size; j++) {
				int n = puzzle[i][j];
				String s;
				if( n>0) {
					
					s = Integer.toString(n);	
				} else {
					s = "";
				}
				while( s.length() < width ) {
					s += " ";
				}
				System.out.print(s + " ");
			}
			System.out.print("\n");
		}System.out.print("\n\n");
		
	}
	
	
	public List<TilePos> allValidMoves() {
		ArrayList<TilePos> out = new ArrayList<TilePos>();
		for(int dx=-1; dx<2; dx++) {
			for(int dy=-1; dy<2; dy++) {
				TilePos tp = new TilePos(empty.x + dx, empty.y + dy);
				if( isValidMove(tp) ) {
					out.add(tp);
				}
			}
		}
		return out;
	}
	
	
	public boolean isValidMove(TilePos p) {
		if( ( p.x < 0) || (p.x >= size) ) {
			return false;
		}
		if( ( p.y < 0) || (p.y >= size) ) {
			return false;
		}
		int dx = empty.x - p.x;
		int dy = empty.y - p.y;
		if( (Math.abs(dx) + Math.abs(dy) != 1 ) || (dx*dy != 0) ) {
			return false;
		}
		return true;
	}
	
	
	public void move(TilePos p) {
		if( !isValidMove(p) ) {
			throw new RuntimeException("Invalid move");
		}
		assert puzzle[empty.x][empty.y]==0;
		
		puzzle[empty.x][empty.y] = puzzle[p.x][p.y];
		puzzle[p.x][p.y]=0;
		empty = p;
	}
	
	
	
	public UCS moveClone(TilePos p) {
		UCS out = new UCS(this);
		out.move(p);
		/*if(puzzle[p.x][p.y] == 1 || puzzle[p.x][p.y] == 2 || puzzle[p.x][p.y] == 3)
			cost = cost+1;
		if(puzzle[p.x][p.y] == 4 || puzzle[p.x][p.y] == 5 || puzzle[p.x][p.y] == 6)
			cost = cost + 2;
		if(puzzle[p.x][p.y] == 7 || puzzle[p.x][p.y] == 8)
			cost = cost + 3;*/
		return out;
	}

	
	public void shuffle(int howmany) {
		for(int i=0; i<howmany; i++) {
			List<TilePos> possible = allValidMoves();
			int which =  (int) (Math.random() * possible.size());
			TilePos move = possible.get(which);
			this.move(move);
		}
	}

	
	public void shuffle() {
		shuffle(size*size*size);
	}

	
	public int numberMisplacedTiles() {
		int wrong=0;
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				if( (puzzle[i][j] >0) && ( puzzle[i][j] != GOAL.puzzle[i][j] ) ){
					wrong++;
				}
			}
		}
		return wrong;
	}
	
	
	public boolean isSolved() {
		return numberMisplacedTiles() == 0;
	}
	
	
	
	
	
	public List<UCS> allAdjacentPuzzles() {
		ArrayList<UCS> out = new ArrayList<UCS>();
		for( TilePos move: allValidMoves() ) {
			out.add( moveClone(move) );
		}
		return out;
	}
	
	
	public List<UCS> dijkstraSolve() {
	  	Queue<UCS> toVisit = new LinkedList<UCS>();
	  	HashMap<UCS,UCS> predecessor = new HashMap<UCS,UCS>();
	  	toVisit.add(this);
	  	predecessor.put(this, null);
	  	int cnt=0;
	  	while( toVisit.size() > 0) {
	  		UCS curr = toVisit.remove();
	  		cnt++;
	  		if( curr.isSolved() ) {
	  			System.out.printf("Solution considered %d nodes", cnt);
	  			LinkedList<UCS> solution = new LinkedList<UCS>();
	  			UCS backtrace=curr;
	  			while( backtrace != null ) {
	  				solution.addFirst(backtrace);
	  				backtrace = predecessor.get(backtrace);
	  			}
	  			return solution;
	  		}
	  		for(UCS fp: curr.allAdjacentPuzzles()) {
	  			if( !predecessor.containsKey(fp) ) {
	  				predecessor.put(fp,curr);
	  				toVisit.add(fp);
	  			}
	  		}
	  	}
	  	return null;
	}
	
	private static void showSolution(List<UCS> solution) {
		if (solution != null ) {
			System.out.printf("\nGoal State achieved in %d moves:\n", solution.size());
			System.out.print("The Following is the path for goal state: \n");
			for(UCS temp:solution){
				
			
			for( UCS sp: solution) {
				diff(temp,sp);
				temp.show();sp=temp;break;}
			System.out.printf("\n The Total cost to get the solution is:%d\n", cost);
			
		}} else {
			System.out.println("Sorry, Could not be able to find solution :(");			
		}
	}
	
	
	private static int diff(UCS temp, UCS sp) {
		
		for(int i=0; i<size; i++) {
			//System.out.print("\n");
			for(int j=0; j<size; j++) {
				if (temp.puzzle[i][j] ==0){
					System.out.println(sp.puzzle[i][j]+"\n"+i+"\n"+j);
					System.out.println("\n");
					if(sp.puzzle[i][j]==1 || sp.puzzle[i][j]==2 || sp.puzzle[i][j]==3)
						cost = cost+1;
					if(sp.puzzle[i][j]==4 || sp.puzzle[i][j]==5 || sp.puzzle[i][j]==6)
						cost = cost+2;
					if(sp.puzzle[i][j]==7 || sp.puzzle[i][j]==8)
						cost = cost+3;
				}
					
				}
			}
		return 0;
	}

	public static void main(String[] args) {
		UCS p = new UCS();
		p.shuffle(6);  
		
		System.out.println("Some Random State:");
		p.show();
		
		List<UCS> solution;

		solution = p.dijkstraSolve();
		showSolution(solution);
	}

}