package com.smile24es.resource.management.controller;

import com.smile24es.resource.management.beans.Resource;
import com.smile24es.resource.management.service.ResourceManager;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResourceManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceManagementController.class);

    @Autowired
    private ResourceManager resourceManager;

    @PostMapping("/resources")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public ModelAndView saveResources(
        OAuth2AuthenticationToken authentication, @ModelAttribute Resource resource, Model model) {
        LOGGER.info("Ready to save resource [{}]", resource);
        Resource populatedResource = resourceManager.addResources(resource);
        LOGGER.info("Resource has persisted properly [{}]", populatedResource);
        Map modelMap = new HashMap();
        modelMap.put("resource",new Resource());
        modelMap.put("categories",resourceManager.getAllCategories());
        modelMap.put("events",resourceManager.getAllEvents());
        modelMap.put("fileTypes",resourceManager.getAllFileTypes());
        modelMap.put("details",authentication.getPrincipal().getAttributes());
        return new ModelAndView("add_resource" , modelMap);
    }
}
