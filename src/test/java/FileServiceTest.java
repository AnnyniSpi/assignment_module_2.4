import dev.annyni.dao.FileDao;
import dev.annyni.entity.File;
import dev.annyni.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class FileServiceTest {

    private FileDao fileDao;
    private FileService fileService;
    private File testFile;

//    @BeforeEach
//    void init() {
//        fileDao = mock(FileDao.class);
//        fileService = new FileService(fileDao);
//
//        testFile = File.builder()
//                .id(1)
//                .name("Test")
//                .filePath("/test")
//                .build();
//    }
//
//    @Test
//    void createTest() {
//        Mockito.when(fileDao.save(any(File.class))).thenReturn(testFile);
//
//        File file = fileService.create(testFile);
//
//        Mockito.when(fileDao.findByID(anyInt())).thenReturn(Optional.ofNullable(file));
//        Optional<File> byId = fileService.getById(file.getId());
//
//        System.out.println();
//
//        assertEquals(1, byId.get().getId());
//        assertEquals("Test", byId.get().getName());
//        assertEquals("/test", byId.get().getFilePath());
//
//        verify(fileDao, times(1)).save(testFile);
//    }
//
//    @Test
//    void updateTest() {
//        File updateFile = File.builder()
//                .id(1)
//                .name("Update")
//                .filePath("/update")
//                .build();
//
//        Mockito.when(fileDao.update(any(File.class))).thenReturn(updateFile);
//
//        File file = fileService.update(testFile);
//
//        assertEquals(1, file.getId());
//        assertEquals("Update", file.getName());
//        assertEquals("/update", file.getFilePath());
//
//        verify(fileDao, times(1)).save(updateFile);
//    }
//
//    @Test
//    void findByIdTest(){
//        Mockito.when(fileDao.findByID(anyInt())).thenReturn(Optional.ofNullable(testFile));
//
//        Optional<File> byId = fileService.getById(1);
//
//        System.out.println();
//
//        assertNotNull(byId);
//        assertEquals("Test", byId.get().getName());
//        assertEquals("/test", byId.get().getFilePath());
//    }
//
//    @Test
//    void getAllTest(){
//        List<File> files = new ArrayList<>();
//        files.add(testFile);
//
//        Mockito.when(fileDao.findAll()).thenReturn(files);
//
//        List<File> all = fileService.getAll();
//
//        assertNotNull(all);
//        assertEquals(1, all.size());
//        assertEquals("Test", all.get(0).getName());
//        assertEquals("/test", all.get(0).getFilePath());
//    }
//
//    @Test
//    void deleteTest(){
//        Mockito.when(fileDao.delete(anyInt())).thenReturn(true);
//        boolean result = fileService.delete(1);
//        assertTrue(result);
//        verify(fileDao, times(1)).delete(1);
//
//    }
}
