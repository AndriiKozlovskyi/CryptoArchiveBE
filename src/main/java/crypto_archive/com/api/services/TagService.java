package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.TagMapper;
import crypto_archive.com.api.repositories.TagRepository;
import crypto_archive.com.api.requests.TagRequest;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.table_entities.Event;
import crypto_archive.com.api.table_entities.Tag;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public Set<TagResponse> getAllTags() {
        return TagMapper.INSTANCE.toDtos(new HashSet<>(tagRepository.findAll()));
    }

    public TagResponse updateTag(Integer id, TagRequest tagRequest) {
        Tag _tag = tagRepository.findById(id)
                .map(tag -> {
                    tag.setName(tagRequest.getName());
                    return tagRepository.save(tag);
                }).orElseThrow(() -> new ResourceNotFoundException("Tag not found with id " + id));

        return TagMapper.INSTANCE.toDto(_tag);
    }

    public void deleteTag(Integer id) {
        tagRepository.deleteById(id);
    }

    public Set<Tag> getTagsByIds(Set<Integer> ids) {
        Set<Tag> tags = new HashSet<>();
        for(Integer id : ids) {
            tags.add(getTagById(id));
        }
        return tags;
    }

    public Tag getTagById(Integer id) {
        return tagRepository.getReferenceById(id);
    }
}
