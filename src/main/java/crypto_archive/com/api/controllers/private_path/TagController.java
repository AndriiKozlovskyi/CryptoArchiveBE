package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.mappers.TagMapper;
import crypto_archive.com.api.repositories.TagRepository;
import crypto_archive.com.api.requests.TagRequest;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.table_entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${base-path}/tag")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody TagRequest tag) {
        Tag _tag = new Tag();
        _tag.setName(tag.getName());
        Tag createdTag = tagRepository.save(_tag);
        System.out.println(createdTag);
        return ResponseEntity.ok(TagMapper.INSTANCE.toDto(createdTag));
    }
}
