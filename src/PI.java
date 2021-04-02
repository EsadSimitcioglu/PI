import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PI {

    public static void main(String[] args) {

        ArrayList<Integer> sumOfNodes = new ArrayList<>(); // This arraylist holds tree for sum of the nodes
        ArrayList<Integer> countNodes = new ArrayList<>(); // This arraylist holds the node how much it creates a sumNode
        ArrayList<Integer> startNodes = new ArrayList<>(); // This arraylist holds the value of nodes
        ArrayList<Integer> sumsNodesStart = new ArrayList<>(); // This arraylist holds the sumNodes of the specific node (e.g. 5.node have 2 sums nodes

        sumOfNodes.add(-1); // for solve the index problem (e.g. ArrayList start with 0 index but tree start with 1 node)
        countNodes.add(-1);
        startNodes.add(-1);
        sumsNodesStart.add(-1);

        int depth = -1; // depth of the tree
        int firstElement = 0; // for detecting the first node
        int max = -1; // maximum sum
        int node = 1; // node count
        int counter = 0;
        int nodeCounter = 0;

        int leftSide = 1; // for detecting left node of the tree
        int rightSide = 3; // for detecting right node of the tree
        int sumsNodes = 1; // addition of the sum of the node

        String character = ""; // detecting the number in the line


        try {
            File myObj = new File("input.txt");
            Scanner scanner = new Scanner(myObj);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                depth++;
                for (int i = 0; i < line.length(); i++) {
                    int number = 0;

                    if (line.charAt(i) != ' ') {     // if the line has a number
                        character += line.charAt(i);
                    }

                    if (line.charAt(i) == ' ' || i==line.length()-1){  // if the index has a space which means the number ended or line ended
                        number = Integer.parseInt(character);

                        if (leftSide == sumsNodes){ // if the node is the left most node
                            if (firstElement==0){ // for the first node of the tree
                                sumOfNodes.add(number);
                                firstElement=1;
                            }
                            else{
                                if (!isPrime(number)){ // if the number is not prime
                                    if (sumOfNodes.get(sumsNodes/2) == 0){ // if the parent nodes value is 0 then the road is restricted
                                        sumOfNodes.add(0);
                                    }
                                    else{ // if not parent not 0 then just add the number
                                        sumOfNodes.add(sumOfNodes.get(sumsNodes/2) + number);
                                    }
                                }
                                else{ // if the number is prime add 0 to sumOfNodes because that rode is restricted
                                    sumOfNodes.add(0);
                                }
                            }
                            startNodes.add(node);
                            countNodes.add(1);
                            sumsNodesStart.add(sumsNodes);
                            node++;
                            sumsNodes++;
                            leftSide = (int)Math.pow(2,depth+1);
                        }
                        else if(rightSide == sumsNodes){ // if the node is the right most node
                            if (!isPrime(number)){ // if the number is not prime
                                if (sumOfNodes.get(sumsNodes-(int)(Math.pow(2,depth)))== 0){ // if the parent nodes value is 0 then the road is restricted
                                    sumOfNodes.add(0);
                                }
                                else{ // if not parent not 0 then just add the number
                                    sumOfNodes.add(sumOfNodes.get(sumsNodes-(int)(Math.pow(2,depth))) + number);
                                }
                            }
                            else{ // if the number is prime add 0 to sumOfNodes because that rode is restricted
                                sumOfNodes.add(0);
                            }
                            startNodes.add(node);
                            countNodes.add(1);
                            sumsNodesStart.add(sumsNodes);
                            node++;
                            sumsNodes++;
                            rightSide += Math.pow(2,depth+1);
                        }
                        else{ // if the node isn't right most node or left most node
                            startNodes.add(node);
                            sumsNodesStart.add(sumsNodes);
                            if (!isPrime(number)){ // if the number is not prime
                                int startNode = node-(depth+1);
                                int endNode = countNodes.get(startNode);
                                for(int j = sumsNodesStart.get(startNode);j<sumsNodesStart.get(startNode)+endNode;j++){ // this for roads that coming from left parent node
                                    nodeCounter++;
                                    if (sumOfNodes.get(j) == 0){ // if the parent nodes value is 0 then the road is restricted
                                        sumOfNodes.add(0);
                                    }
                                    else{ // if not parent not 0 then just add the number
                                        sumOfNodes.add(sumOfNodes.get(j) + number);
                                    }
                                }
                                startNode = node-(depth);
                                endNode = countNodes.get(startNode);
                                for(int j = sumsNodesStart.get(startNode);j<sumsNodesStart.get(startNode)+endNode;j++){ // this for roads that coming from right parent node
                                    nodeCounter++;
                                    if (sumOfNodes.get(j) == 0){
                                        sumOfNodes.add(0);
                                    }
                                    else{
                                        sumOfNodes.add(sumOfNodes.get(j) + number);
                                    }
                                }
                            }
                            else{ // if the number is prime add 0 to sumOfNodes because that rode is restricted
                                int startNode = node-(depth+1);
                                int endNode = countNodes.get(startNode);
                                for(int j = sumsNodesStart.get(startNode);j<sumsNodesStart.get(startNode)+endNode;j++){
                                    nodeCounter++;
                                    sumOfNodes.add(0);
                                }
                                startNode = node-(depth);
                                endNode = countNodes.get(startNode);
                                for(int j = sumsNodesStart.get(startNode);j<sumsNodesStart.get(startNode)+endNode;j++){
                                    nodeCounter++;
                                    sumOfNodes.add(0);
                                }
                            }
                            node++;
                            countNodes.add(nodeCounter);
                            sumsNodes+=nodeCounter;
                        }
                        nodeCounter=0;
                        character = "";
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        for(int i = sumOfNodes.size()-1;i>=0;i--){ // traversing the sumsOfNodes arraylist to obtain the maximum sum with trying to reach at the end of the pyramid as much as possible
            counter++;
            if (sumOfNodes.get(i) > max){
                max = sumOfNodes.get(i);
            }
            if (counter==(Math.pow(2,depth)) && max == 0){ // if the line has no maximum number then we go to the previous line
                max = -2;
                depth--;
            }
            else if(counter==(Math.pow(2,depth)) && max != 0 ){ // we started from the bottom. So if the line has a maximum number we can break the for
                break;
            }
        }

        System.out.println("The Maximum Number is : " + max);

    }

        public static boolean isPrime (int number){ // for detecting if the number is prime
                if (number == 1) {
                    return false;
                }
                for (int i = 2; i < (number / 2) + 1; i++) {
                    if (number % i == 0)
                        return false;
                }
                return true;
            }
        }


