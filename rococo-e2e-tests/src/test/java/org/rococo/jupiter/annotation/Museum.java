package org.rococo.jupiter.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.rococo.jupiter.extension.CreateMuseumExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(CreateMuseumExtension.class)
public @interface Museum {
}
