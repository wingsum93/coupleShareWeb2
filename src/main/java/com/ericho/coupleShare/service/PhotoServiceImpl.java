package com.ericho.coupleShare.service;

import com.ericho.coupleShare.dao.PhotoDao;
import com.ericho.coupleShare.model.Photo;
import com.ericho.coupleShare.model.PhotoType;
import com.ericho.coupleShare.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by steve_000 on 15/4/2017.
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoDao dao;

    @Override
    public List<Photo> getAllPhoto(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public Photo findById(Integer id) {
        Optional<Photo> x = dao.findById(id);
        return x.orElse(null);
    }

    @Override
    public Photo addNewPhoto(Date date, File photoFile, String username) {

        Photo p = Photo.fromNormal(date, photoFile, username);
        return dao.save(p);
    }

    @Override
    public Photo addNewStatusPhoto(Date date, File photoFile, String username) {

        Photo p = Photo.fromStatusPhoto(date, photoFile, username);
        return dao.save(p);
    }

    @Override
    public Photo findByUsernameAndUUid(String username, String uuid) {

        List<Photo> ps = dao.findByUsernameAndUuid(username, uuid);
        if (ps.isEmpty())
            return null;

        return ps.get(0);
    }


    @Override
    public Photo getStatusPhotoByUUID(String username, String uuid) {
        List<Photo> ps = dao.findByUuidAndPhotoType(uuid, PhotoType.StatusPhoto);
        if (ps.isEmpty()) return null;
        else return ps.get(0);
    }

    @Override
    public List<Photo> findByUsernames(List<String> usernames) {
        List<Photo> res = dao.findByUsernameIn(usernames,
                Sort.by(Sort.Direction.DESC, "id"));
        return ArrayUtil.parse(res);
    }

    @Override
    public List<Photo> findByUsernamesAndPhotoType(List<String> usernames, PhotoType type) {
        List<Photo> res = dao.findByUsernameAndPhotoTypeOrderByIdDesc(usernames, type);
        return ArrayUtil.parse(res);
    }

    @Override
    public boolean deletePhotoByUUID(String username, String uuid, File photoDir) {
        Photo p = this.findByUsernameAndUUid(username, uuid);
        if (p == null) throw new IllegalStateException("photo File Not Found!!");

        File photoFile = new File(photoDir, p.getPhotoName());

        boolean isDeleted = photoFile.delete();

        if (isDeleted) {
            this.dao.delete(p);
        }

        return isDeleted;
    }

    @Override
    public boolean deleteAllPhoto(String username, File photoDir) {
        List<Photo> items = dao.findByUsernameAndPhotoTypeOrderByCollectDateDesc(Collections.singletonList(username), PhotoType.Normal);

        for (Photo p : items) {
            File photoFile = new File(photoDir, p.getPhotoName());

            boolean isDeleted = photoFile.delete();

            if (isDeleted) {
                this.dao.delete(p);
            }
        }
        return true;
    }


}
