import java.util.*;
import java.io.*;

/**
 * Given a 2D board and a word, find if the word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cell, where adjacent cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * Input The board to be used may be hard coded as:
 *   [
 *   [ABCE],
 *   [SFCS],
 *   [ADEE]
 *   ]
 *
 * Your program should accept as its first argument a path to a filename. Each line in this file contains a word.
 *   e.g.
 *   ASADB
 *   ABCCED
 *   ABCF
 *
 * Output
 *   Print out True if the word exists in the board, False otherwise.
 *   e.g
 *   False
 *   True
 *   False
 *
 * @author Victor Ho
 * @version 1.0
 * @since   2017-04-14
*/
class WordSearch
{
  // The Board
  private final static char[][] board = {
  {'A', 'B', 'C', 'E' },
  {'S', 'F', 'C', 'S' },
  {'A', 'D', 'E', 'E' }
  };

  public static void main(String[] args){ // First argument is the filename
    // Check argument.
    if ( args.length == 0 ){
      System.err.println("Proper Usage is: java WordSearch <filename>");
      System.exit(0);
    }

    String[] words = null;

    // 1. Read file and put it to String[] words.
    try{
      FileInputStream fstream = new FileInputStream(args[0]);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine;
      List<String> strings = new ArrayList<>();
      System.out.println ("Words from file:" + args[0]);
      System.out.println ("=========================================================");
      while ((strLine = br.readLine()) != null)   { //Read File Line By Line
        System.out.println (strLine);
        strings.add(strLine);
      }
      System.out.println ("=========================================================");
      in.close();
      words = strings.toArray(new String[strings.size()]);
    }catch (Exception e){
      System.err.println("Error: " + e.getMessage());
      System.exit(0);
    }

    // 2. Search the words from the board.
    new WordSearch().searchWords(board, words);

  }

  /**
   * Search by dfs algorithm.
   *
   * @param  board  2D array of the board.
   * @param  words  array of input words.
   * @exception IllegalArgumentException if either the input board or words is null.
   * @return        void
   */
  public void searchWords(char[][] board, String[] words) {

    int m=board.length;
    int n=board[0].length;

    boolean[][] visited = null; // no duplicate movement for each of words.
    boolean[][] marked = new boolean[m][n];  // The same letter cell may not be used more than once.

    boolean found = false;
    for(String word: words){
      found = false;
      visited = new boolean[m][n];
      for(int i=0; i<m && !found; i++){
        for(int j=0; j<n && !found; j++){
           if ( dfs(board, word, "", visited, marked, i, j) ){
             marked[i][j]= true;
             found = true;
           }
        }
      }
      if ( found )
        System.out.println("True");
      else
        System.out.println("False");
    }
  }

  public boolean dfs(char[][] board, String word, String str, boolean[][] visited, boolean[][] marked, int i, int j){

    int m=board.length;
    int n=board[0].length;

    if(i<0 || j<0 || i>=m || j>=n || visited[i][j] || marked[i][j] ){
      return false;
    }
    str = str + board[i][j];
    if(!word.startsWith(str)){
      return false;
    }

    if ( word.equals(str) ){
      return true;
    }

    visited[i][j]=true;
    if ( dfs(board, word, str, visited, marked, i-1, j) ){
      marked[i-1][j] = true;
      //System.out.println("FOUND:i:" + (i-1) + ",j:" + j);
      return true;
    }
    if ( dfs(board, word, str, visited, marked, i+1, j)  ){
      marked[i+1][j] = true;
      //System.out.println("FOUND:i:" + (i+1) + ",j:" + j);
      return true;
    }
    if ( dfs(board, word, str, visited, marked, i, j-1)  ){
      marked[i][j-1] = true;
      //System.out.println("FOUND:i:" + i + ",j:" + (j-1));
      return true;
    }
    if ( dfs(board,word, str, visited, marked, i, j+1) ){
      marked[i][j+1] = true;
      //System.out.println("FOUND:i:" + i + ",j:" + (j+1));
      return true;
    }
    visited[i][j]=false;
    return false;
  }

}
