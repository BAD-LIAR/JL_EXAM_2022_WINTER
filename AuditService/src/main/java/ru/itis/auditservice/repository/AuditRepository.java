package ru.itis.auditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.auditservice.entity.AuditEntity;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
}
