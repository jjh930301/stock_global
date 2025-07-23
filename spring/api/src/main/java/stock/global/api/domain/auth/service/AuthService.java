package stock.global.api.domain.auth.service;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import stock.global.api.domain.auth.dto.ChangePasswordDto;
import stock.global.api.domain.auth.dto.MemberDto;
import stock.global.api.domain.auth.dto.MemberResponseDto;
import stock.global.api.domain.auth.dto.PermitAccountDto;
import stock.global.api.repositories.MemberHistoryRepository;
import stock.global.api.repositories.MemberRepository;
import stock.global.core.constants.DB;
import stock.global.core.entities.Member;
import stock.global.core.entities.MemberHistory;
import stock.global.core.entities.MemberHistoryId;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.exceptions.ApiException;
import stock.global.core.models.ApiRes;
import stock.global.core.models.TokenInfo;
import stock.global.core.util.JwtUtil;
import stock.global.core.util.StringUtil;

@Slf4j
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberHistoryRepository memberHistoryRepository;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final JwtUtil jwtUtil;
    private final JavaMailSender mailSender;
    private final StringUtil stringUtil;

    public AuthService(
        MemberRepository memberRepository,
        MemberHistoryRepository memberHistoryRepository,
        BCryptPasswordEncoder bcryptEncoder,
        JwtUtil jwtUtil,
        JavaMailSender mailSender,
        StringUtil stringUtil
    ) {
        this.memberRepository = memberRepository;
        this.memberHistoryRepository = memberHistoryRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.jwtUtil = jwtUtil;
        this.mailSender = mailSender;
        this.stringUtil = stringUtil;
    }

    @Transactional(transactionManager = DB.JPA_TX_MANAGER)
    public ApiRes<MemberResponseDto> loginMember(MemberDto dto , String ip) {
        final Member member = this.memberRepository
            .findByAccountId(dto.getAccountId())
            .orElseThrow(() -> new ApiException("check account id and password" , HttpStatus.NOT_FOUND));
        if(
            member == null || 
            !this.bcryptEncoder.matches(dto.getAccountPassword(), member.getPassword())
        ) throw new ApiException("check account id and password" , HttpStatus.NOT_FOUND);
        final boolean isHistory = this.memberHistoryRepository.existByMemberId(member.getId());
        this.memberHistoryRepository.save(
            MemberHistory
                .builder()
                .id(
                    MemberHistoryId
                        .builder()
                        .id(member.getId())
                        .ip(ip)
                        .createdAt(LocalDateTime.now())
                        .build()
                )
                .member(member)
                .build()
        );
        return ApiRes
            .<MemberResponseDto>builder()
            .messages(Arrays.asList("Successfully login %s".formatted(member.getAccountId())))
            .payload(
                MemberResponseDto
                    .builder()
                    .accountId(member.getAccountId())
                    .accessToken(
                        this.jwtUtil.createToken(
                            member.getId(), 
                            member.getAccountId(), 
                            member.getType()
                        )
                    )
                    .isHIstory(isHistory)
                    .type(member.getType())
                    .build()
            )
            .build();
    }

    public ApiRes<MemberResponseDto> getUserInfo(TokenInfo token) {
        return ApiRes
            .<MemberResponseDto>builder()
            .payload(
                MemberResponseDto
                .builder()
                .accountId(token.getAccountId())
                .accessToken(
                    this.jwtUtil.createToken(
                        token.getId(), 
                        token.getAccountId(), 
                        token.getType() == MemberTypeEnum.ADMIN.ordinal() 
                            ? MemberTypeEnum.ADMIN 
                            : MemberTypeEnum.USER
                    )
                )
                .type(
                    token.getType() == MemberTypeEnum.ADMIN.ordinal() 
                        ? MemberTypeEnum.ADMIN 
                        : MemberTypeEnum.USER
                )
                .build()
            )
            .build();
    }

    public ApiRes<?> permitAccount(PermitAccountDto dto , Long adminId) {
        Member member = this.memberRepository.findByAccountId(dto.getEmail())
            .orElseThrow(() -> new ApiException("not found account" , HttpStatus.BAD_REQUEST));
        String password = this.stringUtil.generateRandomString(8);
        member.setPassword(this.bcryptEncoder.encode(password));
        member.setType(dto.getType());
        member.setAccessedBy(adminId);
        this.memberRepository.save(member);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(member.getAccountId());
        message.setFrom(System.getenv("MAIL_ID"));
        message.setSubject("no reply");
        message.setText("""
            temp password : %s
        """.formatted(password));
        mailSender.send(message);
        return ApiRes
            .builder()
            .payload(true)
            .messages(Arrays.asList("successfully send message"))
            .build();
    }

    public ApiRes<Boolean> postAccount(String email){
        this.memberRepository.findByAccountIdAndDeletedAtIsNull(email).ifPresent(member -> {
            throw new ApiException("cannot create %s".formatted(email) , HttpStatus.BAD_REQUEST);
        });
        this.memberRepository.save(
            Member
                .builder()
                .accountId(email)
                .type(MemberTypeEnum.USER)
                .build()
        );
        return ApiRes
            .<Boolean>builder()
            .payload(true)
            .messages(Arrays.asList("successfully request account"))
            .build();
    }

    public ApiRes<Boolean> patchPassword(TokenInfo token , ChangePasswordDto body) {
        if(!body.getPassword().equals(body.getConfirmPassword()))
            throw new ApiException("password is not match");
        
        Member member = this.memberRepository.findById(token.getId()).orElseThrow(() -> {
            throw new ApiException("not found %s".formatted(token.getAccountId()));
        });
        if(!this.bcryptEncoder.matches(body.getPrevPassword(), member.getPassword()))
            throw new ApiException("not match previous password");
        member.setPassword(this.bcryptEncoder.encode(body.getPassword()));
        this.memberRepository.save(member);
        return ApiRes.<Boolean>of(true, "successfully change password");
    }
}
