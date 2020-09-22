package com.mexus.homeleisure.upload.security;

import com.mexus.homeleisure.upload.api.common.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.Collections;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("JWT 필터 테스트")
class JwtFilterTest extends BaseControllerTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("서명이 틀렸을 때")
    void FailBecauseWrongSignature() throws Exception {
        String token = jwtTokenProvider.createAccessToken("TestUser1", Collections.singletonList(UserRole.ROLE_USER));
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/upload")
                .header("Authorization", "Bearer " + token + "i"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("error").value("0003"))
                .andDo(document("0003"))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("토큰형식이 틀렸을 때 ")
    void FailBecauseMalformed() throws Exception {
        String token = jwtTokenProvider.createAccessToken("TestUser1", Collections.singletonList(UserRole.ROLE_USER));
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/upload")
                .header("Authorization", "Bearer " + "i" + token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("error").value("0004"))
                .andDo(document("0004"))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("Bearer 방식의 인증이 아닐 때")
    void FailBecauseNotBearer() throws Exception {
        String token = jwtTokenProvider.createAccessToken("TestUser1", Collections.singletonList(UserRole.ROLE_USER));
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/upload")
                .header("Authorization", token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("error").value("0005"))
                .andDo(document("0005"))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("만료된 토큰 일 때")
    void FailBecauseExpired() throws Exception {
        String token = jwtTokenProvider.generateToken("TestUser1", Collections.singletonList(UserRole.ROLE_USER), -10);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/upload")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("error").value("0006"))
                .andDo(document("0006"))
                .andDo(print())
        ;
    }
}