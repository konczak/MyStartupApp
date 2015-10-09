package pl.konczak.mystartupapp.domain.employee.finder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import pl.konczak.mystartupapp.domain.employee.dto.PersistentAuditEventSnapshot;
import pl.konczak.mystartupapp.domain.employee.entity.PersistentAuditEvent;
import pl.konczak.mystartupapp.domain.employee.repo.IPersistentAuditEventRepository;
import pl.konczak.mystartupapp.sharedkernel.annotations.Finder;

@Finder
public class PersistentAuditEventSnapshotFinder
   implements IPersistentAuditEventSnapshotFinder {

   private final IPersistentAuditEventRepository persistentAuditEventRepository;

   @Autowired
   public PersistentAuditEventSnapshotFinder(IPersistentAuditEventRepository persistentAuditEventRepository) {
      this.persistentAuditEventRepository = persistentAuditEventRepository;
   }

   private List<PersistentAuditEventSnapshot> convert(List<PersistentAuditEvent> persistentAuditEvents) {
      return persistentAuditEvents.stream()
         .map(PersistentAuditEvent::toSnapshot)
         .collect(Collectors.toList());
   }

   @Override
   public List<PersistentAuditEventSnapshot> findAll() {
      List<PersistentAuditEvent> persistentAuditEvents = persistentAuditEventRepository.findAll();

      return convert(persistentAuditEvents);
   }

   @Override
   public List<PersistentAuditEventSnapshot> findByPrincipal(String principal) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public List<PersistentAuditEventSnapshot> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

   @Override
   public List<PersistentAuditEventSnapshot> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

}
