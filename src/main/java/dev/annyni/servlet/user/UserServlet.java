package dev.annyni.servlet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.entity.User;
import dev.annyni.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<User> user = userService.getById(id);
        if (user.isPresent()){
            resp.setStatus(HttpServletResponse.SC_OK);

            ObjectMapper objectMapper = new ObjectMapper();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), user.get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        User user = User.builder()
                .name(name)
                .build();

        userService.create(user);

        resp.setStatus(HttpServletResponse.SC_CREATED);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(json);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<User> user = userService.getById(id);
        if (user.isPresent()){
            userService.update(user.get());

            ObjectMapper objectMapper = new ObjectMapper();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), user.get());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<User> user = userService.getById(id);
        if (user.isPresent()){
            userService.delete(id);

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
