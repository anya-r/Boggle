import java.util.*;
import java.io.File; 
import java.io.FileNotFoundException; 

public class BoggleSolver
{
	HashSet<String> dictionary;

	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A - Z.)
	public BoggleSolver(String dictionaryName)
	{
		dictionary = new HashSet<>();
		try 
		{
			File file = new File(dictionaryName);
			Scanner scanner = new Scanner(file);
			String word = "";
			while (scanner.hasNext())
			{
				word = scanner.nextLine();
				dictionary.add(word.toUpperCase());
			}
			scanner.close();
        }
		
		catch (FileNotFoundException m)
        {
            System.out.println("error: file not found");
        }
		//System.out.println(dictionary);
    }

	//Returns the set of all valid words in the given Boggle board, as an Iterable object
	public Iterable<String> getAllValidWords(BoggleBoard board)
	{
		HashSet<String> set = new HashSet<>();
		boolean[][] explored = new boolean[board.rows()][board.cols()];

		for (int r = 0; r < board.rows(); r++)
		{
			for (int c = 0; c < board.cols(); c++)
			{
				wordsHelper(board, r, c, "", explored, set);
			}
		}
		return set;
		
		

	}
	
	private void wordsHelper(BoggleBoard board, int r, int c, String word, boolean[][] explored, HashSet<String> set)
	{
		if (explored[r][c] == true)
			return;
		
		//add letter
		word += board.getLetter(r, c);
		if (board.getLetter(r, c) == 'Q')
			word += "U";

		//check if in dictionary
		if (word.length() > 2 && dictionary.contains(word.toUpperCase()))
			set.add(word);

		explored[r][c] = true;
		//find all adjavent nodes
		//if (explored[r][c])
		for (int i = r - 1; i <= r + 1; i++)
		{
			for (int j = c - 1; j <= c + 1; j++)
			{
				//see if node is out of bounds
				if (i >= 0 && j >= 0 && i < board.rows() && j < board.cols())
				{
					//make sure its not counting itself as its own neighbor
					// if (i != r && j != c)
					// {
						wordsHelper(board, i, j, word, explored, set);
					//}
					//explored[r][c] = false;
				}
			}
		}
		explored[r][c] = false;

		
		

	}

	// Returns the score of the given word if it is in the dictionary, zero otherwise.
	// (You can assume the word contains only the uppercase letters A - Z.)
	public int scoreOf(String word)
	{
		int length = word.length();
		if (dictionary.contains(word.toUpperCase()) && length > 0)
		{			
			if (length <= 2)
				return 0;
			else if (length <= 4)
				return 1;
			else if (length == 5)
				return 2;
			else if (length == 6)
				return 3;
			else if (length == 7)
				return 5;
			else
				return 11;
		}
		return 0;
	}

	public static void main(String[] args) 
	{
		System.out.println("WORKING");

		final String PATH   = "./data/";
		BoggleBoard  board  = new BoggleBoard(PATH + "board-q.txt");
		BoggleSolver solver = new BoggleSolver(PATH + "dictionary-algs4.txt");

		int totalPoints = 0;

		for (String s : solver.getAllValidWords(board)) {
			System.out.println(s + ", points = " + solver.scoreOf(s));
			totalPoints += solver.scoreOf(s);
		}

		System.out.println("Score = " + totalPoints); //should print 84

		new BoggleGame(4, 4);
	}

	//test review problems kfbksndms
	// public int sumValues(int[] nums)
	// {
	// 	return sumHelper(nums, 0);
	// }
	// private int sumHelper(int[] nums, int i)
	// {
	// 	if (i == nums.length - 1)
	// 		return nums[i];
	// 	return nums[i] + sumHelper(nums, i + 1);
	// }

	// public int minVal(int[] nums)
	// {
	// 	return minValHelper(nums, 1, nums[0]);
	// }
	// private int minValHelper(int[] nums, int i, int min)
	// {
	// 	if (i >= nums.length)
	// 		return min;
	// 	if (nums[i] <= min)
	// 	{
	// 		return minValHelper(nums, i + 1, nums[i]);
	// 	}
	// 	return minValHelper(nums, i + 1, min);
	// }

	// //idk if this one will work fskjf
	// public String reverseStr(String str)
	// {
	// 	if (str.length() == 0 || str.length() == 1)
	// 		return str;
	// 	return reverseStr(str.substring(1)) + str.substring(0, 1);
	// }

}
