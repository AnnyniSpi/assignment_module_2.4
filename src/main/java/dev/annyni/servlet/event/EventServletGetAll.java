package dev.annyni.servlet.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/events")
public class EventServletGetAll extends HttpServlet {

    private final EventService eventService = EventService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Event> events = eventService.getAll();

        if (!events.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), events);
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

    }


}