package com.orderService.codeCoverage;

import com.orderService.config.UserClient;
import com.orderService.dto.UserDTO;
import com.orderService.exception.ResourceNotFoundException;
import com.orderService.entity.Order;
import com.orderService.repository.OrderRepository;
import com.orderService.service.OrderService;
import org.apache.catalina.User;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private OrderService service;

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
        Order order = new Order(null, 1L, "Laptop", 2, 1500.0);
        when(userClient.getUserById(1L)).thenReturn((UserDTO) new Object());
        when(repository.save(order)).thenReturn(new Order(1L, 1L, "Laptop", 2, 1500.0));

        Order result = service.create(order);
        assertNotNull(result);
        assertEquals("Laptop", result.getProduct());
    }

    @Test
    void testCreate_InvalidUser() {
        Order order = new Order(null, 99L, "Phone", 1, 500.0);
        when(userClient.getUserById(99L)).thenThrow(new RuntimeException("User not found"));
        assertThrows(ResourceNotFoundException.class, () -> service.create(order));
    }

    @Test
    void testGet() {
        Order order = new Order(1L, 1L, "Tablet", 3, 300.0);
        when(repository.findById(1L)).thenReturn(Optional.of(order));
        Order result = service.get(1L);
        assertEquals("Tablet", result.getProduct());
    }

    @Test
    void testUpdate() {
        Order existing = new Order(1L, 1L, "Tablet", 3, 300.0);
        Order update = new Order(null, 1L, "Tablet Pro", 5, 600.0);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Order result = service.update(1L, update);
        assertEquals("Tablet Pro", result.getProduct());
    }

    @Test
    void testDelete() {
        service.delete(1L);
        verify(repository).deleteById(1L);
    }
}
