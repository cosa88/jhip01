import { ICustomerGroup } from '@/shared/model/customer-group.model';

export interface ICustomer {
  id?: number;
  name?: string;
  enName?: string;
  fullName?: string;
  customerGroups?: ICustomerGroup[] | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string,
    public enName?: string,
    public fullName?: string,
    public customerGroups?: ICustomerGroup[] | null
  ) {}
}
