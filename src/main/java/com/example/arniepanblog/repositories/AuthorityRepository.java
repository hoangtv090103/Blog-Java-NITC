package com.example.arniepanblog.repositories;

import com.example.arniepanblog.models.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository <Authority, String> {
}
