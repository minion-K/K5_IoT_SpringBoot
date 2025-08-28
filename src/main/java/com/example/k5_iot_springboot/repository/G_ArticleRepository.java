package com.example.k5_iot_springboot.repository;

import com.example.k5_iot_springboot.entity.G_Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface G_ArticleRepository extends JpaRepository<G_Article, Long> {
}
