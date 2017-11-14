package com.springrest.rest_controllers;

import java.util.ArrayList;

import com.springrest.exceptions.InvalidRequestException;
import com.springrest.model.CustomResponseObject;
import com.springrest.model.User;
import com.springrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //Get
    @RequestMapping(method = RequestMethod.GET, value ="/")
    public ArrayList<User> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value ="/{id}")
    public ResponseEntity<CustomResponseObject> getById(@PathVariable(value="id")int id)
            throws InvalidRequestException {


        User usrs = null;
        CustomResponseObject response = new CustomResponseObject();

        try{
            usrs = userService.getById(id);
            response.setMessage("Success");
            response.setStatus_code(200);
            response.setData(usrs);

            return new ResponseEntity(response, HttpStatus.OK);

        }catch(InvalidRequestException e){
            throw e;

        }
    }

    @RequestMapping(method = RequestMethod.GET, value ="/manual")
    public ArrayList<User> getUsersManually() {
        return userService.getAllUsersManually();
    }

    //Create
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public User addNew(@RequestBody User user) {
        return userService.addNew(user);
    }

    //Update
    @RequestMapping(method = RequestMethod.PATCH, value = "/")
    public User updateById(@RequestBody User user) {
        return userService.updateById(user);
    }

    //Delete
    @RequestMapping(method= RequestMethod.DELETE, value = "/")
    public User deleteById(@RequestParam User user){
        return userService.deleteById(user.getId());
    }
}
