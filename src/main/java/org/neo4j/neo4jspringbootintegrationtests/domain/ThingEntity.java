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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * @author Michael J. Simons
 */
@NodeEntity("Thing")
public class ThingEntity {
	@Id @GeneratedValue
	private Long id;

	@Index(unique = true)
	private String name;

	private double value = 0.0;

	@Relationship("IS_RELATED_TO")
	List<RelatedThing> relatedThings = new ArrayList<>();

	public ThingEntity(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<RelatedThing> getRelatedThings() {
		return Collections.unmodifiableList(relatedThings);
	}

	public ThingEntity addRelatedThing(String name) {
		this.relatedThings.add(new RelatedThing(name));
		return this;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof ThingEntity))
			return false;
		ThingEntity that = (ThingEntity) o;
		return name.equals(that.name);
	}

	@Override public int hashCode() {
		return Objects.hash(name);
	}
}
