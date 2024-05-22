package dev.annyni.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/file")
public class FileServlet extends HttpServlet {

    private final FileService fileService = FileService.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<File> file = fileService.getById(id);

        if (file.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_OK);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), file.get());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File file = File.builder()
                .name(req.getParameter("name"))
                .filePath(req.getParameter("path"))
                .build();

        fileService.create(file);

        System.out.println(file);

        resp.setStatus(HttpServletResponse.SC_CREATED);

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(file.getId());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File readValue = objectMapper.readValue(req.getInputStream(), File.class);

        Optional<File> file = fileService.getById(readValue.getId());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if(file.isPresent()){
            File update = fileService.update(file.get());
            objectMapper.writeValue(resp.getWriter(), update);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = getId(req, resp);

        Optional<File> file = fileService.getById(id);
        if (file.isPresent()){
            fileService.delete(id);

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
