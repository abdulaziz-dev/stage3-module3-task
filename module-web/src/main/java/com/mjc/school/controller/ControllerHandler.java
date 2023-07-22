package com.mjc.school.controller;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.impl.AuthorController;
import com.mjc.school.controller.impl.NewsController;
import com.mjc.school.controller.impl.TagController;
import com.mjc.school.service.dto.AuthorRequestDTO;
import com.mjc.school.service.dto.NewsRequestDTO;
import com.mjc.school.service.dto.NewsResponseDTO;
import com.mjc.school.service.dto.TagRequestDTO;
import com.mjc.school.service.exception.ErrorCodes;
import com.mjc.school.service.exception.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ControllerHandler {
    private final NewsController newsController;
    private final AuthorController authorController;
    private final TagController tagController;

    @Autowired
    public ControllerHandler(NewsController newsController, AuthorController authorController, TagController tagController) {
        this.newsController = newsController;
        this.authorController = authorController;
        this.tagController = tagController;
    }

    @CommandHandler( command = 1)
    public void getAllNews(){
        System.out.println(Operations.GET_ALL_NEWS.getOperation());
        newsController.readAll().forEach(System.out::println);
    }

    @CommandHandler( command = 2)
    public void getNewsById(){
        System.out.println(Operations.GET_NEWS_BY_ID.getOperation());
        Long id = readNumber("News Id");
        System.out.println(newsController.readById(id));
    }

    @CommandHandler(command = 3)
    public void createNews(){
        System.out.println(Operations.CREATE_NEWS.getOperation());
        NewsRequestDTO dto = readNewsValues(null);
        System.out.println(newsController.create(dto));
    }

    @CommandHandler(command = 4)
    public void updateNews(){
        System.out.println(Operations.UPDATE_NEWS.getOperation());
        Long id = readNumber("News id");
        NewsRequestDTO dto = readNewsValues(id);
        System.out.println(newsController.update(dto));
    }

    @CommandHandler(command = 5)
    public void deleteNewsById(){
        Long id = readNumber("News id");
        System.out.println(newsController.deleteById(id));
    }

    @CommandHandler( command = 6)
    public void getAllAuthors(){
        System.out.println(Operations.GET_ALL_AUTHORS.getOperation());
        authorController.readAll().forEach(System.out::println);
    }

    @CommandHandler( command = 7)
    public void getAuthorById(){
        System.out.println(Operations.GET_AUTHOR_BY_ID.getOperation());
        Long id = readNumber("Author Id");
        System.out.println(authorController.readById(id));
    }

    @CommandHandler(command = 8)
    public void createAuthor(){
        System.out.println(Operations.CREATE_AUTHOR.getOperation());
        AuthorRequestDTO dto = readAuthorValues(null);
        System.out.println(authorController.create(dto));
    }

    @CommandHandler(command = 9)
    public void updateAuthor(){
        System.out.println(Operations.UPDATE_AUTHOR.getOperation());
        Long id = readNumber("Author id");
        AuthorRequestDTO dto = readAuthorValues(id);
        System.out.println(authorController.update(dto));
    }

    @CommandHandler(command = 10)
    public void deleteAuthorById(){
        System.out.println(Operations.DELETE_AUTHOR_BY_ID.getOperation());
        Long id = readNumber("Author id");
        System.out.println(authorController.deleteById(id));
    }


    @CommandHandler( command = 11)
    public void getAllTags(){
        System.out.println(Operations.GET_ALL_TAGS.getOperation());
        tagController.readAll().forEach(System.out::println);
    }

    @CommandHandler( command = 12)
    public void getTagById(){
        System.out.println(Operations.GET_TAG_BY_ID.getOperation());
        Long id = readNumber("Tag Id");
        System.out.println(tagController.readById(id));
    }

    @CommandHandler(command = 13)
    public void createTag(){
        System.out.println(Operations.CREATE_TAG.getOperation());
        TagRequestDTO dto = readTagValues(null);
        System.out.println(tagController.create(dto));
    }

    @CommandHandler(command = 14)
    public void updateTag(){
        System.out.println(Operations.UPDATE_TAG.getOperation());
        Long id = readNumber("Tag id");
        TagRequestDTO dto = readTagValues(id);
        System.out.println(tagController.update(dto));
    }

    @CommandHandler(command = 15)
    public void deleteTagById(){
        System.out.println(Operations.DELETE_TAG_BY_ID.getOperation());
        Long id = readNumber("Tag id");
        System.out.println(tagController.deleteById(id));
    }

    @CommandHandler(command = 16)
    public void readAuthorByNewsId(){
        System.out.println(Operations.READ_AUTHOR_BY_NEWS_ID.getOperation());
        Long newsId = readNumber("News id");
        Long authorId = newsController.readById(newsId).authorId();
        System.out.println(authorController.readById(authorId));

    }

    @CommandHandler(command = 17)
    public void readTagByNewsId(){
        System.out.println(Operations.READ_TAG_BY_NEWS_ID.getOperation());
        Long newsId = readNumber("News id");
        System.out.println(newsController.readById(newsId).tagsSet().toString());
    }

    @CommandHandler(command = 18)
    public void readNewsByParams(){
        Scanner sc = new Scanner(System.in);
        System.out.println(Operations.READ_NEWS_BY_PARAMS.getOperation());
        System.out.println("[Optional] Enter tag id:");
        Long tagId;
        String tagIdString = sc.nextLine();
        if (!tagIdString.isBlank()){
            tagId = Long.parseLong(tagIdString);
        } else {
            tagId = null;
        }
        System.out.println("[Optional] Enter tag name:");
        String tagName = sc.nextLine();
        System.out.println("[Optional] Enter author name:");
        String authorName = sc.nextLine();
        System.out.println("[Optional] Enter news title: ");
        String title = sc.nextLine();
        System.out.println("[Optional] Enter news content:");
        String content = sc.nextLine();

        List<NewsResponseDTO> news = newsController.readByParams(tagId, tagName, authorName, title, content);
        if (news.isEmpty())  {
            System.out.println("Cannot find news with provided parameters.");
        } else {
            news.forEach(System.out::println);
        }
    }

    private Long readNumber(String type) {
        System.out.println("Enter " + type +":");
        Scanner scr = new Scanner(System.in);
        try {
            return scr.nextLong();
        } catch (Exception e){
            throw new ValidatorException(String.format(ErrorCodes.CHECK_SHOULD_BE_NUMBER.getMessage(), type));
        }
    }

    private TagRequestDTO readTagValues(Long id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter tag name: ");
        String name = sc.nextLine();
        return new TagRequestDTO(id, name);
    }

    private AuthorRequestDTO readAuthorValues(Long id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter author name: ");
        String name = sc.nextLine();
        return new AuthorRequestDTO(id, name);
    }

    private NewsRequestDTO readNewsValues(Long id){
        Scanner scr = new Scanner(System.in);
        System.out.println("Enter news title:");
        String title = scr.nextLine();
        System.out.println("Enter news content:");
        String content = scr.nextLine();
        Long authorId = readNumber("Author Id");
        System.out.println("[Optional] Enter tag ids (seperated by comma):  ");
        String tags = scr.nextLine();
        Set<Long> tagSet = new HashSet<>();
        if (!tags.isBlank()){
            String[] tag_ids = tags.split(",");
            try {
                for (String s: tag_ids){
                    tagSet.add(Long.parseLong(s.trim()));
                }
            } catch (InputMismatchException e){
                throw new ValidatorException(String.format(ErrorCodes.CHECK_SHOULD_BE_NUMBER.getMessage(), "Tag id"));
            }
        }
        return new NewsRequestDTO(id, title, content, authorId, tagSet);
    }
}
