package com.expensetracker.controller;

import com.expensetracker.model.Wallet;
import com.expensetracker.service.WalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;


    @Test
    void readWallets() throws Exception {
        Mockito.when(walletService.getWallets()).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/wallets")).andExpect(status().isOk()).andReturn();

    }

    @Test
    void createWallet() throws Exception {
        Wallet wallet = new Wallet(1L, "Mock Wallet", new BigDecimal("1000.0"));
        Mockito.when(walletService.createWallet(wallet)).thenReturn(wallet);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wallet)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Mock Wallet"))
                .andExpect(jsonPath("$.value").value(1000.00))
                .andReturn();

        Mockito.verify(walletService, Mockito.times(1)).createWallet(wallet);
    }

    @Test
    void updateWallet() throws Exception {
        Wallet wallet = new Wallet(10L, "Mock Wallet", new BigDecimal("104.0"));
        Mockito.when(walletService.updateWallet(wallet)).thenReturn(wallet);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(wallet))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mock Wallet"))
                .andExpect(jsonPath("$.value").value(104.0))
                .andReturn();
    }

    @Test
    void deleteCategory() throws Exception {
        Mockito.doNothing().when(walletService).deleteById(Mockito.anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/wallets/1")).andExpect(status().isOk());

        Mockito.verify(walletService, Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}