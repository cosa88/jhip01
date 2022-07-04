import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICustomerGroup } from '@/shared/model/customer-group.model';

import CustomerGroupService from './customer-group.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CustomerGroup extends Vue {
  @Inject('customerGroupService') private customerGroupService: () => CustomerGroupService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public customerGroups: ICustomerGroup[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCustomerGroups();
  }

  public clear(): void {
    this.retrieveAllCustomerGroups();
  }

  public retrieveAllCustomerGroups(): void {
    this.isFetching = true;
    this.customerGroupService()
      .retrieve()
      .then(
        res => {
          this.customerGroups = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ICustomerGroup): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCustomerGroup(): void {
    this.customerGroupService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('jhip01App.customerGroup.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCustomerGroups();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
