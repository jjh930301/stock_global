package stock.global.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="site_tokens")
@SuperBuilder
public class SiteToken extends BaseEntity{
    @Id
    @Column
    private Long site;

    @Column
    private String token;

    public SiteToken(Long site , String token) {
        super();
        this.site = site;
        this.token = token;
    }

    public Long getSite() {
        return this.site;
    }

    public String getValue() {
        return this.token;
    }
}
