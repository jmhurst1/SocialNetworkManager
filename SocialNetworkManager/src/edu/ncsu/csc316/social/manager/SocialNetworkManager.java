package edu.ncsu.csc316.social.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;

import edu.ncsu.csc316.dsa.graph.Graph;
import edu.ncsu.csc316.dsa.graph.ShortestPathUtil;
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
	
	private Graph<String, Friendship> friendGraph;

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
	
	private Graph<String, Friendship> buildGraph( List<Friendship> friendList ) {
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
			friendGraph.insertEdge(v1, v2, x );
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
		Vertex<String> v1 = null;
		for ( Vertex<String> vert : friendGraph.vertices() ) //Find the vertex for email 1
	        if ( vert.getElement().equals( emailOne ) )
	            v1 = vert;
	    if (v1 == null) //Invalid email
	       return emailOne + " does not exist in the social network.";
	    Vertex<String> v2 = null;
		for ( Vertex<String> vert : friendGraph.vertices() ) //Find the vertex for email 2
	        if ( vert.getElement().equals( emailTwo ) )
	            v2 = vert;
	    if (v2 == null) //Invalid email
	       return emailOne + " does not exist in the social network.";
	    List<String> list = DSAFactory.getIndexedList();
	    Map<Vertex<String>, Integer> shortestPaths = ShortestPathUtil.dijkstra(this.friendGraph, v1);
	    Map<Vertex<String>, Edge<Friendship>> shortestPathsEdges = ShortestPathUtil.shortestPathTree(this.friendGraph, v1, shortestPaths);
	    Iterable<Map.Entry<Graph.Vertex<String>, Graph.Edge<Friendship>>> edges = shortestPathsEdges.entrySet();
	    for (Map.Entry<Graph.Vertex<String>, Graph.Edge<Friendship>> e : edges) {
	    	if (e.getKey().equals(v2)) {
	    		list.addFirst(e.getKey().getElement());
    			Vertex<String> temp = this.friendGraph.opposite(e.getKey(), e.getValue());
	    		while (!temp.equals(v1)) {
	    			list.addFirst(temp.getElement());
	    			temp = this.friendGraph.opposite(temp, shortestPathsEdges.get(temp));
	    		}
	    	}
	    }
	    int degrees = shortestPaths.get(v2);
	    StringBuilder output = new StringBuilder( degrees + " degrees of separation between " + emailOne + " and " + emailTwo + " [\n" );
	    output.append("       " + emailOne + "\n");
	    for( int i = 0; i < list.size(); i++ ) {
	    	output.append("   --> " + list.get(i) + "\n");
	    }
	    output.append("]");
		return output.toString();
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
	    Iterable<Edge<Friendship>> directFriends = friendGraph.outgoingEdges( v );
	    Map<String, Integer> directFriendVertices = DSAFactory.getMap();
	    for (Edge<Friendship> friend : directFriends )
	        directFriendVertices.put(friendGraph.opposite(v, friend).getElement(), 1);
	    for ( Edge<Friendship> friend : directFriends ) { //Go through friends
	        Vertex<String> friendV = friendGraph.opposite(v, friend);
	        Iterable<Edge<Friendship>> friendFriends = friendGraph.outgoingEdges( friendV );
	        for ( Edge<Friendship> secondFriend : friendFriends ) //Go through friends of friend
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