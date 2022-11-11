package com.b2list.test.web

import br.com.colman.simplecpfvalidator.isCpf
import com.b2list.test.repositories.user.UserActionResponseModel
import com.b2list.test.repositories.user.UserModel
import com.b2list.test.repositories.user.UserRepository
import com.b2list.test.utils.Utilities
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class UserRestController {

    @Autowired
    lateinit var repository: UserRepository

    @RequestMapping("/api/users/all")
    fun findAll(remote:HttpServletRequest): Any? {
        return try{
            repository.findAll()
        }catch (ex:Exception){
            println("API ERROR: /api/users/all caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            JSONObject(UserActionResponseModel(true, mutableListOf("Invalid search attribute."))).toMap()
        }
    }

    @RequestMapping("/api/users/get/{cpf}")
    fun findByCpf(@PathVariable cpf: String, remote:HttpServletRequest): Any?{
        return try{
            repository.findByCpf(cpf.toLong()).orElse(null)
        }catch (ex:Exception){
            println("API ERROR: /api/users/get/$cpf caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            JSONObject(UserActionResponseModel(true, mutableListOf("Invalid search attribute."))).toMap()
        }
    }

    @PostMapping(path = ["/api/users/create"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@RequestBody json: String?, remote:HttpServletRequest): Any?
    {
        var error = false
        val errorItems = mutableListOf<String>()

        try {
            val data = JSONObject(json)

            var cpf = data["cpf"].toString()
            if(!cpf.isCpf()){ error = true; errorItems.add("cpf-invalid")
            } else{ cpf = cpf.replace(".", "").replace("-", "") }

            val username = data["username"].toString().replace("\\s".toRegex(), "")
            if(username.length < 3){error = true; errorItems.add("username-lenght")}

            val email = data["email"].toString().lowercase()
            if(!"^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex().matches(email)){ error = true; errorItems.add("email-invalid") }

            val name = Utilities.capitalizeString(data["name"].toString().lowercase())
            if(name.length < 3){error = true; errorItems.add("name-lenght")}

            val lastName = Utilities.capitalizeString(data["last_name"].toString())
            if(lastName.length < 3){error = true; errorItems.add("last_name-lenght")}

            repository.findByUsername(username).ifPresent {
                error = true
                errorItems.add("username-duplicated")
            }

            repository.findByCpf(cpf.toLong()).ifPresent {
                error = true
                errorItems.add("cpf-duplicated")
            }

            if(!error){
                repository.save(UserModel(cpf.toLong(), username, email, name, lastName))
            }

            return JSONObject(UserActionResponseModel(error, errorItems)).toMap()
        }
        catch (ex:Exception){
            println("API ERROR: /api/users/create caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            return JSONObject(UserActionResponseModel(true, mutableListOf("An Error ocurred on creation proccess."))).toMap()
        }
    }

    @DeleteMapping(path = ["/api/users/delete/{cpf}"])
    fun deleteUser(remote:HttpServletRequest, @PathVariable cpf: String): Any?
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
            return JSONObject(UserActionResponseModel(true, mutableListOf("An Error ocurred on creation proccess."))).toMap()
        }
        return UserActionResponseModel(error, errorItems)
    }

    @PatchMapping(path = ["/api/users/update/{cpf}"])
    fun updateUser(@RequestBody json: String?, remote:HttpServletRequest, @PathVariable cpf: String): Any?
    {
        var error = false
        val errorItems = mutableListOf<String>()

        try {
            val updateData = JSONObject(json)

            val email = updateData["email"].toString().lowercase()
            if(!"^[A-Za-z](.*)(@)(.+)(\\.)(.+)".toRegex().matches(email)){ error = true; errorItems.add("email-invalid") }

            val name = Utilities.capitalizeString(updateData["name"].toString().lowercase())
            if(name.length < 3){error = true; errorItems.add("name-lenght")}

            val lastName = Utilities.capitalizeString(updateData["last_name"].toString())
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
            return JSONObject(UserActionResponseModel(error, errorItems)).toMap()
        }
        catch (ex:Exception){
            println("API ERROR: /api/users/update/$cpf caused by ${ex.localizedMessage} : FROM ${remote.remoteAddr}")
            return JSONObject(UserActionResponseModel(true, mutableListOf("An Error ocurred on update proccess."))).toMap()
        }
    }

}