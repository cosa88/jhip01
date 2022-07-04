import { ICustomer } from '@/shared/model/customer.model';

export interface ICustomerGroup {
  id?: number;
  name?: string;
  enName?: string;
  customer?: ICustomer | null;
}

export class CustomerGroup implements ICustomerGroup {
  constructor(public id?: number, public name?: string, public enName?: string, public customer?: ICustomer | null) {}
}
