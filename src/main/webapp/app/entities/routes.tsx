import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Users from './users';
import Accounts from './accounts';
import AccountingJournal from './accounting-journal';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="users/*" element={<Users />} />
        <Route path="accounts/*" element={<Accounts />} />
        <Route path="accounting-journal/*" element={<AccountingJournal />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
