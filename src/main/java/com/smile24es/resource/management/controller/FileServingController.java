package com.smile24es.resource.management.controller;

import com.smile24es.resource.management.beans.Resource;
import com.smile24es.resource.management.service.ResourceManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileServingController {

    @Autowired
    private ResourceManager resourceManager;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public ModelAndView userDetails(OAuth2AuthenticationToken authentication) {
        return new ModelAndView("userProfile" , Collections.singletonMap("details", authentication.getPrincipal().getAttributes()));
    }

    @GetMapping("/sample")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public ModelAndView samplePage(OAuth2AuthenticationToken authentication) {
        return new ModelAndView("sample" , Collections.singletonMap("details", authentication.getPrincipal().getAttributes()));
    }

    @GetMapping("/search_resource")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public ModelAndView searchResporces(OAuth2AuthenticationToken authentication) {
        List<Resource> allSavedFiles = resourceManager.getAllSavedFiles();
        Map modelMap = new HashMap();
        modelMap.put("details",authentication.getPrincipal().getAttributes());
        modelMap.put("resourses",allSavedFiles);
        return new ModelAndView("search_resource" ,modelMap);
    }

    @GetMapping("/add_resource")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public ModelAndView addResources(OAuth2AuthenticationToken authentication,  @ModelAttribute Resource resource, Model model) {
        Map modelMap = new HashMap();
        modelMap.put("resource",resource);
        modelMap.put("categories",resourceManager.getAllCategories());
        modelMap.put("events",resourceManager.getAllEvents());
        modelMap.put("fileTypes",resourceManager.getAllFileTypes());
        modelMap.put("details",authentication.getPrincipal().getAttributes());
        return new ModelAndView("add_resource" , modelMap);
    }
}
