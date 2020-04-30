package com.marcosvidolin.jokenpo.rest.api.controller;

import com.marcosvidolin.jokenpo.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameRestController.class)
public class GameRestControllerTest {

    @MockBean
    private GameService service;

    @Autowired
    private MockMvc mvc;

    @Test
    public void create_whenRequestWithNoContent_mustReturnBadRequest() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/games")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void create_whenRequestValidGame_mustReturnNewGame()
            throws Exception {

        mvc.perform( MockMvcRequestBuilders
                .post("/api/v1/games")
                .content("{\"players\": [\"marcos\",\"joao\"]}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


}
