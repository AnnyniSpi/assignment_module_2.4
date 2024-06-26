package dev.annyni.servlet.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.dao.FileDao;
import dev.annyni.dao.UserDao;
import dev.annyni.dao.impl.FileDaoImpl;
import dev.annyni.dao.impl.UserDaoImpl;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.entity.User;
import dev.annyni.service.FileService;
import dev.annyni.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/user")
@RequiredArgsConstructor
public class UserServlet extends HttpServlet {

    private final UserService userService = createService();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<User> user = userService.getById(id);
        if (user.isPresent()){
            resp.setStatus(HttpServletResponse.SC_OK);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), user.get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User readValue = objectMapper.readValue(req.getInputStream(), User.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (readValue != null) {
            User user = userService.create(readValue);
            resp.setStatus(HttpServletResponse.SC_CREATED);

            objectMapper.writeValue(resp.getWriter(), user);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User readValue = objectMapper.readValue(req.getInputStream(), User.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (readValue != null){
            User update = userService.update(readValue);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), update);
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

    private static UserService createService() {
        UserDao userDao = new UserDaoImpl();
        return new UserService(userDao);
    }

}
