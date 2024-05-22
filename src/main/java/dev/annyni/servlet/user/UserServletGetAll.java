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
import java.util.List;

@WebServlet("/users")
public class UserServletGetAll extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userService.getAll();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (!users.isEmpty()){
            objectMapper.writeValue(resp.getWriter(), users);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
