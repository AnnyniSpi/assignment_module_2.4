package dev.annyni.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.entity.File;
import dev.annyni.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/files")
public class FileServletGetAll extends HttpServlet {

    private final FileService fileService = FileService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<File> files = fileService.getAll();

        if (!files.isEmpty()){
            ObjectMapper objectMapper = new ObjectMapper();

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(resp.getWriter(), files);
        } else {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }

    }
}
