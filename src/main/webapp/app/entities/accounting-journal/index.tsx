import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AccountingJournal from './accounting-journal';
import AccountingJournalDetail from './accounting-journal-detail';
import AccountingJournalUpdate from './accounting-journal-update';
import AccountingJournalDeleteDialog from './accounting-journal-delete-dialog';

const AccountingJournalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AccountingJournal />} />
    <Route path="new" element={<AccountingJournalUpdate />} />
    <Route path=":id">
      <Route index element={<AccountingJournalDetail />} />
      <Route path="edit" element={<AccountingJournalUpdate />} />
      <Route path="delete" element={<AccountingJournalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AccountingJournalRoutes;
