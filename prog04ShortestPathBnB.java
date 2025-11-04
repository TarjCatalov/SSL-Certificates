import java.util.*;

/** shortest path by backtracking
 * @author M A Hakim Newton
 */

/** graph node
 */
class GraphNode
{
	// the node id
	public int Id;

	/** constructor
	 * @param nodeId the node id
	 */
	GraphNode(int nodeId)
	{
		Id = nodeId;
	}
}

/** graph edge
 */
class GraphEdge
{
	public int source;	// source node id
	public int target;	// target node id
	public int weight;	// edge weight

	/** constructor
	 */
	GraphEdge(int sourceId, int targetId, int edgeWeight)
	{
		source = sourceId;
		target = targetId;
		weight = edgeWeight;
	}
}

/** graph: a list of nodes with adjacency lists
 */
class Graph
{
	// list of nodes
	public List<GraphNode> nodeList;

	// list of adjacency lists
	public List<List<GraphEdge>> adjList;

	/** constructor for unweighted graph
	 * @param nodeCount the number of nodes, indexes 0 to nodeCount - 1
	 * @param adjMatrix the adjacency matrix for the graph  
	 */ 
	public Graph(int nodeCount, int [][] adjMatrix)
	{
		nodeList = new ArrayList<GraphNode>();
    adjList = new ArrayList<List<GraphEdge>>();

		for (int id = 0; id < nodeCount; id++)
		{
			nodeList.add(new GraphNode(id));	
    	adjList.add(new ArrayList<GraphEdge>());
		}

		for (int i = 0; i < nodeCount; i++)
			for (int j = 0; j < nodeCount; j++)
				if (adjMatrix[i][j] > 0)
					adjList.get(i).add(new GraphEdge(i, j, adjMatrix[i][j]));	
	}
}

/** tree node
 */
class TreeNode
{
	// corresponding graph node 
	public GraphNode graphNode;

	// the parent tree node in the search tree
	public TreeNode parentTreeNode;

	/** constructor
	 * @param node the graph node
	 * @param parent the parent tree node, could be null
	 */
	TreeNode(GraphNode node, TreeNode parent)
	{
		graphNode = node;
		parentTreeNode = parent; 
	}
}

/** an interface to generalise stack and queues
 * stacks and queues will be specific implementations
 */
interface TreeNodeList 
{ 
	public boolean insert(TreeNode node);
	public TreeNode remove();
	public boolean contains(TreeNode node);
	public boolean contains(GraphNode node);
	public String toString();
	public boolean empty();
	public int size();
	public void clear();
}

class StackTreeNodeList implements TreeNodeList 
{ 
	private Stack<TreeNode> nodes = new Stack<TreeNode>();

	@Override
	public boolean insert(TreeNode node) 
	{
		nodes.push(node);
		return true;
	}

	@Override
	public TreeNode remove() 
	{
		return nodes.pop();
	}

	@Override
	public boolean contains(TreeNode node) 
	{
		return nodes.contains(node);
	}

	@Override 
	public boolean contains(GraphNode gnode)
	{
		for(TreeNode tnode : nodes)
			if (tnode.graphNode.Id == gnode.Id)
				return true;
		return false;
	}

	@Override
	public String toString()
	{
		String output = "";
		for(int i = 0; i < nodes.size(); i++)
			output += " " + nodes.get(i).graphNode.Id;
		return output;
	}

	@Override
	public boolean empty() 
	{
		return nodes.empty();
	}

	@Override
	public int size()
	{
		return nodes.size();
	}

	@Override
	public void clear()
	{
		nodes.clear();
	}
}

class QueueTreeNodeList implements TreeNodeList 
{ 
  private Queue<TreeNode> nodes = new LinkedList<TreeNode>();

	@Override
	public boolean insert(TreeNode node) 
	{
		return nodes.offer(node);
	}

	@Override
	public TreeNode remove() 
	{
		return nodes.poll();
	}

	@Override
	public boolean contains(TreeNode node) 
	{
		return nodes.contains(node);
	}

	@Override 
	public boolean contains(GraphNode gnode)
	{
		for(TreeNode tnode : nodes)
			if (tnode.graphNode.Id == gnode.Id)
				return true;
		return false;
	}

