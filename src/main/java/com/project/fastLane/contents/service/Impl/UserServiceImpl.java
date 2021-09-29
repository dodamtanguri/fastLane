package com.project.fastLane.contents.service.Impl;

import com.project.fastLane.contents.model.dto.UserDto;
import com.project.fastLane.contents.model.entity.UserEntity;
import com.project.fastLane.contents.repository.UserRepository;
import com.project.fastLane.contents.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info(String.valueOf(mapper));
        UserEntity user = mapper.map(userDto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return mapper.map(user, UserDto.class);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(userEntity.getEmail(), userEntity.getPassword(),
                true, true, true, true, new ArrayList<>());

    }
}
