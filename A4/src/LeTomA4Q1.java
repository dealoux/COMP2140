/*** LeTomA4Q1
 *
 * COMP 2140 SECTION D01*
 * ASSIGNMENT   Assignment 4, question 1
 * @author      Tom Le, 7871324
 * @version     July 14th 2020
 *
 * PURPOSE: This program constructs and displays expression trees */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;


enum NodeType{
    OPERATOR,
    VARIABLE,
    NUMBER;
}

// the Main class
public class LeTomA4Q1 {
    private static ExpressionTree tree = new ExpressionTree();

    /** private helper methods */
    // This method handles the input file, privately called by the main method
    private static void processInput() throws FileNotFoundException {
        // Prompt user for an input file
        String path;
        Scanner userinput = new Scanner(System.in);
        System.out.println( "\nEnter the input file name (.txt files only): " );
        path = userinput.nextLine();
        File fileName = new File(path); // input file
        Scanner scanner = new Scanner(fileName);

        System.out.println("\nProcessing file " + fileName + "\n");

        // While the input file still has next line
        while (scanner.hasNextLine()){
            String input = scanner.nextLine();
            String token[] = input.split(" ");

            switch (token[0]){
                case "COMMENT":
                    System.out.println(input.substring(8));
                    break;

                case "NEW":
                    tree.treeConstruct(token);
                    break;

                case "PRINTPREFIX":
                    tree.printPrefix();
                    break;

                case "PRINTPOSTFIX":
                    tree.printPostfix();
                    break;

                case "PRINTINFIX":
                    tree.printInfix();
                    break;

                case "SIMPLIFY":
                    //tree.simplify();
                    break;
            }
        } // end of while the input file still has next line
    }// end of readInput() method


    // Main method
    public static void main(String[] args) throws FileNotFoundException {
        processInput();
        System.out.println("Program has executed successfully");
    }// end of main method
} // end of LeTomA4Q1(main) class


// The (Tree) Node Class
class Node{
    private NodeType type;
    private char operator;
    private String name;
    private int value;
    private Node left;
    private Node right;

    // default constructor
    public Node(NodeType type, Node left, Node right) {
        this.type = type;
        operator = '\0';
        name = "";
        value = Integer.MIN_VALUE;
        this.left = left;
        this.right = right;
    }

    // Accessors
    public NodeType getType() {
        return type;
    }

