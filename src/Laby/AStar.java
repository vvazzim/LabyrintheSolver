package Laby;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import Laby.WeightedGraph.Graph;

public class AStar {
	  private PriorityQueue<WeightedGraph.Vertex> openList;
	  private List<WeightedGraph.Vertex> closedList;
	  private Map<WeightedGraph.Vertex, WeightedGraph.Vertex> cameFrom;
	  private Map<WeightedGraph.Vertex, Double> gScore;
	  private Map<WeightedGraph.Vertex, Double> fScore;
	  private WeightedGraph.Vertex start, goal;

	  public AStar(WeightedGraph.Vertex start, WeightedGraph.Vertex goal) {
	      this.openList = new PriorityQueue<>();
	      this.closedList = new ArrayList<>();
	      this.cameFrom = new HashMap<>();
	      this.gScore = new HashMap<>();
	      this.fScore = new HashMap<>();
	      this.start = start;
	      this.goal = goal;
	  }

	  public List<WeightedGraph.Vertex> solve() {
	      openList.add(start);
	      gScore.put(start, 0.0);
	      fScore.put(start, heuristicCostEstimate(start));
	      while (!openList.isEmpty()) {
	          WeightedGraph.Vertex current = openList.poll();
	          if (current.equals(goal)) {
	              return reconstructPath();
	          }
	          closedList.add(current);
	          for (WeightedGraph.Edge edge : getEdges(current)) {
	        	   WeightedGraph.Vertex next = edge.destination;
	              if (closedList.contains(next)) {
	                continue;
	              }
	              double tentativeGScore = gScore.get(current) + edge.weight;
	              if (!openList.contains(next)) {
	                openList.add(next);
	              } else if (tentativeGScore >= gScore.get(next)) {
	                continue;
	              }
	              cameFrom.put(next, current);
	              gScore.put(next, tentativeGScore);
	              fScore.put(next, tentativeGScore + heuristicCostEstimate(next));
	          }
	      }
	      return null; // No solution
	  }

	  private List<WeightedGraph.Vertex> reconstructPath() {
	      List<WeightedGraph.Vertex> path = new ArrayList<>();
	      WeightedGraph.Vertex current = goal;
	      while (current != null) {
	          path.add(current);
	          current = cameFrom.get(current);
	      }
	      Collections.reverse(path);
	      return path;
	  }

	  private double heuristicCostEstimate(WeightedGraph.Vertex node) {
		   // Calculate the Manhattan distance between the current node and the goal node
		   int dx = Math.abs(node.num % Graph.num_v - goal.num % Graph.num_v);
		   int dy = Math.abs(node.num / Graph.num_v - goal.num / Graph.num_v);

		   // Calculate the Manhattan distance between the current node and the fire
		   int fireDx = Math.abs(node.firePosition % Graph.num_v - goal.firePosition % Graph.num_v);
		   int fireDy = Math.abs(node.firePosition / Graph.num_v - goal.firePosition / Graph.num_v);

		   // Add the fire distance to the goal distance
		   return dx + dy + fireDx + fireDy;
		}



	  private List<WeightedGraph.Edge> getEdges(WeightedGraph.Vertex node) {
	      return node.adjacencylist;
	  }
	}

