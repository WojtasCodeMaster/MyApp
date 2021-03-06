package com.test.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TweetController {

    @Autowired
    private Twitter twitter;

    @RequestMapping("/")
    public String home() {
        return "searchPage";
    }


    @RequestMapping("/tweetList")
    public String getTweet(@RequestParam(defaultValue = "spring") String search, Model model) {
        SearchResults searchResults = twitter.searchOperations().search(search);

        List<String> tweets = searchResults.getTweets()
                .stream()
                .map(Tweet::getText)
                .collect(Collectors.toList());

        model.addAttribute("tweets", tweets);
        return "resultTweetPage";
    }

    @RequestMapping("/result")
    public String getTweetObject(@RequestParam(defaultValue = "spring") String search, Model model) {

        SearchResults searchResults = twitter.searchOperations().search(search);
        List<Tweet> tweets = searchResults.getTweets();
        model.addAttribute("tweets", tweets);
        model.addAttribute("search", search);
        return "resultTweet2Page";
    }

    @RequestMapping(value = "/postSearch", method = RequestMethod.POST)
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String search = request.getParameter("search");

        if(search.toLowerCase().contains("śmieci")){
            redirectAttributes.addFlashAttribute("error","Wpisz co innego");
            return "redirect:/";
        }
        redirectAttributes.addAttribute("search", search);
        return "redirect:result";
    }
}