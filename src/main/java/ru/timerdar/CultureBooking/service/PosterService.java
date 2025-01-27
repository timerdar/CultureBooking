package ru.timerdar.CultureBooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.timerdar.CultureBooking.model.Event;
import ru.timerdar.CultureBooking.model.Poster;
import ru.timerdar.CultureBooking.repository.PosterRepository;

import java.awt.*;
import java.io.IOException;

@Service
public class PosterService {

    @Autowired
    private PosterRepository posterRepository;

    public Poster savePoster(Long id, MultipartFile image) throws IOException{
        Poster poster = new Poster(id, image.getBytes());
        return posterRepository.save(poster);
    }

    public Poster getPosterOfEvent(Long id){
        return posterRepository.getReferenceById(id);
    }

}
