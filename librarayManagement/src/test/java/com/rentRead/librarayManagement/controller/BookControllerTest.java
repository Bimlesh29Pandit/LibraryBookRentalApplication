package com.rentRead.librarayManagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;



import com.rentRead.librarayManagement.dto.BookDto;
import com.rentRead.librarayManagement.service.BookService;
import com.rentRead.librarayManagement.service.JwtService;


@WebMvcTest(BookController.class)
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	BookService bookService;
	@MockitoBean
	JwtService jwtService;
//	@Autowired
//	private ObjectMapper objectMapper;
	
	BookDto bookDto1, bookDto2;
	
	@BeforeEach
	void setUp() {
		
		 bookDto1 = new BookDto(1L, "Book 1", "Author 1", "Genre 1", true);
	     bookDto2 = new BookDto(2L, "Book 2", "Author 2", "Genre 2", true);
		
	}
	
	@WithMockUser(username = "admin",roles = {"ADMIN"})
	@Test
	public void getAllBook() throws Exception {
		List<BookDto> bookDtos = List.of(bookDto1, bookDto2);
		ResponseEntity<List<BookDto>> response = new ResponseEntity<>(bookDtos,HttpStatus.OK);
		
		
		Mockito.when(bookService.getAllBooks()).thenReturn(response);
		
		mockMvc.perform(get("/books"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(response.getBody().size()))
		.andExpect(jsonPath("$[0].id").value(1L))
		.andExpect(jsonPath("$[1].id").value(2L));
		
	}
	@WithMockUser(username = "admin",roles = {"ADMIN"})
	@Test
	public void getBookById() throws Exception {
		Long id = bookDto1.getId();
		ResponseEntity<BookDto> response = new ResponseEntity<>(bookDto1,HttpStatus.OK);
		
		Mockito.when(bookService.getBookById(id)).thenReturn(response);
		mockMvc.perform(get("/books/{bookId}",id)).andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(id));
		
	}


}

//@GetMapping("/{bookId}")
//public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId){
//	
//	return bookService.getBookById(bookId);
//	
//}
//
//
////Only ADMIN can access
//@PostMapping
//public ResponseEntity<BookDto> addBook(@RequestBody Book book){
//	return bookService.addBook(book);
//}
//@PutMapping
//public ResponseEntity<BookDto> updateBook(@RequestBody Book book){
//	return bookService.updateBook(book);
//}
//
//@DeleteMapping("/{bookId}")
//public ResponseEntity<String> deleteBook(@PathVariable Long bookId){
//	return bookService.deleteBook(bookId);
//}

//@Test
//public void getAllUsersTest() throws Exception {
//	List<User> users = new ArrayList<User>();
//	user = new User();
//	user.setUserId("123");
//	user.setUserName("Bimlesh");
//	users.add(user);
//	user = new User();
//	user.setUserId("124");
//	user.setUserName("Vedant");
//	users.add(user);
//	
//	Mockito.when(userService.getAllUsers()).thenReturn(users);
//	
//	mockMvc.perform(get("/users"))
//	.andExpect(status().isOk())
//	.andExpect(jsonPath("$.length()").value(users.size()))
//	.andExpect(jsonPath("[0].userId").value("123"))
//	.andExpect(jsonPath("$[1].userId").value("124"));
//}

//@RestController
//@RequestMapping("/books")
//public class BookController {
//	@Autowired
//	BookService bookService;
//	
//	
//	@GetMapping
//	public ResponseEntity<List<BookDto>> getAllBook(){
//		
//		return bookService.getAllBooks();
//		
//	}