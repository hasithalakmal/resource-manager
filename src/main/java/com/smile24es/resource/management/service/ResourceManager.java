package com.smile24es.resource.management.service;

import com.smile24es.resource.management.beans.Resource;
import java.util.List;

public interface ResourceManager {

    Resource addResources(Resource resource);

    List<String> getAllCategories();

    List<String> getAllEvents();

    List<String> getAllFileTypes();

    List<Resource> getAllSavedFiles();

}
