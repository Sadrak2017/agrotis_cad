package org.agrotis.cad.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "property")
@Entity(name = "property")
public class PropertyEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "valor", nullable = false)
  private String description;

  @Column(name = "created", updatable = false, nullable = false)
  private Timestamp created = new Timestamp(System.currentTimeMillis());

}
