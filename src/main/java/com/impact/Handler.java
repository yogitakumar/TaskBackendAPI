package com.impact;
import com.impact.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);
		Task t1 = new Task("001", "buy milk", true);
		Task t2 = new Task("002", "mend the table", false);
		Task t3 = new Task("003", "shop grocery", true);
		//Task t4 = null;
		//System.out.println(t4.getDescription());
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(t1);
		tasks.add(t2);
		tasks.add(t3);

		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(tasks)
		    	.build();
	}
}
