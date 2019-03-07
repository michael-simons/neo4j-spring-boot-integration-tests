/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
