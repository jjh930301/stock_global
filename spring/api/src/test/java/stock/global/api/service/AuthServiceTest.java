package stock.global.api.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import stock.global.api.domain.auth.dto.MemberDto;
import stock.global.api.domain.auth.service.AuthService;
import stock.global.api.repositories.MemberRepository;
import stock.global.core.entities.Member;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.exceptions.ApiException;


@ActiveProfiles("test") 
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceTest {
    
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BCryptPasswordEncoder bcryptEncoder;

    @InjectMocks
    private AuthService authService;

    final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @DisplayName("로그인 실패 - 비밀번호 불일치")
    @Test
    void testLoginMember() {
        // given
        MemberDto dto = new MemberDto(
            "loginUser",
            "rawPassword"
        );

        // Member 객체의 실제 저장된 비밀번호는 "encodedWrongPassword"라고 가정
        Member memberEntity = new Member(
            dto.getAccountId(), 
            "encodedWrongPassword", 
            MemberTypeEnum.USER
        );

        // when - Mock 설정
        when(memberRepository.findByAccountId("loginUser")).thenReturn(Optional.of(memberEntity));
        when(bcryptEncoder.matches("rawPassword", "encodedWrongPassword")).thenReturn(false);

        // then - 예외 발생 검증
        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            authService.loginMember(dto , "127.0.0.1");
        });

        assertEquals("check account id and password", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
