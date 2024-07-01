package crypto_archive.com.api.controllers.private_path;

import crypto_archive.com.api.mappers.TagMapper;
import crypto_archive.com.api.repositories.TagRepository;
import crypto_archive.com.api.requests.TagRequest;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.services.TagService;
import crypto_archive.com.api.table_entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${base-path}/tag")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TagController {
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagResponse>> allTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody TagRequest tag) {
        Tag _tag = new Tag();
        _tag.setName(tag.getName());
        Tag createdTag = tagRepository.save(_tag);
        return ResponseEntity.ok(TagMapper.INSTANCE.toDto(createdTag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