 	@Override
	public String toString(){
			String output = "";
			TreeNode tnode;
			for (int i = 0; i < nodes.size(); i++){
					tnode = remove();
					output += " " + tnode.graphNode.Id;
					insert(tnode);
			}
			return output;
	}

	@Override
	public boolean empty() 
	{
		return nodes.isEmpty();
	}

	@Override
	public int size()
	{
		return nodes.size();
	}
	
	@Override
	public void clear()
	{
		nodes.clear();
	}
}

/** Path Lengths
 */
class PathLength
{
	public int curr;
	public int min;

	PathLength(int c, int m)
	{
		curr = c;
		min = m;
	}
}

/** Path Explorer
 */
class PathExplorer
{
 /** 
  * print path recursively
  */
	public static void copyBestPath(TreeNode v, QueueTreeNodeList Q)
	{
		if (v == null) return;
		copyBestPath(v.parentTreeNode, Q);
		Q.insert(v);
	}

 /**
	* recursive backtracking shortest path 
	* @param G the given graph
	* @param P the current path
	* @param B the best path so far
	* @param currPathLength the length of the current path
	* @param bestPathLength the length of the best path
	* @param s the source tree node
	* @param d the destination graph node
	*/
	public static void searchRecurSub(Graph G, StackTreeNodeList P, QueueTreeNodeList B, 
		PathLength L, int edgeLength, TreeNode s, GraphNode d) 
	{
		P.insert(s);
		L.curr = L.curr + edgeLength;

		if (s.graphNode.Id == d.Id)	// destination found
		{
			if (L.curr < L.min) // a better path found, so keep a copy
			{
				L.min = L.curr;
				B.clear();
				copyBestPath(s, B);
			}
		}

		List<GraphEdge> neigh = G.adjList.get(s.graphNode.Id);
		for (int i = 0; i < neigh.size(); i++) 
		{
			GraphNode w = G.nodeList.get(neigh.get(i).target);
			if (!P.contains(w))
			{
				int tempEdgeLength = neigh.get(i).weight;
				if (L.curr + tempEdgeLength < L.min)
					searchRecurSub(G, P, B, L, tempEdgeLength, new TreeNode(w, s), d);
			}
		}
		
		L.curr = L.curr - edgeLength;
		P.remove();
	}

	/*
	 * recursive backtracking shortest path
	 * @param G the given graph
	 * @param s the source graph node
	 * @param d the destination graph node
	 */
	public static void searchRecurMain(Graph G, GraphNode s, GraphNode d) 
	{	
		// we do not need any open list for recursive traversal.
		StackTreeNodeList P = new StackTreeNodeList();	// current path		
		QueueTreeNodeList B = new QueueTreeNodeList();	// best path so far 
		PathLength L = new PathLength(0, 9999999);

		searchRecurSub(G, P, B, L, 0, new TreeNode(s,null), d);    
		System.out.println("min path length: " + L.min);   
		System.out.print("min path: ");
		while(!B.empty())
		{
			TreeNode t = B.remove();
			System.out.print(" " + t.graphNode.Id);
		}
		System.out.println();
	}
}

/** set and and run
 */
class prog04ShortestPathBnB
{
	/** main function
	 * @param args the array of string arguments
	 */
	public static void main(String [] args)
	{
		/*
		 *             0  
		 *     2    /     \  1
		 *       /           \
		 *    /                \ 
		 *  1 -------\    /----- 2
		 *  |         \  /       |
		 *  | 2   4    /\   2    | 2
		 *  3 -------/   \------ 4
		 *     \               /
		 *        \         / 
		 *      3    \   /   2
     *             5
		 */           
 		
		int nodeCount = 6; // number of nodes
		int[][] adjMatrix	= { // adjacency matrix
													{ 0, 2, 1, 0, 0, 0 },
													{ 2, 0, 0, 2, 2, 0 },
													{ 1, 0, 0, 4, 2, 0 },
													{ 0, 2, 4, 0, 0, 3 },
													{ 0, 2, 2, 0, 0, 2 },
													{ 0, 0, 0, 3, 2, 0 }
												};

		Graph G = new Graph(nodeCount, adjMatrix);

		PathExplorer.searchRecurMain(G, G.nodeList.get(0), G.nodeList.get(5));
 }
}
