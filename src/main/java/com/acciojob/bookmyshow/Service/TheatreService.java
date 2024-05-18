package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Models.Theatre;
import com.acciojob.bookmyshow.Repository.TheatreRepository;
import com.acciojob.bookmyshow.Requests.AddTheatreRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheatreService
{
    @Autowired
    private TheatreRepository theatreRepository;

    public String addTheatre(AddTheatreRequest theatreRequest)
    {
        Theatre theatre=Theatre.builder().noOfScreens(theatreRequest.getNoOfScreens())
                .name(theatreRequest.getName())
                .address(theatreRequest.getAddress())
                .build();

        theatre=theatreRepository.save(theatre);
        return "Theatre has been saved to theDB with theatreId "+theatre.getTheatreId();
    }
}
