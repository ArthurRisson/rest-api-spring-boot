package br.edu.atitus.api_example.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.atitus.api_example.entities.PointEntity;
import br.edu.atitus.api_example.entities.UserEntity;

public interface PointRepository extends JpaRepository<PointEntity, UUID> {
    
    // Método para encontrar pontos de um usuário específico
    List<PointEntity> findByUser(UserEntity user);
    
}