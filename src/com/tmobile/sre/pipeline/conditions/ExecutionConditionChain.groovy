/*
 * Copyright © 2019 T-Mobile USA, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tmobile.sre.pipeline.conditions

import com.tmobile.sre.pipeline.environment.PipelineEnvironment
import com.tmobile.sre.pipeline.model.PipelineState
import com.tmobile.sre.pipeline.model.PipelineStep

/**
 * Combines multiple ExecutionConditions into a single
 * instance.
 *
 * If any of the sub conditions returns false, false will be
 * returned immediately.
 */
class ExecutionConditionChain implements ExecutionCondition {
  List<ExecutionCondition> conditions = []

  ExecutionConditionChain(ExecutionCondition... conditions) {
    this.conditions.addAll(Arrays.asList(conditions))
  }

  boolean shouldRun(PipelineState state, PipelineEnvironment environment, PipelineStep step) {
    for (int i=0; i<conditions.size(); i++) {
      if (! conditions[i].shouldRun(state, environment, step)) {
        return false;
      }
    }
    return true;
  }
}