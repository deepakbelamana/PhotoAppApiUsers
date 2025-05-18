package com.dpkprojects.app.photoapp.api.users.data.clients;

import com.dpkprojects.app.photoapp.api.users.ui.model.album.AlbumResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "album-service")
public interface AlbumClientService {
    @GetMapping("/users/{userId}/albums")
    @CircuitBreaker(name = "album-service", fallbackMethod = "getAlbumsFallbackMethod")
    public List<AlbumResponseModel> getAlbums(@PathVariable String userId);

    default List<AlbumResponseModel> getAlbumsFallbackMethod(String userId, Throwable exception) {
        System.out.println("user id" + userId);
        System.out.println("exception took place :" + exception.getMessage());
        return new ArrayList<>();
    }
}
