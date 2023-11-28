package org.rococo.jupiter.extension;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.rococo.db.jpa.EntityManagerFactoryProvider;

public class TestPreConditionExtension implements SuiteExtension {

    @Override
    public void beforeAllTests(ExtensionContext extensionContext) {
        System.out.println("#### BEFORE ALL TESTS! ####");
    }

    @Override
    public void afterAllTests() {
        System.out.println("#### AFTER ALL TESTS! ####");
        EntityManagerFactoryProvider.INSTANCE.allStoredEntityManagerFactories()
                .forEach(EntityManagerFactory::close);
    }
}
