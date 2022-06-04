package com.airbnb.service.wish.unit;

import com.airbnb.api.wish.dto.WishResponse;
import com.airbnb.domain.House;
import com.airbnb.domain.Role;
import com.airbnb.domain.User;
import com.airbnb.domain.Wish;
import com.airbnb.repository.HouseRepository;
import com.airbnb.repository.UserRepository;
import com.airbnb.repository.WishRepository;
import com.airbnb.service.WishService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class WishServiceTest {

    @InjectMocks
    private WishService wishService;

    @Mock
    private WishRepository wishRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HouseRepository houseRepository;

    @Test
    @DisplayName("원하는 방을 찜 목록에 추가되고, 해당 wish를 반환한다.")
    public void create_wish_test() {
        // given
        House wantHouse = new House("house1", 10000, null, null, null);
        User user = new User("Shine", "test@gamil.com", Role.GUEST);
        Wish wish = new Wish(wantHouse, user);

        given(houseRepository.findById(any())).willReturn(Optional.of(wantHouse));
        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(wishRepository.save(any())).willReturn(wish);

        // when
        Wish wishResult = wishService.addWish(user.getId(), wantHouse.getId());

        // then
        then(wishResult.getUser()).isEqualTo(user);
        then(wishResult.getHouse()).isEqualTo(wantHouse);
        then(wishResult).isEqualTo(wish);
    }

    @Test
    @DisplayName("존재하지 않는 방의 번호가 넘어올 경우 예외를 던진다.")
    public void create_wish_not_exists_house_test() {
        // given
        User user = new User("Shine", "test@gamil.com", Role.GUEST);
        given(houseRepository.findById(any())).willReturn(Optional.ofNullable(null));

        // when
        Throwable throwable = catchThrowable(() -> wishService.addWish(user.getId(), any()));

        // then
        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("존재하지 않는 회원 번호가 넘어올 경우 예외를 던진다.")
    public void create_wish_not_exists_user_test() {
        // given
        House wantHouse = new House("house1", 10000, null, null, null);
        given(houseRepository.findById(any())).willReturn(Optional.of(wantHouse));
        given(userRepository.findById(any())).willReturn(Optional.ofNullable(null));

        // when
        Throwable throwable = catchThrowable(() -> wishService.addWish(any(), wantHouse.getId()));

        // then
        then(throwable).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("회원의 이메일로 회원이 찜한 숙소 리스트를 조회할 수 있다.")
    public void get_wish_list_test() {
        // given
        User user = new User("Shine", "test@gamil.com", Role.GUEST);
        House wantHouse1 = new House("house1", 10000, null, null, null);
        House wantHouse2 = new House("house2", 20000, null, null, null);
        House wantHouse3 = new House("house3", 30000, null, null, null);

        Wish wish1 = new Wish(wantHouse1, user);
        Wish wish3 = new Wish(wantHouse3, user);

        given(userRepository.findUserWishListByEmail(user.getEmail())).willReturn(List.of(wish1, wish3));

        // when
        List<WishResponse> wishList = wishService.getWishListByEmail(user.getEmail());

        // then
        then(wishList).extracting("name").containsExactly("house1", "house3");
    }
}
