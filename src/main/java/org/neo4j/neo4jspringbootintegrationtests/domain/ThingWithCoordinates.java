package org.neo4j.neo4jspringbootintegrationtests.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.conversion.PointConverter;

/**
 * @author Michael J. Simons
 */
@NodeEntity
public class ThingWithCoordinates {
	@Id @GeneratedValue
	private Long id;

	private Point plainPlaint;

	@Convert(PointConverter.class)
	private Point convertedPoint;

	public Long getId() {
		return id;
	}

	public Point getPlainPlaint() {
		return plainPlaint;
	}

	public void setPlainPlaint(Point plainPlaint) {
		this.plainPlaint = plainPlaint;
	}

	public Point getConvertedPoint() {
		return convertedPoint;
	}

	public void setConvertedPoint(Point convertedPoint) {
		this.convertedPoint = convertedPoint;
	}
}
