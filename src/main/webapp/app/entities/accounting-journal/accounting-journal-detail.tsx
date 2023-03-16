import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './accounting-journal.reducer';

export const AccountingJournalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const accountingJournalEntity = useAppSelector(state => state.accountingJournal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accountingJournalDetailsHeading">
          <Translate contentKey="myApp.accountingJournal.detail.title">AccountingJournal</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="myApp.accountingJournal.id">Id</Translate>
            </span>
          </dt>
          <dd>{accountingJournalEntity.id}</dd>
          <dt>
            <span id="direction">
              <Translate contentKey="myApp.accountingJournal.direction">Direction</Translate>
            </span>
          </dt>
          <dd>{accountingJournalEntity.direction}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="myApp.accountingJournal.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{accountingJournalEntity.amount}</dd>
          <dt>
            <span id="balanceBefore">
              <Translate contentKey="myApp.accountingJournal.balanceBefore">Balance Before</Translate>
            </span>
          </dt>
          <dd>{accountingJournalEntity.balanceBefore}</dd>
          <dt>
            <span id="balanceAfter">
              <Translate contentKey="myApp.accountingJournal.balanceAfter">Balance After</Translate>
            </span>
          </dt>
          <dd>{accountingJournalEntity.balanceAfter}</dd>
          <dt>
            <span id="createAt">
              <Translate contentKey="myApp.accountingJournal.createAt">Create At</Translate>
            </span>
          </dt>
          <dd>
            {accountingJournalEntity.createAt ? (
              <TextFormat value={accountingJournalEntity.createAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updateAt">
              <Translate contentKey="myApp.accountingJournal.updateAt">Update At</Translate>
            </span>
          </dt>
          <dd>
            {accountingJournalEntity.updateAt ? (
              <TextFormat value={accountingJournalEntity.updateAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createBy">
              <Translate contentKey="myApp.accountingJournal.createBy">Create By</Translate>
            </span>
          </dt>
          <dd>{accountingJournalEntity.createBy}</dd>
          <dt>
            <span id="updateBy">
              <Translate contentKey="myApp.accountingJournal.updateBy">Update By</Translate>
            </span>
          </dt>
          <dd>{accountingJournalEntity.updateBy}</dd>
          <dt>
            <Translate contentKey="myApp.accountingJournal.accountId">Account Id</Translate>
          </dt>
          <dd>{accountingJournalEntity.accountId ? accountingJournalEntity.accountId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/accounting-journal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/accounting-journal/${accountingJournalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccountingJournalDetail;
