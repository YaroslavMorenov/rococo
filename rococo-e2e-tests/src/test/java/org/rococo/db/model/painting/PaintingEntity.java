package org.rococo.db.model.painting;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "painting")
public class PaintingEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(nullable = false)
  private String title;

  @Column
  private String description;

  @Column(columnDefinition = "bytea")
  private byte[] content;
  @Column(name = "museum_id")
  private UUID museumId;
  @Column(name = "artist_id", nullable = false)
  private UUID artistId;
}
