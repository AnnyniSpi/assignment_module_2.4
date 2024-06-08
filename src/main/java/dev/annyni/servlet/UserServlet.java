package dev.annyni.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.dao.UserDao;
import dev.annyni.dao.impl.UserDaoImpl;
import dev.annyni.dto.UserDto;
import dev.annyni.entity.User;
import dev.annyni.mapper.UserMapper;
import dev.annyni.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

    private final UserService userService = createUserService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty()){
            List<User> users = userService.getAll();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            if (!users.isEmpty()){
                List<UserDto> usersDto = users.stream()
                        .map(userMapper::toDto)
                        .toList();
                objectMapper.writeValue(resp.getWriter(), usersDto);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), "Not found Users!");
            }
        }else {
            Integer id = getId(pathInfo);
            Optional<User> user = userService.getById(id);

            if (user.isPresent()) {
                Optional<UserDto> userDto = user.stream()
                        .map(userMapper::toDto)
                        .findFirst();

                System.out.println();

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                objectMapper.writeValue(resp.getWriter(), userDto.get());
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), "Not found User by ID!");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = objectMapper.readValue(req.getInputStream(), UserDto.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (userDto != null) {
            User user = userMapper.toEntity(userDto);
            user = userService.create(user);
            UserDto createdUserDto = userMapper.toDto(user);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(), createdUserDto);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), "Internal server error");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto userDto = objectMapper.readValue(req.getInputStream(), UserDto.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (userDto != null){
            User user = userMapper.toEntity(userDto);
            User updated = userService.update(user);
            UserDto updatedDto = userMapper.toDto(updated);

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), updatedDto);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), "The User was not found in the DB. " +
                                                      "It's impossible to change the data!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        Integer id = getId(pathInfo);

        Optional<User> user = userService.getById(id);
        if (user.isPresent()){
            userService.delete(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            objectMapper.writeValue(resp.getWriter(), "User deleted!");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), "Not found User by ID!");
        }
    }

    private static Integer getId(
            String pathInfo) {

        String[] pathArray = pathInfo.split("/");
        if (pathArray.length == 2){
            return Integer.parseInt(pathArray[1]);
        } else {
            throw new RuntimeException("Invalid path!");
        }

    }

    static UserService createUserService() {
        UserDao userDao = new UserDaoImpl();
        return new UserService(userDao);
    }

}
