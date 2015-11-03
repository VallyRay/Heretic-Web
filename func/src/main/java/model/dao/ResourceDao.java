package model.dao;

import model.Resource;

import java.sql.SQLException;
import java.util.List;

public interface ResourceDao {
    void addResource(Resource resource) throws SQLException;

    void deleteResource(Resource resource) throws SQLException;

    Resource getResource(int id) throws SQLException;

    List<? extends Resource> getResourceList() throws SQLException;
}
