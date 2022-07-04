import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import CustomerService from '@/entities/customer/customer.service';
import { ICustomer } from '@/shared/model/customer.model';

import { ICustomerGroup, CustomerGroup } from '@/shared/model/customer-group.model';
import CustomerGroupService from './customer-group.service';

const validations: any = {
  customerGroup: {
    name: {
      required,
    },
    enName: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CustomerGroupUpdate extends Vue {
  @Inject('customerGroupService') private customerGroupService: () => CustomerGroupService;
  @Inject('alertService') private alertService: () => AlertService;

  public customerGroup: ICustomerGroup = new CustomerGroup();

  @Inject('customerService') private customerService: () => CustomerService;

  public customers: ICustomer[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.customerGroupId) {
        vm.retrieveCustomerGroup(to.params.customerGroupId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.customerGroup.id) {
      this.customerGroupService()
        .update(this.customerGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhip01App.customerGroup.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.customerGroupService()
        .create(this.customerGroup)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhip01App.customerGroup.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveCustomerGroup(customerGroupId): void {
    this.customerGroupService()
      .find(customerGroupId)
      .then(res => {
        this.customerGroup = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.customerService()
      .retrieve()
      .then(res => {
        this.customers = res.data;
      });
  }
}
