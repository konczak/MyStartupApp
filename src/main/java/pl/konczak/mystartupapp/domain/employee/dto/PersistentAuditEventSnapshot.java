package pl.konczak.mystartupapp.domain.employee.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class PersistentAuditEventSnapshot {

   private final Long id;

   private final String principal;

   private final LocalDateTime auditEventDate;

   private final String auditEventType;

   private final Map<String, String> data;

   public PersistentAuditEventSnapshot(Long id, String principal, LocalDateTime auditEventDate, String auditEventType,
      Map<String, String> data) {
      this.id = id;
      this.principal = principal;
      this.auditEventDate = auditEventDate;
      this.auditEventType = auditEventType;
      this.data = data;
   }

   public Long getId() {
      return id;
   }

   public String getPrincipal() {
      return principal;
   }

   public LocalDateTime getAuditEventDate() {
      return auditEventDate;
   }

   public String getAuditEventType() {
      return auditEventType;
   }

   public Map<String, String> getData() {
      return data;
   }

}
