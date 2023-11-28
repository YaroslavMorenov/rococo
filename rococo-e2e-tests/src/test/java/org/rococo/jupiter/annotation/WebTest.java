package org.rococo.jupiter.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.rococo.jupiter.extension.ApiLoginExtension;
import org.rococo.jupiter.extension.BrowserExtension;
import org.rococo.jupiter.extension.DbCreateUserExtension;
import org.rococo.jupiter.extension.TestPreConditionExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({TestPreConditionExtension.class, DbCreateUserExtension.class, ApiLoginExtension.class, BrowserExtension.class})
public @interface WebTest {
}
