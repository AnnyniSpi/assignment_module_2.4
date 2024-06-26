package dev.annyni.servlet.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.dao.EventDao;
import dev.annyni.dao.impl.EventDaoImpl;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.entity.User;
import dev.annyni.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/event")
@RequiredArgsConstructor
public class EventServlet extends HttpServlet {

    private final EventService eventService = createService();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<Event> event = eventService.getById(id);

        if (event.isPresent()){
            resp.setStatus(HttpServletResponse.SC_OK);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), event.get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Event readValue = objectMapper.readValue(req.getInputStream(), Event.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (readValue != null) {
            Event event = eventService.create(readValue);

            resp.setStatus(HttpServletResponse.SC_CREATED);

            objectMapper.writeValue(resp.getWriter(), event);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Event readValue = objectMapper.readValue(req.getInputStream(), Event.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (readValue != null){
            Event update = eventService.update(readValue);

            objectMapper.writeValue(resp.getWriter(), update);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<Event> event = eventService.getById(id);

        if (event.isPresent()){
            eventService.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    static Integer getId(HttpServletRequest req, HttpServletResponse resp) {
        String fileIdString = req.getParameter("id");
        return Integer.parseInt(fileIdString);
    }

    private static EventService createService() {
        EventDao eventDao = new EventDaoImpl();
        return new EventService(eventDao);
    }
}
