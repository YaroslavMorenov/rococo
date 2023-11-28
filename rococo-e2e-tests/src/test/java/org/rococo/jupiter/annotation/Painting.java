package org.rococo.jupiter.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.rococo.jupiter.extension.CreatePaintingExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(CreatePaintingExtension.class)
public @interface Painting {

    boolean isNeedToCreatePainting() default true;
    boolean isNeedToCreateMuseum() default false;
}
