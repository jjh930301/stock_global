package stock.global.api.service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import stock.global.api.domain.auth.dto.MemberDto;
import stock.global.api.domain.auth.dto.MemberResponseDto;
import stock.global.api.domain.auth.dto.RegistMemberDto;
import stock.global.api.domain.auth.service.AuthService;
import stock.global.api.repositories.MemberRepository;
import stock.global.core.entities.MemberEntity;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.exceptions.ApiException;
import stock.global.core.models.ApiRes;
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

    @DisplayName("사용자 생성")
    @Test
    void testRegistUser() {
        RegistMemberDto dto = new RegistMemberDto("testmember", "password", MemberTypeEnum.USER);
        MemberEntity memberEntity = new MemberEntity(dto.getAccountId(), dto.getAccountPassword(), dto.getType());

        when(memberRepository.save(any(MemberEntity.class))).thenReturn(memberEntity);

        ApiRes<MemberResponseDto> response = authservice.registUser(dto);

        assertNotNull(response);
        assertNotNull(response.getPayload()); // Make sure payload is not null
        assertEquals("testmember", response.getPayload().getAccountId());
        assertEquals(MemberTypeEnum.USER, response.getPayload().getType());
    }

    @DisplayName("로그인")
    @Test
    void testLoginMember() {
        MemberDto dto = new MemberDto("loginUser", "wrongpassword");
    
        MemberEntity memberEntity = new MemberEntity("loginUser", "encodedPassword", MemberTypeEnum.USER);
        when(this.memberRepository.findByAccountId("loginUser")).thenReturn(memberEntity);
        when(this.bcryptEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);
    
        ApiException exception = Assertions.assertThrows(ApiException.class, () -> {
            this.authservice.loginMember(dto);
        });
    
        assertEquals("check account id and password", exception.getMessage());
    }
}