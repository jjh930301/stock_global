package stock.global.core.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stock.global.core.enums.MemberTypeEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="account_id")
    private String accountId;

    @Column(name="password")
    private String password;

    @Column(name = "type")
    private MemberTypeEnum type;

    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

    public MemberEntity(
        String accountId,
        String password,
        MemberTypeEnum type
    ) {
        this.setAccountId(accountId);
        this.setPassword(password);
        this.setType(type);
    }
}
