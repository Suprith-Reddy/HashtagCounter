import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class hashtagcounter {
    public static void main(String[] args) throws IOException {
        // Hashtable has hashTagName and node pointer
        Hashtable<String, FibonacciHeapNode> hashTags = new Hashtable<>();
        // Instance of Max Fibonacci Heap class
        MaxFibonacciHeap maxFibonacciHeap = new MaxFibonacciHeap();
        // Dynamic Array List to store nodes removed from the heap
        ArrayList<FibonacciHeapNode> removedNodes = new ArrayList<>();
        // Hash Tag Entry Counter initialization used as TIE BREAKER
        int hashTagEntryCounter = 1;

        // Initializing and reading I/O calls
        String inputFileName = args[0];
        String outputFileName;
        PrintWriter writer = null;

        // If output file name is given in arguments then assign it to a writer object
        if(args.length == 2){
            outputFileName = args[1];
            // Creates empty output file yet to be written if it's not in the directory
            // else clears the existing file yet to be written
            writer = new PrintWriter(outputFileName);
            // writer = new PrintWriter(outputFileName, StandardCharsets.UTF_8); // Thunder doesn't support charset constructor
        }

        FileReader fileReader = new FileReader(inputFileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // Reading input line by line from the given file
        String input = bufferedReader.readLine();

        // Reads input until the string is "stop"
        while (!input.equalsIgnoreCase("stop")) {
            String[] tempArray = input.split(" ");
            // If '#' is the starting character, perform increaseKey or insertNode on condition
            if (input.startsWith("#")) {
                if (hashTags.containsKey(tempArray[0])) {
                    // Increase the key value if it is in hashTags
                    maxFibonacciHeap.increaseKey(hashTags.get(tempArray[0]), Integer.parseInt(tempArray[1]));
                }
                else {
                    // else set the value and insert to hashTags table
                    FibonacciHeapNode node = new FibonacciHeapNode(Integer.parseInt(tempArray[1]));
                    node.setHashTagName(tempArray[0]);
                    node.setHashTagEntry(hashTagEntryCounter); // TIE BREAKER
                    hashTagEntryCounter++;
                    hashTags.put(tempArray[0], node);
                    maxFibonacciHeap.insertNode(node);
                }
            }
            // else remove the nodes based on the no. of hashTags to be displayed
            // and output the result
            // then re-insert the deleted nodes back into the heap
            else {
                int n = Integer.parseInt(input);
                String result = "";

                while (n > 0) {
                    // Remove the max node from heap
                    FibonacciHeapNode node = maxFibonacciHeap.extractMaximumNode();
                    if (n == 1) {
                        result = result.concat(node.getHashTagName().substring(1));
                    }
                    else {
                        result = result.concat(node.getHashTagName().substring(1) + ",");
                    }
                    // Adds removed max nodes to the dynamic array
                    removedNodes.add(node);
                    hashTags.put(node.getHashTagName(), node);
                    // Repeat until n == 0
                    n--;
                }
                if(writer != null){
                    // Write the result string to output_file
                    writer.println(result);
                }else{
                    // Write the result string to stdout(screen)
                    System.out.println(result);
                }

                // The removed nodes are re-inserted into the heap
                for (FibonacciHeapNode rn : removedNodes) {
                    rn.setChildPointer(null);
                    rn.setParentPointer(null);
                    rn.setLeft(null);
                    rn.setRight(null);
                    rn.setChildCut(false);
                    rn.setDegree(0);

                    maxFibonacciHeap.insertNode(rn);
                }
                removedNodes.clear();

            }
            input = bufferedReader.readLine();
        }
        // Closing all I/O calls
        if(writer != null)
            writer.close();
        bufferedReader.close();
        fileReader.close();
    }
}

/**
 * Class/Model for the Node Structure
 */
class FibonacciHeapNode {
    //Node Data
    private int data;
    //Child Pointer
    private FibonacciHeapNode childPointer;
    //Parent Pointer
    private FibonacciHeapNode parentPointer;
    //Left Sibling Pointer
    private FibonacciHeapNode left;
    //Right Sibling Pointer
    private FibonacciHeapNode right;
    //ChildCut Indicator (True/False)
    private boolean childCut;
    //Node's Degree
    private int degree;
    //HashTag Name
    private String hashTagName;
    //HasTag Entry Number
    private int hashTagEntry;

    /*
     * Getter and Setter Methods for the above fields:
     * @return is a getter method
     * @param <parameterField> is a setter method
     */

    /**
     * @return : data
     */
    public int getData() {
        return data;
    }
    /**
     * @param data for assigning
     */
    public void setData(int data) {
        this.data = data;
    }
    /**
     * @return : childPointer
     */
    public FibonacciHeapNode getChildPointer() {
        return childPointer;
    }
    /**
     * @param childPointer for assigning
     */
    public void setChildPointer(FibonacciHeapNode childPointer) {
        this.childPointer = childPointer;
    }
    /**
     * @return : parentPointer
     */
    public FibonacciHeapNode getParentPointer() {
        return parentPointer;
    }
    /**
     * @param parentPointer for assigning
     */
    public void setParentPointer(FibonacciHeapNode parentPointer) {
        this.parentPointer = parentPointer;
    }
    /**
     * @return : left
     */
    public FibonacciHeapNode getLeft() {
        return left;
    }
    /**
     * @param left for assigning
     */
    public void setLeft(FibonacciHeapNode left) {
        this.left = left;
    }
    /**
     * @return : right
     */
    public FibonacciHeapNode getRight() {
        return right;
    }
    /**
     * @param right for assigning
     */
    public void setRight(FibonacciHeapNode right) {
        this.right = right;
    }
    /**
     * @return : childCut
     */
    public boolean getChildCut() {
        return childCut;
    }
    /**
     * @param childCut for assigning
     */
    public void setChildCut(boolean childCut) {
        this.childCut = childCut;
    }
    /**
     * @return : degree
     */
    public int getDegree() {
        return degree;
    }
    /**
     * @param degree for assigning
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }
    /**
     * @return : hashTagName
     */
    public String getHashTagName() {
        return hashTagName;
    }
    /**
     * @param hashTagName for assigning
     */
    public void setHashTagName(String hashTagName) {
        this.hashTagName = hashTagName;
    }
    /**
     * @return : hashTagEntry
     */
    public int getHashTagEntry() {
        return hashTagEntry;
    }
    /**
     * @param hashTagEntry for assigning
     */
    public void setHashTagEntry(int hashTagEntry) {
        this.hashTagEntry = hashTagEntry;
    }
    /**
     * Parameterized constructor which creates a Fibonacci Node class object
     * by using the data
     * @param data for assigning
     */
    public FibonacciHeapNode(int data) {
        super(); //Inherits the method from parent class
        this.data = data;
        this.degree = 0;
        this.childCut = false;
    }
}

/**
 * Max - Fibonacci Heap class which has following fields and methods:
 * fields:-
 * maxPointer
 * methods:-
 * insertNode [insertNodeHelper1 & insertNodeHelper2], extractMaximumNode,
 * compareAndMeld [compareAndMeldHelper], increaseKey, and cascadingCut
 */
class MaxFibonacciHeap {
    // Maximum Node Pointer
    private FibonacciHeapNode maxPointer;

    /**
     * Inserts a node into fibonacci heap and
     * also ensures maximum key/pair is always
     * the first key/pair in the heap
     * It uses two helper methods namely
     * insertNodeHelper1 and insertNodeHelper2
     * @param newNode for assigning
     */
    public void insertNode(FibonacciHeapNode newNode) {
        if (maxPointer != null) {
            // Update the maxPointer, if the newly inserted node data value is greater than
            // the maxPointer's data value or if equal check for the hashtag entry number and
            // update accordingly
            if (newNode.getData() > maxPointer.getData()) {
                insertNodeHelper1(newNode);
            }
            else if (newNode.getData() == maxPointer.getData()){
                if(newNode.getHashTagEntry() < maxPointer.getHashTagEntry()){
                    insertNodeHelper1(newNode);
                }
                else{
                    insertNodeHelper2(newNode);
                }
            }
            else {
                insertNodeHelper2(newNode);
            }
        }
        else{
            maxPointer = newNode; // First node insertion into the heap
        }
    }
    private void insertNodeHelper1(FibonacciHeapNode newNode){
        newNode.setRight(maxPointer);
        maxPointer.setLeft(newNode);
        maxPointer = newNode;
    }
    private void insertNodeHelper2(FibonacciHeapNode newNode){
        newNode.setLeft(maxPointer);
        if (maxPointer.getRight() != null) {
            newNode.setRight(maxPointer.getRight());
            maxPointer.getRight().setLeft(newNode);
        }
        maxPointer.setRight(newNode);
    }

    /**
     * Extracts/Removes the maximum data node
     * then does pairwise combining
     * @return : tempNode(next maximum data node)
     */
    public FibonacciHeapNode extractMaximumNode() {
        FibonacciHeapNode tempNode = maxPointer;
        FibonacciHeapNode countNode;

        // Creates doubly linked list : insertNode()
        if (null != maxPointer.getChildPointer()) {
            countNode = maxPointer.getChildPointer();
            while (countNode != null) {
                FibonacciHeapNode temp2Node = countNode;
                countNode = countNode.getRight();
                temp2Node.setParentPointer(null);
                temp2Node.setRight(null);
                temp2Node.setLeft(null);
                insertNode(temp2Node);
            }
        }

        // Set a temporary maxPointer
        if (maxPointer.getRight() != null) {
            maxPointer = maxPointer.getRight();
            maxPointer.setLeft(null);
        } else {
            maxPointer = null;
        }

        // Heap rearrangement : pairwise combine
        if (maxPointer != null) {
            HashMap<Integer, FibonacciHeapNode> degreeHashTable = new HashMap<>();
            countNode = maxPointer;
            while (countNode != null) {
                int degree = countNode.getDegree();
                FibonacciHeapNode temp3Node = countNode;
                countNode = countNode.getRight();
                while (degreeHashTable.containsKey(degree)) {
                    FibonacciHeapNode node = degreeHashTable.get(degree);
                    temp3Node = compareAndMeld(node, temp3Node);
                    degreeHashTable.remove(degree);
                    degree++;
                }
                degreeHashTable.put(degree, temp3Node);

            }

            // Heap reconstruction with combined nodes
            maxPointer = null;
            for (int degree : degreeHashTable.keySet()) {
                FibonacciHeapNode temp4Node = degreeHashTable.get(degree);
                temp4Node.setLeft(null);
                temp4Node.setRight(null);
                insertNode(degreeHashTable.get(degree));
            }
        }

        return tempNode;
    }

    /**
     * Same degree nodes will be combined by comparing node's data values and
     * returns combined node
     * @param node1 for comparing with
     * @param node2 parameter
     * @return node1 or node2 (based upon helper method)
     */
    private FibonacciHeapNode compareAndMeld(FibonacciHeapNode node1, FibonacciHeapNode node2) {
        // node1 is larger node
        if (node1.getData() > node2.getData()) {
            return compareAndMeldHelper(node1, node2);
        }
        // Equal Nodes
        else if(node1.getData() == node2.getData()){
            if(node1.getHashTagEntry() < node2.getHashTagEntry())
                return compareAndMeldHelper(node1, node2);
            else
                return compareAndMeldHelper(node2, node1);
        }
        // node2 is larger node
        else {
            return compareAndMeldHelper(node2, node1);
        }
    }
    private FibonacciHeapNode compareAndMeldHelper(FibonacciHeapNode node1, FibonacciHeapNode node2){
        FibonacciHeapNode child = node1.getChildPointer();

        if (node2.getLeft() != null)
            node2.getLeft().setRight(node2.getRight());
        if (node2.getRight() != null)
            node2.getRight().setLeft(node2.getLeft());

        if (child != null) {
            child.setLeft(node2);
            node2.setRight(child);
            node2.setLeft(null);
            node2.setParentPointer(node1);
            node1.setChildPointer(node2);
        }
        else {
            node1.setChildPointer(node2);
            node2.setParentPointer(node1);
            node2.setLeft(null);
            node2.setRight(null);
        }

        node1.setDegree(node1.getDegree() + 1);

        return node1;
    }

    /**
     * Increases the node data value by newValue and calls cascadingCut method
     * @param node to increase
     * @param addValue to add specific target value
     */
    public void increaseKey(FibonacciHeapNode node, int addValue) {
        // Setting the new value to the node by summing up
        node.setData(node.getData() + addValue);

        // Calling cascadeCut() if changed value is greater than parent's value
        // else if changed value > maxPointer => assign maxPointer to the node
        if (node.getParentPointer() != null) {
            if(node.getParentPointer().getData() < node.getData()||
                    (node.getParentPointer().getData() == node.getData()
                            && node.getParentPointer().getHashTagEntry() > node.getHashTagEntry())){
                cascadingCut(node);
            }
        }
        else {
            if(maxPointer.getData() < node.getData() ||
                    (maxPointer.getData() == node.getData()
                            && maxPointer.getHashTagEntry() > node.getHashTagEntry())){
                if (node.getLeft() != null)
                    node.getLeft().setRight(node.getRight());
                if (node.getRight() != null)
                    node.getRight().setLeft(node.getLeft());
                maxPointer.setLeft(node);
                node.setRight(maxPointer);
                node.setLeft(null);
                maxPointer = node;
            }
        }
    }

    /**
     * The node will be inserted into the doubly linked list and
     * if the parentPointer's childCut value is false, it is set to true
     * else cascadingCut is executed on parentPointer
     * until it reaches a node with childCut value as false or the root node
     * @param node to perform cut
     */
    public void cascadingCut(FibonacciHeapNode node) {

        FibonacciHeapNode parentPointer = node.getParentPointer();

        if (node.getLeft() != null)
            node.getLeft().setRight(node.getRight());
        if (node.getRight() != null)
            node.getRight().setLeft(node.getLeft());

        if (parentPointer != null && parentPointer.getChildPointer() == node) {
            parentPointer.setChildPointer(node.getRight());
        }

        node.setParentPointer(null);
        node.setLeft(null);
        node.setRight(null);
        insertNode(node); // Linking step : doubly linked list

        if (parentPointer != null) {
            parentPointer.setDegree((parentPointer.getDegree())-1);
            boolean childCut = parentPointer.getChildCut();
            if (!childCut)
                parentPointer.setChildCut(true);
            else
                cascadingCut(parentPointer);
        }
    }
}