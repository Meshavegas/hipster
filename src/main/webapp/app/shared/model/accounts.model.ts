import dayjs from 'dayjs';
import { IAccountingJournal } from 'app/shared/model/accounting-journal.model';
import { IUsers } from 'app/shared/model/users.model';

export interface IAccounts {
  id?: number;
  accountNumber?: string | null;
  balance?: number | null;
  currency?: string | null;
  createAt?: string | null;
  updateAt?: string | null;
  createBy?: string | null;
  updateBy?: string | null;
  accountingJournals?: IAccountingJournal[] | null;
  userId?: IUsers | null;
}

export const defaultValue: Readonly<IAccounts> = {};
