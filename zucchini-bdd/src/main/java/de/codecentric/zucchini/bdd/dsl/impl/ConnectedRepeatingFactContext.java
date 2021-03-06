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
import de.codecentric.zucchini.bdd.dsl.Fact;
import de.codecentric.zucchini.bdd.dsl.RepeatingFactContext;
import de.codecentric.zucchini.bdd.dsl.impl.facts.DelegatingFact;

/**
 * Defines the part of the DSL that allows the definition of additional facts and everything that a
 * {@link de.codecentric.zucchini.bdd.dsl.impl.ConnectedFirstStepContext} does.
 */
public class ConnectedRepeatingFactContext extends ConnectedFirstStepContext implements RepeatingFactContext {
	public ConnectedRepeatingFactContext(ExecutionContext executionContext) {
		super(executionContext);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepeatingFactContext andGiven(Fact fact) {
		getExecutionContext().addFact(fact);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepeatingFactContext andGiven(String factName) {
		return andGiven(new DelegatingFact(factName));
	}
}
