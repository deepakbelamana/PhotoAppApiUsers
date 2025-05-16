package com.dpkprojects.app.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dpkprojects.app.photoapp.api.users.data.clients.AlbumClientService;
import com.dpkprojects.app.photoapp.api.users.ui.model.album.AlbumResponseModel;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dpkprojects.app.photoapp.api.users.data.UserDataRepository;
import com.dpkprojects.app.photoapp.api.users.data.UserEntity;
import com.dpkprojects.app.photoapp.api.users.shared.UserDto;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDataRepository userRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    //RestTemplate restTemplate;
    AlbumClientService albumClientService;

    @Autowired
    Environment environment;

	Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public UserDto CreateUser(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userRepo.save(userEntity);

        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepo.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepo.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("user not found..!");
        }
       /* String albumsUrl = String.format(environment.getProperty("albums.url"),userId);
        ResponseEntity<List<AlbumResponseModel>> albumListResponse = restTemplate.exchange(albumsUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
        });
        List<AlbumResponseModel> albumList = albumListResponse.getBody();*/
        List<AlbumResponseModel> albumList = null;
        try {
            albumList = albumClientService.getAlbums(userId);
        } catch (FeignException fe) {
			logger.error(fe.getLocalizedMessage());
        }
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        userDto.setAlbumResponseModelList(albumList);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }
}
