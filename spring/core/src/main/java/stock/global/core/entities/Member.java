package stock.global.core.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stock.global.core.config.DefaultTime;
import stock.global.core.enums.MemberTypeEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Member extends DefaultTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="account_id" , length=64)
    private String accountId;

    @Column(name="password" , columnDefinition="VARCHAR(128) DEFAULT NULL")
    private String password;

    @Column(name = "type")
    private MemberTypeEnum type;

    @Column(name = "accessed_by")
    private Long accessedBy;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(
        targetEntity=MemberHistory.class,
        fetch=FetchType.LAZY,
        mappedBy="member"
    )
    private List<MemberHistory> histories;

    public Member(
        String accountId,
        String password,
        MemberTypeEnum type
    ) {
        this.setAccountId(accountId);
        this.setPassword(password);
        this.setType(type);
    }
}
