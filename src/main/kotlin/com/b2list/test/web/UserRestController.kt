package com.b2list.test.web

import br.com.colman.simplecpfvalidator.isCpf
import com.b2list.test.repositories.user.UserActionResponseModel
import com.b2list.test.repositories.user.UserModel
import com.b2list.test.repositories.user.UserRepository
import com.b2list.test.repositories.user.UserUpdateModel
import com.b2list.test.utils.Utilities
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@ApiResponses(value = [ApiResponse(code = 500, message = "Internal Server Error")])
@ApiModel(discriminator = "TESTE")
class UserRestController {

    @Autowired
    lateinit var repository: UserRepository

    @GetMapping("/api/users/all")
    @Operation(summary = "Get all registered users", description = "Return an array with all users data")
    fun findAll(remote:HttpServletRequest): List<UserModel?>? {
        return try{
            repository.findAll()
        }catch (ex:Exception){
            println("API ERROR: /api/users/all caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            null
        }
    }

    @GetMapping("/api/users/get/{cpf}")
    @Operation(summary = "Get registered user data", description = "Return user data")
    fun findByCpf(@PathVariable cpf: String, remote:HttpServletRequest): UserModel?{
        return try{
            repository.findByCpf(cpf.toLong()).orElse(null)
        }catch (ex:Exception){
            println("API ERROR: /api/users/get/$cpf caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            null
        }
    }

    @PostMapping(path = ["/api/users/create"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Operation(summary = "Register new user", description = "Create new user")
    fun createUser(@RequestBody data: UserModel, remote:HttpServletRequest): UserActionResponseModel
    {
        var error = false
        val errorItems = mutableListOf<String>()

        try {
            val cpf = data.cpf
            if(!cpf.isCpf()){ error = true; errorItems.add("cpf-invalid") }

            val username = data.username.replace("\\s".toRegex(), "")
            if(username.length < 3){error = true; errorItems.add("username-lenght")}

            val email = data.email.lowercase()
            if(!"^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex().matches(email)){ error = true; errorItems.add("email-invalid") }

            val name = Utilities.capitalizeString(data.name.lowercase())
            if(name.length < 3){error = true; errorItems.add("name-lenght")}

            val lastName = Utilities.capitalizeString(data.lastName)
            if(lastName.length < 3){error = true; errorItems.add("last_name-lenght")}

            repository.findByUsername(username).ifPresent {
                error = true
                errorItems.add("username-duplicated")
            }

            repository.findByCpf(cpf).ifPresent {
                error = true
                errorItems.add("cpf-duplicated")
            }

            if(!error){
                repository.save(UserModel(cpf, username, email, name, lastName))
            }

            return UserActionResponseModel(error, errorItems)
        }
        catch (ex:Exception){
            println("API ERROR: /api/users/create caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            return UserActionResponseModel(true, mutableListOf("An Error ocurred on creation proccess."))
        }
    }

    @DeleteMapping(path = ["/api/users/delete/{cpf}"])
    @Operation(summary = "Delete user", description = "Delete registered user")
    fun deleteUser(remote:HttpServletRequest, @PathVariable cpf: String): UserActionResponseModel
    {
        var error = false
        val errorItems = mutableListOf<String>()
        try {
            repository.findByCpf(cpf.toLong()).ifPresentOrElse({
                repository.delete(it)
            }, {
                error = true
                errorItems.add("This user does not exist.")
            })
        }
        catch (ex:Exception){
            println("API ERROR: /api/users/delete/$cpf caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            error = true
            errorItems.add("An Error ocurred on creation proccess.")
        }
        return UserActionResponseModel(error, errorItems)
    }

    @PatchMapping(path = ["/api/users/update/{cpf}"])
    @Operation(summary = "Update user", description = "Update user data")
    fun updateUser(@RequestBody updateData: UserUpdateModel, remote:HttpServletRequest, @PathVariable cpf: String): UserActionResponseModel
    {
        var error = false
        val errorItems = mutableListOf<String>()

        try {
            val email = updateData.email.lowercase()
            if(!"^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex().matches(email)){ error = true; errorItems.add("email-invalid") }

            val name = Utilities.capitalizeString(updateData.name)
            if(name.length < 3){error = true; errorItems.add("name-lenght")}

            val lastName = Utilities.capitalizeString(updateData.lastName)
            if(lastName.length < 3){error = true; errorItems.add("last_name-lenght")}

            if(!error){
                repository.findByCpf(cpf.toLong()).ifPresentOrElse({
                    it.name = name
                    it.lastName = lastName
                    it.email = email
                    repository.save(it)
                }, {
                    error = true
                    errorItems.add("This user does not exists.")
                })
            }
            return UserActionResponseModel(error, errorItems)
        }
        catch (ex:Exception){
            println("API ERROR: /api/users/update/$cpf caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            return UserActionResponseModel(true, mutableListOf("An Error ocurred on update proccess."))
        }
    }

}