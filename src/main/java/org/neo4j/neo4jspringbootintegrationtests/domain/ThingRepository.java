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

import java.util.Optional;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.Repository;

/**
 * @author Michael J. Simons
 */
public interface ThingRepository extends Repository<ThingEntity, Long> {
	Optional<ThingEntity> findOneByName(String name);

	Optional<ClosedThingProjection> findFirstByName(String name);

	Optional<OpenThingProjection> findOneByValue(double value);

	@Query("MATCH (t:Thing) WHERE t.name = $name AND t.value = $value RETURN t.name + ': ' + t.value AS someString")
	ThingResult findSomethingNameAndValue(String name, double value);
}
