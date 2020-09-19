package com.mexus.homeleisure.upload.api.common;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("시스템 오류 테스트")
class SystemErrorTest extends BaseControllerTest {

    @Test
    @Disabled
    @WithMockUser("TestUser1")
    @DisplayName("해석할 수 없는 RequestBody가 왔을 때")
    void FailBecauseCantParse() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/board/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("Can't Parse Value Like this"))
                .andExpect(status().isBadRequest())
                .andDo(print())
        ;
    }
}
