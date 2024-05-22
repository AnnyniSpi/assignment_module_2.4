package dev.annyni.servlet.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.entity.User;
import dev.annyni.service.EventService;
import dev.annyni.service.FileService;
import dev.annyni.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/event")
public class EventServlet extends HttpServlet {

    private final EventService eventService = EventService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final FileService fileService = FileService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<Event> event = eventService.getById(id);

        if (event.isPresent()){
            resp.setStatus(HttpServletResponse.SC_OK);

            ObjectMapper objectMapper = new ObjectMapper();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), event.get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdString = req.getParameter("userId");
        String fileIdString = req.getParameter("fileId");
        int userId = Integer.parseInt(userIdString);
        int fileId = Integer.parseInt(fileIdString);

        Optional<User> user = userService.getById(userId);
        Optional<File> file = fileService.getById(fileId);

        if (user.isPresent() && file.isPresent()) {
            Event event = Event.builder()
                    .user(user.get())
                    .file(file.get())
                    .build();

            eventService.create(event);

            resp.setStatus(HttpServletResponse.SC_CREATED);

            ObjectMapper objectMapper = new ObjectMapper();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), file.get());
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<Event> event = eventService.getById(id);
        if (event.isPresent()){
            eventService.update(event.get());

            ObjectMapper objectMapper = new ObjectMapper();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), event.get());
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
}
