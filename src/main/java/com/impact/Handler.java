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

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		LOG.info("received");
		Task t1 = new Task("001", "buy milk", true);
		Task t2 = new Task("002", "mend the table", false);
		Task t3 = new Task("003", "shop grocery", true);
		//Task t4 = null;
		//System.out.println(t4.getDescription());
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(t1);
		tasks.add(t2);
		tasks.add(t3);
APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
response.setStatusCode(200);
		return response;
	}
}
