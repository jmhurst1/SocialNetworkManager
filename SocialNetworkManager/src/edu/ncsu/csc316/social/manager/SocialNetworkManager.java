package edu.ncsu.csc316.social.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;

import edu.ncsu.csc316.dsa.graph.Graph;
import edu.ncsu.csc316.dsa.graph.Graph.Edge;
import edu.ncsu.csc316.dsa.graph.Graph.Vertex;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.sorter.Sorter;
import edu.ncsu.csc316.social.data.Friendship;
import edu.ncsu.csc316.social.factory.DSAFactory;
import edu.ncsu.csc316.social.io.TextFileIO;

/**
 * SocialNetworkManager manages details related to social networks, including
 * calculating degrees of separation and suggesting friends.
 * 
 * @author Dr. King
 * @author Jason Hurst
 * @author <YOUR NAME HERE>
 *
 */
public class SocialNetworkManager {
	
	private Graph<String, Integer> friendGraph;

	/**
	 * Initializes the SocialNetworkManager and builds the graph
	 * 
	 * @param pathToFriendshipFile the path to the input friendship information
	 * @throws FileNotFoundException if the input file is not found
	 * @throws ParseException        if the input file contains an unparsable date
	 */
	public SocialNetworkManager(String pathToFriendshipFile) throws FileNotFoundException, ParseException {
		friendGraph = buildGraph( TextFileIO.readFriendships( pathToFriendshipFile ) );
	}
	
	private Graph<String, Integer> buildGraph( List<Friendship> friendList ) {
		friendGraph = DSAFactory.getUndirectedGraph();
		Map<String, Vertex<String>> vertexTable = DSAFactory.getMap();
		for(int i = 0; i < friendList.size(); i++) {
			Friendship x = friendList.get(i);
			Vertex<String> v1;
			Vertex<String> v2;
			if( vertexTable.get(x.getEmail1()) == null) {
				v1 = friendGraph.insertVertex( x.getEmail1() );
				vertexTable.put(x.getEmail1(), v1);
			} else {
				v1 = vertexTable.get(x.getEmail1());
			}
			if( vertexTable.get(x.getEmail2()) == null) {
				v2 = friendGraph.insertVertex( x.getEmail2() );
				vertexTable.put(x.getEmail2(), v2);
			} else {
				v2 = vertexTable.get(x.getEmail2());
			}
			friendGraph.insertEdge(v1, v2, x.getWeight() );
		}
		
		return friendGraph;
	}

	/**
	 * Returns a report of the degrees of separation between the two provided email
	 * addresses
	 * 
	 * @param emailOne the email address of the first user
	 * @param emailTwo the email address of the second user
	 * @return the report of the degrees of separation between the two provided
	 *         email addresses
	 */
	public String getDegreesReport(String emailOne, String emailTwo) {
		return null;
	}

	/**
	 * Returns a report of the friend suggestions for the provided email address
	 * 
	 * @param email the email address for which to provide friend suggestions
	 * @return a report of the friend suggestions for the provided email address
	 */
	public String getSuggestionReport(String email) {
		Map<String, Integer> suggestedFriends = DSAFactory.getMap();
	    Vertex<String> v = null;
	    for ( Vertex<String> vert : friendGraph.vertices() ) //Find the vertex for the given email
	        if ( vert.getElement().equals( email ) )
	            v = vert;
	    if (v == null) //Invalid email
	       return email + " does not exist in the social network.";
	    Iterable<Edge<Integer>> directFriends = friendGraph.outgoingEdges( v );
	    Map<String, Integer> directFriendVertices = DSAFactory.getMap();
	    for (Edge<Integer> friend : directFriends )
	        directFriendVertices.put(friendGraph.opposite(v, friend).getElement(), 1);
	    for ( Edge<Integer> friend : directFriends ) { //Go through friends
	        Vertex<String> friendV = friendGraph.opposite(v, friend);
	        Iterable<Edge<Integer>> friendFriends = friendGraph.outgoingEdges( friendV );
	        for ( Edge<Integer> secondFriend : friendFriends ) //Go through friends of friend
	            if ( !( v.equals( friendGraph.opposite(friendV, secondFriend) ) ||
	                directFriendVertices.get( friendGraph.opposite(friendV, secondFriend ).getElement()) != null ||
	                suggestedFriends.get( friendGraph.opposite(friendV, secondFriend).getElement()) != null ) )  //If the found vertex is a valid suggestion...
	                suggestedFriends.put( friendGraph.opposite(friendV, secondFriend).getElement(), 1); //Add the valid suggestion to the list
	    }
	    Sorter< String > sorter = DSAFactory.getComparisonSorter();
	    String sortedArray[] = new String[suggestedFriends.size()];
	    int count = 0;
	    for( String friendEmail : suggestedFriends ) {
	    	sortedArray[count++] = friendEmail;
	    }
	    sorter.sort(sortedArray);
	    if ( count == 0 )
	    	return "No friend suggestions for " + email;
	    StringBuilder output = new StringBuilder( "Friend Suggestions for " + email + " [\n" );
	    for( int i = 0; i < count; i++ ) {
	    	output.append("   " + sortedArray[i] + "\n");
	    }
	    output.append("]");
		return output.toString();
	}
}