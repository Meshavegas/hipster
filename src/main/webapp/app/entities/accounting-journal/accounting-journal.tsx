import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAccountingJournal } from 'app/shared/model/accounting-journal.model';
import { getEntities } from './accounting-journal.reducer';

export const AccountingJournal = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const accountingJournalList = useAppSelector(state => state.accountingJournal.entities);
  const loading = useAppSelector(state => state.accountingJournal.loading);
  const totalItems = useAppSelector(state => state.accountingJournal.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="accounting-journal-heading" data-cy="AccountingJournalHeading">
        <Translate contentKey="myApp.accountingJournal.home.title">Accounting Journals</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myApp.accountingJournal.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/accounting-journal/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myApp.accountingJournal.home.createLabel">Create new Accounting Journal</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {accountingJournalList && accountingJournalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="myApp.accountingJournal.id">Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('direction')}>
                  <Translate contentKey="myApp.accountingJournal.direction">Direction</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('amount')}>
                  <Translate contentKey="myApp.accountingJournal.amount">Amount</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('balanceBefore')}>
                  <Translate contentKey="myApp.accountingJournal.balanceBefore">Balance Before</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('balanceAfter')}>
                  <Translate contentKey="myApp.accountingJournal.balanceAfter">Balance After</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createAt')}>
                  <Translate contentKey="myApp.accountingJournal.createAt">Create At</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateAt')}>
                  <Translate contentKey="myApp.accountingJournal.updateAt">Update At</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createBy')}>
                  <Translate contentKey="myApp.accountingJournal.createBy">Create By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updateBy')}>
                  <Translate contentKey="myApp.accountingJournal.updateBy">Update By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="myApp.accountingJournal.accountId">Account Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {accountingJournalList.map((accountingJournal, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/accounting-journal/${accountingJournal.id}`} color="link" size="sm">
                      {accountingJournal.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`myApp.Direction.${accountingJournal.direction}`} />
                  </td>
                  <td>{accountingJournal.amount}</td>
                  <td>{accountingJournal.balanceBefore}</td>
                  <td>{accountingJournal.balanceAfter}</td>
                  <td>
                    {accountingJournal.createAt ? (
                      <TextFormat type="date" value={accountingJournal.createAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {accountingJournal.updateAt ? (
                      <TextFormat type="date" value={accountingJournal.updateAt} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{accountingJournal.createBy}</td>
                  <td>{accountingJournal.updateBy}</td>
                  <td>
                    {accountingJournal.accountId ? (
                      <Link to={`/accounts/${accountingJournal.accountId.id}`}>{accountingJournal.accountId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/accounting-journal/${accountingJournal.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/accounting-journal/${accountingJournal.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/accounting-journal/${accountingJournal.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="myApp.accountingJournal.home.notFound">No Accounting Journals found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={accountingJournalList && accountingJournalList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default AccountingJournal;
