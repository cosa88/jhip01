/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CustomerGroupDetailComponent from '@/entities/customer-group/customer-group-details.vue';
import CustomerGroupClass from '@/entities/customer-group/customer-group-details.component';
import CustomerGroupService from '@/entities/customer-group/customer-group.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CustomerGroup Management Detail Component', () => {
    let wrapper: Wrapper<CustomerGroupClass>;
    let comp: CustomerGroupClass;
    let customerGroupServiceStub: SinonStubbedInstance<CustomerGroupService>;

    beforeEach(() => {
      customerGroupServiceStub = sinon.createStubInstance<CustomerGroupService>(CustomerGroupService);

      wrapper = shallowMount<CustomerGroupClass>(CustomerGroupDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { customerGroupService: () => customerGroupServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCustomerGroup = { id: 123 };
        customerGroupServiceStub.find.resolves(foundCustomerGroup);

        // WHEN
        comp.retrieveCustomerGroup(123);
        await comp.$nextTick();

        // THEN
        expect(comp.customerGroup).toBe(foundCustomerGroup);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCustomerGroup = { id: 123 };
        customerGroupServiceStub.find.resolves(foundCustomerGroup);

        // WHEN
        comp.beforeRouteEnter({ params: { customerGroupId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.customerGroup).toBe(foundCustomerGroup);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
