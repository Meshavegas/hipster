import dayjs from 'dayjs';
import { IAccounts } from 'app/shared/model/accounts.model';
import { Direction } from 'app/shared/model/enumerations/direction.model';

export interface IAccountingJournal {
  id?: number;
  direction?: Direction | null;
  amount?: number | null;
  balanceBefore?: number | null;
  balanceAfter?: number | null;
  createAt?: string | null;
  updateAt?: string | null;
  createBy?: string | null;
  updateBy?: string | null;
  accountId?: IAccounts | null;
}

export const defaultValue: Readonly<IAccountingJournal> = {};
