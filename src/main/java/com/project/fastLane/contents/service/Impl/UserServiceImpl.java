package com.project.fastLane.contents.service.Impl;

import com.project.fastLane.commons.enmuns.Status;
import com.project.fastLane.contents.model.dto.UserDto;
import com.project.fastLane.contents.model.entity.UserEntity;
import com.project.fastLane.contents.repository.UserRepository;
import com.project.fastLane.contents.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

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
    public UserDto loginUser(String email) {
        UserEntity userEntity = userRepository.findByEmailAndStatus(email, Status.Y.name())
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 email 입니다."));

        userEntity.setStatus(Status.N);
        userRepository.save(userEntity);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new User(userEntity.getEmail(), userEntity.getPassword(),
                true, true, true, true, new ArrayList<>());

    }
}
