******* CSC 172 - Project 3: Street Mapping *******

Authors:
+ Jassem Toumi
+ Moez Boussarsar

==== Program Description ====
The code defines a class "graphDrawer" that creates a graphical representation of a graph (standardized text input), using Swing GUI components. It has two panels, one for drawing the graph and another for controlling the search for the shortest path between two nodes using Dijkstra's algorithm. The 'GraphPanel' class draws the graph on the panel, with scaling and highlighting of the shortest path. The
"ControlPanel" class provides text fields for entering the starting and ending nodes, and a search button that triggers a search for the shortest path, which is then highlighted in the "GraphPanel". This search button is our own idea and implementation to make the program deliver a nicer user experience (so that you can find different short paths on the same graph without having to re-run the program everytime). 

==== Project Structure ====
Classes:
- Node: This class implements the "Comparable" interface. It has instance variables for the name, latitude, and longitude of a node, as well as an ArrayList of adjacent nodes. The class has methods for calculating the distance between nodes using the Haversine formula, getting the name of a node, and comparing nodes based on their distance. The purpose of this class is to represent a node in a graph with its corresponding properties and methods for processing and analyzing it.
- Edge:
The "Edge" class represents a relationship between two nodes in a graph or a map. It has instance variables for the source and destination nodes, weight, a boolean for highlighting, and a name. Its constructor initializes these variables.
- Graph:
The "Graph" class represents a graph composed of nodes and edges. It has instance variables for a HashMap of nodes, an ArrayList of edges, and a name for the graph. The class has methods for adding a node to the HashMap, adding an edge to the ArrayList, and getting a node by ID from the HashMap.
- GraphDrawer:
The "graphDrawer" class is a JFrame-based class that displays a graph with a graph panel and a control panel. The purpose of this class is to provide a graphical interface for visualizing and interacting with our desired graphs.
- GraphPanel:
The GraphPanel class extends the JPanel class and is responsible for drawing the graph. The paintComponent method overrides the painComponent method of the JPanel class and draws the edges. It calculates the max and min edge lengths, and the max and min latitudes and longitudes of the nodes in the graph. It then scales the graph and draws each edge. If an edge is highlighted, it is drawn in red. The higlightShortestPath method highlights the edges that are part of the shortest path between two nodes and repaints the panel.
- ControlPanel:
The ControlPanel class is a panel with text fields for the start and end nodes, and a search button. It has a reference to a GraphPanel object, which it uses to highlight the shortest path between the start and end nodes when the search button is clicked. The search method finds the start and end nodes in the GraphPanel object, runs Dijkstra's algorithm to find the shortest path between them, and calls the highlightShortestPath method of the GraphPanel object to highlight the path.

==== Challenges ====
(Nodes arraylist)
We started the graph implementation by storing the node objects in an ArrayList which took a lot of running time and sometimes would stop for bigger maps. Then, we changed to a HashMap storing method which made it much faster. 
(Scanner / BufferedReader)
We changed from a Scanner input reading to a BufferedReader object which has also optimized running time. 
(Scaling in the graphics)
We had a hard time finding the optimal equation for showing all graphs with the predefined dimensions of the frame, but with a little bit of trial and error we were able to fit all the edges of the graphs within the frame. 
(Reading from the command line) 
We had some trouble implementing both a command line and a graphics solution (input field+button) for passing the nodes for the shortest path.


==== Runtime Analysis ====
Dijakstra's Algorithm runs in O(V+E) in worst case scenario. Where V is the number of vertices and E is the number of Edges. 
Reading the input file using scanner works faster for smaller maps but for larger maps the bufferedreader works faster. 
The storing/insertion of vertices using an ArrayList caused the running time to increase remarkably, on average it takes O(n). While a hashMap implementation takes only O(1) causing the running time to decrease by significant amount. 

==== Program Running ====
> javac StreetMap.java
> java StreetMap [file_name] [show/directions] [start_intersection] [end_intersection]

Example:
> javac StreetMap.java
> java StreetMap ur.txt show directions HOYT SUEB

==== Citations ====
1: https://www.geeksforgeeks.org/haversine-formula-to-find-distance-between-two-points-on-a-sphere/
