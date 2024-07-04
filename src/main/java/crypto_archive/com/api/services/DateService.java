package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.DateMapper;
import crypto_archive.com.api.repositories.DateRepository;
import crypto_archive.com.api.responses.DateResponse;
import crypto_archive.com.api.table_entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DateService {
    @Autowired
    private DateRepository dateRepository;
    @Autowired
    private UserService userService;

//    public Set<DateResponse> allDates(HttpHeaders headers) {
//        User user = userService.getUserFromHeaders(headers)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        return DateMapper.INSTANCE.toDtos(dateRepository.findByUser(user).get());
//    }


}
