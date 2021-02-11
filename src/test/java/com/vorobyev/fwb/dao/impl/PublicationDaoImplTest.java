package com.vorobyev.fwb.dao.impl;

import com.vorobyev.fwb.dao.PublicationDao;
import com.vorobyev.fwb.entity.Publication;
import com.vorobyev.fwb.exception.DaoException;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class PublicationDaoImplTest {

    PublicationDao dao = PublicationDaoImpl.getInstance();

    @Test
    public void testFindPublicationById() {
        try {
            Optional<Publication> optionalPublication = dao.findPublicationById(1);
            System.out.println(optionalPublication.get().toString());
        } catch (DaoException exception) {
            exception.printStackTrace();
        }
    }
}