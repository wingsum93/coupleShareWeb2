package com.ericho.coupleShare.controller

import com.ericho.coupleShare.mobile.BaseResponse
import com.ericho.coupleShare.mobile.BaseSingleResponse
import com.ericho.coupleShare.mobile.inputmodel.CreateDeleteRelationInputModel
import com.ericho.coupleShare.mobile.inputmodel.UploadLocationInputModel
import com.ericho.coupleShare.mobile.inputmodel.ViewLocationInputModel
import com.ericho.coupleShare.mobile.model.PhotoBo
import com.ericho.coupleShare.mobile.model.StatusBo
import com.ericho.coupleShare.model.LocationInfo
import com.ericho.coupleShare.service.ApiService
import com.ericho.coupleShare.service.PhotoService
import com.ericho.coupleShare.service.ProductService
import com.ericho.coupleShare.service.UserService
import com.ericho.coupleShare.util.SecurityUtil
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import org.apache.commons.io.IOUtils
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.text.DateFormat
import java.util.*

@RestController
@RequestMapping("/api")
class ApiController {
    var log = LogFactory.getLog(ApiController::class.java)
    private val gson = GsonBuilder().setDateFormat(DateFormat.LONG)
            .registerTypeAdapter(Date::class.java, JsonDeserializer { json, typeOfT, context -> Date(json.asJsonPrimitive.asLong) }).create()

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val productService: ProductService? = null

    @Autowired
    private val photoService: PhotoService? = null

    @Autowired
    private val apiService: ApiService? = null

    //              for properties
    @Value("\${coupleshare.folder}")
    private val filePath: String? = null

