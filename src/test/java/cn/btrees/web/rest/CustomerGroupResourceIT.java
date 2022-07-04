package cn.btrees.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cn.btrees.IntegrationTest;
import cn.btrees.domain.CustomerGroup;
import cn.btrees.repository.CustomerGroupRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CustomerGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/customer-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerGroupRepository customerGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerGroupMockMvc;

    private CustomerGroup customerGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerGroup createEntity(EntityManager em) {
        CustomerGroup customerGroup = new CustomerGroup().name(DEFAULT_NAME).enName(DEFAULT_EN_NAME);
        return customerGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerGroup createUpdatedEntity(EntityManager em) {
        CustomerGroup customerGroup = new CustomerGroup().name(UPDATED_NAME).enName(UPDATED_EN_NAME);
        return customerGroup;
    }

    @BeforeEach
    public void initTest() {
        customerGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomerGroup() throws Exception {
        int databaseSizeBeforeCreate = customerGroupRepository.findAll().size();
        // Create the CustomerGroup
        restCustomerGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerGroup)))
            .andExpect(status().isCreated());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerGroup testCustomerGroup = customerGroupList.get(customerGroupList.size() - 1);
        assertThat(testCustomerGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerGroup.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    void createCustomerGroupWithExistingId() throws Exception {
        // Create the CustomerGroup with an existing ID
        customerGroup.setId(1L);

        int databaseSizeBeforeCreate = customerGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerGroup)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerGroupRepository.findAll().size();
        // set the field null
        customerGroup.setName(null);

        // Create the CustomerGroup, which fails.

        restCustomerGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerGroup)))
            .andExpect(status().isBadRequest());

        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerGroupRepository.findAll().size();
        // set the field null
        customerGroup.setEnName(null);

        // Create the CustomerGroup, which fails.

        restCustomerGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerGroup)))
            .andExpect(status().isBadRequest());

        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomerGroups() throws Exception {
        // Initialize the database
        customerGroupRepository.saveAndFlush(customerGroup);

        // Get all the customerGroupList
        restCustomerGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME)));
    }

    @Test
    @Transactional
    void getCustomerGroup() throws Exception {
        // Initialize the database
        customerGroupRepository.saveAndFlush(customerGroup);

        // Get the customerGroup
        restCustomerGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, customerGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCustomerGroup() throws Exception {
        // Get the customerGroup
        restCustomerGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomerGroup() throws Exception {
        // Initialize the database
        customerGroupRepository.saveAndFlush(customerGroup);

        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();

        // Update the customerGroup
        CustomerGroup updatedCustomerGroup = customerGroupRepository.findById(customerGroup.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerGroup are not directly saved in db
        em.detach(updatedCustomerGroup);
        updatedCustomerGroup.name(UPDATED_NAME).enName(UPDATED_EN_NAME);

        restCustomerGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCustomerGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCustomerGroup))
            )
            .andExpect(status().isOk());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
        CustomerGroup testCustomerGroup = customerGroupList.get(customerGroupList.size() - 1);
        assertThat(testCustomerGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerGroup.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCustomerGroup() throws Exception {
        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();
        customerGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerGroup.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomerGroup() throws Exception {
        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();
        customerGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomerGroup() throws Exception {
        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();
        customerGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerGroupWithPatch() throws Exception {
        // Initialize the database
        customerGroupRepository.saveAndFlush(customerGroup);

        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();

        // Update the customerGroup using partial update
        CustomerGroup partialUpdatedCustomerGroup = new CustomerGroup();
        partialUpdatedCustomerGroup.setId(customerGroup.getId());

        restCustomerGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerGroup))
            )
            .andExpect(status().isOk());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
        CustomerGroup testCustomerGroup = customerGroupList.get(customerGroupList.size() - 1);
        assertThat(testCustomerGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomerGroup.getEnName()).isEqualTo(DEFAULT_EN_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCustomerGroupWithPatch() throws Exception {
        // Initialize the database
        customerGroupRepository.saveAndFlush(customerGroup);

        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();

        // Update the customerGroup using partial update
        CustomerGroup partialUpdatedCustomerGroup = new CustomerGroup();
        partialUpdatedCustomerGroup.setId(customerGroup.getId());

        partialUpdatedCustomerGroup.name(UPDATED_NAME).enName(UPDATED_EN_NAME);

        restCustomerGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomerGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomerGroup))
            )
            .andExpect(status().isOk());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
        CustomerGroup testCustomerGroup = customerGroupList.get(customerGroupList.size() - 1);
        assertThat(testCustomerGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomerGroup.getEnName()).isEqualTo(UPDATED_EN_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCustomerGroup() throws Exception {
        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();
        customerGroup.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomerGroup() throws Exception {
        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();
        customerGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomerGroup() throws Exception {
        int databaseSizeBeforeUpdate = customerGroupRepository.findAll().size();
        customerGroup.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(customerGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustomerGroup in the database
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomerGroup() throws Exception {
        // Initialize the database
        customerGroupRepository.saveAndFlush(customerGroup);

        int databaseSizeBeforeDelete = customerGroupRepository.findAll().size();

        // Delete the customerGroup
        restCustomerGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, customerGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerGroup> customerGroupList = customerGroupRepository.findAll();
        assertThat(customerGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