    public char getOp() {
        return operator;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    // Setters
    public void setType(NodeType type) {
        this.type = type;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    // This method returns true if the node has 2 children
    public Boolean hasChildren(){
        return left != null && right != null;
    }

    public String toString(){
        String result = "";

        switch (type){
            case VARIABLE -> result = name;
            case OPERATOR -> result += operator;
            case NUMBER -> result += value;
        }

        return result;
    }
} // end of Node class


// The expression tree class
class ExpressionTree{
    private Node root;
    private Stack stack;
    private Queue queue = new Queue();

    // default constructor
    public ExpressionTree(){
        root = null;
    }


    /** Private helper methods */
    // This method returns true if the provided string is an operator
    private Boolean isOperator(String str){
        return (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("^"));
    }

    // This method constructs the tree from postfix notation
    private void postfixConstruct(String key){
        // checks if the key is an operator
        Node newNode;

        if(isOperator(key)){
            Node B = stack.pop();
            Node C = stack.pop();
            newNode = new Node(NodeType.OPERATOR, C, B);
            newNode.setOperator(key.charAt(0));
        }

        // else if the string is a variable
        else if(Character.isLetter(key.charAt(0))){
            newNode = new Node(NodeType.VARIABLE, null, null);
            newNode.setName(key);
        }

        // else the string is a numeric operand
        else{
            newNode = new Node(NodeType.NUMBER, null, null);
            newNode.setValue(Integer.parseInt(key));
        }

        stack.push(newNode);
    } // end of postfixConstruct(...) helper method

    // This method checks for the node type and enqueue it into the queue
    private void keyEnqueue(String key){
        Node newNode;
        // checks if the key is an operator
        if(isOperator(key)){
            newNode = new Node(NodeType.OPERATOR, null, null);
            newNode.setOperator(key.charAt(0));
        }
        // else if the string is a variable
        else if(Character.isLetter(key.charAt(0))){
            newNode = new Node(NodeType.VARIABLE, null, null);
            newNode.setName(key);
        }
        // else the string is a numeric operand
        else{
            newNode = new Node(NodeType.NUMBER, null, null);
            newNode.setValue(Integer.parseInt(key));
        }
        queue.enqueue(newNode);
    } // end of keyEnqueue(...) helper method

    // This method constructs the tree from prefix notation and returns the root node of the tree once complete
    private Node prefixConstruct(){
        Node curr;
        Node next1;
        Node next2;

        while (queue.getSize() > 1){
            curr = queue.dequeue(); // get the top item from the queue

            // else the curr node has no children
            if(curr.getType() == NodeType.OPERATOR && !curr.hasChildren()){
                next1 = queue.peek();
                next2 = queue.peek2();

                if((next1.getType() != NodeType.OPERATOR && next2.getType() != NodeType.OPERATOR) || (next1.getType() != NodeType.OPERATOR && next1.hasChildren() && next2.getType() != NodeType.OPERATOR && next2.hasChildren())){
                    curr.setLeft(queue.dequeue());
                    curr.setRight(queue.dequeue());
                }
            }
            queue.enqueue(curr);
        }

        return queue.dequeue();
    } // end of prefixConstruct(...) helper method

    // traverse helper methods, for printing purposes
    private void preorderTraverse(Node curr){
        if(curr != null){
            System.out.print(curr.toString() + " ");

            if(curr.getLeft() != null){
                simplify(curr.getLeft());
            }

            if(curr.getRight() != null){
                simplify(curr.getRight());
            }
        }
    } // end of preorderTraverse(...) helper method

    private void postorderTraverse(Node curr){
        if(curr != null){
            if(curr.getLeft() != null){
                simplify(curr.getLeft());
            }

            if(curr.getRight() != null){
                simplify(curr.getRight());
            }

            System.out.print(curr.toString() + " ");
        }
    } // end of postorderTraverse(...) helper method

    private void inorderTraverse(Node curr){
        if(curr != null){
            if(curr.getLeft() != null){
                System.out.print("( ");
                simplify(curr.getLeft());
            }

            System.out.print(curr.toString() + " ");

            if(curr.getRight() != null){
                simplify(curr.getRight());
                System.out.print(")");
            }
        }
    } // end of inorderTraverse(...) helper method


    // This method simplifies the tree recursively
    private void simplify(Node curr){
        Node left = curr.getLeft();
        Node right = curr.getRight();

        if(curr.getType() == NodeType.OPERATOR){
            if(left.getType() == NodeType.NUMBER && right.getType() == NodeType.NUMBER){
                int result = 0;
                switch (curr.getOp()){
                    case '+' -> result = left.getValue() + right.getValue();
                    case '-' -> result = left.getValue() - right.getValue();
                    case '*' -> result = left.getValue() * right.getValue();
                    case '^' -> result = left.getValue() ^ right.getValue();
                }
                curr.setOperator('\0');
                curr.setValue(result);
                curr.setLeft(null);
                curr.setRight(null);
            }

            else if(curr.getOp() == '*'){
                if (left.getType() == NodeType.NUMBER){
                    if(left.getValue() == 1){

                    }
                    else if(left.getValue() == 0){

                    }
                }
                else{
                    if(right.getValue() == 1){

                    }
                    else if(right.getValue() == 0){

                    }
                }
            }

            else if(curr.getOp() == '^'){
                if (left.getType() == NodeType.NUMBER){
                    if(left.getValue() == 1 || left.getValue() == 0) {

                    }
                }
                else{
                    if(right.getValue() == 1){

                    }
                    else if(right.getValue() == 0){

                    }
                }
            }
        }

        if(left != null){
            simplify(left);
        }

        if(right != null){
            simplify(right);
        }
    } // end of simplify(...) helper method


    /** Public methods */
    // This method construct a new expression tree base on the input
    public void treeConstruct(String[] arr){
        // prefix notation
        if(isOperator(arr[1])){
            for(int i = 1; i<arr.length; i++){
                keyEnqueue(arr[i]);
            }
            root = prefixConstruct();
        }

        // postfix notation
        else{
            stack = new Stack(arr.length);
            for(int i = 1; i<arr.length; i++){
                postfixConstruct(arr[i]);
            }
            root = stack.pop();
        }

        System.out.println("New tree constructed");
    } // end of treeConstruct(...) method

    // This method prints the tree using prefix notation
    public void printPrefix(){
        preorderTraverse(root);
    } // end of printPrefix() method

    // This method prints the tree using postfix notation
    public void printPostfix(){
        postorderTraverse(root);
    } // end of printPostfix() method

    // This method prints the tree using infix notation
    public void printInfix(){
        inorderTraverse(root);
    } // end of printInfix() method

    // This method construct a new expression tree base on the input
    public void simplify(){
        simplify(root);
        System.out.println("Tree simplified");
    } // end of simplify() method
} // end of ExpressionTree class



// The Queue class
class Queue{
    // The QueueNode private class
    class QueueNode{
        private Node treeNode;
        private QueueNode next;
        private QueueNode prev;

        // default constructor
        public QueueNode(Node treeNode, QueueNode next, QueueNode prev){
            this.treeNode = treeNode;
            this.next = next;
            this.prev = prev;
        }

        // Accessors
        public Node getTreeNode() {
            return treeNode;
        }

        public QueueNode getNext() {
            return next;
        }

        public QueueNode getPrev() {
            return prev;
        }

        // Setters
        public void setNext(QueueNode next) {
            this.next = next;
        }

        public void setPrev(QueueNode prev) {
            this.prev = prev;
        }
    } // end of QueueNode private class

    // Queue variables
    private QueueNode head;
    private QueueNode tail;
    private int size;

    // default constructor
    public Queue() {
        head = tail = null;
        size = 0;
    }

    /** Public methods */
    // This method returns the queue size
    public int getSize(){
        return size;
    }

    // This method enqueues a new train car to the train end of the train and updates the sizes accordingly
    public void enqueue(Node treeNode){
        // append the new node to the end of the list(train)
        QueueNode newNode = new QueueNode(treeNode, null, null);

        // if the queue is empty, insert at head
        if(isEmpty()){
            head = tail = newNode;
        }
        // else the queue is not empty
        else{
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
        size++;
    } // end of append(...) method

    // This method removes and returns the first item of the queue
    public Node dequeue(){
        QueueNode result;

        // if the queue is empty, throw an exception
        if(isEmpty()){
            throw new RuntimeException("The queue is empty");
        }
        // else if the queue only has one item
        else if(size == 1 && head == tail){
            tail = null;
            result = head;
            head = head.getNext();
        }
        // else the queue has more than one item
        else{
            result = head;
            head = head.getNext();
            head.setPrev(null);
        }
        size--;
        return result.getTreeNode();
    } // end of dequeue() method

    // This method returns the top item of the queue
    public Node peek(){
        return head.getTreeNode();
    } // end of peek() method

    // This method returns the 2nd top item of the queue
    public Node peek2(){
        return head.getNext().getTreeNode();
    } // end of peek2() method

    // This method returns true if the queue is empty or false otherwise
    public boolean isEmpty(){
        return size == 0 && head == null;
    } // end of isEmpty() method

    // This method returns a String of all of the items inside the queue
    public String toString(){
        String result = "";
        QueueNode curr = head;
        while(curr != null){
            result += curr.getTreeNode().toString() + "\n";
            curr = curr.getNext();
        }
        return result;
    } // end of toString() method
} // end of Queue class



// The Stack class
class Stack{
    private Node[] stack;
    private int size;

    // default constructor
    public Stack(int length){
        stack = new Node[length];
        size = 0;
    }

    /** Public methods */
    // This method pushes the new position into the stack
    public void push(Node treeNode){
        if(size >= stack.length){
            throw new RuntimeException("The stack is full");
        }
        stack[size] = treeNode;
        size++;
    } // end of push(...) method

    // This method removes and returns the top item of the stack
    public Node pop(){
        if(isEmpty()){
            throw new RuntimeException("The stack is empty");
        }
        size--;
        return stack[size];
    } // end of pop() method

    // This method returns the top item of the stack
    public Node peek(){
        Node top = pop();
        push(top);
        return top;
    } // end of peek() method

    // This method returns true if the stack is empty or false otherwise
    public boolean isEmpty(){
        return size == 0;
    } // end of isEmpty() method

    // This method returns a String of all of the items inside the stack
    public String toString(){
        String result = "";
        for(int i=0; i<size; i++){
            result += stack[i].toString() + "\n";
        }
        return result;
    } // end of toString() method

} // end of Stack class