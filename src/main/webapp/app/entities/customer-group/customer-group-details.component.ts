import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICustomerGroup } from '@/shared/model/customer-group.model';
import CustomerGroupService from './customer-group.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CustomerGroupDetails extends Vue {
  @Inject('customerGroupService') private customerGroupService: () => CustomerGroupService;
  @Inject('alertService') private alertService: () => AlertService;

  public customerGroup: ICustomerGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.customerGroupId) {
        vm.retrieveCustomerGroup(to.params.customerGroupId);
      }
    });
  }

  public retrieveCustomerGroup(customerGroupId) {
    this.customerGroupService()
      .find(customerGroupId)
      .then(res => {
        this.customerGroup = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
