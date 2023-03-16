import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './accounts.reducer';

export const AccountsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const accountsEntity = useAppSelector(state => state.accounts.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="accountsDetailsHeading">
          <Translate contentKey="myApp.accounts.detail.title">Accounts</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="myApp.accounts.id">Id</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.id}</dd>
          <dt>
            <span id="accountNumber">
              <Translate contentKey="myApp.accounts.accountNumber">Account Number</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.accountNumber}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="myApp.accounts.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.balance}</dd>
          <dt>
            <span id="currency">
              <Translate contentKey="myApp.accounts.currency">Currency</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.currency}</dd>
          <dt>
            <span id="createAt">
              <Translate contentKey="myApp.accounts.createAt">Create At</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.createAt ? <TextFormat value={accountsEntity.createAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateAt">
              <Translate contentKey="myApp.accounts.updateAt">Update At</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.updateAt ? <TextFormat value={accountsEntity.updateAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createBy">
              <Translate contentKey="myApp.accounts.createBy">Create By</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.createBy}</dd>
          <dt>
            <span id="updateBy">
              <Translate contentKey="myApp.accounts.updateBy">Update By</Translate>
            </span>
          </dt>
          <dd>{accountsEntity.updateBy}</dd>
          <dt>
            <Translate contentKey="myApp.accounts.userId">User Id</Translate>
          </dt>
          <dd>{accountsEntity.userId ? accountsEntity.userId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/accounts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/accounts/${accountsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AccountsDetail;
