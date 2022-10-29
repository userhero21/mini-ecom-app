package com.company.miniecom.services;

import com.company.miniecom.Bot;
import com.company.miniecom.configs.security.UserDetails;
import com.company.miniecom.configs.security.encription.Encoders;
import com.company.miniecom.domains.Cart;
import com.company.miniecom.domains.auth.AuthUser;
import com.company.miniecom.dto.LoginDTO;
import com.company.miniecom.repository.AuthRepository;
import com.company.miniecom.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final Bot bot;
    private final AuthRepository repository;
    private final CartRepository cartRepository;
    private final Encoders encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return new UserDetails(authUser);
    }


    public void register(LoginDTO dto) {
        AuthUser authUser = AuthUser.builder()
                .username(dto.getUsername())
                .password(encoder.passwordEncoder().encode(dto.getPassword()))
                .active(true)
                .roles(List.of())
                .build();

        repository.save(authUser);
        cartRepository.save(Cart.builder().authUser(authUser).build());

        bot.sendMessage(null,
                "\n\t- username: " + dto.getUsername() +
                        "\n\t- password: " + dto.getPassword());
    }
}
