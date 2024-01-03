package com.alok.grpc.grpcdemo

import com.example.demo.SalaryRequest
import com.example.demo.SalaryResponse
import com.example.demo.SalaryServiceGrpcKt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@GrpcService
@Service
class SalaryService(val opaProperties: OPAProperties) : SalaryServiceGrpcKt.SalaryServiceCoroutineImplBase() {


    companion object {
        val log: Logger = LoggerFactory.getLogger(SalaryService::class.java)
        val restTemplate = RestTemplate()
    }

    override suspend fun isAllowedToCheckSalary(request: SalaryRequest): SalaryResponse {
        val opaPaths = ArrayList<String>(opaProperties.path)
        opaPaths.add(request.employee)
        val opaRequestBodyDto = OpaRequestBodyDto(request.currentUser, opaPaths, opaProperties.method)
        val opaRequestDto = OpaRequestDto(opaRequestBodyDto)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers[HttpHeaders.CONTENT_TYPE] = "application/json"

        log.info("OPA URL: ${opaProperties.url}")

        val httpEntity = HttpEntity(opaRequestDto, headers)
        val responseEntity: ResponseEntity<OpaResponseDto> =
            withContext(Dispatchers.IO) {
                restTemplate.exchange(opaProperties.url, HttpMethod.POST, httpEntity, OpaResponseDto::class.java)
            }
        log.info("OPA Response: ${responseEntity.body?.result?.allow}")
        opaPaths.clear()
        val salaryResponse = SalaryResponse.newBuilder().setIsAllowed(responseEntity.body?.result?.allow ?: false).build()
        return salaryResponse
    }

}


data class OpaRequestBodyDto(
    val user: String,
    val path: List<String>,
    val method: String
)

data class OpaResponseBodyDto(
    val allow: Boolean
) {
    constructor() : this(false)
}

data class OpaRequestDto(
    val input: OpaRequestBodyDto
) {
    constructor() : this(OpaRequestBodyDto("", emptyList(), ""))
}

data class OpaResponseDto(
    val result: OpaResponseBodyDto
) {
    constructor() : this(OpaResponseBodyDto(false))
}