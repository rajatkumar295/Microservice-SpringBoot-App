package com.userservice.codeCoverage;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.entity.User;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testCreate() {
        User user = new User(null, "test", "pass", "email@test.com");
        when(repository.save(user)).thenReturn(new User(1L, "test", "pass", "email@test.com"));
        User result = service.create(user);
        assertNotNull(result);
        assertEquals("test", result.getUsername());
    }

    @Test
    void testGet() {
        User user = new User(1L, "test", "pass", "email@test.com");
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        User result = service.get(1L);
        assertEquals("test", result.getUsername());
    }

    @Test
    void testGet_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.get(1L));
    }

    @Test
    void testUpdate() {
        User existing = new User(1L, "old", "pass", "old@mail.com");
        User update = new User(null, "new", "pass", "new@mail.com");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);
        User result = service.update(1L, update);
        assertEquals("new", result.getUsername());
    }

    @Test
    void testDelete() {
        service.delete(1L);
        verify(repository).deleteById(1L);
    }
}
