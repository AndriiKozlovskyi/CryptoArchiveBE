package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.responses.DateResponse;
import crypto_archive.com.api.services.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "${base-path}/date")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DateController {
    @Autowired
    DateService dateService;

    @GetMapping
    public ResponseEntity<?> getAllDates(@RequestHeader HttpHeaders headers) {
//        Set<DateResponse> dates = dateService.allDates(headers);
        return ResponseEntity.ok("SSSS");
    }

}
