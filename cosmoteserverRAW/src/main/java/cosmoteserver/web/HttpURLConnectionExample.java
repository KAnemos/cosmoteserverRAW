/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosmoteserver.web;

/**
 *
 * @author koutote
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample {

	private final String USER_AGENT = "Mozilla/5.0";


	// HTTP GET request
	public int sendGet(String ip_box,String msgcom) throws Exception {

		//String url = "http://"+ip_box+":8081/STM32F7xx.html?cmd="+msgcom;
		//String url = "http://"+ip_box+":80/STM32F7xx.html?cmd="+msgcom;
		String url = "http://"+ip_box+":65497/Arduino.html?cmd="+msgcom;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
                return responseCode;

	}
    
}
