import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Customer = () => import('@/entities/customer/customer.vue');
const CustomerUpdate = () => import('@/entities/customer/customer-update.vue');
const CustomerDetails = () => import('@/entities/customer/customer-details.vue');

const CustomerGroup = () => import('@/entities/customer-group/customer-group.vue');
const CustomerGroupUpdate = () => import('@/entities/customer-group/customer-group-update.vue');
const CustomerGroupDetails = () => import('@/entities/customer-group/customer-group-details.vue');

const Project = () => import('@/entities/project/project.vue');
const ProjectUpdate = () => import('@/entities/project/project-update.vue');
const ProjectDetails = () => import('@/entities/project/project-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'customer',
      name: 'Customer',
      component: Customer,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/new',
      name: 'CustomerCreate',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/edit',
      name: 'CustomerEdit',
      component: CustomerUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer/:customerId/view',
      name: 'CustomerView',
      component: CustomerDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer-group',
      name: 'CustomerGroup',
      component: CustomerGroup,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer-group/new',
      name: 'CustomerGroupCreate',
      component: CustomerGroupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer-group/:customerGroupId/edit',
      name: 'CustomerGroupEdit',
      component: CustomerGroupUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'customer-group/:customerGroupId/view',
      name: 'CustomerGroupView',
      component: CustomerGroupDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project',
      name: 'Project',
      component: Project,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project/new',
      name: 'ProjectCreate',
      component: ProjectUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project/:projectId/edit',
      name: 'ProjectEdit',
      component: ProjectUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'project/:projectId/view',
      name: 'ProjectView',
      component: ProjectDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
