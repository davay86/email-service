package com.babcock.integration.asserter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class WaitForService extends WaitUntilAsserter {

    private String url;
    private RestTemplate restTemplate;

    public WaitForService(String url, RestTemplate restTemplate) {
        super();
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    protected boolean execute() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if(response.getStatusCode().is2xxSuccessful()) {
                return true;
            }else {
                System.out.print(".");
                return false;
            }
        }catch (ResourceAccessException ex) {
            System.out.print(".");
            return false;
        }catch (Exception ex) {
            System.err.println("Service Not Found Retrying : "+ex);
            return false;
        }
    }

    @Override
    protected String getTaskName() {
        return "WaitForService";
    }

    @Override
    protected String getFailureMessage() {
        return url+" not available";
    }
}
