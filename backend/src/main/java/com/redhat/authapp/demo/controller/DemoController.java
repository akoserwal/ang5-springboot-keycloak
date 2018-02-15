package com.redhat.authapp.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akoserwa on 12/2/17.
 */
@RestController
public class DemoController {

    @RequestMapping(value = "/rest/{name}")
    public ResponseEntity home(@PathVariable String name){
        Map<String, String> model = new HashMap<>();
        model.put("data",name+"sucess");
        return new ResponseEntity(model, HttpStatus.OK);
    }
}
