package stock.global.core.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@SuperBuilder
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    protected LocalDateTime updatedAt;
    
    public BaseEntity(){}

    public BaseEntity(LocalDateTime createdAt , LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
