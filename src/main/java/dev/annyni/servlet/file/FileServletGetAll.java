package dev.annyni.servlet.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.annyni.dao.FileDao;
import dev.annyni.dao.impl.FileDaoImpl;
import dev.annyni.entity.File;
import dev.annyni.service.EventService;
import dev.annyni.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

@WebServlet("/files")
@RequiredArgsConstructor
public class FileServletGetAll extends HttpServlet {

    private final FileService fileService = createService();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<File> files = fileService.getAll();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (!files.isEmpty()){
            objectMapper.writeValue(resp.getWriter(), files);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private static FileService createService() {
        FileDao fileDao = new FileDaoImpl();
        return new FileService(fileDao);
    }
}
