package stock.global.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.media.Schema;
import stock.global.core.enums.MemberTypeEnum;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Schema(hidden= true)
@Documented
public @interface TokenRole {
    MemberTypeEnum value() default MemberTypeEnum.USER;
}