package com.test.schoolservice.controller;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SchoolServiceController {
	 @Autowired
	    RestTemplate restTemplate;
	 
	    @RequestMapping(value = "/getSchoolDetails/{schoolname}", method = RequestMethod.GET)
	    public String getStudents(@PathVariable String schoolname)
	    {
	        System.out.println("Getting School details for " + schoolname);
	 
	        String response = restTemplate.exchange("http://student-service/getStudentDetailsForSchool/{schoolname}",
	                                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, schoolname).getBody();
	 
	        System.out.println("Response Received as " + response);
	 
	        return "School Name -  " + schoolname + " \n Student Details " + response;
	    }
	    
	    @RequestMapping(value = "/testAPI", method = RequestMethod.POST)
	    public String testAPI(String json)
	    {
	    	
	    	HttpHeaders headers = new HttpHeaders();
    	    headers.setContentType(MediaType.APPLICATION_JSON);
    	    headers.setBasicAuth("ctsibmbpmoncloud@gmail.com", "Bpmoncloud@5");
    	    
		String response = restTemplate.exchange(
				"https://cognizant-ipm.bpm.ibmcloud.com/bpm/dev/rest/bpm/wle/v1/process?action=start&bpdId=25.289b7b83-127d-4e26-9efa-5cbc8192172f&processAppId=2066.e9507f34-8e04-4513-861d-9a12c0b9376d&params=%7B%22newBusinessData%22%3A%7B%22kliId%22%3A%22ABC%22%2C%22userId%22%3A%22Dev%22%2C%22action%22%3A%22Start%22%7D%7D&parts=all",
				HttpMethod.POST, new HttpEntity<HttpHeaders>(headers), new ParameterizedTypeReference<String>() {
				}, json).getBody();

	        return response;
	    }
	    
	    @Bean
	    @LoadBalanced
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
}
