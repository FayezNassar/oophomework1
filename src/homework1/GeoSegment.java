package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments are
 * immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street, boundary,
 * or other feature. As an example usage, this map
 * <p>
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * <p>
 * could be represented by the following GeoSegments: ("Trumpeldor Avenue", a,
 * i), ("Trumpeldor Avenue", z, i), ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 * <p>
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical GeoPoint
 * endpoints. Equality between GeoSegment objects requires that the names be
 * equal String objects and the end points be equal GeoPoint objects.
 * </p>
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <p>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment {

    String name; // name of the geographic feature identified
    GeoPoint p1; // first endpoint of the segment
    GeoPoint p2; // second endpoint of the segment
    double length; // straight-line distance between p1 and p2, in kilometers
    double heading; // compass heading from p1 to p2, in degrees

    // Abstraction Function:
    // A GeoSegment s is NotInWorld
    // if p1 or p2 is not valid points or is the same point
    // (s.p1 , s.p2) otherwise is our segment in the world.

    // Representation invariant for every GeoSegment s:
    // p1.equals(p2)==true
    // (MIN_LATITUDE <= s.p1.latitude <= MAX_LATITUDE) and
    // (MIN_LONGITUDE <= s.p1.longitude <= MAX_LONGITUDE)

    /**
     * Constructs a new GeoSegment with the specified name and endpoints.
     *
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and
     * endpoints.
     **/
    public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
        this.name = name; // because the class String is immutable.. so we can do =
        this.p1 = new GeoPoint(p1.latitude, p1.longitude);
        this.p2 = new GeoPoint(p2.latitude, p2.longitude);
        this.length = p1.distanceTo(p2);
        this.heading = p1.headingTo(p2);
        checkRep();
    }

    /**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     *
     * @return a new GeoSegment gs such that gs.name = this.name && gs.p1 =
     * this.p2 && gs.p2 = this.p1
     **/
    public GeoSegment reverse() {
        checkRep();
        return new GeoSegment(this.name, this.p2, this.p1);
    }

    /**
     * Returns the name of this GeoSegment.
     *
     * @return the name of this GeoSegment.
     */
    public String getName() {
        checkRep();
        return this.name;
    }

    /**
     * Returns first endpoint of the segment.
     *
     * @return first endpoint of the segment.
     */
    public GeoPoint getP1() {
        checkRep();
        return this.p1.clone();
    }

    /**
     * Returns second endpoint of the segment.
     *
     * @return second endpoint of the segment.
     */
    public GeoPoint getP2() {
        checkRep();
        return this.p2.clone();
    }

    /**
     * Returns the length of the segment.
     *
     * @return the length of the segment, using the flat-surface, near the
     * Technion approximation.
     */
    public double getLength() {
        checkRep();
        return this.length;
    }

    /**
     * Returns the compass heading from p1 to p2.
     *
     * @return the compass heading from p1 to p2, in degrees, using the
     * flat-surface, near the Technion approximation.
     * @requires this.length != 0
     **/
    public double getHeading() {
        checkRep();
        return this.heading;
    }

    /**
     * Compares the specified Object with this GeoSegment for equality.
     *
     * @return gs != null && (gs instanceof GeoSegment) && gs.name = this.name
     * && gs.p1 = this.p1 && gs.p2 = this.p2
     **/
    public boolean equals(Object gs) {
        checkRep();
        return gs != null && (gs instanceof GeoSegment) && ((GeoSegment) gs).name.equals(this.name)
                && ((GeoSegment) gs).p1.equals(this.p1) && ((GeoSegment) gs).p2.equals(this.p2);
    }

    /**
     * Returns a hash code value for this.
     *
     * @return a hash code value for this.
     **/
    public int hashCode() {
        // This implementation will work, but you may want to modify it
        // for improved performance.


        checkRep();
        return (this.p1.hashCode() + this.p2.hashCode()) / 2;
    }

    /**
     * Returns a string representation of this.
     *
     * @return a string representation of this.
     **/
    public String toString() {
        checkRep();
        return "start point: " + this.p1.toString() + "  end point: " + this.p2.toString();
    }

    /**
     * Checks to see if the representation invariant is being violated.
     *
     * @throws AssertionError if representation invariant is violated.
     */
    private void checkRep() {
        assert this.length != 0 :
                "Length must be more than 0";  //TODO: CHeck this thing


    }

}
