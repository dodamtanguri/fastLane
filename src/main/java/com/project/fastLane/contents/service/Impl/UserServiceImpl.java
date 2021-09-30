package com.project.fastLane.contents.service.Impl;

import com.project.fastLane.commons.enmuns.Status;
import com.project.fastLane.contents.model.dto.UserDto;
import com.project.fastLane.contents.model.entity.UserEntity;
import com.project.fastLane.contents.model.request.LoginReq;
import com.project.fastLane.contents.model.request.PasswordReq;
import com.project.fastLane.contents.repository.UserRepository;
import com.project.fastLane.contents.service.UserService;
import java.util.ArrayList;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    /**
     * 회원가입
     *
     * @param userDto
     * @return UserRes
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        log.info(String.valueOf(mapper));

        UserEntity user = mapper.map(userDto, UserEntity.class);

        // 비밀번호 bcrypt
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    /**
     * 회원로그인
     *
     * @param req
     * @return nocontent
     * @throws IllegalAccessException
     */
    @Override
    @Transactional(readOnly = true)
    public void loginUser(LoginReq req) throws IllegalAccessException {

        UserEntity userEntity = userRepository.findByEmailAndStatus(req.getEmail(), Status.Y)
            .orElseThrow(() -> new UsernameNotFoundException(req.getEmail()));

        // 비밀번호 체크
        if (!passwordEncoder.matches(req.getPassword(), userEntity.getPassword())) {
            throw new IllegalAccessException("잘못된 비밀번호입니다.");
        }
    }

    /**
     * 회원 삭제
     *
     * @param email
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 email 입니다."));

        userEntity.setStatus(Status.N);
        userRepository.save(userEntity);
    }

    /**
     * 회원비밀번호 변경
     *
     * @param req
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyPassword(PasswordReq req) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();
        UserEntity userEntity = userRepository.findByEmailAndStatus(email, Status.Y)
            .orElseThrow(() -> new UsernameNotFoundException(email));
        userEntity.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(userEntity);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new User(userEntity.getEmail(), userEntity.getPassword(),
            true, true, true, true, new ArrayList<>());

    }
}
