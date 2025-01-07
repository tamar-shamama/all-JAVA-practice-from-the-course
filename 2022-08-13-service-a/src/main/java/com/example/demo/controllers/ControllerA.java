package com.example.demo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ControllerA {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	
	@GetMapping("/service/a")
	public String handleA() {
		return "from Service A";
	}
	
	
	
	////============= chose instance manually
	
	
	/**get uri of some instance of server b - no load balancer
	 * @param serviceId
	 * @return URI
	 */
	private URI getInstanceBaseUri(String serviceId) {
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
		for (ServiceInstance serviceInstance : instances) {
			System.out.println(serviceInstance);
		}
		return instances.get(0).getUri();
	}
	
	
	@GetMapping("/service/a/callToB")
	public String callToServiceB() {
		
		// service b name
		String serviceId = "service-b";
		
		// get service b uri
		URI baseUri = getInstanceBaseUri(serviceId);
		
		// get the full path to an end point in service b
		String url = baseUri + "/service/b";
		
		// get the answer from service b
		String ans = restTemplate.getForObject(url, String.class);
		
		// return the answer
		return "b was called. it's answer is: " + ans;
	}
	
	
	
	////============= let LoadBalancer chose

	
	
	/**get uri of some instance of server b - with load balancer
	 * @param serviceId
	 * @return URI
	 */
	private URI getInstanceBaseUri2(String serviceId) {
		ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
		return serviceInstance.getUri();
	}
	
	
	@GetMapping("/service/a/callToB2")
	public String callToServiceB2() {
		
		String serviceId = "service-b";
		URI baseUri = getInstanceBaseUri2(serviceId);
		String url = baseUri + "/service/b";
		String ans = restTemplate.getForObject(url, String.class);
		return "b was called. it's answer is: " + ans;
	}
	
	
	
	////============= let aspect chose (iteration on the RestTemplate bean!)
	
	
	@HystrixCommand(fallbackMethod = "callToB3FallBack")
	@GetMapping("/service/a/callToB3")
	public String callToServiceB3() {
		
		String url = "http://service-b/service/b";
		String ans = restTemplate.getForObject(url, String.class);
		return "b was called. it's answer is: " + ans;
	}
	
	
	public String callToB3FallBack(Throwable t) {
		return "service B not available: " + t;
	}

	
	
	
	

}
