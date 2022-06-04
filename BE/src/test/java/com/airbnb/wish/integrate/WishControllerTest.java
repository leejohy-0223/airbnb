package com.airbnb.wish.integrate;

import com.airbnb.api.wish.dto.WishCreateRequest;
import com.airbnb.api.wish.dto.WishDeleteRequest;
import com.airbnb.domain.*;
import com.airbnb.repository.HouseRepository;
import com.airbnb.repository.UserRepository;
import com.airbnb.repository.WishRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class WishControllerTest {

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
    @DisplayName("가입된 회원이 wish list에 숙소를 추가하면 OK응답이 온다")
    public void create_wish_list_test() throws Exception {
        // given
        User user = new User("Shine", userEmail, Role.GUEST);
        User host = new User("Host", "host@gamil.com", Role.HOST);
        House house = new House("house", 10000, new DetailInfo(10, "oneRoom", "방입니다", 4.8, 10), null, host);

        // 등록하기
        userRepository.save(user);
        houseRepository.save(house);

        // 가입 후 토큰을 생성
        String jwtToken = jwtTokenProvider.createAccessToken(userEmail);

        // 요청 dto 생성
        WishCreateRequest wishCreateRequest = new WishCreateRequest(user.getId(), house.getId());

        ResultActions resultActions = mockMvc.perform(post("/api/wish")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(wishCreateRequest)))
                .andDo(print());

        // when, then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("valid").exists())
                .andExpect(jsonPath("message").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andDo(document("create-wish-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Jwt Token")
                        ),
                        requestFields(
                                fieldWithPath("userId").description("id of user"),
                                fieldWithPath("houseId").description("id of house")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("valid").description("result validation"),
                                fieldWithPath("message").description("result message")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("token이 없는 회원이 wish list에 숙소를 추가하면 에러 메시지를 반환한다.")
    public void create_wish_list_error_test() throws Exception {
        // given
        User user = new User("Shine", userEmail, Role.GUEST);
        User host = new User("Host", "host@gamil.com", Role.HOST);
        House house = new House("house", 10000, new DetailInfo(10, "oneRoom", "방입니다", 4.8, 10), null, host);

        // 등록하기
        userRepository.save(user);
        houseRepository.save(house);

        // 요청 dto 생성
        WishCreateRequest wishCreateRequest = new WishCreateRequest(user.getId(), house.getId());

        ResultActions resultActions = mockMvc.perform(post("/api/wish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(wishCreateRequest)))
                .andDo(print());

        // when, then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("valid").value("false"))
                .andExpect(jsonPath("message").exists())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andDo(document("create-wish-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type")
                        ),
                        requestFields(
                                fieldWithPath("userId").description("id of user"),
                                fieldWithPath("houseId").description("id of house")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("valid").description("result validation"),
                                fieldWithPath("message").description("result message")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("가입한 회원은 위시 리스트 요청시 리스트를 반환한다.")
    public void get_wish_list_test() throws Exception {
        // given
        User user = new User("Shine", userEmail, Role.GUEST);
        User host = new User("Host", "host@gamil.com", Role.HOST);


        // 등록하기
        userRepository.save(user);
        userRepository.save(host);

        // wishList등록하기
        addWishHouse(user, host, 1);
        addWishHouse(user, host, 2);
        addWishHouse(user, host, 3);

        // 가입 후 토큰을 생성
        String jwtToken = jwtTokenProvider.createAccessToken(user.getEmail());

        ResultActions resultActions = mockMvc.perform(get("/api/wish")
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
                                fieldWithPath("[].userId").description("Id of user"),
                                fieldWithPath("[].name").description("Name of house"),
                                fieldWithPath("[].price").description("Price of house"),
                                fieldWithPath("[].detailInfo.maxNumber").description("Maximum allowable number of people"),
                                fieldWithPath("[].detailInfo.type").description("Room type"),
                                fieldWithPath("[].detailInfo.roomIntroduction").description("Short room introduction"),
                                fieldWithPath("[].detailInfo.rate").description("Rate of house"),
                                fieldWithPath("[].detailInfo.commentCount").description("Count of comment")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("가입한 회원은 위시 리스트 에서 일부 집을 삭제할 수 있다.")
    public void delete_house_from_wish_list_test() throws Exception {
        // given
        User user = new User("Shine", userEmail, Role.GUEST);
        User host = new User("Host", "host@gamil.com", Role.HOST);

        // 등록하기
        userRepository.save(user);
        userRepository.save(host);

        // wishList등록하기
        long firstWishId = addWishHouse(user, host, 1);
        addWishHouse(user, host, 2);
        addWishHouse(user, host, 3);

        // 가입 후 토큰을 생성
        String jwtToken = jwtTokenProvider.createAccessToken(user.getEmail());

        // 요청 dto 생성
        WishDeleteRequest wishDeleteRequest = new WishDeleteRequest(user.getId(), firstWishId);

        ResultActions resultActions = mockMvc.perform(delete("/api/wish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(wishDeleteRequest)))
                .andDo(print());

        // when, then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("valid").value("true"))
                .andExpect(jsonPath("message").value("OK"))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andDo(document("delete-wish-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type")
                        ),
                        requestFields(
                                fieldWithPath("userId").description("id of user"),
                                fieldWithPath("wishId").description("id of wish")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("valid").description("result validation"),
                                fieldWithPath("message").description("result message")
                        )
                ))
        ;
    }

    @Test
    @DisplayName("가입한 회원일지라도 존재하지 않는 wish를 삭제하려 들면 에러를 반환한다.")
    public void delete_wish_from_not_exists_wish_test() throws Exception {
        // given
        User user = new User("Shine", userEmail, Role.GUEST);
        User host = new User("Host", "host@gamil.com", Role.HOST);

        // 등록하기
        userRepository.save(user);
        userRepository.save(host);

        // wishList등록하기
        long firstWishId = addWishHouse(user, host, 1);
        addWishHouse(user, host, 2);
        addWishHouse(user, host, 3);

        // 가입 후 토큰을 생성
        String jwtToken = jwtTokenProvider.createAccessToken(user.getEmail());

        // 요청 dto 생성
        WishDeleteRequest wishDeleteRequest = new WishDeleteRequest(user.getId(), 777L);

        ResultActions resultActions = mockMvc.perform(delete("/api/wish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(wishDeleteRequest)))
                .andDo(print());

        // when, then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("valid").value("false"))
                .andExpect(jsonPath("message").value("해당 위시 리스트를 찾을 수 없습니다."))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andDo(document("delete-wish-list",
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("header content type")
                        ),
                        requestFields(
                                fieldWithPath("userId").description("id of user"),
                                fieldWithPath("wishId").description("id of wish")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("valid").description("result validation"),
                                fieldWithPath("message").description("result message")
                        )
                ))
        ;
    }

    public long addWishHouse(User user, User host, int num) {
        House house = new House("house" + num, 10000, new DetailInfo(10, "oneRoom", "방입니다", 4.8, 10), null, host);

        houseRepository.save(house);
        Wish wish = wishRepository.save(new Wish(house, user));
        return wish.getId();
    }
}
