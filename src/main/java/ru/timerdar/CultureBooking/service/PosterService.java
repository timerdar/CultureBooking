package ru.timerdar.CultureBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.timerdar.CultureBooking.model.Poster;
import ru.timerdar.CultureBooking.repository.PosterRepository;

import java.io.IOException;

@Service
public class PosterService {

    @Autowired
    private PosterRepository posterRepository;

    public Poster savePoster(Long id, MultipartFile image) throws IOException{
        if (posterRepository.existsById(id)){
            posterRepository.updatePoster(id, image.getBytes());
        }else{
            posterRepository.save(new Poster(id, image.getBytes()));
        }
        return posterRepository.getReferenceById(id);
    }

    public Poster getPosterOfEvent(Long id){
        return posterRepository.getReferenceById(id);
    }

}
