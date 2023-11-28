package org.rococo.jupiter.extension;


import io.qameta.allure.AllureId;
import org.junit.jupiter.api.extension.*;
import org.rococo.db.model.user.Authority;
import org.rococo.db.model.user.AuthorityEntity;
import org.rococo.db.model.user.UserEntity;
import org.rococo.db.repository.user.UserRepository;
import org.rococo.db.repository.user.UserRepositoryHibernate;
import org.rococo.jupiter.annotation.ApiLogin;
import org.rococo.jupiter.annotation.DBUser;
import org.rococo.model.UserJson;

import java.util.ArrayList;
import java.util.Arrays;

import static org.rococo.model.UserJson.emptyUserJson;
import static org.rococo.util.FakerUtils.generateRandomName;

public class DbCreateUserExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(DbCreateUserExtension.class);
    private static final String DEFAULT_PASSWORD = "12345";
    private final UserRepository userRepository = new UserRepositoryHibernate();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        boolean dbUserAnnotation = extensionContext.getRequiredTestMethod().isAnnotationPresent(DBUser.class);
        if (dbUserAnnotation) {
            UserJson userJson = usersForTest(extensionContext);
            String password = userJson.getPassword().isEmpty() ? DEFAULT_PASSWORD : userJson.getPassword();
            UserEntity authUser = new UserEntity();
            authUser.setUsername(userJson.getUsername().isEmpty() ? generateRandomName() : userJson.getUsername());
            authUser.setPassword(password);
            authUser.setEnabled(true);
            authUser.setAccountNonExpired(true);
            authUser.setAccountNonLocked(true);
            authUser.setCredentialsNonExpired(true);
            authUser.setAuthorities(new ArrayList<>(Arrays.stream(Authority.values())
                    .map(a -> {
                        AuthorityEntity ae = new AuthorityEntity();
                        ae.setAuthority(a);
                        ae.setUser(authUser);
                        return ae;
                    }).toList()));

            userRepository.createUserForTest(authUser);
            UserJson result = UserJson.fromEntity(authUser);
            result.setPassword(password);
            extensionContext.getStore(NAMESPACE).put(getAllureId(extensionContext), result);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(UserJson.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(NAMESPACE)
                .get(getAllureId(extensionContext), UserJson.class);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        UserJson userFromTest = extensionContext.getStore(NAMESPACE).get(getAllureId(extensionContext), UserJson.class);
        if (userFromTest != null) {
            userRepository.removeAfterTest(UserJson.toEntity(userFromTest));
        }
    }

    public static String getAllureId(ExtensionContext context) {
        AllureId allureId = context.getRequiredTestMethod().getAnnotation(AllureId.class);
        if (allureId == null) {
            throw new IllegalStateException("Annotation @AllureId must be present!");
        }
        return allureId.value();
    }

    private UserJson usersForTest(ExtensionContext extensionContext) {
        ApiLogin apiLogin = extensionContext.getRequiredTestMethod().getAnnotation(ApiLogin.class);
        UserJson userJson = new UserJson();
        if (apiLogin != null) {
            userJson.setUsername(apiLogin.username());
            userJson.setPassword(apiLogin.password());
            return userJson;
        }
        return emptyUserJson();
    }
}
