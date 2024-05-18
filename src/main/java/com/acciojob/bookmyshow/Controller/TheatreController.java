package com.acciojob.bookmyshow.Controller;

import com.acciojob.bookmyshow.Requests.AddTheatreRequest;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("theatre")
public class TheatreController
{
    @PostMapping("add")
    public RequestEntity addTheatre(@RequestBody AddTheatreRequest addTheatreRequest)
    {
        //now convert this to an entity


    }
}
