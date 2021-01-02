package com.smile24es.resource.management.dao.impl;

import com.smile24es.resource.management.dao.GenericDao;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * The base dao class for generic functionality
 *
 * @author hasithagamage
 * @date 04/03/2020
 */
@Repository("genericDao")
public class GenericDaoImpl extends JdbcDaoSupport implements GenericDao {

    @Autowired
    @Qualifier("rmsDataSource")
    private DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }


}
