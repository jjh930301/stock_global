package stock.global.core.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class MemberHistoryId implements Serializable{
    @Column(name = "ip" , columnDefinition = "VARCHAR(32)")
    private String ip;

    @Column(name = "id" , columnDefinition = "bigint unsigned")
    private Long id;

    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
}
