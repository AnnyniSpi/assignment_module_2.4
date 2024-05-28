package dev.annyni.servlet.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.dao.EventDao;
import dev.annyni.dao.impl.EventDaoImpl;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@WebServlet("/events")
@RequiredArgsConstructor
public class EventServletGetAll extends HttpServlet {

    private final EventService eventService = createService();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Event> events = eventService.getAll();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        System.out.println();

        if (!events.isEmpty()){
            objectMapper.writeValue(resp.getOutputStream(), events);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private static EventService createService() {
        EventDao eventDao = new EventDaoImpl();
        return new EventService(eventDao);
    }

}
