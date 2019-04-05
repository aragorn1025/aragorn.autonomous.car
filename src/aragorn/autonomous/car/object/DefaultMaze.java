package aragorn.autonomous.car.object;

import java.awt.geom.Point2D;
import aragorn.math.geometry.LineSegment2D;
import aragorn.math.geometry.Polygon2D;

public class DefaultMaze extends LinearMaze {

	public DefaultMaze() {
		super(new Polygon2D(new Point2D.Double(-6, -3), new Point2D.Double(-6, 22), new Point2D.Double(18, 22), new Point2D.Double(18, 50), new Point2D.Double(30, 50),
				new Point2D.Double(30, 10), new Point2D.Double(6, 10), new Point2D.Double(6, -3), new Point2D.Double(-6, -3)),
				new LineSegment2D(new Point2D.Double(18, 40), new Point2D.Double(30, 37)));
	}
}