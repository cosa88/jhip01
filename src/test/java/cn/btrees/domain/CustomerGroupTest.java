package cn.btrees.domain;

import static org.assertj.core.api.Assertions.assertThat;

import cn.btrees.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustomerGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerGroup.class);
        CustomerGroup customerGroup1 = new CustomerGroup();
        customerGroup1.setId(1L);
        CustomerGroup customerGroup2 = new CustomerGroup();
        customerGroup2.setId(customerGroup1.getId());
        assertThat(customerGroup1).isEqualTo(customerGroup2);
        customerGroup2.setId(2L);
        assertThat(customerGroup1).isNotEqualTo(customerGroup2);
        customerGroup1.setId(null);
        assertThat(customerGroup1).isNotEqualTo(customerGroup2);
    }
}
