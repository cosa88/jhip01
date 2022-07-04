package cn.btrees.repository;

import cn.btrees.domain.CustomerGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomerGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerGroupRepository extends JpaRepository<CustomerGroup, Long> {}
