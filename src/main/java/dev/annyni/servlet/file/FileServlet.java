package dev.annyni.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.dao.FileDao;
import dev.annyni.dao.impl.FileDaoImpl;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/file")
@RequiredArgsConstructor
public class FileServlet extends HttpServlet {

    private final FileService fileService = createService();
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
        File readValue = objectMapper.readValue(req.getInputStream(), File.class);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (readValue != null) {
            File file = fileService.create(readValue);

            resp.setStatus(HttpServletResponse.SC_CREATED);

            objectMapper.writeValue(resp.getWriter(), file);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File readValue = objectMapper.readValue(req.getInputStream(), File.class);

        System.out.println();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if(readValue != null){
            File update = fileService.update(readValue);

            System.out.println(update);

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

    private static Integer getId(HttpServletRequest req, HttpServletResponse resp) {
        String fileIdString = req.getParameter("id");
        return Integer.parseInt(fileIdString);
    }

    private static FileService createService() {
        FileDao fileDao = new FileDaoImpl();
        return new FileService(fileDao);
    }

}
