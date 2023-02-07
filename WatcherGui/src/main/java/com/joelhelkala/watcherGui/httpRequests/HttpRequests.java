package com.joelhelkala.watcherGui.httpRequests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.joelhelkala.watcherGui.Datatypes.UserType;
import com.joelhelkala.watcherGui.Nodes.Node.Node;
import com.joelhelkala.watcherGui.User.User;
import com.joelhelkala.watcherGui.User.Roles.Role;
import com.joelhelkala.watcherGui.frames.WelcomePage;

/*
 * Class to handle httpRequests to the server
 */
public class HttpRequests {
	
	private final static String address = "http://localhost:8080/api/v1";
	
	// Sends a simple ping GET to the server to check if it's up
	public static boolean PingRequest() throws IOException{
		URL url = new URL(address + "/ping");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestMethod("GET");
        
        try {
        	if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        		connection.disconnect();
        		return true;
        	}
		} catch (IOException e) {}
        finally {
        	connection.disconnect();
        }
		return false;
	}
	
	// Gets all the users from the server
	public static List<UserType> GetUsers() {
		JSONArray arr = basicGetRequest("/appuser/admin");
		List<UserType> userList = new ArrayList<>();
		for(int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			userList.add(new UserType(obj.getString("name"),
									obj.getString("email"),
									obj.getBoolean("enabled"),
									obj.getBoolean("locked"),
									obj.getString("role").equals("ADMIN") ? Role.ADMIN : Role.USER,
									obj.getLong("id")));
		}
		return userList; 
		
	}
	
	/*
	 * Sends a POST request to register a new user to database
	 * Server sends a token if the information given are valid
	 * This token must be confirmed under certain time
	 */
	public static JSONObject RegisterUser(String firstname, String email, String password) {
		JSONObject response_json = null;
		
		try {
			URL url = new URL(address+"/registration");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			
			JSONObject obj = new JSONObject();
			obj.put("firstName", firstname);
			obj.put("email", email);
			obj.put("password", password);
			
			String jsonString = obj.toString();
			
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = jsonString.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
			
			int status = con.getResponseCode();
			if (status > 299) {
				try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(con.getErrorStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    response_json = new JSONObject(response.toString());
				    response_json.put("status", status);
				}
			} else {
				try(BufferedReader br = new BufferedReader(
				  new InputStreamReader(con.getInputStream(), "utf-8"))) {
				    StringBuilder response = new StringBuilder();
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        response.append(responseLine.trim());
				    }
				    response_json = new JSONObject(response.toString());
				    response_json.put("status", status);
				}
			}
			con.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response_json;
	}
	
	/*
	 * Send a login POST request to server
	 * Server should return a session token and user information
	 */
	public static boolean LoginRequest(String user, String password) {
		try {
			String urlParameters  = "username=" + user + "&password=" + password;
			byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
			StringBuilder response = new StringBuilder();
			URL url = new URL(address+"/login");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty("charset", "utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Length", Integer.toString(postData.length));
			con.setUseCaches(false);
			con.setDoOutput(true);
			
			try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
			   wr.write( postData );
			}
			
			try(BufferedReader br = new BufferedReader(
			  new InputStreamReader(con.getInputStream(), "utf-8"))) {
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			    }
			}
			// Get the values from the response and set them to the user
			JSONObject object = new JSONObject(response.toString());
			User.setToken(object.get("access_token").toString());
			User.setName(object.get("name").toString());
			User.setEmail(object.get("email").toString());
			if(object.get("role").equals("ADMIN")) User.setRole(Role.ADMIN);
			else User.setRole(Role.USER);
			
			con.disconnect();
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * Gets all the history data from the given node_id. 
	 * TODO: Set a parameter that configures the amount of history data will be fetched
	 */
	public static JSONArray getNodeData(Integer node_id) {
		JSONArray data = basicGetRequest("/nodeData/" + node_id);	
		return data;
	}
	
	public static JSONArray parse(String responseBody) {
		JSONArray body = new JSONArray(responseBody);
		for(int i = 0; i < body.length(); i++) {
			JSONObject obj = body.getJSONObject(i);
			int code = obj.getInt("code");
			int id = obj.getInt("id");
			String user = obj.getString("user");
			System.out.println(code + " " + id + " " + user);
		}
		return body;
	}

	/*
	 * Gets all the nodes available from the server
	 */
	public static List<Node> getNodes() {
		JSONArray data = basicGetRequest("/node");
		int nodes_amount = data.length();
		List<Node> nodes = new ArrayList<Node>(nodes_amount);
		
		for(int i = 0; i < nodes_amount; i++) {
			JSONObject obj = data.getJSONObject(i);
			String description = obj.getString("description");
			String location = obj.getString("location");
			Float latitude = obj.getFloat("latitude");
			Float longitude = obj.getFloat("longitude");
			Integer id = obj.getInt("id");
			nodes.add(new Node(description, latitude, longitude, location, id));
		}
		return nodes;
	}
	
	// Sends a basic GET request to the given url and returns a JSONArray from response
	private static JSONArray basicGetRequest(String uri) {
		JSONArray data = new JSONArray();
		URL url;
		try {
			url = new URL(address + uri);
			StringBuilder response = new StringBuilder();
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
	        con.setRequestProperty("Authorization","Bearer " + User.getToken());
			
			try(BufferedReader br = new BufferedReader(
			  new InputStreamReader(con.getInputStream(), "utf-8"))) {
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			    }
			}
			/*if (con.getResponseCode() == 403) {
				WelcomePage.TimedOutSession();
				// Set success to true so error dialog wont be shown
				return null;
			}*/
			data = new JSONArray(response.toString());
			con.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	// Deletes the user with given id
	// returns true if delete was succesfull
	public static boolean deleteUser(Long id) {
		try {
			URL url = new URL(address + "/appuser/admin/"+id);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("DELETE");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
	        con.setRequestProperty("Authorization","Bearer " + User.getToken());
	        
	        int status = con.getResponseCode();
			con.disconnect();
			
			if(status < 300) return true;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Send a PUT request to update a node
	 */
	public static boolean updateNode(Node node) {
		boolean success = false;
		JSONObject response_json = new JSONObject();
		URL url;
		try {
			url = new URL(address + "/node/" + node.getId());
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization","Bearer " + User.getToken());
			con.setDoOutput(true);
			
			JSONObject obj = new JSONObject();
			obj.put("id", node.getId());
			obj.put("location", node.getLocation());
			obj.put("description", node.getDescription());
			obj.put("latitude", node.getLatitude());
			obj.put("longitude", node.getLongitude());
			
			String jsonString = obj.toString();
			
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = jsonString.getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
			
			int status = con.getResponseCode();
			con.disconnect();
			if(status < 300) success = true;
			else if (status == 403) {
				WelcomePage.TimedOutSession();
				// Set success to true so error dialog wont be shown
				success = true;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}

	public static boolean updateUserAdmin(String name, String email, Boolean enabled, Boolean locked, Long id) {
		try {
			String queryParams = String.format("name=%s&email=%s&enabled=%b&locked=%b",name, email, enabled, locked);
			URL url = new URL(address + "/appuser/admin/" + id + "?" + queryParams);

			StringBuilder response = new StringBuilder();
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization","Bearer " + User.getToken());
			con.setDoOutput(true);


	        int status = con.getResponseCode();
			con.disconnect();
			
			if(status < 300) return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * User updates its own details
	 */
	public static boolean updateUser(String name, String email, String description) {
		try {
			String queryParams = String.format("name=%s&email=%s&description=%s",name, email, description);
			URL url = new URL(address + "/appuser/" + "5" + "?" + queryParams);

			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("PUT");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Authorization","Bearer " + User.getToken());
			con.setDoOutput(true);

	        int status = con.getResponseCode();
			con.disconnect();
			
			if(status < 300) return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
