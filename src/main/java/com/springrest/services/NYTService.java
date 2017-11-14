package com.springrest.services;

import com.springrest.exceptions.InvalidRequestException;
import com.springrest.mappers.NYTMapper;
import com.springrest.model.nyt.Multimedia;
import com.springrest.model.nyt.NYTTopStories;
import com.springrest.model.nyt.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@Service
public class NYTService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    NYTMapper mapper;




    public NYTTopStories loadAllStories(){
        NYTTopStories top = restTemplate.getForObject(
                "https://api.nytimes.com/svc/topstories/v2/home.json?apikey=01681978f72144b7ab97df6c0af4cd24",
                NYTTopStories.class);
        insertTopStories(top);

        return top;

    }

    public void insertTopStories(NYTTopStories stories){

        for (Results result : stories.getResults()){

            int id;

            try{
                id = mapper.getStoryId((result.getUrl()));
            }catch (Exception exc){
                id =0;
            }


            if (id == 0){

                int success = mapper.insertTopStory(result);

                if (success > 0){


                    id = mapper.getStoryId((result.getUrl()));

                    for (Multimedia media : result.getMultimedia()){
                        media.setTop_story_id(id);

                        mapper.insertMultimedia(media);

                    }

                }
            }

        }
    }

    public ArrayList<Results> getTopStories(String query){

        if (!query.equalsIgnoreCase("null")){
            return mapper.searchTopStories("%" + query + "%");
        }else {
            return mapper.getAllTopStories();
        }

    }

    public ArrayList<Results> topStoriesBySection(String section) throws InvalidRequestException {



        try {
            int tempId = mapper.checkIfSectionExists(section);
        } catch (Exception npe){
            throw new InvalidRequestException("Unknown section: " + section, 400);

        }

        return mapper.searchBySection(section);
    }


}
