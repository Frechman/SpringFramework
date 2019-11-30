package ru.gavrilov.libraryreact.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gavrilov.libraryreact.repository.UserRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpringDataJpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final ru.gavrilov.libraryreact.model.User userByEmail = userRepository.findByEmail(username);
        return new User(userByEmail.getEmail(), userByEmail.getPassword(),
                userByEmail.getRoles().stream()
                        .map(Enum::name)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet()));
    }
}
