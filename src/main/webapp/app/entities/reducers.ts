import users from 'app/entities/users/users.reducer';
import accounts from 'app/entities/accounts/accounts.reducer';
import accountingJournal from 'app/entities/accounting-journal/accounting-journal.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  users,
  accounts,
  accountingJournal,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
