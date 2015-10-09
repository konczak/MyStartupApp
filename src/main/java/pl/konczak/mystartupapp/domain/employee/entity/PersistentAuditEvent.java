package pl.konczak.mystartupapp.domain.employee.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.validation.constraints.NotNull;

import org.springframework.boot.actuate.audit.AuditEvent;

import pl.konczak.mystartupapp.config.persistance.converter.LocalDateTimePersistenceConverter;
import pl.konczak.mystartupapp.domain.employee.dto.PersistentAuditEventSnapshot;

@Entity
public class PersistentAuditEvent
   implements Serializable {

   private static final long serialVersionUID = 8588815863555757736L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @NotNull
   @Column(nullable = false)
   private String principal;

   @NotNull
   @Convert(converter = LocalDateTimePersistenceConverter.class)
   private LocalDateTime auditEventDate;

   private String auditEventType;

   @ElementCollection
   @MapKeyColumn(name = "name")
   @Column(name = "value")
   @CollectionTable
   private final Map<String, String> data = new HashMap<>();

   protected PersistentAuditEvent() {
   }

   public PersistentAuditEvent(String principal, String auditEventType, Map<String, String> data) {
      this.principal = principal;
      this.auditEventDate = LocalDateTime.now();
      this.auditEventType = auditEventType;
      this.data.putAll(data);
   }

   public PersistentAuditEventSnapshot toSnapshot() {
      return new PersistentAuditEventSnapshot(id, principal, auditEventDate, auditEventType,
         Collections.unmodifiableMap(data));
   }

   public AuditEvent toAuditEvent() {
      ZonedDateTime zdt = auditEventDate.atZone(ZoneId.systemDefault());
      Date output = Date.from(zdt.toInstant());
      return new AuditEvent(output, principal, auditEventType, convertDataToObjects(data));
   }

   private Map<String, Object> convertDataToObjects(Map<String, String> data) {
      Map<String, Object> results = new HashMap<>();

      if (data != null) {
         data.keySet()
            .stream()
            .forEach(key -> results.put(key, data.get(key)));
      }

      return results;
   }
}
