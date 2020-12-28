package ru.otus.spring.web;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.Main;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllBooks_RestGet() throws Exception {
        mockMvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Lord of the Rings")))
                .andExpect(jsonPath("$[2].name", is("Martian")));
    }

    @Test
    void getCommentsForBook_RestGet() throws Exception {
        mockMvc.perform(get("/api/book/3/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"text\":\"MartianComment\"},{\"id\":\"2\",\"text\":\"Martian Comment 2\"}]"));
    }

    @Test
    @Transactional
    void addBook_RestPost() throws Exception {
        mockMvc.perform(post("/api/book")
                .content("{\"name\":\"asdasd\",\"author\":\"Tolstoy\",\"genre\":\"History\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":4,\"name\":\"asdasd\",\"author\":{\"id\":3,\"name\":\"Tolstoy\"},\"genre\":{\"id\":3,\"name\":\"History\"},\"comments\":null}"));

    }

    @Test
    @Transactional
    void addBook_RestPost_Error() throws Exception {
        mockMvc.perform(post("/api/book")
                .content("{\"name\":\"asdasd\",\"author\":\"Tolstoy\",\"genre\":\"History2\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());


    }

    @Test
    void getBookById_RestGet() throws Exception {
        mockMvc.perform(get("/api/book/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"3\",\"name\":\"Martian\",\"author\":\"Tolstoy\",\"genre\":\"Comics\"}"));
    }

    @Test
    @Transactional
    void updateBook_RestPut() throws Exception {
        mockMvc.perform(put("/api/book")
                .content("{\"id\":\"3\",\"name\":\"Martian\",\"author\":\"Tolstoy\",\"genre\":\"Drama\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":3,\"name\":\"Martian\",\"author\":{\"id\":3,\"name\":\"Tolstoy\"},\"genre\":{\"id\":1,\"name\":\"Drama\"},\"comments\":null}"));

    }

    @Test
    @Transactional
    void updateBook_RestPut_Error() throws Exception {

            mockMvc.perform(put("/api/book")
                    .content("{\"id\":\"3\",\"name\":\"Martian\",\"author\":\"Tolstoy2\",\"genre\":\"Drama\"}")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
    }

    @Test
    @Transactional
    void deleteBook_RestDelete() throws Exception {
        mockMvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        mockMvc.perform(delete("/api/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
