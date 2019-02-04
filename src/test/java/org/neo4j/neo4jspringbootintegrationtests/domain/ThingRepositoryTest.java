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

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.neo4j.ogm.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author Michael J. Simons
 */
@DataNeo4jTest
public class ThingRepositoryTest {
	private static ServerControls embeddedDatabaseServer;

	@BeforeAll
	static void startEmbeddedDatabaseServer() {

		embeddedDatabaseServer = TestServerBuilders.newInProcessBuilder()
			.withFixture(""
				+ "CREATE (:Thing {name: 'Thing1', value:1.0})\n"
				+ "CREATE (:Thing {name: 'Thing2', value:2.0})\n"
				+ "CREATE (t:Thing {name: 'Thing42', value:42.0})\n"
				+ "CREATE (r1:RelatedThing {name: 'r1'})\n"
				+ "CREATE (r2:RelatedThing {name: 'r2'})\n"
				+ "CREATE (t) - [:IS_RELATED_TO] -> (r1)\n"
				+ "CREATE (t) - [:IS_RELATED_TO] -> (r2)"
			)
			.newServer();
	}

	@TestConfiguration
	static class Config {
		@Bean
		public org.neo4j.ogm.config.Configuration configuration() {
			return new Configuration.Builder()
				.uri(embeddedDatabaseServer.boltURI().toString())
				.build();
		}
	}

	private final ThingRepository thingRepository;

	@Autowired
	public ThingRepositoryTest(ThingRepository thingRepository) {
		this.thingRepository = thingRepository;
	}

	@Test
	void shouldFindStandardEntity() {
		Optional<ThingEntity> optionalThingEntity = thingRepository.findOneByName("Thing2");
		assertThat(optionalThingEntity).isPresent().get().extracting(ThingEntity::getValue).isEqualTo(2.0);
	}

	@Test
	void shouldLoadRelationships() {
		Optional<ThingEntity> optionalThingEntity = thingRepository.findOneByName("Thing42");
		assertThat(optionalThingEntity)
			.isPresent().get()
			.extracting(ThingEntity::getRelatedThings)
			.asList()
			.hasSize(2)
			.extracting("name")
			.containsExactlyInAnyOrder("r1", "r2");

	}

	@Test
	void shouldFindClosedProjection() {
		String nameOfThing = "Thing1";
		Optional<ClosedThingProjection> optionalClosedProjection = thingRepository.findFirstByName(nameOfThing);
		assertThat(optionalClosedProjection)
			.isPresent().get()
			.extracting(ClosedThingProjection::getName).isEqualTo(nameOfThing);
	}

	@Test
	void shouldFindOpenProjection() {
		String nameOfThing = "Thing1";
		Optional<OpenThingProjection> optionalOpenProjection = thingRepository.findOneByValue(42.0);
		assertThat(optionalOpenProjection)
			.isPresent().get()
			.extracting(OpenThingProjection::getFirstRelatedThingName).isEqualTo("r1");
	}

	@Test
	void shouldFindQueryResults() {
		ThingResult optionalThingResult = thingRepository.findSomethingNameAndValue("Thing1", 1.0);
		assertThat(optionalThingResult).isNotNull().extracting(ThingResult::getSomeString).isEqualTo("Thing1: 1.0");
	}

	@AfterAll
	static void stopEmbeddedDatabaseServer() {

		embeddedDatabaseServer.close();
	}
}
