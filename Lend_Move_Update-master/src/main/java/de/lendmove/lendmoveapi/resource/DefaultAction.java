package de.lendmove.lendmoveapi.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Template request for the controller
 */
public interface DefaultAction<T>
{
    @GetMapping("/all")
    @CrossOrigin(origins = "*")
    ResponseEntity<Map<String, Object>> findAll(@RequestParam(required = false) String name,
                                                @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size,
                                                @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortDir);

    @GetMapping("/{id}")
    @CrossOrigin(origins = "*")
    ResponseEntity<T> findById(@PathVariable("id") long id);

    @PostMapping("/add")
    @CrossOrigin(origins = "*")
    ResponseEntity<Map<String, Object>> create(@RequestBody T t);

    @PutMapping("/update/{id}")
    @CrossOrigin(origins = "*")
    ResponseEntity<Map<String, Object>> update(@PathVariable("id") long id, @RequestBody T t);

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @CrossOrigin(origins = "*")
    ResponseEntity<HttpStatus> delete(@PathVariable("id") long id);

    @GetMapping("/search/{searchText}")
    @CrossOrigin(origins = "*")
    ResponseEntity<Map<String, Object>> search(@PathVariable(required = true) String searchText,
                                               @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size);
}
