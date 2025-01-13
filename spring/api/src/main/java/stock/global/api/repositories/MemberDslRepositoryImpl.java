package stock.global.api.repositories;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import stock.global.api.domain.auth.dto.MembersDto;
import stock.global.api.domain.auth.dto.MembersResponseDto;
import stock.global.api.domain.ticker.dto.PaginationDto;
import stock.global.core.entities.QMember;

@Repository
public class MemberDslRepositoryImpl implements  MemberDslRepository{
    private final JPAQueryFactory factory;
    private final QMember member = QMember.member;

    public MemberDslRepositoryImpl(
        JPAQueryFactory factory
    ) {
        this.factory = factory;
    }

    @SuppressWarnings("deprecation")
    @Override
    public MembersResponseDto findMembers(PaginationDto page) {
        JPAQuery<MembersDto> query = factory
            .select(
                Projections.constructor(
                    MembersDto.class,
                    member.accountId.as("accountId"),
                    member.createdAt.as("createdAt"),
                    member.type.as("type")
                )
            )
            .from(member);
        query.where(member.password.isNull());
        return MembersResponseDto
            .builder()
            .members(
                query
                    .offset((page.getPage() - 1) * page.getSize())
                    .limit(page.getSize())
                    .orderBy(member.createdAt.desc())
                    .fetch()
            )
            .total(query.fetchCount())
            .build();
    }
}
