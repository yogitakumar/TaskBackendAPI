package com.impact;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.impact.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		LOG.info("received");

		String userId = request.getPathParameters().get("userId");
		List<Task> tasks = new ArrayList<Task>();

		if(userId.equals("001")){
			Task t1 = new Task("001", "buy milk", true);
			tasks.add(t1);
		}
		else{
			Task t2 = new Task("002", "mend the table", false);
			tasks.add(t2);
		}

		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(200);

        ObjectMapper objectMapper = new ObjectMapper();
        try{
        	String responsebody = objectMapper.writeValueAsString(tasks);
        	response.setBody(responsebody);
		}
        catch (JsonProcessingException e){
        	LOG.error("Unable to marshall task array",e);
		}
		return response;
	}
}
