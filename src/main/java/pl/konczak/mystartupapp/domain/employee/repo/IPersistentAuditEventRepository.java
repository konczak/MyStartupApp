package pl.konczak.mystartupapp.domain.employee.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.konczak.mystartupapp.domain.employee.entity.PersistentAuditEvent;

public interface IPersistentAuditEventRepository
   extends JpaRepository<PersistentAuditEvent, Long> {

   List<PersistentAuditEvent> findByPrincipal(String principal);

   List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after);

   List<PersistentAuditEvent> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
