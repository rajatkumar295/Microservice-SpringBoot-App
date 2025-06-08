package com.orderService.codeCoverage;

import com.orderService.controller.OrderController;
import com.orderService.entity.Order;
import com.orderService.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void testCreateOrder() throws Exception {
        Order order = new Order(1L, 1L, "Laptop", 1, 1500.0);
        Mockito.when(service.create(any())).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.product").value("Laptop"));
    }

    @Test
    void testGetOrder() throws Exception {
        Order order = new Order(1L, 1L, "Mouse", 2, 20.0);
        Mockito.when(service.get(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Mouse"));
    }

    @Test
    void testUpdateOrder() throws Exception {
        Order order = new Order(1L, 1L, "Keyboard", 1, 45.0);
        Mockito.when(service.update(any(), any())).thenReturn(order);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("Keyboard"));
    }

    @Test
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isNoContent());
    }
}
