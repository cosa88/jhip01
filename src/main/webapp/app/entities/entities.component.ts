import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import CustomerService from './customer/customer.service';
import CustomerGroupService from './customer-group/customer-group.service';
import ProjectService from './project/project.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('customerService') private customerService = () => new CustomerService();
  @Provide('customerGroupService') private customerGroupService = () => new CustomerGroupService();
  @Provide('projectService') private projectService = () => new ProjectService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
