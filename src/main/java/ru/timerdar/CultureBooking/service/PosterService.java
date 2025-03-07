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

    public Poster savePoster(Long eventId, MultipartFile image) throws IOException{
        if (posterRepository.existsByEventId(eventId)){
            posterRepository.updatePoster(eventId, image.getBytes());
        }else{
            posterRepository.save(new Poster(null, eventId, image.getBytes()));
        }
        return posterRepository.getReferenceById(eventId);
    }

    public Poster getPosterOfEvent(Long id){
        return posterRepository.getReferenceById(id);
    }

}
