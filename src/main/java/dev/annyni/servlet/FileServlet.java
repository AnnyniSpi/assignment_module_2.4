package dev.annyni.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.dao.FileDao;
import dev.annyni.dao.impl.FileDaoImpl;
import dev.annyni.dto.FileDto;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.entity.User;
import dev.annyni.mapper.FileMapper;
import dev.annyni.service.EventService;
import dev.annyni.service.FileService;
import dev.annyni.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static dev.annyni.servlet.EventServlet.createEventService;
import static dev.annyni.servlet.UserServlet.createUserService;

@WebServlet("/files/*")
@MultipartConfig
public class FileServlet extends HttpServlet {

    private final UserService userService = createUserService();
    private final FileService fileService = createFileService();
    private final EventService eventService = createEventService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final FileMapper fileMapper = FileMapper.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty()){
            List<File> files = fileService.getAll();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            if (!files.isEmpty()){
                List<FileDto> filesDto = files.stream()
                        .map(fileMapper::toDto)
                        .toList();
                objectMapper.writeValue(resp.getWriter(), filesDto);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), "Not found Files!");
            }
        }else {
            Integer id = getId(pathInfo);
            Optional<File> file = fileService.getById(id);

            if (file.isPresent()) {
                Optional<FileDto> fileDto = file.stream()
                        .map(fileMapper::toDto)
                        .findFirst();

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                objectMapper.writeValue(resp.getWriter(), fileDto.get());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdHeader = req.getHeader("user_id");
        Part filePart = req.getPart("file");

        if (userIdHeader == null || filePart == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), "Missing user ID or file in request");
        }

        uploadFile(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FileDto fileDto = objectMapper.readValue(req.getInputStream(), FileDto.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if(fileDto != null){
            File file = fileMapper.toEntity(fileDto);
            File updated = fileService.update(file);
            FileDto updatedDto = fileMapper.toDto(updated);

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), updatedDto);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), "The File was not found in the DB. " +
                                                      "It's impossible to change the data!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        Integer id = getId(pathInfo);

        Optional<File> file = fileService.getById(id);
        if (file.isPresent()){
            fileService.delete(id);

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            objectMapper.writeValue(resp.getWriter(), "File deleted!");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), "Not found File by ID!");
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

    private static FileService createFileService() {
        FileDao fileDao = new FileDaoImpl();
        return new FileService(fileDao);
    }

    private void uploadFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            Integer userId = Integer.parseInt(req.getHeader("user_id"));
            Optional<User> user = userService.getById(userId);

            if (user.isPresent()){
                Part filePart = req.getPart("file");
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                byte[] fileData = filePart.getInputStream().readAllBytes();

                String uploadDir = "upload/";
                Files.createDirectories(Paths.get(uploadDir));

                String filePath = uploadDir + fileName;
                Files.write(Paths.get(filePath), fileData);

                File file = new File();
                file.setName(fileName);
                file.setFilePath(filePath);
                file = fileService.create(file);

                Event event = new Event();
                event.setUser(user.get());
                event.setFile(file);
                eventService.create(event);

                FileDto fileDto = fileMapper.toDto(file);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectMapper.writeValue(resp.getWriter(), fileDto);
            }else {
                throw new RuntimeException("User not found!");
            }
        } catch (ServletException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(resp.getWriter(), "Failed to upload file");
        }
    }

}
