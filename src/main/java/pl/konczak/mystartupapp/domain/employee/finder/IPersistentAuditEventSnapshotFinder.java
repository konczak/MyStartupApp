package pl.konczak.mystartupapp.domain.employee.finder;

import java.time.LocalDateTime;
import java.util.List;

import pl.konczak.mystartupapp.domain.employee.dto.PersistentAuditEventSnapshot;

public interface IPersistentAuditEventSnapshotFinder {

   List<PersistentAuditEventSnapshot> findAll();

   List<PersistentAuditEventSnapshot> findByPrincipal(String principal);

   List<PersistentAuditEventSnapshot> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after);

   List<PersistentAuditEventSnapshot> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
