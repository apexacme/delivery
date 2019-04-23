package com.example.template.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    SampleService sampleService;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String,Object> call(HttpServletRequest request,
                                               HttpServletResponse response,
                                               @PathVariable(value = "name") String name
    ) throws Exception {
        Map<String,Object> returnData = new HashMap<String,Object>();
        returnData.put("username",name);

        sampleService.call();

        return returnData;
    }
}
