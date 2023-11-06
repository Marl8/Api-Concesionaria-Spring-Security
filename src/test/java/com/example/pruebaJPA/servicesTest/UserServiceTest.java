package com.example.pruebaJPA.servicesTest;

import com.example.pruebaJPA.dto.UserDto;
import com.example.pruebaJPA.dto.UserResponseDto;
import com.example.pruebaJPA.dto.UserSaveDto;
import com.example.pruebaJPA.dto.UserUpdateDto;
import com.example.pruebaJPA.entity.UserEntity;
import com.example.pruebaJPA.repository.IUserRepository;
import com.example.pruebaJPA.service.UserServiceImpl;
import com.example.pruebaJPA.utils.ObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    IUserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl service;

    @Test
    @DisplayName("Test OK de mostrar todos los users con paginación")
    void getUsersTestOk(){

        // Arrange
        List<UserDto> usersDto = ObjectUtils.listaUsersDto();
        List<UserEntity> users = ObjectUtils.listaUsers();
        Pageable pageable= PageRequest.of(0, 5);
        Page<UserEntity> listaSut = new PageImpl<>(users);
        Page<UserDto> expected = new PageImpl<>(usersDto);

        // Act
        when(repository.findAll(pageable)).thenReturn(listaSut);
        Page<UserDto> actual = service.getUsers(pageable);

        // Assert
        assertAll(
                () -> assertEquals(expected.getSize(), actual.getSize()),
                () -> assertEquals(expected.getTotalElements(), actual.getTotalElements()),
                () -> assertEquals(expected.get().findFirst(), actual.get().findFirst())
        );
    }

    @Test
    @DisplayName("Test OK para guardar Usuarios")
    void saveUserOkTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // Arrange
        UserEntity user = ObjectUtils.user();
        Optional<UserEntity> opUser = Optional.empty();
        UserSaveDto saveDto = ObjectUtils.userSaveDto();
        UserResponseDto sut = new UserResponseDto("Usuario guardado");
        UserResponseDto expected = new UserResponseDto("Usuario guardado con éxito");
        Method metodoPrivado = UserServiceImpl.class.getDeclaredMethod("verificarSiExiste", String.class);
        metodoPrivado.setAccessible(true);

        // Act
        when(metodoPrivado.invoke(service,user.getUsername())).thenReturn(opUser);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("123");
        when(repository.save(user)).thenReturn(user);
        UserResponseDto actual = service.save(saveDto);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test Update Ok")
    void testUpdateOKTest(){

        // Arrange
        UserUpdateDto userUpdateDto = ObjectUtils.userUpdateDto();
        Optional<UserEntity> user = ObjectUtils.userOptional();
        UserResponseDto expected = new UserResponseDto("El usuario fue modificado con éxito");

        // Act
        when(repository.findById(any())).thenReturn(user);
        UserResponseDto actual = service.update(userUpdateDto);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test delete Ok")
    void testDeleteOkTest(){

        // Arrange
        Optional<UserEntity> user = ObjectUtils.userOptional();
        UserResponseDto expected = new UserResponseDto("El usuario fue eliminado con éxito");

        // Act
        when(repository.findById(any())).thenReturn(user);
        UserResponseDto actual = service.delete(user.get().getId());

        // Assert
        assertEquals(expected, actual);
    }
}
