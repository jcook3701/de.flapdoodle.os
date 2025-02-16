/*
 * Copyright (C) 2020
 *   Michael Mosmann <michael@mosmann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.os.linux;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.VersionWithPriority;
import de.flapdoodle.os.common.DistinctPeculiarity;
import de.flapdoodle.os.common.HasPecularities;
import de.flapdoodle.os.common.Peculiarity;
import de.flapdoodle.os.common.attributes.Attribute;
import de.flapdoodle.os.common.attributes.Attributes;
import de.flapdoodle.os.common.matcher.Matchers;

import java.util.List;

public enum AmazonVersion implements VersionWithPriority {
	// amzn2
	// os.version=4.9.76-3.78.amzn1.x86_64
	AmazonLinux(osVersionMatches(".*amzn1.*")),
	AmazonLinux2(osVersionMatches(".*amzn2.*"));

	private final List<Peculiarity> peculiarities;

	AmazonVersion(Peculiarity... peculiarities) {
		this.peculiarities  = HasPecularities.asList(peculiarities);
	}

	@Override
	public List<Peculiarity> pecularities() {
		return peculiarities;
	}

	/**
	 * as we rely on 'os.version' only, this detection is pretty weak
	 * it should have a lower priority than others
	 */
	@Override
	public int priority() {
		return -1;
	}

	static DistinctPeculiarity<String> osVersionMatches(String name) {
		return DistinctPeculiarity.of(osVersion(), Matchers.matchPattern(name));
	}

	static Attribute<String> osVersion() {
		return Attributes.systemProperty("os.version");
	}
}
