package com.example.template;

import com.example.template.example.SampleUser;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, String> {
}
