import dev.annyni.dao.EventDao;
import dev.annyni.entity.Event;
import dev.annyni.entity.File;
import dev.annyni.entity.User;
import dev.annyni.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EventServiceTest {

    private EventDao eventDao;
    private EventService eventService;
    private Event testEvent;

//    @BeforeEach
//    void init() {
//        eventDao = Mockito.mock(EventDao.class);
//        eventService = new EventService(eventDao);
//
//        testEvent = Event.builder()
//                .id(1)
//                .file(File.builder()
//                        .name("Test")
//                        .filePath("/test")
//                        .build())
//                .user(new User())
//                .build();
//    }
//
//    @Test
//    void createTest() {
//        Mockito.when(eventDao.save(any(Event.class))).thenReturn(testEvent);
//
//        Event event = eventService.create(testEvent);
//
//        Mockito.when(eventDao.findByID(anyInt())).thenReturn(Optional.ofNullable(event));
//        Optional<Event> byId = eventService.getById(event.getId());
//
//        System.out.println();
//
//        assertEquals(1, byId.get().getId());
//
//        verify(eventDao, times(1)).save(testEvent);
//    }
//
//    @Test
//    void updateTest() {
//        Event updateEvent = Event.builder()
//                .id(1)
//                .file(File.builder()
//                        .name("Update")
//                        .filePath("/update")
//                        .build())
//                .build();
//
//        Mockito.when(eventDao.update(any(Event.class))).thenReturn(updateEvent);
//
//        Event event = eventService.update(testEvent);
//
//        assertEquals(1, event.getId());
//        assertEquals("Update", event.getFile().getName());
//        assertEquals("/update", event.getFile().getFilePath());
//
//        verify(eventDao, times(1)).save(updateEvent);
//    }
//
//    @Test
//    void findByIdTest(){
//        Mockito.when(eventDao.findByID(anyInt())).thenReturn(Optional.ofNullable(testEvent));
//
//        Optional<Event> byId = eventService.getById(1);
//
//        assertNotNull(byId);
//        assertEquals("Test", byId.get().getFile().getName());
//        assertEquals("/test", byId.get().getFile().getFilePath());
//    }
//
//    @Test
//    void getAllTest(){
//        List<Event> events = new ArrayList<>();
//        events.add(testEvent);
//
//        Mockito.when(eventDao.findAll()).thenReturn(events);
//
//        List<Event> all = eventService.getAll();
//
//        assertNotNull(all);
//        assertEquals(1, all.size());
//        assertEquals("Test", all.get(0).getFile().getName());
//        assertEquals("/test", all.get(0).getFile().getFilePath());
//    }
//
//    @Test
//    void deleteTest(){
//        Mockito.when(eventDao.delete(anyInt())).thenReturn(true);
//        boolean result = eventService.delete(1);
//        assertTrue(result);
//        verify(eventDao, times(1)).delete(1);
//
//    }
}
