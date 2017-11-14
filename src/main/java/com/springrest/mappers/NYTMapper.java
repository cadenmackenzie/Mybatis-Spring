package com.springrest.mappers;

import com.springrest.model.nyt.Multimedia;
import com.springrest.model.nyt.NYTTopStories;
import com.springrest.model.nyt.Results;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;


@Mapper
public interface NYTMapper {

    // a query to check if top story already exists in db - using url
    String GET_STORY_ID = "SELECT id FROM `second_rds`.nyt_top_stories where url= #{url}";

    // a query to insert a new top story
    String INSERT_TOP_STORY = "INSERT INTO `second_rds`.nyt_top_stories (section, subsection, title," +
            "url, byline, item_type, updated_date, created_date, published_date, material_type_facet, kicker," +
            "short_url) " +
            "VALUES (#{section}, #{subsection}, #{title}, #{url}, #{byline}, " +
            "#{item_type}, #{updated_date}, #{created_date}, #{published_date}, #{material_type_facet}," +
            " #{kicker}, #{short_url})";

    // a query to get id from the latest top story that can be used to insert multimedia

    String INSERT_MULTIMEDIA = "INSERT INTO `second_rds`.multimedia (top_story_id, url, format, height, " +
            "width, type, subtype, caption, copyright)" +
            "VALUES (#{top_story_id}, #{url}, #{format}, #{height}, #{width}, #{type}, " +
            "#{subtype}, #{caption}, #{copyright})";

    String SELECT_ALL_TOP_STORIES = "Select * from `second_rds`.nyt_top_stories";

    String SELECT_WITH_SEARCH_PARAM = "Select * from `second_rds`.nyt_top_stories where title like #{query}";

    String SELECT_STORIES_BY_SECTION = "" +
            "select * from `second_rds`.nyt_top_stories " +
            "where section= #{section}" +
            "order by updated_date desc " +
            "limit 10";

    String CHECK_SECTION_EXISTS = "" +
            "select id from `second_rds`.nyt_top_stories " +
            "where section = #{section} " +
            "limit 1";


    @Select(GET_STORY_ID)
    public int getStoryId(String url);

    @Select(SELECT_ALL_TOP_STORIES)
    public ArrayList<Results> getAllTopStories();

    @Select(SELECT_WITH_SEARCH_PARAM)
    public ArrayList<Results> searchTopStories(String query);

    @Select(SELECT_STORIES_BY_SECTION)
    public ArrayList<Results> searchBySection(String section);

    @Select(CHECK_SECTION_EXISTS)
    public int checkIfSectionExists(String section);

    @Insert(INSERT_TOP_STORY)
    public int insertTopStory(Results story);

    @Insert(INSERT_MULTIMEDIA)
    public int insertMultimedia(Multimedia multimedia);


}
