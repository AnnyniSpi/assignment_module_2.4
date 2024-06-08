package dev.annyni.service;

import dev.annyni.dao.EventDao;
import dev.annyni.entity.Event;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EventService {

    private final EventDao eventDao;

    public Event create(Event entity){
        return eventDao.save(entity);
    }

    public Event update(Event entity){
        eventDao.update(entity);
        return entity;
    }

    public boolean delete(Integer id){
        return eventDao.delete(id);
    }

    public Optional<Event> getById(Integer id){
        return eventDao.findByID(id);
    }

    public List<Event> getAll(){
        return eventDao.findAll();
    }
}
