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

import de.codecentric.zucchini.bdd.dsl.*;
import de.codecentric.zucchini.bdd.resolver.StatementResolverHolder;

import java.util.ArrayList;
import java.util.List;

import static de.codecentric.zucchini.bdd.dsl.impl.util.ArrayConverter.createMutableList;

public class ConnectedRepeatingConditionalContext implements RepeatingConditionalContext {
	private final List<Fact> facts;

	private final List<Step> steps = new ArrayList<Step>();

	public ConnectedRepeatingConditionalContext(List<Fact> facts, Step step) {
		this.facts = facts;
		steps.add(step);
	}

	@Override
	public RepeatingConditionalContext andWhen(Step step) {
		this.steps.add(step);
		return this;
	}

	@Override
	public RepeatingConditionalContext andWhen(String stepName) {
		this.steps.add(StatementResolverHolder.getStatementResolver().resolveStatement(stepName, Step.class));
		return this;
	}

	@Override
	public RepeatingOutcomeContext then(Result result) {
		return new ConnectedRepeatingOutcomeContext(facts, steps, createMutableList(result));
	}

	@Override
	public RepeatingOutcomeContext then(String resultName) {
		return then(StatementResolverHolder.getStatementResolver().resolveStatement(resultName, Result.class));
	}
}