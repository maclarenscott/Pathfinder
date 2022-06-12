// package ICS.Pathfinder;
public class Pixel {
    private boolean state = false; // traversable or not
    private boolean active = false; // highlighted
    private boolean significant = false; // part of path
    private boolean isStartPoint = false; 
    private boolean isEndPoint = false;

    public boolean getIsStartPoint() {
        return this.isStartPoint;
    }

    public void setIsStartPoint(boolean isStartPoint) {
        this.isStartPoint = isStartPoint;
    }

    public boolean getIsEndPoint() {
        return this.isEndPoint;
    }

    public void setIsEndPoint(boolean isEndPoint) {
        this.isEndPoint = isEndPoint;
    }

    public Pixel() {}

    // Gs and Ss
    public boolean getSignificant() {
        return this.significant;
    }

    public void setSignificant(boolean significant) {
        this.significant = significant;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String toString() {
        return "(" + state + "," + active + "," + significant + ")";
    }


}
