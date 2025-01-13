package stock.global.core.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "member_histories")
public class MemberHistory {
    @EmbeddedId
    private MemberHistoryId id;

    @MapsId(value="id")
    @ManyToOne(targetEntity=Member.class)
    @JoinColumn(
        name = "id" , 
        columnDefinition = "BIGINT NOT NULL",
        foreignKey = @ForeignKey(name = "fk_members_histories")
    )
    private Member member;
}
