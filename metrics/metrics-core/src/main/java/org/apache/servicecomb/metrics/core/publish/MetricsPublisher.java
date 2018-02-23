/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.metrics.core.publish;

import java.util.Map;

import org.apache.servicecomb.core.metrics.InvocationFinishedEvent;
import org.apache.servicecomb.core.metrics.InvocationStartExecutionEvent;
import org.apache.servicecomb.core.metrics.InvocationStartedEvent;
import org.apache.servicecomb.foundation.common.utils.EventUtils;
import org.apache.servicecomb.metrics.core.MonitorManager;
import org.apache.servicecomb.metrics.core.event.InvocationFinishedEventListener;
import org.apache.servicecomb.metrics.core.event.InvocationStartExecutionEventListener;
import org.apache.servicecomb.metrics.core.event.InvocationStartedEventListener;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestSchema(schemaId = "metricsEndpoint")
@RequestMapping(path = "/metrics")
public class MetricsPublisher {

  public MetricsPublisher() {
    //init
    EventUtils.registerEventListener(InvocationFinishedEvent.class, new InvocationFinishedEventListener());
    EventUtils.registerEventListener(InvocationStartExecutionEvent.class, new InvocationStartExecutionEventListener());
    EventUtils.registerEventListener(InvocationStartedEvent.class, new InvocationStartedEventListener());
    MonitorManager.getInstance();
  }

  @ApiResponses({
      @ApiResponse(code = 400, response = String.class, message = "illegal request content"),
  })
  @RequestMapping(path = "/", method = RequestMethod.GET)
  @CrossOrigin
  public Map<String, Double> measure() {
    return MonitorManager.getInstance().measure();
  }
}