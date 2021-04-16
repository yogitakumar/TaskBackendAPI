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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GetTasksHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	private static final Logger LOG = LogManager.getLogger(GetTasksHandler.class);

	private Connection connection= null;
	private PreparedStatement preparedStatement=null;
	private ResultSet resultSet = null;


	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
		LOG.info("received");

		String userId = request.getPathParameters().get("userId");
		List<Task> tasks = new ArrayList<Task>();

		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			connection = DriverManager.getConnection(
					String.format("jdbc:mysql//%s/%s?user=%s&password=%s",
							      "rds-instance-task.chiwvf0mvtlq.eu-west-2.rds.amazonaws.com",
							      "taskdb",
							      "root",
							      "xxxxx")
			);

			preparedStatement = connection.prepareStatement("select * from task where userId=?");
			preparedStatement.setString(1,userId);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()){
				Task task = new Task(resultSet.getString("taskId"),
					            	 resultSet.getString("description"),
						             resultSet.getBoolean("completed"));

				tasks.add(task);
			}
		}catch (Exception e){
LOG.error(String.format("Unable to query database for tasks for user %s",userId),e);
		}
		finally{
			closeConnection();
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
	private  void closeConnection(){
		try{
			if(resultSet != null)
				resultSet.close();
			if(preparedStatement != null)
				preparedStatement.close();
			if(connection != null)
				connection.close();
		}
		catch (Exception e){
			LOG.error("Unable to close connection to MYSQL - {}",e.getMessage());
		}
	}
}

