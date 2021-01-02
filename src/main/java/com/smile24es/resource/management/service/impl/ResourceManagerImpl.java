package com.smile24es.resource.management.service.impl;

import com.smile24es.resource.management.beans.Resource;
import com.smile24es.resource.management.common.ErrorCodes;
import com.smile24es.resource.management.dao.ResourceDao;
import com.smile24es.resource.management.exception.ResourceManagerException;
import com.smile24es.resource.management.service.ResourceManager;
import com.smile24es.resource.management.service.StorageService;
import com.smile24es.resource.management.common.Constants;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResourceManagerImpl implements ResourceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceManagerImpl.class);

    @Autowired
    private StorageService storageService;

    @Autowired
    private ResourceDao resourceDao;

    @Value("${root.server.path}")
    private Path rootServerLocation;

    @Value("${app.directory.path}")
    private Path rootDirectoryLocation;

    @Override
    public Resource addResources(Resource resource) {
        LOGGER.info("Ready to add resources [{}]", resource);
        validateResources(resource);
        String subDirectoryPath = Constants.SLASH + resource.getCategory() + Constants.SLASH + resource.getEvent();
        String fileDirectoryPath = rootServerLocation.toString() + Constants.SLASH + rootDirectoryLocation + subDirectoryPath;
        long currentTimeMillis = System.currentTimeMillis();
        String originalFilename = resource.getFile().getOriginalFilename();
        if (StringUtils.isBlank(resource.getDisplayName())) {
            resource.setDisplayName(originalFilename);
        }
        String updatedFileName = currentTimeMillis + Constants.UNDERSCORE + originalFilename;
        resource.setRetrieveFilePath(rootDirectoryLocation + subDirectoryPath + Constants.SLASH + updatedFileName);
        Path path = createDirectory(fileDirectoryPath);
        storageService.store(resource.getFile(), path, updatedFileName);
        Resource savedResource = resourceDao.saveResource(resource);
        return savedResource;
    }

    @Override
    public List<String> getAllCategories() {
        return resourceDao.getDistinctCategories();
    }

    @Override
    public List<String> getAllEvents() {
        return resourceDao.getDistinctEvents();
    }

    @Override
    public List<String> getAllFileTypes() {
        return resourceDao.getDistinctFileTypes();
    }

    @Override
    public List<Resource> getAllSavedFiles() {
        return resourceDao.getAllFileDetails();
    }

    private void validateResources(Resource resource) {
        if (resource == null) {
            throw new ResourceManagerException(HttpStatus.BAD_REQUEST, ErrorCodes.INVALID_RESOURCE, "Null resource");
        }

        if (StringUtils.isBlank(resource.getCategory())) {
            throw new ResourceManagerException(HttpStatus.BAD_REQUEST, ErrorCodes.INVALID_RESOURCE, "Empty resource category");
        }

        if (StringUtils.isBlank(resource.getEvent())) {
            throw new ResourceManagerException(HttpStatus.BAD_REQUEST, ErrorCodes.INVALID_RESOURCE, "Empty resource event");
        }

        if (StringUtils.isBlank(resource.getFileType())) {
            throw new ResourceManagerException(HttpStatus.BAD_REQUEST, ErrorCodes.INVALID_RESOURCE, "Empty resource file type");
        }

//        if (StringUtils.isBlank(resource.getDisplayName())) {
//            throw new ResourceManagerException(HttpStatus.BAD_REQUEST, ErrorCodes.INVALID_RESOURCE, "Empty resource file display name");
//        }

        if (resource.getFile() == null) {
            throw new ResourceManagerException(HttpStatus.BAD_REQUEST, ErrorCodes.INVALID_RESOURCE, "Empty file uploading");
        }
    }

    private Path createDirectory(String file_directory_path) {
        Path path = null;
        try {
            path = Paths.get(file_directory_path);
            Files.createDirectories(path);
            LOGGER.info("Directory Path has created [{}]", path);
        } catch (IOException e) {
            LOGGER.error("Directory Path has not created [{}]", file_directory_path, e);
        }
        return path;
    }
}
