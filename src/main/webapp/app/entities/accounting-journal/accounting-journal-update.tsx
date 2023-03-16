import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAccounts } from 'app/shared/model/accounts.model';
import { getEntities as getAccounts } from 'app/entities/accounts/accounts.reducer';
import { IAccountingJournal } from 'app/shared/model/accounting-journal.model';
import { Direction } from 'app/shared/model/enumerations/direction.model';
import { getEntity, updateEntity, createEntity, reset } from './accounting-journal.reducer';

export const AccountingJournalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const accounts = useAppSelector(state => state.accounts.entities);
  const accountingJournalEntity = useAppSelector(state => state.accountingJournal.entity);
  const loading = useAppSelector(state => state.accountingJournal.loading);
  const updating = useAppSelector(state => state.accountingJournal.updating);
  const updateSuccess = useAppSelector(state => state.accountingJournal.updateSuccess);
  const directionValues = Object.keys(Direction);

  const handleClose = () => {
    navigate('/accounting-journal' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAccounts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createAt = convertDateTimeToServer(values.createAt);
    values.updateAt = convertDateTimeToServer(values.updateAt);

    const entity = {
      ...accountingJournalEntity,
      ...values,
      accountId: accounts.find(it => it.id.toString() === values.accountId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createAt: displayDefaultDateTime(),
          updateAt: displayDefaultDateTime(),
        }
      : {
          direction: 'CREDIT',
          ...accountingJournalEntity,
          createAt: convertDateTimeFromServer(accountingJournalEntity.createAt),
          updateAt: convertDateTimeFromServer(accountingJournalEntity.updateAt),
          accountId: accountingJournalEntity?.accountId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myApp.accountingJournal.home.createOrEditLabel" data-cy="AccountingJournalCreateUpdateHeading">
            <Translate contentKey="myApp.accountingJournal.home.createOrEditLabel">Create or edit a AccountingJournal</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="accounting-journal-id"
                  label={translate('myApp.accountingJournal.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myApp.accountingJournal.direction')}
                id="accounting-journal-direction"
                name="direction"
                data-cy="direction"
                type="select"
              >
                {directionValues.map(direction => (
                  <option value={direction} key={direction}>
                    {translate('myApp.Direction.' + direction)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('myApp.accountingJournal.amount')}
                id="accounting-journal-amount"
                name="amount"
                data-cy="amount"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.accountingJournal.balanceBefore')}
                id="accounting-journal-balanceBefore"
                name="balanceBefore"
                data-cy="balanceBefore"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.accountingJournal.balanceAfter')}
                id="accounting-journal-balanceAfter"
                name="balanceAfter"
                data-cy="balanceAfter"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.accountingJournal.createAt')}
                id="accounting-journal-createAt"
                name="createAt"
                data-cy="createAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myApp.accountingJournal.updateAt')}
                id="accounting-journal-updateAt"
                name="updateAt"
                data-cy="updateAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myApp.accountingJournal.createBy')}
                id="accounting-journal-createBy"
                name="createBy"
                data-cy="createBy"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.accountingJournal.updateBy')}
                id="accounting-journal-updateBy"
                name="updateBy"
                data-cy="updateBy"
                type="text"
              />
              <ValidatedField
                id="accounting-journal-accountId"
                name="accountId"
                data-cy="accountId"
                label={translate('myApp.accountingJournal.accountId')}
                type="select"
              >
                <option value="" key="0" />
                {accounts
                  ? accounts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/accounting-journal" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AccountingJournalUpdate;
