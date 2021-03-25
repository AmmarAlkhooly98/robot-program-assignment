/*
    Group Assignment - Java 2
        Group members:
           - Ammar Yaser Elkhooly 1002057910
           - Mohamed Safwat 1001852241
*/

public class Main {

    public static void main(String[] args) {
        NavigateRoute obj = new NavigateRoute();
        System.out.println("Robot Navigation Status: " + obj.tracePath(1,1,30,30,"F29R29"));
        CheckNavigate obj2 = new CheckNavigate();
        System.out.print(obj2.checkPath(1,1,"F29R29F3B"));
    }
}

class CheckCode {

    public boolean check(String path) {

        // first character of the path should always be from (F,B,L,R)
        if(path.charAt(0)!='F' && path.charAt(0)!='B' && path.charAt(0)!='L' && path.charAt(0)!='R' ) {
            return false;
        }

        // declare steps to count number of steps in a direction
        // steps should not be greater than 99
        int steps=0;

        // using regex to split the string accordingly
        String path2[] = path.replace(" ", "").split("(?<=[\\(\\)\\+\\-*\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*\\/\\^A-Za-z])");

        //iterate over the string to check its validity
        for(int i=1;i<path2.length;i++) {
            String c = String.valueOf(path2[i]);

            // the current letter is from (F,B,L,R) or not
            if(c.contains("F") || c.contains("B") || c.contains("L") || c.contains("R")) {
                // and no two letter from (F,B,L,R) can appear simultaneously
                continue;
            }
            // c is some number from 1 to 30
            else if(Integer.parseInt(c)>0 && Integer.parseInt(c)<=30) {
                int num = Integer.parseInt(c);
                steps += num;
            }

            // c is neither a number nor a letter from (F,B,L,R)
            else {
                return false;
            }
        }
        // steps shall not exceed the boundaries of 1 - 99
        if(steps<1 || steps>99) return false;
        // every condition is passed hence true
        return true;
    }
}

class CheckNavigate {

    public int checkPath(int row, int col, String path) {

        // first validate the path using method from CheckCode class
        CheckCode obj = new CheckCode();
        if(obj.check(path) == false ) {
            System.out.println("Invalid String Path");
            return 0;
        }

        // number of steps taken by the robot
        int total_steps=0;

        // current number of steps to be taken and direction
        int steps=0;
        String direction="X";

        // current coordinates of robot
        int x=row, y=col;

        String path2[] = path.replace(" ", "").split("(?<=[\\(\\)\\+\\-*\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*\\/\\^A-Za-z])");

        for(int i=0;i<path2.length;i++) {
            String c = String.valueOf(path2[i]);
            if(c.contains("F") || c.contains("B") || c.contains("L") || c.contains("R")) {

                // first change the robot's postion according to previous move
                // and increase the total_steps
                if(direction.contains("F")) y+=steps;
                else if(direction.contains("B")) y-=steps;
                else if(direction.contains("L")) x-=steps;
                else if(direction.contains("R")) x+=steps;
                total_steps += steps;

                // set steps=0 for next direction
                // and change the direction
                steps=0;
                direction = c;

                //check whether the robot hit the boundary wall or not
                // if it hit any wall it should stop its movement
                // and correct the total number of steps
                if(x>30) {
                    total_steps -= (x-30);
                    break;
                }
                else if(x<1) {
                    total_steps += (x-1);
                    break;
                }
                else if(y>30) {
                    total_steps -= (y-30);
                    break;
                }
                else if(y<1) {
                    total_steps += (y-1);
                    break;
                }
            }
            else if(Integer.parseInt(c)>0 && Integer.parseInt(c)<=30) {
                int num = Integer.parseInt(c);
                steps += num;
            }
        }

        // complete the last move
        if(steps != 0) {
            if(direction.contains("F")) y+=steps;
            else if(direction.contains("B")) y-=steps;
            else if(direction.contains("L")) x-=steps;
            else if(direction.contains("R")) x+=steps;
            total_steps += steps;

            //check whether the robot hit the boundary wall or not
            // if it hit any wall it should stop its movement
            // and correct the total number of steps
            if(x>30) {
                total_steps -= (x-30);
            }
            else if(x<1) {
                total_steps += (x-1);
            }
            else if(y>30) {
                total_steps -= (y-30);
            }
            else if(y<1) {
                total_steps += (y-1);
            }
        }
        System.out.print("Robot Check Navigate steps from "+"("+ row+","+col+"): ");
        return total_steps;
    }

}

class NavigateRoute {

    public int tracePath(int srow, int scol, int trow, int tcol, String path) {

        // first validate the path using method from CheckCode class
        CheckCode obj = new CheckCode();
        if(obj.check(path) == false ) {
            System.out.println("Invalid String Path");
            return 0;
        }

        // current number of steps to be taken and direction
        int steps=0;
        String direction ="X";

        // current coordinates of robot
        int x=srow, y=scol;

        // flag id robot hits the wall
        boolean isWentOffWall = false;

        System.out.println("starting at: "+srow+","+scol+"\nTarget: "+trow+","+tcol);

        String path2[] = path.replace(" ", "").split("(?<=[\\(\\)\\+\\-*\\/\\^A-Za-z])|(?=[\\(\\)\\+\\-*\\/\\^A-Za-z])");

        for(int i=0;i<path2.length;i++) {
            String c = String.valueOf(path2[i]);

            if(c.contains("F") || c.contains("B") || c.contains("L") || c.contains("R")) {

                // first change the robot's position according to previous move
                // and increase the total_steps
                if(direction.contains("F")) y+=steps;
                else if(direction.contains("B")) y-=steps;
                else if(direction.contains("L")) x-=steps;
                else if(direction.contains("R")) x+=steps;

                // set steps=0 for next direction
                // and change the direction
                steps=0;
                direction = c;

                //check whether the robot went off the boundary wall or not
                // if it hit any wall it should stop its movement
                if(x>30 || x<1 || y>30 || y<1) {
                    isWentOffWall = true;
                    break;
                }

            }
            else if(Integer.parseInt(c)>0 && Integer.parseInt(c)<=30) {
                int num = Integer.parseInt(c);
                steps += num;
            }
        }

        // complete the last move
        if(steps != 0) {
            if(direction.contains("F")) y+=steps;
            else if(direction.contains("B")) y-=steps;
            else if(direction.contains("L")) x-=steps;
            else if(direction.contains("R")) x+=steps;

            //check whether the robot went off the boundary wall or not
            // if it hit any wall it should stop its movement
            if(x>30 || x<1 || y>30 || y<1) {
                isWentOffWall = true;
            }
            System.out.println("Robot at: "+x+","+y);
        }


        // it went off the wall
        if(isWentOffWall) return -2;

        // robot didnt reached it's target
        if(x!=trow || y!=tcol) return 0;

        // robot reached it's target
        return 1;
    }

}