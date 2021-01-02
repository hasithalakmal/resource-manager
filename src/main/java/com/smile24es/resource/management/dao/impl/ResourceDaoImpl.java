package com.smile24es.resource.management.dao.impl;

import static com.smile24es.resource.management.dao.impl.DaoConstants.AUTH_LEVEL;
import static com.smile24es.resource.management.dao.impl.DaoConstants.CATEGORY;
import static com.smile24es.resource.management.dao.impl.DaoConstants.CREATED_TS;
import static com.smile24es.resource.management.dao.impl.DaoConstants.EVENT;
import static com.smile24es.resource.management.dao.impl.DaoConstants.EVENT_DATE;
import static com.smile24es.resource.management.dao.impl.DaoConstants.FILE_DESCRIPTION;
import static com.smile24es.resource.management.dao.impl.DaoConstants.FILE_FORMAT;
import static com.smile24es.resource.management.dao.impl.DaoConstants.FILE_ID;
import static com.smile24es.resource.management.dao.impl.DaoConstants.FILE_NAME;
import static com.smile24es.resource.management.dao.impl.DaoConstants.GENERATED_FILE_PATH;
import static com.smile24es.resource.management.dao.impl.DaoConstants.STATUS;

import com.smile24es.resource.management.beans.Resource;
import com.smile24es.resource.management.dao.ResourceDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceDaoImpl extends GenericDaoImpl implements ResourceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceDaoImpl.class);

    @Value("${resource.host.url}")
    private String hostUrl;

    private static final String INSERT_FILE_DATA = "INSERT INTO FILE_DATA (CATEGORY, EVENT, FILE_NAME, FILE_DESCRIPTION,"
        + " EVENT_DATE, FILE_FORMAT, AUTH_LEVEL, GENERATED_FILE_PATH, STATUS, CREATED_BY) "
        + "VALUES (:category, :event, :fileName, :fileDescription, :eventDate, :fileFormat, :authLevel, :generatedFilePath, :status, :createdBy)";

    private static final String SELECT_DISTINCT_CATEGORIES = "SELECT DISTINCT CATEGORY FROM FILE_DATA";

    private static final String SELECT_DISTINCT_EVENTS = "SELECT DISTINCT EVENT FROM FILE_DATA";

    private static final String SELECT_DISTINCT_FILE_FORMAT = "SELECT DISTINCT FILE_FORMAT FROM FILE_DATA";

    private static final String SELECT_FILE_DATA = "SELECT FILE_ID, FILE_NAME, FILE_DESCRIPTION, CATEGORY, EVENT, FILE_FORMAT, "
        + "GENERATED_FILE_PATH, EVENT_DATE, AUTH_LEVEL, STATUS, CREATED_TS, CREATED_BY FROM FILE_DATA";

    @Override
    public Resource saveResource(Resource resource) {
            LOGGER.debug("Preparing to persist resource. resource {}", resource);

            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());

            KeyHolder holder = new GeneratedKeyHolder();

            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue(DaoConstants.PARAM_CATEGORY, resource.getCategory());
            parameters.addValue(DaoConstants.PARAM_EVENT, resource.getEvent());
            parameters.addValue(DaoConstants.PARAM_FILE_NAME, resource.getDisplayName());
            parameters.addValue(DaoConstants.PARAM_FILE_DESCRIPTION, resource.getDescription());
            parameters.addValue(DaoConstants.PARAM_EVENT_DATE, resource.getEventDate());
            parameters.addValue(DaoConstants.PARAM_FILE_FORMAT, resource.getFileType());
            parameters.addValue(DaoConstants.PARAM_OAUTH_LEVEL, resource.getAuthLevel());
            parameters.addValue(DaoConstants.PARAM_GENERATED_FILE_PATH, resource.getRetrieveFilePath());
            parameters.addValue(DaoConstants.PARAM_STATUS, resource.getStatus());
            parameters.addValue(DaoConstants.PARAM_CREATED_BY, resource.getCreatedBy());

            namedParameterJdbcTemplate.update(INSERT_FILE_DATA, parameters, holder);
            resource.setId(holder.getKey() == null ? null : holder.getKey().longValue());
            return resource;

    }

    @Override
    public List<String> getDistinctCategories() {
        LOGGER.info("Preparing to retrieve all categories");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
        final List<String> listOfCategories = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_DISTINCT_CATEGORIES,
            (RowMapper<String>) (rs, rowNum) -> {
                String category = populateDistinctLists(rs, CATEGORY);
                listOfCategories.add(category);
                return null;
            });
        return listOfCategories;
    }

    @Override
    public List<String> getDistinctEvents() {
        LOGGER.info("Preparing to retrieve all events");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
        final List<String> listOfEvents = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_DISTINCT_EVENTS,
            (RowMapper<String>) (rs, rowNum) -> {
                String category = populateDistinctLists(rs, EVENT);
                listOfEvents.add(category);
                return null;
            });
        return listOfEvents;
    }

    @Override
    public List<String> getDistinctFileTypes() {
        LOGGER.info("Preparing to retrieve all fileTypes");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
        final List<String> listOfFileTypes = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_DISTINCT_FILE_FORMAT,
            (RowMapper<String>) (rs, rowNum) -> {
                String category = populateDistinctLists(rs, FILE_FORMAT);
                listOfFileTypes.add(category);
                return null;
            });
        return listOfFileTypes;
    }

    @Override
    public List<Resource> getAllFileDetails() {
        LOGGER.info("Preparing to retrieve all fileTypes");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
        final List<Resource> listOfFileTypes = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_FILE_DATA,
            (RowMapper<Resource>) (rs, rowNum) -> {
                Resource resource = populateFileDetails(rs);
                listOfFileTypes.add(resource);
                return null;
            });
        return listOfFileTypes;
    }

    private Resource populateFileDetails(ResultSet resultSet) throws SQLException {
        Resource resource = new Resource();
        resource.setId(resultSet.getLong(FILE_ID));
        resource.setDisplayName(resultSet.getString(FILE_NAME));
        resource.setDescription(resultSet.getString(FILE_DESCRIPTION));
        resource.setCategory(resultSet.getString(CATEGORY));
        resource.setEvent(resultSet.getString(EVENT));
        resource.setFileType(resultSet.getString(FILE_FORMAT));
        resource.setRetrieveFilePath(resultSet.getString(GENERATED_FILE_PATH));
        resource.setEventDate(resultSet.getString(EVENT_DATE));
        resource.setCreatedTs(resultSet.getTimestamp(CREATED_TS).toString());
        resource.setAuthLevel(resultSet.getString(AUTH_LEVEL));
        resource.setStatus(resultSet.getString(STATUS));
        resource.setHostUrl(hostUrl);
        return resource;
    }

    private String populateDistinctLists(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getString(columnName);

    }
}
