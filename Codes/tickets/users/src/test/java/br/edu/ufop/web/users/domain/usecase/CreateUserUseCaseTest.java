package br.edu.ufop.web.users.domain.usecase;

import br.edu.ufop.web.users.domain.CCNetworkDomain;
import br.edu.ufop.web.users.domain.UserDomain;
import br.edu.ufop.web.users.domain.port.CCNRepositoryPort;
import br.edu.ufop.web.users.enums.EnumUserType;
import br.edu.ufop.web.users.exception.UseCaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    private UserDomain userDomain;
    private CCNetworkDomain ccNetworkDomain;
    @Mock
    private CCNRepositoryPort repositoryPort;

    @BeforeEach
    void setUp() {
        this.userDomain = new UserDomain();
        this.userDomain.setId(UUID.randomUUID());
        this.userDomain.setEmail(UUID.randomUUID().toString());
        this.userDomain.setPassword(UUID.randomUUID().toString());

        this.ccNetworkDomain = new CCNetworkDomain();
        this.ccNetworkDomain.setId(UUID.randomUUID());
        this.ccNetworkDomain.setName(UUID.randomUUID().toString());
    }

    @AfterEach
    void tearDown() {
        this.userDomain = null;
    }

    @Test
    void userNameIsNull_deve_disparar_exception() {
        this.userDomain.setName(null);

        Assertions.assertNull(this.userDomain.getName());

        CreateUserUseCase useCase = new CreateUserUseCase(null);
        useCase.setUserDomain(this.userDomain);

        Assertions.assertThrows(UseCaseException.class, () -> {
            useCase.validate();
        });

    }

    @Test
    void userNameNotNull_deve_passar() {

        this.userDomain.setName(UUID.randomUUID().toString());
        this.userDomain.setCcNetwork(this.ccNetworkDomain);

        Assertions.assertNotNull(this.userDomain);
        Assertions.assertNotNull(this.userDomain.getName());
        Assertions.assertNull(this.userDomain.getType());

        CreateUserUseCase useCase = new CreateUserUseCase(this.repositoryPort);
        useCase.setUserDomain(this.userDomain);

        // Mock para a porta do cartão do crédito
        when( repositoryPort.findById(Mockito.any()) )
                .thenReturn(Optional.of(this.ccNetworkDomain));


        Assertions.assertDoesNotThrow(() -> {
            useCase.validate();
        });

        Assertions.assertEquals(this.userDomain.getType(), EnumUserType.CUSTOMER);






    }
    @Test
    void setUserDomain() {
    }
}