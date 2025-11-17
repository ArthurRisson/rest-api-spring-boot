package br.edu.atitus.api_example.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_example.entities.PointEntity;
import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.PointRepository;
import br.edu.atitus.api_example.repositories.UserRepository;

@RestController
@RequestMapping("/ws/point")
public class PointController {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public PointController(PointRepository pointRepository, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    // Método auxiliar para pegar o usuário autenticado
    private UserEntity getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @GetMapping
    public ResponseEntity<List<PointEntity>> getPoints() {
        // REQUISITO: Listar apenas pontos do usuário logado
        UserEntity user = getAuthenticatedUser();
        List<PointEntity> points = pointRepository.findByUser(user);
        return ResponseEntity.ok(points);
    }

    @PostMapping
    public ResponseEntity<PointEntity> savePoint(@RequestBody PointEntity point) {
        UserEntity user = getAuthenticatedUser();
        point.setUser(user);
        point.setId(null);
        PointEntity savedPoint = pointRepository.save(point);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPoint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePoint(@PathVariable UUID id, @RequestBody PointEntity pointDTO) {
        // REQUISITO: Verificar se o ponto existe
        PointEntity point = pointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto não encontrado")); // Em um cenário ideal, use Exception Handler para retornar 404

        // REQUISITO: Verificar se o ponto pertence ao usuário logado
        UserEntity user = getAuthenticatedUser();
        if (!point.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Este ponto não pertence a você.");
        }

        // Atualiza os dados
        point.setDescription(pointDTO.getDescription());
        point.setLatitude(pointDTO.getLatitude());
        point.setLongitude(pointDTO.getLongitude());
        
        // Salva
        pointRepository.save(point);
        
        return ResponseEntity.ok(point);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePoint(@PathVariable UUID id) {
        // Sugestão: Também validar se o ponto é do usuário antes de deletar
        PointEntity point = pointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ponto não encontrado"));
        
        UserEntity user = getAuthenticatedUser();
        if (!point.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Este ponto não pertence a você.");
        }

        pointRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}