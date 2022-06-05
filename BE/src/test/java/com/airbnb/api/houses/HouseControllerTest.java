package com.airbnb.api.houses;

import com.airbnb.domain.*;
import com.airbnb.repository.HouseRepository;
import com.airbnb.repository.UserRepository;
import com.airbnb.repository.WishRepository;
import com.airbnb.utils.geometry.GeometryUtils;
import com.airbnb.utils.oauth.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class HouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private WishRepository wishRepository;

    String userEmail = "user@gamil.com";

    @Test
    @DisplayName("검색 조건을 받아서 1Km 이내의 조회한 숙소 목록을 반환한다.")
    public void get_house_list_in_1km_test() throws Exception {
        // given
        User user = new User("Shine", userEmail, Role.GUEST);
        User host = new User("Host", "host@gamil.com", Role.HOST);


        // 등록하기
        userRepository.save(user);
        userRepository.save(host);

        // house 목록 추가하기
        addHouse(user, host, 127.029371, 37.495421, 1);
        addHouse(user, host, 127.029371, 37.495422, 2);
        addHouse(user, host, 127.029371, 37.495423, 3);
        addHouse(user, host, 127.056910, 37.495424, 4);
        addHouse(user, host, 127.056911, 37.495425, 5);

        // 가입 후 토큰을 생성
        String jwtToken = jwtTokenProvider.createAccessToken(user.getEmail());

        ResultActions resultActions = mockMvc.perform(get("/api/houses")
                        .param("longitude", "127.029371")
                        .param("latitude", "37.495428")
                        .param("startDateTime", "202205031100")
                        .param("endDateTime", "202205031120")
                        .param("minFee", "10000")
                        .param("maxFee", "100000")
                        .param("numberOfGuests", "7")
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print());

        // when, then
        resultActions.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("house1"))
                .andExpect(jsonPath("$[1].name").value("house2"))
                .andExpect(jsonPath("$[2].name").value("house3"))
                .andDo(document("get-wish-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("[].id").description("Id of wish"),
                                fieldWithPath("[].name").description("Name of house"),
                                fieldWithPath("[].price").description("Price of house"),
                                fieldWithPath("[].detailInfo.maxNumber").description("Maximum allowable number of people"),
                                fieldWithPath("[].detailInfo.type").description("Room type"),
                                fieldWithPath("[].detailInfo.roomIntroduction").description("Short room introduction"),
                                fieldWithPath("[].detailInfo.rate").description("Rate of house"),
                                fieldWithPath("[].detailInfo.commentCount").description("Count of comment"),
                                fieldWithPath("[].images").description("Images of house"),
                                fieldWithPath("[].hostName").description("Host name")
                        )
                ))
        ;
    }

    public long addHouse(User user, User host, Double longitude, Double latitude, int num) {
        House house = new House("house" + num, 10000,
                new DetailInfo(10, "oneRoom", "방입니다", 4.8, 10),
                GeometryUtils.toPoint(longitude, latitude), host);

        houseRepository.save(house);
        Wish wish = wishRepository.save(new Wish(house, user));
        return wish.getId();
    }

}
