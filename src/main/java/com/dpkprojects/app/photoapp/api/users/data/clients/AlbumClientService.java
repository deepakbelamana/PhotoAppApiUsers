package com.dpkprojects.app.photoapp.api.users.data.clients;

import com.dpkprojects.app.photoapp.api.users.ui.model.album.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "album-service")
public interface AlbumClientService {
    @GetMapping("/users/{userId}/albums")
    public List<AlbumResponseModel> getAlbums(@PathVariable String userId);
}
