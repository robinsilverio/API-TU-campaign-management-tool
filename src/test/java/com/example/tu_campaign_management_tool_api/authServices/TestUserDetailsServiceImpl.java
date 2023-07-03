package com.example.tu_campaign_management_tool_api.authServices;

import com.example.tu_campaign_management_tool_api.models.AuthUser;
import com.example.tu_campaign_management_tool_api.repositories.UserRepository;
import com.example.tu_campaign_management_tool_api.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestUserDetailsServiceImpl {
    @Mock
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserRepository userRepository;
    private AuthUser expectedUser;
    private UserDetails actualUserDetails;

    private void arrangeUserAndMockUserRepository() {
        expectedUser = new AuthUser("T14832", "T14832", "test123", "Super User e-Sales");
        setUserRepository(userDetailsService, userRepository);
    }

    private void actUserDetailService() {
        when(userRepository.findByLogin(expectedUser.getLogin())).thenReturn(Optional.of(expectedUser));
        actualUserDetails = userDetailsService.loadUserByUsername(expectedUser.getLogin());
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsService = new UserDetailsServiceImpl();
    }

    @Test
    public void should_check_if_method_findByLogin_is_invoked_one_time_when_retrieving_a_user() {
        // Arrange.
        arrangeUserAndMockUserRepository();
        // Act.
        when(userRepository.findByLogin(expectedUser.getLogin())).thenReturn(Optional.of(expectedUser));
        UserDetails userDetails = userDetailsService.loadUserByUsername(expectedUser.getLogin());
        // Assert.
        verify(userRepository, times(1)).findByLogin(expectedUser.getLogin());
    }

    @Test
    public void should_return_a_user_after_retrieving_a_user_containing_a_username() {
        // Arrange.
        arrangeUserAndMockUserRepository();
        // Act.
        actUserDetailService();
        // Assert.
        assertThat(actualUserDetails.getUsername(), is(expectedUser.getLogin()));
    }

    @Test
    public void should_trigger_an_UsernameNotFoundException_when_retrieving_a_non_existent_user() {
        // Arrange
        String nonExistingUsername = "aaaa";
        setUserRepository(userDetailsService, userRepository);
        // Act and Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(nonExistingUsername);
        });
        assertThat("Gebruiker niet gevonden met de gebruikersnaam: " + nonExistingUsername, is(exception.getMessage()));
    }

    /**
     * To prevent violation of don't tell principle, we are adding a
     * user repository to the service by using reflection. Which means, we perform
     * a validation instead of modifying the class by checking if
     * there is a case that the user repo exists in userDetailsService class,
     * the property is filled with the mock.
     *
     * @param userDetailsService
     * @param userRepository
     * @author Robin Medeiros Silverio
     */
    private void setUserRepository(UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        try {
            Field userRepositoryField = UserDetailsServiceImpl.class.getDeclaredField("userRepository");
            userRepositoryField.setAccessible(true);
            userRepositoryField.set(userDetailsService, userRepository);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
