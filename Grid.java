// package ICS.Pathfinder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

class Grid {
    private int height;
    private int width;
    public Pixel[][] board;

    public Grid(int height, int width) {
        this.height = height;
        this.width = width;

        board = new Pixel[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = new Pixel();
            }
        }
    }

    public boolean hasStart() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y].getIsStartPoint()) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] getStart() {
        System.out.println("getting start");
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (board[row][col].getIsStartPoint()) {
                    System.out.println(String.format("start: (%s,%s)", col, row));
                    return new int[] { col, row };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    public int[] getEnd() {
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (board[row][col].getIsEndPoint()) {
                    System.out.println(String.format("end: (%s,%s)", col, row));
                    return new int[] { col, row };
                }
            }
        }
        return new int[] { -1, -1 };
    }

    public boolean hasEnd() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (board[x][y].getIsEndPoint()) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<int[]> getNeighborPixels(int[] position, int[] target) {
        ArrayList<int[]> ret = new ArrayList<int[]>();
        int x = position[0];
        int y = position[1];

        int pullX = target[0] - position[0];
        int pullY = target[1] - position[1];

        int[] dx = new int[] { 0, 0, 0, 0 }; // x unit vector
        int[] dy = new int[] { 0, 0, 0, 0 }; // y unit vector
        // If on target point
        if (pullX == 0 && pullY == 0) {
            dx = new int[] { 0, 0, 0, 0 }; // x unit vector
            dy = new int[] { 0, 0, 0, 0 }; // y unit vector
        } // if not on target
        else {
            // if v is 0 and h is not
            if (pullY == 0) {
                // and horz is east
                if (pullX > 0) {
                    System.out.println(String.format("v is 0 and h is E"));
                    dx = new int[] { 1, 0, 0, -1 };
                    dy = new int[] { 0, 1, -1, 0 };
                } // and horz is west
                else {
                    System.out.println(String.format("v is 0 and h is W"));
                    dx = new int[] { -1, 0, 0, 1 };
                    dy = new int[] { 0, 1, -1, 0 };
                }

            } // if h is 0 and v is not
            if (pullX == 0) {
                // and vert is south
                if (pullY > 0) {
                    System.out.println(String.format("h is 0 and v is S"));
                    dy = new int[] { 1, 0, 0, -1 };
                    dx = new int[] { 0, 1, -1, 0 };
                } // and vert is north
                else {
                    System.out.println(String.format("h is 0 and v is N"));
                    dy = new int[] { -1, 0, 0, 1 };
                    dx = new int[] { 0, 1, -1, 0 };
                }

            }
            // if neither horz nor vert are 0
            else if (!(pullX == 0) && !(pullY == 0)) {
                //If pullx is greater than pull y
                if(Math.abs(pullX)>Math.abs(pullY)){
                    // If horz needs to go east, prioritize E over W
                    if (pullX > 0) {
                        System.out.print(String.format("h is E "));
                        dx = new int[] { 1, 0, -1, 0 };
                    } // if horz needs to go west, prioritize W over E
                    else if (pullX < 0) {
                        System.out.print(String.format("h is W "));
                        dx = new int[] { -1, 0, 1, 0 };
                    }
                    // if vert needs to go South, prioritize S over N
                    if (pullY > 0) {
                        System.out.println(String.format("v is S"));
                        dy = new int[] { 0, 1, 0, -1 };
                    }
                    // if vert needs to go North, prioritize N over S
                    else if (pullY < 0) {
                        System.out.println(String.format("v is N"));
                        dy = new int[] { 0, -1, 0, 1 };
                    }
                }//if pull y is greater than pull x
                else{
                    if (pullX > 0) {
                        System.out.print(String.format("h is E "));
                        dx = new int[] { 0, 1, 0, -1 };
                    } // if horz needs to go west, prioritize W over E
                    else if (pullX < 0) {
                        System.out.print(String.format("h is W "));
                        dx = new int[] { 0, -1, 0, 1, };
                    }
                    // if vert needs to go South, prioritize S over N
                    if (pullY > 0) {
                        System.out.println(String.format("v is S"));
                        dy = new int[] { 1, 0, -1, 0 };
                    }
                    // if vert needs to go North, prioritize N over S
                    else if (pullY < 0) {
                        System.out.println(String.format("v is N"));
                        dy = new int[] { -1, 0, 1, 0 };
                    }
                }

            }
        }

        System.out.println("pullX: " + pullX);
        System.out.println("pullY: " + pullY);
        System.out.println("dx: " + Arrays.toString(dx));
        System.out.println("dy: " + Arrays.toString(dy));

        for (int i = 0; i < 4; i++) {
            int rr = x + dx[i];
            int cc = y + dy[i];

            // Check if cells are valid
            if ((rr < width && rr >= 0) && (cc < height && cc >= 0)) {
                // Check if cell is not blocked
                System.out.println(
                        "The state of  " + String.format("(%s,%s) is %s", rr, cc, getBoard()[rr][cc].getState()));
                if (!getBoard()[cc][rr].getState()) {
                    ret.add(new int[] { rr, cc });
                } else {
                    System.out.println("there is a blockage at " + String.format("(%s,%s)", rr, cc));
                }
            }
        }
        return ret;
    }

    public void reset() {
        board = new Pixel[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = new Pixel();
            }
        }
    }

    public void mouseActive(int x, int y, int screenW, int screenH, Options options) {
        int col = (int) (x / (screenW / width));
        int row = (int) (y / (screenH / height));
        if (!(options.getStartStatus() || options.getEndStatus())) {
            board[row][col].setActive(true);
        }
    }

    public void mouseUp(Options options) {
        if (!(options.getStartStatus() || options.getEndStatus())) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (!board[x][y].getIsEndPoint() && !board[x][y].getIsStartPoint()) {
                        if (board[x][y].getActive()) {
                            board[x][y].setState(!board[x][y].getState());
                            board[x][y].setActive(false);
                        }
                    }
                }
            }
        }
    }

    public void setStart(int x, int y, int screenW, int screenH, Options options) {
        if (options.getStartStatus() && !hasStart()) {
            System.out.println("setting start");
            int col = (int) (x / (screenW / width));
            int row = (int) (y / (screenH / height));

            board[row][col].setIsStartPoint(true);
            board[row][col].setState(false);

        }
        options.setStartStatus(false);
    }

    public void setEnd(int x, int y, int screenW, int screenH, Options options) {
        if (options.getEndStatus() && !hasEnd()) {
            System.out.println("setting end");
            int col = (int) (x / (screenW / width));
            int row = (int) (y / (screenH / height));

            board[row][col].setIsEndPoint(true);
            board[row][col].setState(false);
            System.out.println(board[row][col].toString());
        }
        options.setEndStatus(false);
    }

    // Gs and Ss
    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Pixel[][] getBoard() {
        return board;
    }

    public String toString() {
        String ret = "";
        int index = 0;
        for (Pixel[] col : board) {
            ret += "\nR:" + index + " ";
            for (Pixel row : col) {
                ret += row.toString();
            }
            index++;
        }
        return ret;
    }

    
}
