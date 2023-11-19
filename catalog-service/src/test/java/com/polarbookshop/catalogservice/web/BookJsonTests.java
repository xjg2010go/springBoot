package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book; 
import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;


@JsonTest
class BookJsonTests {
   
    @Autowired 
    private JacksonTester<Book> json; 

    @Test 
    void testSerialize() throws Exception {
        var now = Instant.now();
        var book = new Book(394L, "1234567890", "Title", "Author", 9.90, now, now, 21);
        var jsonContent = json.write(book); 

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }
    

    @Test 
    void testDeserialize() throws Exception {
        var instant = Instant.parse("2021-09-07T22:50:37.135029Z");
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.90
                }    
                """;
        
        assertThat(json.parse(content)).usingRecursiveComparison()
        .isEqualTo(new Book(394L, "1234567890", "Title", "Author", 9.90, instant, instant, 21));
    }
}
