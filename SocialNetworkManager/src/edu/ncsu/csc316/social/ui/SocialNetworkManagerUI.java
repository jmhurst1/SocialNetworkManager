/**
 * 
 */
package edu.ncsu.csc316.social.ui;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

import edu.ncsu.csc316.social.manager.SocialNetworkManager;


/**
 * Runs the UI and allows the user to get the degrees of separation
 * or get suggestions for a friend after providing an input file of
 * a graph of friends.
 * @author Jason
 *
 */
public class SocialNetworkManagerUI {

	/**
	 * Runs the UI and allows the user to get the degrees of separation 
	 * or get suggestions for a friend after providing an input file of
	 * a graph of friends.
	 * @param args arguments provided to the program
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to SocialNetworkManager");
		System.out.println("A program that helps find suggestions and calculate degrees of seperation");
		System.out.println("Enter an input file or press q to quit");
		String userInput = "";
		SocialNetworkManager manager = null;
		while (userInput.equals("")) {
			System.out.print("Enter file name: ");
			userInput = sc.nextLine();

			if (userInput.equalsIgnoreCase("Q")) {
				System.exit(0);
			}

			try {
				manager = new SocialNetworkManager( userInput );
			} catch (FileNotFoundException | ParseException e) {
				userInput = "";
				System.out.println("Invalid file");
			}
		}

		System.out.println();
		System.out.println();
		
		userInput = "";
		while( !( userInput.equals("s") || userInput.equals("d") ) ) {
			System.out.println("Enter 's' for suggestions");
			System.out.println("Enter 'd' for degrees of separation");
			System.out.println("Enter 'q' to quit");
			userInput = sc.nextLine();
			if( userInput.equals("q"))
				System.exit(0);
		}
		
		System.out.println();
		System.out.println();

		if ( userInput.equals( "s" ) ) { //Friend Suggestions flow
			System.out.println("Enter in email for suggestions");
			userInput = sc.nextLine();
			System.out.println(manager.getSuggestionReport(userInput));
		} else { //Degrees of Separation flow
			System.out.println("Enter first email");
			String email1 = sc.nextLine();
			System.out.println("Enter second email");
			String email2 = sc.nextLine();
			System.out.println(manager.getDegreesReport(email1, email2));
		}
		sc.close();
	}

}
