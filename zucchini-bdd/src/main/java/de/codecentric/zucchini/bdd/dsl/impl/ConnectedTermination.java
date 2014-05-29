/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.codecentric.zucchini.bdd.dsl.impl;

import de.codecentric.zucchini.bdd.ExecutionContext;
import de.codecentric.zucchini.bdd.ExecutionException;
import de.codecentric.zucchini.bdd.Executor;
import de.codecentric.zucchini.bdd.ExecutorHolder;
import de.codecentric.zucchini.bdd.dsl.Step;
import de.codecentric.zucchini.bdd.dsl.Fact;
import de.codecentric.zucchini.bdd.dsl.Result;
import de.codecentric.zucchini.bdd.dsl.Termination;

import java.util.List;

public class ConnectedTermination implements Termination {
	private final List<Fact> facts;
	private final List<Step> steps;
	private final List<Result> results;

	ConnectedTermination(List<Fact> facts, List<Step> steps, List<Result> results) {
		this.facts = facts;
		this.steps = steps;
		this.results = results;
	}

	@Override
	public void end() {
		Executor executor = ExecutorHolder.getExecutor();
		if (executor == null) {
			throw new ExecutionException("No executor is defined. Use ExecutorHolder.setExecutor() to set one.");
		}
		executor.execute(new ExecutionContext(facts, steps, results));
	}
}