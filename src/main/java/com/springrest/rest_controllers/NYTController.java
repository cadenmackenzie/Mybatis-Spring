package com.springrest.rest_controllers;

import com.springrest.exceptions.InvalidRequestException;
import com.springrest.model.CustomResponseObject;
import com.springrest.model.nyt.NYTTopStories;
import com.springrest.model.nyt.Results;
import com.springrest.services.NYTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/nyt")
public class NYTController {

    @Autowired
    NYTService service;


    //Get
    @RequestMapping("/topstories/load")
    public ResponseEntity<NYTTopStories> loadTopStories (){
        return new ResponseEntity(service.loadAllStories(), HttpStatus.OK);
    }

    @RequestMapping("/topstories/{section:.+}")
    public ResponseEntity<CustomResponseObject> topStoriesBySection(@PathVariable("section") String section)
            throws InvalidRequestException {

        ArrayList<Results> nyt = null;
        CustomResponseObject response = new CustomResponseObject();

        try{
            nyt = service.topStoriesBySection(section);
            response.setMessage("Success");
            response.setStatus_code(200);
            response.setData(nyt);

            return new ResponseEntity(response, HttpStatus.OK);


        } catch (InvalidRequestException snf){
            throw snf;
//            response.setMessage("error");
//            response.setStatus_code(400);
//            response.setError(snf);
//            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        //return new ResponseEntity(service.topStoriesBySection(section), HttpStatus.OK);
    }

    //Get
    @RequestMapping("/topstories")
    public ArrayList<Results> getTopStories (@RequestParam(value = "query", required = false, defaultValue = "null") String query){
        return service.getTopStories(query);
    }



//    //Get
//    @RequestMapping("/topstories")
//    public NYTTopStories getTopStories (@RequestParam(value = "fetch", required = false, defaultValue = "false")
//                                                String fetch,
//                                        @RequestParam(value = "query", required = false) String query){
//        return service.loadAllStories(fetch, query);
//    }

}
