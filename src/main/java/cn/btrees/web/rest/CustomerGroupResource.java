package cn.btrees.web.rest;

import cn.btrees.domain.CustomerGroup;
import cn.btrees.repository.CustomerGroupRepository;
import cn.btrees.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link cn.btrees.domain.CustomerGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CustomerGroupResource {

    private final Logger log = LoggerFactory.getLogger(CustomerGroupResource.class);

    private static final String ENTITY_NAME = "customerGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerGroupRepository customerGroupRepository;

    public CustomerGroupResource(CustomerGroupRepository customerGroupRepository) {
        this.customerGroupRepository = customerGroupRepository;
    }

    /**
     * {@code POST  /customer-groups} : Create a new customerGroup.
     *
     * @param customerGroup the customerGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerGroup, or with status {@code 400 (Bad Request)} if the customerGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-groups")
    public ResponseEntity<CustomerGroup> createCustomerGroup(@Valid @RequestBody CustomerGroup customerGroup) throws URISyntaxException {
        log.debug("REST request to save CustomerGroup : {}", customerGroup);
        if (customerGroup.getId() != null) {
            throw new BadRequestAlertException("A new customerGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerGroup result = customerGroupRepository.save(customerGroup);
        return ResponseEntity
            .created(new URI("/api/customer-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /customer-groups/:id} : Updates an existing customerGroup.
     *
     * @param id the id of the customerGroup to save.
     * @param customerGroup the customerGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerGroup,
     * or with status {@code 400 (Bad Request)} if the customerGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-groups/{id}")
    public ResponseEntity<CustomerGroup> updateCustomerGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerGroup customerGroup
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerGroup : {}, {}", id, customerGroup);
        if (customerGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerGroup result = customerGroupRepository.save(customerGroup);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /customer-groups/:id} : Partial updates given fields of an existing customerGroup, field will ignore if it is null
     *
     * @param id the id of the customerGroup to save.
     * @param customerGroup the customerGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerGroup,
     * or with status {@code 400 (Bad Request)} if the customerGroup is not valid,
     * or with status {@code 404 (Not Found)} if the customerGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-groups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CustomerGroup> partialUpdateCustomerGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerGroup customerGroup
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerGroup partially : {}, {}", id, customerGroup);
        if (customerGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustomerGroup> result = customerGroupRepository
            .findById(customerGroup.getId())
            .map(existingCustomerGroup -> {
                if (customerGroup.getName() != null) {
                    existingCustomerGroup.setName(customerGroup.getName());
                }
                if (customerGroup.getEnName() != null) {
                    existingCustomerGroup.setEnName(customerGroup.getEnName());
                }

                return existingCustomerGroup;
            })
            .map(customerGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, customerGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /customer-groups} : get all the customerGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerGroups in body.
     */
    @GetMapping("/customer-groups")
    public List<CustomerGroup> getAllCustomerGroups() {
        log.debug("REST request to get all CustomerGroups");
        return customerGroupRepository.findAll();
    }

    /**
     * {@code GET  /customer-groups/:id} : get the "id" customerGroup.
     *
     * @param id the id of the customerGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-groups/{id}")
    public ResponseEntity<CustomerGroup> getCustomerGroup(@PathVariable Long id) {
        log.debug("REST request to get CustomerGroup : {}", id);
        Optional<CustomerGroup> customerGroup = customerGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(customerGroup);
    }

    /**
     * {@code DELETE  /customer-groups/:id} : delete the "id" customerGroup.
     *
     * @param id the id of the customerGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-groups/{id}")
    public ResponseEntity<Void> deleteCustomerGroup(@PathVariable Long id) {
        log.debug("REST request to delete CustomerGroup : {}", id);
        customerGroupRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
