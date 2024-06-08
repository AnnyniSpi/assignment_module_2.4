import dev.annyni.dao.EventDao;
import dev.annyni.dao.impl.EventDaoImpl;
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

    @BeforeEach
    void init() {
        eventDao = Mockito.mock(EventDaoImpl.class);
        eventService = new EventService(eventDao);

        User testUser = User.builder().id(1).build();
        File testFile = File.builder().id(1).build();

        testEvent = Event.builder()
                .id(1)
                .user(testUser)
                .file(testFile)
                .build();
    }

    @Test
    void createTest() {
        Mockito.when(eventDao.save(any(Event.class))).thenReturn(testEvent);

        Event event = eventService.create(testEvent);

        Mockito.when(eventDao.findByID(anyInt())).thenReturn(Optional.ofNullable(event));
        Optional<Event> byId = eventService.getById(event.getId());

        System.out.println();

        assertEquals(1, byId.get().getId());

        verify(eventDao, times(1)).save(testEvent);
    }

    @Test
    void updateTest() {
        User updateUser = User.builder().id(2).build();
        File updateFile = File.builder().id(2).build();

        Event updateEvent = Event.builder()
                .id(1)
                .user(updateUser)
                .file(updateFile)
                .build();

        Mockito.when(eventDao.update(any(Event.class))).thenReturn(updateEvent);

        Event event = eventService.update(testEvent);

        assertNotNull(event);
        assertNotNull(event.getUser());
        assertNotNull(event.getFile());
        assertEquals(updateUser.getId(), event.getUser().getId());
        assertEquals(updateFile.getId(), event.getFile().getId());

        verify(eventDao, times(1)).update(updateEvent);
    }

    @Test
    void findByIdTest(){
        Mockito.when(eventDao.findByID(anyInt())).thenReturn(Optional.ofNullable(testEvent));

        Optional<Event> byId = eventService.getById(1);

        assertNotNull(byId);
        assertNotNull(byId.get().getUser());
        assertNotNull(byId.get().getFile());
        assertEquals(testEvent.getUser().getId(), byId.get().getUser().getId());
        assertEquals(testEvent.getFile().getId(), byId.get().getFile().getId());
    }

    @Test
    void getAllTest(){
        List<Event> events = new ArrayList<>();
        events.add(testEvent);

        Mockito.when(eventDao.findAll()).thenReturn(events);

        List<Event> all = eventService.getAll();

        for (Event event : all) {
            assertNotNull(event);
            assertNotNull(event.getUser());
            assertNotNull(event.getFile());
            assertEquals(testEvent.getUser().getId(), event.getUser().getId());
            assertEquals(testEvent.getFile().getId(), event.getFile().getId());

        }
    }

    @Test
    void deleteTest(){
        Mockito.when(eventDao.delete(anyInt())).thenReturn(true);
        boolean result = eventService.delete(1);
        assertTrue(result);
        verify(eventDao, times(1)).delete(1);

    }
}
