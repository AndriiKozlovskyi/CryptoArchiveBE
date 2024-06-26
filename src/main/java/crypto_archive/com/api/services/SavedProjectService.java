package crypto_archive.com.api.services;

import crypto_archive.com.api.repositories.SavedProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavedProjectService {
    @Autowired
    SavedProjectRepository repository;


}
