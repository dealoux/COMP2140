public class Q17 {
    // This method return the index of the PrintJob that matches the id or -1 otherwise
    private int find(int id){
        int i=0;

        while(heap[i] != null){
            if(heap[i].num == id){
                return i;
            }
        }

        return -1;
    } // end of find(...) helper method

    public void changePriority(int id, int newPriority){
        int index = find(id);

        // check if the id is found
        if(index != -1){
            // check if the newPriority is less than the current priority
            if(heap.priority > newPriority){
                heap.priority = newPriority;
                siftDown(index); // sift down if necessary
            }

            // else the newPriority is more than the current priority
            else{
                heap.priority = newPriority;
                siftUp(index); // sift up if necessary
            }
        }
    }
}

class Q20{
    // recursive helper method inorderTraverse3(...)
    private void inorderTraverse3(Node curr, int count){
        if(curr != null){
            if(curr.getLeft() != null){
                System.out.print("( ");
                inorderTraverse3(curr.getLeft(), count++);
            }

            if(count%3 == 0){
                System.out.print(curr.toString() + " ");
            }

            if(curr.getRight() != null){
                System.out.print("( ");
                inorderTraverse3(curr.curr.getRight(), count++);
            }
        }
    } // end of inorderTraverse3(...) helper recursive method

    // main driver method inorderTraverse3()
    public void inorderTraverse3(){
        inorderTraverse3(head, 0);
    }
}

class Q21{
    public Node orderedDifference(Node head1, Node head2){
        Node head = null;
        Node curr = null;
        Node curr1 = head1;
        Node curr2 = head2;

        if(isOrdered(head1) && isOrdered(head2)){
            while(curr1 != null){
                if(curr.getNext() != null && curr1.getNext().getData() < curr1.getData()){
                    return null;
                }

                while(curr2 != null && curr1.getData() > curr2.getData()){
                    if(curr2.getNext() != null && curr2.getNext().getData() < curr2.getData()){
                        return null;
                    }

                    if(head == null){
                        head = new Node(curr2.getData());
                        curr = head;
                    }
                    else{
                        curr.setNext(new Node(curr2.getData()));
                        curr = curr.getNext();
                    }

                    curr2 = curr2.getNext();
                }

                curr.setNext(new Node(curr1.getData()));
                curr = curr.getNext();

                curr1 = curr1.getNext();
            }
        }

        return head;
    } // end of orderedDifference() method
}
