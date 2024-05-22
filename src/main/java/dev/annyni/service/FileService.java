package dev.annyni.service;

import dev.annyni.dao.FileDao;
import dev.annyni.dao.impl.FileDaoImpl;
import dev.annyni.entity.File;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileService {

    private final FileDao fileDao = FileDaoImpl.getInstance();

    private static final FileService INSTANCE = new FileService();

    public static FileService getInstance(){
        return INSTANCE;
    }

    public File create(File entity){
        return fileDao.save(entity);
    }

    public File update(File entity){
        fileDao.update(entity);
        return entity;
    }

    public boolean delete(Integer id){
        return fileDao.delete(id);
    }

    public Optional<File> getById(Integer id){
        return fileDao.findByID(id);
    }

    public List<File> getAll(){
        return fileDao.findAll();
    }

}
