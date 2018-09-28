package com.blog.api.repository;

import com.blog.api.repository.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

}
