package cn.btrees.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "en_name", nullable = false)
    private String enName;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    private Set<CustomerGroup> customerGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Customer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return this.enName;
    }

    public Customer enName(String enName) {
        this.setEnName(enName);
        return this;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Customer fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<CustomerGroup> getCustomerGroups() {
        return this.customerGroups;
    }

    public void setCustomerGroups(Set<CustomerGroup> customerGroups) {
        if (this.customerGroups != null) {
            this.customerGroups.forEach(i -> i.setCustomer(null));
        }
        if (customerGroups != null) {
            customerGroups.forEach(i -> i.setCustomer(this));
        }
        this.customerGroups = customerGroups;
    }

    public Customer customerGroups(Set<CustomerGroup> customerGroups) {
        this.setCustomerGroups(customerGroups);
        return this;
    }

    public Customer addCustomerGroup(CustomerGroup customerGroup) {
        this.customerGroups.add(customerGroup);
        customerGroup.setCustomer(this);
        return this;
    }

    public Customer removeCustomerGroup(CustomerGroup customerGroup) {
        this.customerGroups.remove(customerGroup);
        customerGroup.setCustomer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", enName='" + getEnName() + "'" +
            ", fullName='" + getFullName() + "'" +
            "}";
    }
}
