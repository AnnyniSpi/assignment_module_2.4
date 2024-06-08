package dev.annyni.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.dao.EventDao;
import dev.annyni.dao.impl.EventDaoImpl;
import dev.annyni.dto.EventDto;
import dev.annyni.entity.Event;
import dev.annyni.mapper.EventMapper;
import dev.annyni.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/events/*")
public class EventServlet extends HttpServlet {

    private final EventService eventService = createEventService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final EventMapper eventMapper = EventMapper.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty()){
            List<Event> events = eventService.getAll();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            System.out.println();

            if (!events.isEmpty()){
                List<EventDto> eventsDto = events.stream()
                        .map(eventMapper::toDto)
                        .toList();

                System.out.println();

                objectMapper.writeValue(resp.getOutputStream(), eventsDto);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), "Not found Events!");
            }
        }else {
            Integer id = getId(pathInfo);
            Optional<Event> event = eventService.getById(id);

            if (event.isPresent()) {
                Optional<EventDto> eventDto = event.stream()
                        .map(eventMapper::toDto)
                        .findFirst();

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                System.out.println();

                objectMapper.writeValue(resp.getWriter(), eventDto.get());
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), "Not found Event by ID!");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        EventDto eventDto = objectMapper.readValue(req.getInputStream(), EventDto.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (eventDto != null) {
            Event event = eventMapper.toEntity(eventDto);
            event = eventService.create(event);
            EventDto createdEventDto = eventMapper.toDto(event);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(), createdEventDto);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), "Internal server error");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Event readValue = objectMapper.readValue(req.getInputStream(), Event.class);
        EventDto eventDto = objectMapper.readValue(req.getInputStream(), EventDto.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (eventDto != null){
            Event event = eventMapper.toEntity(eventDto);
            Event updated = eventService.update(event);
            EventDto updatedDto = eventMapper.toDto(updated);

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), updatedDto);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), "The Event was not found in the DB. " +
                                                      "It's impossible to change the data!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        Integer id = getId(pathInfo);

        Optional<Event> event = eventService.getById(id);

        if (event.isPresent()){
            eventService.delete(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            objectMapper.writeValue(resp.getWriter(), "Event deleted!");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), "Not found Event by ID!");
        }
    }

    private static Integer getId(
//            HttpServletRequest req, HttpServletResponse resp,
            String pathInfo) {
//        String fileIdString = req.getParameter("id");
//        return Integer.parseInt(fileIdString);

        String[] pathArray = pathInfo.split("/");
        if (pathArray.length == 2){
            return Integer.parseInt(pathArray[1]);
        } else {
            throw new RuntimeException("Invalid path!");
        }

    }

    static EventService createEventService() {
        EventDao eventDao = new EventDaoImpl();
        return new EventService(eventDao);
    }
}
