package edu.brown.cs.student.main;

import java.util.List;

/**
 * Class representing the stars
 */
public class Star {
    private  int id;
    private  String properName;
    private  double x;
    private  double y;
    private  double z;
    private double relDistance;

    /**
     * Constructors for stars
     * @param id -- Star ID
     * @param properName -- Star Name
     * @param x -- Star x-coordinate
     * @param y --  Star y-coordinate
     * @param z -- Star z-coordinate
     *
     */
    public Star(int id, String properName, double x, double y, double z) {
        this.id = id;
        this.properName = properName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.relDistance = 0.0;
    }

    /**
     * Getter method that returns the ID for a given star
     * @return ID number of type int
     */
    public int getID() {
        return this.id;
    }

    /**
     * Getter method that returns the proper name for a given star
     * @return Proper name of type string
     */
    public String getPN() {
        return this.properName;
    }

    /**
     * Getter method that returns the x coordinate for a given star
     * @return x coordinate of type double
     */
    public double getX() {
        return this.x;
    }

    /**
     * Getter method that returns the y coordinate for a given star
     * @return y coordinate of type int
     */
    public double getY() {
        return this.y;
    }

    /**
     * Getter method that returns the z coordinate for a given star
     * @return z coordinate of type int
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Getter method that returns the relative distance for a given star
     * @return relative distance of type double
     */
    public double getRelDistance() {
        return this.relDistance;
    }

    /**
     *  method that calculates the relative distance between star and given XYZ coordinate
     * @param x -- x value of given coordinate
     * @param y -- y value of given coordinate
     * @param z -- z value of given coordinate
     * Sets the distance field to that value
     * See Formula here: https://www.geeksforgeeks.org/program-to-calculate-distance-between-two-points-in-3-d/
     */
    public void calcReldistance(double x, double y, double z) {
       this.relDistance = Math.pow( (Math.pow(this.x - x, 2) +
                              Math.pow(this.y - y, 2) +
                              Math.pow(this.z - z, 2) *
                                      1.0), 0.5
                            );

    }





}
