package homework1;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A GeoFeature represents a route from one location to another along a single
 * geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have the
 * same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the course
 * of a winding river, or travel along a road through intersections but
 * remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding a
 * segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily the
 * same as the distance along a straight line between its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * 
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {

	// Implementation hint:
	// When asked to return an Iterator, consider using the iterator() method
	// in the List interface. Two nice classes that implement the List
	// interface are ArrayList and LinkedList. If comparing two Lists for
	// equality is needed, consider using the equals() method of List. More
	// info can be found at:
	// http://docs.oracle.com/javase/8/docs/api/java/util/List.html
	final GeoPoint start; // location of the start of the geographic feature
	final GeoPoint end; // location of the end of the geographic feature
	final double startHeading; // direction of travel at the start of the
								// geographic
								// feature, in degrees
	final double endHeading; // direction of travel at the end of the geographic
	// feature, in degrees
	final LinkedList<GeoSegment> geoSegments; // a sequence of segments that
												// make up
	// this geographic feature
	final String name; // name of geographic feature
	final double length; // total length of the geographic feature, in
							// kilometers

	// Abstraction Function:
	// A GeoSegment s is NotInWorld
	// gs is not starts with the end point of gf
	// otherwise  our feature in the world.

	// Representation invariant for every GeoFeature gf:
	// gs is must start with the end point of gf

	/**
	 * Constructs a new GeoFeature.
	 * 
	 * @requires gs != null
	 * @effects Constructs a new GeoFeature, r, such that r.name = gs.name &&
	 *          r.startHeading = gs.heading && r.endHeading = gs.heading &&
	 *          r.start = gs.p1 && r.end = gs.p2
	 **/
	public GeoFeature(GeoSegment gs) {
		this.start = gs.getP1();
		this.end = gs.getP2();
		this.startHeading = gs.getHeading();
		this.endHeading = gs.getHeading();
		this.geoSegments = new LinkedList<GeoSegment>(){{
            add(gs);
        }};
		this.name = gs.name;
		this.length = gs.getLength();
		checkRep();
	}

	/**
	 * Constructs a new GeoFeature.
	 * 
	 * @requires gs != null && gf != null
	 * @effects Constructs a new GeoFeature, r, such that r.name = gs.name &&
	 *          r.startHeading = gf.startHeading && r.endHeading = gs.heading &&
	 *          r.start = gf.start && r.end = gs.p2
	 **/
	public GeoFeature(GeoFeature gf, GeoSegment gs) {
		this.start = gf.getStart();
		this.end = gs.getP2();
		this.startHeading = gf.getStartHeading();
		this.endHeading = gs.getHeading();
		this.geoSegments = new LinkedList<GeoSegment>();
		for (GeoSegment s : gf.geoSegments) {
			this.geoSegments.add(new GeoSegment(s.getName(), s.getP1(), s.getP2()));
		}
		this.geoSegments.add(gs);
		this.name = gs.name;
		this.length = gf.getLength() + gs.getLength();
		checkRep();
	}

	/**
	 * Returns name of geographic feature.
	 * 
	 * @return name of geographic feature
	 */
	public String getName() {
		checkRep();
		return this.name;
	}

	/**
	 * Returns location of the start of the geographic feature.
	 * 
	 * @return location of the start of the geographic feature.
	 */
	public GeoPoint getStart() {
		checkRep();
		return this.start;
	}

	/**
	 * Returns location of the end of the geographic feature.
	 * 
	 * @return location of the end of the geographic feature.
	 */
	public GeoPoint getEnd() {
		checkRep();
		return this.end;
	}

	/**
	 * Returns direction of travel at the start of the geographic feature.
	 * if the first firstSegment.p1.equals(firstSegment.p2) the method will return 0.
	 * @return direction (in standard heading) of travel at the start of the
	 *         geographic feature, in degrees.
	 */
	public double getStartHeading() {
		checkRep();
		return this.startHeading;
	}

	/**
	 * Returns direction of travel at the end of the geographic feature.
	 * if the lastSegment.p1.equals(lastSegment.p2) the method will return 0.
	 * @return direction (in standard heading) of travel at the end of the
	 *         geographic feature, in degrees.
	 */
	public double getEndHeading() {
		checkRep();
		return this.endHeading;
	}

	/**
	 * Returns total length of the geographic feature, in kilometers.
	 * 
	 * @return total length of the geographic feature, in kilometers. NOTE: this
	 *         is NOT as-the-crow-flies, but rather the total distance required
	 *         to traverse the geographic feature. These values are not
	 *         necessarily equal.
	 */
	public double getLength() {
		checkRep();
		return this.length;
	}

	/**
	 * Creates a new GeoFeature that is equal to this GeoFeature with gs
	 * appended to its end.
	 * 
	 * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
	 * @return a new GeoFeature r such that r.end = gs.p2 && r.endHeading =
	 *         gs.heading && r.length = this.length + gs.length
	 **/
	public GeoFeature addSegment(GeoSegment gs) {
		checkRep();
		return new GeoFeature(this, gs);
	}

	/**
	 * Returns an Iterator of GeoSegment objects. The concatenation of the
	 * GeoSegments, in order, is equivalent to this GeoFeature. All the
	 * GeoSegments have the same name.
	 * 
	 * @return an Iterator of GeoSegments such that
	 * 
	 *         <pre>
	 *      this.start        = a[0].p1 &&
	 *      this.startHeading = a[0].heading &&
	 *      this.end          = a[a.length - 1].p2 &&
	 *      this.endHeading   = a[a.length - 1].heading &&
	 *      this.length       = sum(0 <= i < a.length) . a[i].length &&
	 *      for all integers i
	 *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
	 *                                   a[i].p2d  == a[i+1].p1))
	 *         </pre>
	 * 
	 *         where <code>a[n]</code> denotes the nth element of the Iterator.
	 * @see homework1.GeoSegment
	 */
	public Iterator<GeoSegment> getGeoSegments() {
		checkRep();
		return this.geoSegments.iterator();
	}

	/**
	 * Compares the argument with this GeoFeature for equality.
	 * 
	 * @return o != null && (o instanceof GeoFeature) && (o.geoSegments and
	 *         this.geoSegments contain the same elements in the same order).
	 **/
	public boolean equals(Object o) {
		checkRep();
		if (o == null || !(o instanceof GeoFeature))
			return false;
		if (this.geoSegments.size() != ((GeoFeature) o).geoSegments.size())
			return false;
		for (int i = 0; i < this.geoSegments.size(); i++) {
			if (!(this.geoSegments.get(i).equals(((GeoFeature) o).geoSegments.get(i))))
				return false;
		}
		return true;
	}

	/**
	 * Returns a hash code for this.
	 * 
	 * @return a hash code for this.
	 **/
	public int hashCode() {
		// This implementation will work, but you may want to modify it
		// improved performance.
		checkRep();
		int sum = 0;
		for (int i = 0; i < this.geoSegments.size(); i++) {
			sum += this.geoSegments.get(i).hashCode();

		}
		return sum / this.geoSegments.size();
	}

	/**
	 * Returns a string representation of this.
	 * 
	 * @return a string representation of this.
	 **/
	public String toString() {
		checkRep();
		String s = "";
		for (int i = 0; i < this.geoSegments.size(); i++) {
			s += this.geoSegments.get(i).toString() + " ----- ";
		}
		return s;
	}
	
	/**
	 * Checks to see if the representation invariant is being violated.
	 * 
	 * @throws AssertionError
	 *             if representation invariant is violated.
	 */
	private void checkRep() {
		assert this.length != 0:   
			"Length must be more than 0";  //TODO: CHeck this thing		
	}
}
