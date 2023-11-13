package org.rococo.controller;

import org.rococo.model.PaintingJson;
import org.rococo.service.api.RestPaintingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/painting")
public class PaintingController {

  private final RestPaintingClient paintingService;

  @Autowired
  public PaintingController(RestPaintingClient paintingService) {
    this.paintingService = paintingService;
  }

  @GetMapping()
  public Page<PaintingJson> getAll(@RequestParam(required = false) String title,
                                   @PageableDefault Pageable pageable) {
    return paintingService.getAll(title, pageable);
  }

  @GetMapping("/{id}")
  public PaintingJson findPaintingById(@PathVariable("id") String id) {
    return paintingService.findPaintingById(id);
  }

  @GetMapping("/author/{id}")
  public Page<PaintingJson> findPaintingByAuthorId(@PathVariable("id") String id,
                                                   @PageableDefault Pageable pageable) {
    return paintingService.findPaintingByAuthorId(id, pageable);
  }

  @PatchMapping()
  public PaintingJson updatePainting(@RequestBody PaintingJson painting) {
    return paintingService.update(painting);
  }

  @PostMapping()
  public PaintingJson addPainting(@RequestBody PaintingJson painting) {
    return paintingService.add(painting);
  }
}
