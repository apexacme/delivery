package com.example.template.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    SampleService sampleService;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public SampleUser call(HttpServletRequest request,
                                               HttpServletResponse response,
                                               @PathVariable(value = "name") String name
    ) throws Exception {
        
    	return sampleService.call(name);
    }
    
    
    @RequestMapping(value = "/rest", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public SampleUser restCall(HttpServletRequest request,
                                               HttpServletResponse response
    ) throws Exception {
//        Map<String,Object> returnData = new HashMap<String,Object>();
        return sampleService.restCall("restCall");
    }
    
}
