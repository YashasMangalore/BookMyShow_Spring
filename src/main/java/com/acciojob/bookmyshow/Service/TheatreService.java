package com.acciojob.bookmyshow.Service;

import com.acciojob.bookmyshow.Enums.SeatType;
import com.acciojob.bookmyshow.Models.Theatre;
import com.acciojob.bookmyshow.Models.TheatreSeat;
import com.acciojob.bookmyshow.Repository.TheatreRepository;
import com.acciojob.bookmyshow.Repository.TheatreSeatRepository;
import com.acciojob.bookmyshow.Requests.AddTheatreRequest;
import com.acciojob.bookmyshow.Requests.AddTheatreSeatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheatreService
{
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private TheatreSeatRepository theatreSeatRepository;

    public String addTheatre(AddTheatreRequest theatreRequest)
    {
        Theatre theatre=Theatre.builder().noOfScreens(theatreRequest.getNoOfScreens())
                .name(theatreRequest.getName())
                .address(theatreRequest.getAddress())
                .build();

        theatre=theatreRepository.save(theatre);
        return "Theatre has been saved to the DataBase with theatreId: "+theatre.getTheatreId();
    }

    public String associateTheatreSeats(AddTheatreSeatRequest theatreSeatRequest)
    {
        int theatreId= theatreSeatRequest.getTheatreId();
        int noOfClassicSeats= theatreSeatRequest.getNoOfClassicSeats();
        int noOfPremiumSeats= theatreSeatRequest.getNoOfPremiumSeats();
        List<TheatreSeat> theatreSeatList=new ArrayList<>();
        //1 get theatreEntity from DB
        Theatre theatre=theatreRepository.findById(theatreId).get();

        //2 generate seatNO
        int noOfRowsOfClassicSeats= noOfClassicSeats/5;
        int noOfSeatsInLastRow= noOfClassicSeats%5;
        int row;

        for(row=1;row<=noOfRowsOfClassicSeats;row++)
        {
            for (int j = 1; j <=5 ; j++)
            {
                char ch=(char)('A'+j-1);
                String seatNo= ""+row+ch;

                TheatreSeat theatreSeat=TheatreSeat.builder().seatNo(seatNo)
                        .seatType(SeatType.CLASSIC)
                        .theatre(theatre)//theatre entity is foreign key so set
                        .build();

                theatreSeatList.add(theatreSeat);
            }
        }
        for (int j = 1; j <=noOfSeatsInLastRow ; j++) //for last row
        {
            char ch=(char)('A'+j-1);
            String seatNo= ""+row+ch;

            TheatreSeat theatreSeat=TheatreSeat.builder().seatNo(seatNo)
                    .seatType(SeatType.CLASSIC)
                    .theatre(theatre)//theatre entity is foreign key so set
                    .build();

            theatreSeatList.add(theatreSeat);
        }

        //same for premium
        int cur;
        if(noOfSeatsInLastRow>0)
        {
            cur=row+1;
        }
        else
        {
            cur=row;
        }
        int noOfRowsOfPremiumSeats= noOfPremiumSeats/5;
        noOfSeatsInLastRow= noOfPremiumSeats%5;

        for(row=cur;row<=cur+noOfRowsOfPremiumSeats;row++)
        {
            for (int j = 1; j <=5 ; j++)
            {
                char ch=(char)('A'+j-1);
                String seatNo= ""+row+ch;

                TheatreSeat theatreSeat=TheatreSeat.builder().seatNo(seatNo)
                        .seatType(SeatType.PREMIUM)
                        .theatre(theatre)//theatre entity is foreign key so set
                        .build();

                theatreSeatList.add(theatreSeat);
            }
        }
        for (int j = 1; j <=noOfSeatsInLastRow ; j++) //for last row
        {
            char ch=(char)('A'+j-1);
            String seatNo= ""+row+ch;

            TheatreSeat theatreSeat=TheatreSeat.builder().seatNo(seatNo)
                    .seatType(SeatType.PREMIUM)
                    .theatre(theatre)//theatre entity is foreign key so set
                    .build();

            theatreSeatList.add(theatreSeat);
        }
        theatre.setTheatreSeatList(theatreSeatList);
        theatreRepository.save(theatre);

        //save all to DB
        theatreSeatRepository.saveAll(theatreSeatList);

        return "The theatre seats has been associated";
    }
}
