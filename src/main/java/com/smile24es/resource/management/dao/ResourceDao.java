package com.smile24es.resource.management.dao;

import com.smile24es.resource.management.beans.Resource;
import java.util.List;

public interface ResourceDao {

    Resource saveResource(Resource resource);

    List<String> getDistinctCategories();

    List<String> getDistinctEvents();

    List<String> getDistinctFileTypes();

    List<Resource> getAllFileDetails();

}
