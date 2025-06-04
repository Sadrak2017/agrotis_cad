package org.agrotis.cad.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "register")
@Entity(name = "register")
public class RegisterEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "uuid2" )
  @Column(name = "uuid")
  private String uuid;

  @Column(name = "name")
  private String name;

  @Column(name = "date_ini", nullable = false)
  private Integer data_ini;

  @Column(name = "date_fim", nullable = false)
  private Integer date_fim;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "property_id", nullable = false)
  private PropertyEntity propertyEntity;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "laboratory_id", nullable = false)
  private LaboratoryEntity laboratoryEntity;

  @Column(name = "created", updatable = false, nullable = false)
  private Timestamp created = new Timestamp(System.currentTimeMillis());
}
