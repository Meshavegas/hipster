import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUsers } from 'app/shared/model/users.model';
import { getEntities as getUsers } from 'app/entities/users/users.reducer';
import { IAccounts } from 'app/shared/model/accounts.model';
import { getEntity, updateEntity, createEntity, reset } from './accounts.reducer';

export const AccountsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.users.entities);
  const accountsEntity = useAppSelector(state => state.accounts.entity);
  const loading = useAppSelector(state => state.accounts.loading);
  const updating = useAppSelector(state => state.accounts.updating);
  const updateSuccess = useAppSelector(state => state.accounts.updateSuccess);

  const handleClose = () => {
    navigate('/accounts');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
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
      ...accountsEntity,
      ...values,
      userId: users.find(it => it.id.toString() === values.userId.toString()),
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
          ...accountsEntity,
          createAt: convertDateTimeFromServer(accountsEntity.createAt),
          updateAt: convertDateTimeFromServer(accountsEntity.updateAt),
          userId: accountsEntity?.userId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myApp.accounts.home.createOrEditLabel" data-cy="AccountsCreateUpdateHeading">
            <Translate contentKey="myApp.accounts.home.createOrEditLabel">Create or edit a Accounts</Translate>
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
                  id="accounts-id"
                  label={translate('myApp.accounts.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myApp.accounts.accountNumber')}
                id="accounts-accountNumber"
                name="accountNumber"
                data-cy="accountNumber"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.accounts.balance')}
                id="accounts-balance"
                name="balance"
                data-cy="balance"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.accounts.currency')}
                id="accounts-currency"
                name="currency"
                data-cy="currency"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.accounts.createAt')}
                id="accounts-createAt"
                name="createAt"
                data-cy="createAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myApp.accounts.updateAt')}
                id="accounts-updateAt"
                name="updateAt"
                data-cy="updateAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('myApp.accounts.createBy')}
                id="accounts-createBy"
                name="createBy"
                data-cy="createBy"
                type="text"
              />
              <ValidatedField
                label={translate('myApp.accounts.updateBy')}
                id="accounts-updateBy"
                name="updateBy"
                data-cy="updateBy"
                type="text"
              />
              <ValidatedField id="accounts-userId" name="userId" data-cy="userId" label={translate('myApp.accounts.userId')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/accounts" replace color="info">
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

export default AccountsUpdate;
