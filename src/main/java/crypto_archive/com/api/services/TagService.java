package crypto_archive.com.api.services;

import crypto_archive.com.api.mappers.TagMapper;
import crypto_archive.com.api.repositories.TagRepository;
import crypto_archive.com.api.responses.TagResponse;
import crypto_archive.com.api.table_entities.Tag;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public List<TagResponse> getAllTags() {
        return TagMapper.INSTANCE.toDtos(tagRepository.findAll());
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
