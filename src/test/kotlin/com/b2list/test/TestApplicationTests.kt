package com.b2list.test

import com.b2list.test.repositories.user.UserModel
import com.b2list.test.repositories.user.UserUpdateModel
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONArray
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestApplicationTests {

	@Autowired
	lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var objectMapper: ObjectMapper

	val user =  UserModel(87116054057, "test.one", "test@test.com", "Test", "One")
	val userUpdate =  UserUpdateModel( "test2", "Two", "test2@test2.com")

	@Test
	@Order(1)
	fun apiCreateUser(){
		mockMvc.perform(
			post("/api/users/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(jsonPath("error").value(false))
	}

	@Order(2)
	@Test
	fun apiUpdateUser(){
		mockMvc.perform(
			patch("/api/users/update/${user.cpf}").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userUpdate))).andExpect(jsonPath("error").value(false)).andDo { println(it.response.contentAsString) }
	}

	@Order(3)
	@Test
	fun apiGetUser(){
		mockMvc.perform(
			get("/api/users/get/${user.cpf}")).andExpect(jsonPath("cpf").value(user.cpf))
	}

	@Order(4)
	@Test
	fun apiGetAllUsers() {
		mockMvc.perform(
			get("/api/users/all")).andExpect { JSONArray(it.response.contentAsString).length() > 0 }
	}

	@Order(5)
	@Test
	fun apiDeleteUser(){
		mockMvc.perform(
			delete("/api/users/delete/${user.cpf}")).andExpect(jsonPath("error").value(false))
	}


}


