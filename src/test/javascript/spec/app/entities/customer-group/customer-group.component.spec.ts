/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import CustomerGroupComponent from '@/entities/customer-group/customer-group.vue';
import CustomerGroupClass from '@/entities/customer-group/customer-group.component';
import CustomerGroupService from '@/entities/customer-group/customer-group.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('CustomerGroup Management Component', () => {
    let wrapper: Wrapper<CustomerGroupClass>;
    let comp: CustomerGroupClass;
    let customerGroupServiceStub: SinonStubbedInstance<CustomerGroupService>;

    beforeEach(() => {
      customerGroupServiceStub = sinon.createStubInstance<CustomerGroupService>(CustomerGroupService);
      customerGroupServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CustomerGroupClass>(CustomerGroupComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          customerGroupService: () => customerGroupServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      customerGroupServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCustomerGroups();
      await comp.$nextTick();

      // THEN
      expect(customerGroupServiceStub.retrieve.called).toBeTruthy();
      expect(comp.customerGroups[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      customerGroupServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(customerGroupServiceStub.retrieve.callCount).toEqual(1);

      comp.removeCustomerGroup();
      await comp.$nextTick();

      // THEN
      expect(customerGroupServiceStub.delete.called).toBeTruthy();
      expect(customerGroupServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
