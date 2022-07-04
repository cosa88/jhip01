import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import CustomerGroupService from '@/entities/customer-group/customer-group.service';
import { ICustomerGroup } from '@/shared/model/customer-group.model';

import { ICustomer, Customer } from '@/shared/model/customer.model';
import CustomerService from './customer.service';

const validations: any = {
  customer: {
    name: {
      required,
    },
    enName: {
      required,
    },
    fullName: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class CustomerUpdate extends Vue {
  @Inject('customerService') private customerService: () => CustomerService;
  @Inject('alertService') private alertService: () => AlertService;

  public customer: ICustomer = new Customer();

  @Inject('customerGroupService') private customerGroupService: () => CustomerGroupService;

  public customerGroups: ICustomerGroup[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.customerId) {
        vm.retrieveCustomer(to.params.customerId);
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
    if (this.customer.id) {
      this.customerService()
        .update(this.customer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhip01App.customer.updated', { param: param.id });
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
      this.customerService()
        .create(this.customer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhip01App.customer.created', { param: param.id });
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

  public retrieveCustomer(customerId): void {
    this.customerService()
      .find(customerId)
      .then(res => {
        this.customer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.customerGroupService()
      .retrieve()
      .then(res => {
        this.customerGroups = res.data;
      });
  }
}
