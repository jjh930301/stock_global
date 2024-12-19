package stock.global.api.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import stock.global.api.domain.auth.dto.MemberDto;
import stock.global.api.domain.auth.service.AuthService;
import stock.global.api.repositories.MemberRepository;
import stock.global.core.entities.MemberEntity;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.exceptions.ApiException;
import stock.global.core.util.JwtUtil;

public class AuthServiceTest {
    
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder bcryptEncoder;

    @InjectMocks
    private AuthService authservice;

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

    @DisplayName("로그인")
    @Test
    void testLoginMember() {
        MemberDto dto = new MemberDto("loginUser", "wrongpassword");
    
        MemberEntity memberEntity = new MemberEntity("loginUser", "encodedPassword", MemberTypeEnum.USER);
        when(this.memberRepository.findByAccountId("loginUser").get()).thenReturn(memberEntity);
        when(this.bcryptEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);
    
        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            this.authservice.loginMember(dto);
        });
    
        assertEquals("check account id and password", exception.getMessage());
    }
}
