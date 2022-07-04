import { ICustomerGroup } from '@/shared/model/customer-group.model';

export interface IProject {
  id?: number;
  name?: string;
  customerGroup?: ICustomerGroup | null;
}

export class Project implements IProject {
  constructor(public id?: number, public name?: string, public customerGroup?: ICustomerGroup | null) {}
}
