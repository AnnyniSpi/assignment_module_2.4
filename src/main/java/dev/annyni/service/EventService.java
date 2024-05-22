package dev.annyni.service;

import dev.annyni.dao.EventDao;
import dev.annyni.dao.impl.EventDaoImpl;
import dev.annyni.entity.Event;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventService {

    private final EventDao eventDao = EventDaoImpl.getInstance();

    private static final EventService INSTANCE = new EventService();

    public static EventService getInstance(){
        return INSTANCE;
    }

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
