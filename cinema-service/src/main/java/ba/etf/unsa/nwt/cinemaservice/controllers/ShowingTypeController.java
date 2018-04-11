package ba.etf.unsa.nwt.cinemaservice.controllers;

import ba.etf.unsa.nwt.cinemaservice.services.ShowingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/showing-types")
public class ShowingTypeController {

    @Autowired
    ShowingTypeService showingTypeService;

    @GetMapping("/{id}")
    public ResponseEntity getShowingTypeById(@PathVariable("id") Long id) {
        if (showingTypeService.get(id).isPresent())
            return ResponseEntity.ok().body(showingTypeService.get(id).get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity getAllShowingTypes() {
        return ResponseEntity.ok().body(showingTypeService.all());
    }
}