    //-----------------------------------------------------------------------------
    @RequestMapping("/register")
    @ResponseBody
    fun registerUser(@RequestParam username: String,
                     @RequestParam password: String): BaseSingleResponse<String> {
        val response = BaseSingleResponse<String>()
        log.debug("username $username password=$password")
        return try {
            userService!!.register(username, password)
            val authenticate = true
            response.isStatus = authenticate
            response.extra = "extra"
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @PostMapping("/login")
    @ResponseBody
    fun checkLogin(
            @RequestParam username: String,
            @RequestParam password: String?): BaseSingleResponse<Void> {
        log.debug("login  username= $username")
        val response = BaseSingleResponse<Void>()
        return try {
            val authenticate = userService!!.checkLogin(username, password)
            response.isStatus = authenticate
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    fun changePassword(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?,
            @RequestBody json: String?
    ): BaseSingleResponse<Void> {
        val response = BaseSingleResponse<Void>()
        return try {
            val m = gson.fromJson(json, UploadLocationInputModel::class.java)
            //check auth
            SecurityUtil.authenticate(userService, username, token)
            val authenticate = true
            response.isStatus = authenticate
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            response
        }
    }

    @RequestMapping(path = ["/health"])
    @ResponseBody
    fun health(): BaseSingleResponse<String> {
        val response = BaseSingleResponse<String>()
        return try {
            response.isStatus = true
            response.extra = ""
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/uploadPhoto"], method = [RequestMethod.POST])
    @ResponseBody
    fun uploadPhoto(
            @RequestHeader("token") token: String?,
            @RequestHeader(value = "username") username: String?,
            @RequestParam("photo") multipartFile: MultipartFile,
            @RequestParam(name = "tags", required = false) tags: String?
    ): BaseSingleResponse<String> {
        val response = BaseSingleResponse<String>()
        val map: MutableMap<String, String?> = HashMap()
        map["photoName"] = multipartFile.name
        map["contentType"] = multipartFile.contentType
        map["getOriginalFilename"] = multipartFile.originalFilename
        return try {
            //check auth
            SecurityUtil.authenticate(userService, username, token)
            log.debug(gson.toJson(map))
            val folder = File(filePath)
            if (!folder.exists()) {
                folder.mkdirs()
            }
            apiService!!.photo_create(username, multipartFile, filePath)
            val authenticate = true
            response.isStatus = authenticate
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    /**
     * get the list ob photo object can be view by that users
     * @param
     * @return
     */
    @RequestMapping(path = ["/getPhotoList"], method = [RequestMethod.GET])
    @ResponseBody
    fun getPhotoList(
            @RequestHeader(value = "username") username: String?,
            @RequestHeader(value = "token") token: String?): BaseResponse<PhotoBo> {
        val response = BaseResponse<PhotoBo>()
        return try {
            //check auth
            SecurityUtil.authenticate(userService, username, token)
            val res = apiService!!.findCanViewPhotoList(username)
            val authenticate = true
            response.extra = res
            response.isStatus = authenticate
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/viewPhoto/{uuid}"], produces = [MediaType.IMAGE_JPEG_VALUE], method = [RequestMethod.GET]) //get photo data
    @ResponseBody
    fun viewPhoto(
            @PathVariable("uuid") uuid: String?,
            @RequestHeader(value = "username") username: String?,
            @RequestHeader(value = "token") token: String?): ByteArray? {
        val response = BaseSingleResponse<String>()
        return try {
            val p = photoService!!.findByUsernameAndUUid(username, uuid) ?: throw IOException("photo Not Found")
            val folder = File(filePath)
            if (!folder.exists()) folder.mkdirs()
            val f = File(folder, p.photoName)
            if (!folder.exists()) throw FileNotFoundException()
            var `in`: FileInputStream? = null
            try {
                `in` = FileInputStream(f)
            } catch (e: FileNotFoundException) {
                log.warn("warn", e)
            }
            try {
                IOUtils.toByteArray(`in`)
            } finally {
                IOUtils.closeQuietly(`in`)
            }
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            null
        }
    }

    @RequestMapping(path = ["/photo/{uuid}"], method = [RequestMethod.DELETE]) //get photo data
    @ResponseBody
    fun deletePhoto(
            @RequestHeader(value = "token") token: String?,
            @RequestHeader("username") username: String?,
            @PathVariable("uuid") uuid: String?
    ): BaseSingleResponse<Boolean> {
        val response = BaseSingleResponse<Boolean>()
        return try {
            val folder = File(filePath)
            if (!folder.exists()) folder.mkdirs()
            val del = photoService!!.deletePhotoByUUID(username, uuid, folder)
            response.isStatus = true
            response.extra = del
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/allPhoto"], method = [RequestMethod.DELETE]) //delete all normal  photo data
    @ResponseBody
    fun deletePhoto(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?
    ): BaseSingleResponse<Boolean> {
        val response = BaseSingleResponse<Boolean>()
        return try {
            val folder = File(filePath)
            if (!folder.exists()) folder.mkdirs()
            val del = photoService!!.deleteAllPhoto(username, folder)
            response.isStatus = true
            response.extra = del
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/viewStatusPhoto/{uuid}"], method = [RequestMethod.GET], produces = [MediaType.IMAGE_JPEG_VALUE])
    @ResponseBody
    fun viewStatusPhoto(
            @PathVariable("uuid") uuid: String?,
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?
    ): ByteArray? {
        val response = BaseSingleResponse<String>()
        return try {
            val p = photoService!!.getStatusPhotoByUUID(username, uuid) ?: throw IOException("")
            val folder = File(filePath)
            if (!folder.exists()) folder.mkdirs()
            val f = File(folder, p.photoName)
            if (!folder.exists()) throw FileNotFoundException()
            var `in`: FileInputStream? = null
            try {
                `in` = FileInputStream(f)
            } catch (e: FileNotFoundException) {
                log.warn("warn", e)
            }
            try {
                IOUtils.toByteArray(`in`)
            } finally {
                IOUtils.closeQuietly(`in`)
            }
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            null
        }
    }

    @RequestMapping(path = ["/createRelation"], method = [RequestMethod.POST])
    @ResponseBody
    fun createRelation(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?,
            @RequestBody json: String?): BaseSingleResponse<String> {
        val response = BaseSingleResponse<String>()
        return try {
            val m = gson.fromJson(json, CreateDeleteRelationInputModel::class.java)

            //check auth
            SecurityUtil.authenticate(userService, username, token)
            val authenticate = true
            response.isStatus = authenticate
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/relation"], method = [RequestMethod.GET])
    @ResponseBody
    fun viewRelation(
            @RequestHeader(value = "username") username: String?,
            @RequestHeader(value = "token") token: String?): BaseSingleResponse<String> {
        val response = BaseSingleResponse<String>()
        return try {

            //check auth
            SecurityUtil.authenticate(userService, username, token)
            apiService!!.relation_view(username)
            val status = true
            response.isStatus = status
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/relation"], method = [RequestMethod.DELETE])
    @ResponseBody
    fun deleteRelation(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?,
            @RequestBody json: String?): BaseSingleResponse<String> {
        val response = BaseSingleResponse<String>()
        return try {
            val m = gson.fromJson(json, UploadLocationInputModel::class.java)
            //check auth
            SecurityUtil.authenticate(userService, username, token)
            val authenticate = true
            response.isStatus = authenticate
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/status"], method = [RequestMethod.DELETE])
    @ResponseBody
    fun deleteStatus(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?,
            @RequestBody json: String?): BaseSingleResponse<String> {
        val response = BaseSingleResponse<String>()
        return try {
            val m = gson.fromJson(json, UploadLocationInputModel::class.java)
            //check auth
            SecurityUtil.authenticate(userService, username, token)
            val authenticate = true

            //todo
            response.isStatus = authenticate
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/status"], method = [RequestMethod.GET])
    @ResponseBody
    fun viewStatus(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?): BaseResponse<StatusBo> {
        val response = BaseResponse<StatusBo>()
        return try {

            //check auth
            SecurityUtil.authenticate(userService, username, token)
            val list = apiService!!.status_view(username)

            //todo
            response.isStatus = true
            response.extra = list
            log.debug(gson.toJson(response))
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/status"], method = [RequestMethod.POST])
    @ResponseBody
    fun createStatus(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?,
            @RequestParam(name = "photo") file: MultipartFile?,
            @RequestParam(name = "title") title: String?,
            @RequestParam(name = "content") content: String): BaseSingleResponse<String> {
        val response = BaseSingleResponse<String>()
        return try {


            //check auth
            SecurityUtil.authenticate(userService, username, token)
            log.debug("content= $content")
            apiService!!.status_create(username, file, title, content, filePath)
            response.isStatus = true
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/getLocationList"], method = [RequestMethod.POST])
    @ResponseBody
    fun getLocationList(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?,
            @RequestBody m: ViewLocationInputModel?): BaseResponse<LocationInfo> {
        val response = BaseResponse<LocationInfo>()
        return try {
            //check auth
            SecurityUtil.authenticate(userService, username, token)
            val res = apiService!!.location_get_list(username, m)
            log.warn("getLocationList")
            log.warn(gson.toJson(res))
            val status = true
            response.isStatus = status
            response.extra = res
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/uploadLocation"], method = [RequestMethod.POST])
    @ResponseBody
    fun uploadLocation(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?,
            @RequestBody json: String?): BaseResponse<String> {
        val response = BaseResponse<String>()
        return try {
            val m = gson.fromJson(json, UploadLocationInputModel::class.java)
            //check auth
            SecurityUtil.authenticate(userService, username, token)
            apiService!!.location_upload(username, m)
            val status = true
            response.isStatus = status
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }

    @RequestMapping(path = ["/location/{id}"], method = [RequestMethod.DELETE])
    @ResponseBody
    fun getLocationList(
            @RequestHeader("token") token: String?,
            @RequestHeader("username") username: String?,
            @PathVariable("id") locationId: String?): BaseResponse<LocationInfo> {
        val response = BaseResponse<LocationInfo>()
        return try {
            //check auth
            SecurityUtil.authenticate(userService, username, token)
            val status = true
            response.isStatus = status
            response
        } catch (e: Exception) {
            response.isStatus = false
            response.errorMessage = e.message
            log.warn(gson.toJson(response))
            response
        }
    }
}