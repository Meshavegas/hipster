import dayjs from 'dayjs';
import { IAccounts } from 'app/shared/model/accounts.model';

export interface IUsers {
  id?: number;
  name?: string | null;
  email?: string | null;
  password?: string | null;
  createAt?: string | null;
  updateAt?: string | null;
  createBy?: string | null;
  updateBy?: string | null;
  accounts?: IAccounts[] | null;
}

export const defaultValue: Readonly<IUsers> = {};
